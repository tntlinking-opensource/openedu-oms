package com.yzsoft.yxxt.prepare.action;

import com.yzsoft.yxxt.core.service.YxxtService;
import com.yzsoft.yxxt.prepare.model.StudentEnroll;
import com.yzsoft.yxxt.prepare.service.BatchService;
import org.beangle.product.core.service.StudentService;
import org.beangle.struts2.action.EntityDrivenAction;
import org.beangle.website.system.service.DictDataService;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;

public class StudentActionParent extends EntityDrivenAction {

    @Resource
    protected YxxtService yxxtService;
    @Resource
    protected DictDataService dictDataService;
    @Resource
    protected StudentService studentService;
    @Resource
    protected BatchService batchService;


    @Override
    protected String getEntityName() {
        return StudentEnroll.class.getName();
    }

    @Override
    public String index() {
        put("grade", yxxtService.getGrade());
        put("batchs", batchService.findBatch());
        return super.index();
    }

    @Override
    protected void indexSetting() {
        super.indexSetting();
        put("genders", dictDataService.findDictData(StudentService.GENDER));
        put("enrollTypes", studentService.findEnrollType());
        put("educations", dictDataService.findDictData(StudentService.XLCC));
        put("enrollSourceTypes", dictDataService.findDictData("STUDENT_ENROLL_SOURCE_TYPE"));
    }

    @Override
    protected String getDefaultOrderString() {
        return "studentEnroll.code";
    }

    /**
     * 年份   学号	姓名	身份证号	性别
     * 出生年月	毕业学校	省份	地级市	区县
     * 父亲电话	母亲电话	学生手机	通讯电话	通讯地址	通讯地址邮编	邮箱
     * 语文	数学	英语	物理	化学	生物
     * 政治	地理	历史	体育	艺术	加分
     * 院系	专业	专业方向	学制
     *
     * @return
     */
    @Override
    protected Map<String, String> getPropMap() {
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("年级", "student.grade");
        map.put("学号", "student.code");
        map.put("姓名", "student.name");
        map.put("身份证号", "student.cardcode");
        map.put("性别", "student.gender.name");
        map.put("出生日期", "student.birthday");
        //map.put("院系代码", "student.department.code");
        map.put("院系名称", "student.department.name");
        map.put("毕业学校", "student.graduateSchool");
        map.put("省份", "student.enrollProvince.name");
        map.put("地级市", "student.enrollCity.name");
        map.put("区县", "student.enrollCounty.name");
        map.put("父亲电话", "contact.fatherPhone");
        map.put("母亲电话", "contact.motherPhone");
        map.put("学生手机", "student.phone");
        map.put("通讯电话", "student.telephone");
        map.put("通讯地址", "contact.address");
        map.put("通讯地址邮编", "contact.zipcode");
        map.put("邮箱", "contact.email");
        map.put("总分", "scoreTotal");
        map.put("语文", "score.chinese");
        map.put("数学", "score.math");
        map.put("英语", "score.english");
        map.put("物理", "score.physics");
        map.put("化学", "score.chemistry");
        map.put("生物", "score.biology");
        map.put("政治", "score.politics");
        map.put("地理", "score.geography");
        map.put("历史", "score.history");
        map.put("体育", "score.sports");
        map.put("艺术", "score.art");
        map.put("加分", "score.plus");
        map.put("招生类别", "student.enrollType.name");
        map.put("生源性质", "student.enrollSourceType.name");
        map.put("学历层次", "student.education.name");
        map.put("院系", "student.department.name");
        map.put("专业", "student.major.name");
        map.put("专业方向", "student.direction.name");
        map.put("学制", "student.duration");
        return map;
    }
}
