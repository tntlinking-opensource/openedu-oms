package com.yzsoft.yxxt.core.action;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;
import org.beangle.ems.security.model.GroupBean;
import org.beangle.ems.security.service.UserService;
import org.beangle.model.Entity;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.product.core.model.Department;
import org.beangle.product.core.model.Student;
import org.beangle.product.core.model.Teacher;
import org.beangle.product.core.service.TeacherService;
import org.beangle.product.restriction.help.RestrictionHelper;
import org.beangle.struts2.action.EntityDrivenAction;
import org.beangle.website.common.action.BaseCommonAction;
import org.beangle.website.common.model.UploadFile;
import org.beangle.website.common.service.UploadFileService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

import com.yzsoft.yxxt.prepare.model.StudentEnroll;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StudentPhotoAction extends BaseCommonAction {

    @Resource
    private UploadFileService uploadFileService;
    @Resource
    protected RestrictionHelper restrictionHelper;
    @Resource
    protected TeacherService teacherService;
    @Resource
    protected UserService userService;

    @Override
    protected String getEntityName() {
        return Student.class.getName();
    }

    @Override
    protected OqlBuilder<?> getQueryBuilder() {
        OqlBuilder query = (OqlBuilder) super.getQueryBuilder();
        query.where("inSchooled = true");
        if (getDeparts().size() == 1) {
            query.where("student.department in (:depts)", getDeparts());
        }
        Boolean hasPhoto = getBoolean("hasPhoto");
        if (hasPhoto != null) {
            if (hasPhoto) {
                query.where("photo is not null");
            } else {
                query.where("photo is null");
            }
        }
        return query;
    }

    protected List<Department> getDeparts() {
        List<Department> departments = restrictionHelper.getDeparts();
        if(departments.isEmpty() || userService.isMember(getCurrentUser(), new GroupBean(3079L))) {
            Teacher teacher = teacherService.getTeacherByCode(getUsername());
            departments.clear();
            if(null!=teacher&&null!=teacher.getDepartment()) {
                departments.add(teacher.getDepartment());
            }
        }
        return departments;
    }

    @Override
    protected String saveAndForward(Entity<?> entity) {
        String uuid = get("uuid");
        String oldUuid = get("oldUuid");
        uuid = uploadFileService.update(uuid, oldUuid);
        Student student = (Student) entity;
        StudentEnroll enroll = entityDao.getEntity(StudentEnroll.class, "student", student);
        if(enroll != null){
            enroll.setPhoto(uuid);
            entityDao.saveOrUpdate(enroll);
        }
        student.setPhoto(uuid);
        return super.saveAndForward(student);
    }

    @Override
    public String importData() {
        File photos = getFile("importFile");
        ZipFile zipFile = null;
        InputStream inputStream = null;
        FileOutputStream fileOut = null;
        Integer total = 0;
        Integer successNum = 0;
        Integer failNum = 0;
        List<String> errorMessages = new ArrayList<String>();
        File tempFile = new File(System.getProperty("java.io.tmpdir") + "/temp_" + UUID.randomUUID().toString() + ".data");
        try {
            zipFile = new ZipFile(photos);
            for (Enumeration entries = zipFile.getEntries(); entries.hasMoreElements(); ) {
                try {
                    ZipEntry entry = (ZipEntry) entries.nextElement();
                    if (entry.isDirectory()) {
                        continue;
                    }
                    String entryName = entry.getName();
                    if (entryName.indexOf("/") > 0) {
                        entryName = StringUtils.substringAfterLast(entryName, "/");
                    }
                    String studentCode = null;
                    if (entryName.indexOf("_") > 0) {
                        studentCode = StringUtils.substringBefore(entryName, "_");
                    } else {
                        studentCode = StringUtils.substringBefore(entryName, ".");
                    }
                    Student student = entityDao.getEntity(Student.class, "letterCode", studentCode);
                    Assert.notNull(student, "考生号：" + studentCode + "，不存在！");
                    StudentEnroll enroll = null;
                    if (student != null) {
                    	enroll = entityDao.getEntity(StudentEnroll.class, "student", student);
                    	if(enroll == null){
							enroll = new StudentEnroll();
							enroll.setStudent(student);
							enroll.setUser(student.getUser());
						}
                        inputStream = zipFile.getInputStream(entry);
                        fileOut = new FileOutputStream(tempFile);
                        IOUtils.copy(inputStream, fileOut);
                        IOUtils.closeQuietly(fileOut);
                        IOUtils.closeQuietly(inputStream);
                        UploadFile uploadFile = uploadFileService.saveFile(tempFile, entryName, "student/photo", false);
                        uploadFileService.remove(enroll.getPhoto());
                        enroll.setPhoto(uploadFile.getUuid());
                        student.setPhoto(uploadFile.getUuid());
                    }
                    entityDao.saveOrUpdate(student);
                    successNum++;
                } catch (Exception e) {
                    failNum++;
                    errorMessages.add(e.getMessage());
                }
            }
            zipFile.close();
        } catch (IOException e) {
        } finally {
            try {
                zipFile.close();
            } catch (IOException e) {
            }
        }
        put("total", total);
        put("successNum", successNum);
        put("failNum", failNum);
        put("errorMessages", errorMessages);
        return forward();
    }

    @Override
    public String export() throws Exception {
        File file = new File(System.getProperty("java.io.tmpdir") + "/temp_" + UUID.randomUUID().toString() + ".zip");
        ZipOutputStream zos = null;
        FileInputStream fis = null;
        try {
            String userAgent = ServletActionContext.getRequest().getHeader("User-Agent");
            HttpServletResponse response = getResponse();
            OqlBuilder<Student> query = (OqlBuilder<Student>) getExportQueryBuilder();
            query.where("photo is not null");
            List<Student> students = entityDao.search(query);
            zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
            FileInputStream fin = null;
            for (Student student : students) {
                File photo = uploadFileService.getFile(student.getPhoto());
                if (photo.exists()) {
                    String fileName = student.getName() + ".jpg";
                    fileName = new String(fileName.getBytes("UTF-8"), "UTF-8");
                    fin = new FileInputStream(photo);
                    zos.putNextEntry(new ZipEntry(fileName));
                    zos.setEncoding("gbk");
                    IOUtils.copy(fin, zos);
                    IOUtils.closeQuietly(fin);
                }
            }
            IOUtils.closeQuietly(zos);
            //下载
            String fileName = "photos.zip";
            response.setContentType("application/x-msdownload");
            if (userAgent.indexOf("Safari") < 0) {
                response.setHeader("Content-disposition", "attachment; filename*=utf-8''" + URLEncoder.encode(fileName, "UTF-8") + "");
            } else {
                fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
                response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
            }
            OutputStream out = null;
            fis = new FileInputStream(file);
            out = getResponse().getOutputStream();
            IOUtils.copy(fis, out);
            out.flush();
            IOUtils.closeQuietly(out);
        } catch (Exception e) {
        } finally {
            IOUtils.closeQuietly(zos);
            IOUtils.closeQuietly(fis);
            file.delete();
        }
        return null;
    }
}
