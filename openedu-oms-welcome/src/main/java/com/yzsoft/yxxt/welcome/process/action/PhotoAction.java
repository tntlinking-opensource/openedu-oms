package com.yzsoft.yxxt.welcome.process.action;

import org.beangle.product.core.model.Student;
import org.beangle.website.common.model.UploadFile;
import org.beangle.website.common.service.UploadFileService;
import org.beangle.website.common.util.ImageSizer;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.File;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PhotoAction extends ProcessLinkActionSupport {

    @Resource
    private UploadFileService uploadFileService;

    @Override
    protected void editSetting(Long batchId, Student student) {

    }

    @Override
    protected void save(Long batchId, Student student) {
        super.save(batchId, student);
        File file = getFile("file");
        if (file != null) {
            String fileName = get("fileFileName");
            ImageSizer.saveImg(file.getAbsolutePath(), file.getAbsolutePath(), 200, 400);
            UploadFile uploadFile = uploadFileService.saveFile(file, fileName, "student/photo", false);
            uploadFileService.remove(student.getPhoto());
            student.setPhoto(uploadFile.getUuid());
            entityDao.saveOrUpdate(student);
        }
    }
}
