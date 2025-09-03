package com.yzsoft.yxxt.mobile.statistics.action;

import com.yzsoft.yxxt.core.service.YxxtService;
import com.yzsoft.yxxt.process.service.YxxtProcessService;
import com.yzsoft.yxxt.statistics.service.YxStatisticsService;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.process.model.ProcessBatch;
import org.beangle.product.core.model.Student;
import org.beangle.struts2.action.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WapStatisticsAction extends BaseAction {

    @Autowired
    private YxxtService yxxtService;
    @Autowired
    private YxStatisticsService yxStatisticsService;
    @Autowired
    private YxxtProcessService yxxtProcessService;

    public String index() {
        Student student = yxxtService.getStudent();
		ProcessBatch batch = yxxtProcessService.getBatch(student);
        put("student", student);
        put("batch", batch);
        return forward();
    }

    /**
     * 和我同名的学生数量统计
     *
     * @return
     */
    public String name() {
        Student student = yxxtService.getStudent();
        put("name", student.getName());
        put("datas", yxStatisticsService.countName(student));
        put("hasFindStudent", true);
        return forward();
    }

    /**
     * 学校重名最高的前5个名字
     *
     * @return
     */
    public String name5() {
        Student student = yxxtService.getStudent();
        put("datas", yxStatisticsService.countName5(student));
        return forward();
    }

    /**
     * 和我同一个学院的学生统计
     *
     * @return
     */
    public String department() {
        Student student = yxxtService.getStudent();
        put("department", student.getDepartment().getName());
        put("datas", yxStatisticsService.countGenderByDepartment(student));
        put("hasFindStudent", true);
        return forward();
    }

    /**
     * 和我同一个专业的学生统计
     *
     * @return
     */
    public String major() {
        Student student = yxxtService.getStudent();
        put("major", student.getMajor().getName());
        put("datas", yxStatisticsService.countGenderByMajor(student));
        put("hasFindStudent", true);
        return forward();
    }

    /**
     * 和我同一天生日学生统计
     *
     * @return
     */
    public String birthday() {
        Student student = yxxtService.getStudent();
        put("birthday", student.getCardcode().substring(10, 14));
        put("datas", yxStatisticsService.countBirthday(student));
        put("hasFindStudent", true);
        return forward();
    }

    /**
     * 和我同一个星座的数量统计
     *
     * @return
     */
    public String constellation() {
        Student student = yxxtService.getStudent();
        put("datas", yxStatisticsService.countConstellation(student));
        return forward();
    }

    /**
     * 和我同一个民族的数量统计
     *
     * @return
     */
    public String nation() {
        Student student = yxxtService.getStudent();
        put("datas", yxStatisticsService.countNation(student));
        return forward();
    }

    /**
     * 学校同一个星座最多的数量统计
     *
     * @return
     */
    public String constellationTop() {
        Student student = yxxtService.getStudent();
        String constellation = yxStatisticsService.getConstellationTop(student);
        put("datas", yxStatisticsService.countConstellationTop(student, constellation));
        put("constellation", constellation);
        return forward();
    }

    /**
     * 我的报道顺序排名
     *
     * @return
     */
    public String enrollRank() {
        Student student = yxxtService.getStudent();
        ProcessBatch batch = yxxtProcessService.getBatch(student);
        if (batch != null) {
            Long studentNum = yxxtProcessService.getStudentNum(batch);
            put("rank", yxStatisticsService.countEnrollRank(student, batch));
            put("studentNum", studentNum);
        }
        return forward();
    }

    public String findStudent() {
        put("gender", get("gender"));
        put("name", get("name"));
        put("department", get("department"));
        put("major", get("major"));
        put("birthday", get("birthday"));
        return forward();
    }

    public String findStudentByName() {
        String gender = get("gender");
        PageLimit pageLimit = getPageLimit();
        List<Student> students = yxStatisticsService.findStudentByName(gender, pageLimit);
        put("students", students);
        return forward("students");
    }

    public String findStudentByDepartment() {
        String gender = get("gender");
        String department = get("department");
        PageLimit pageLimit = getPageLimit();
        List<Student> students = yxStatisticsService.findStudentByDepartment(gender, department, pageLimit);
        put("students", students);
        return forward("students");
    }

    public String findStudentByMajor() {
        String gender = get("gender");
        String major = get("major");
        PageLimit pageLimit = getPageLimit();
        List<Student> students = yxStatisticsService.findStudentByMajor(gender, major, pageLimit);
        put("students", students);
        return forward("students");
    }

    public String findStudentByBirthday() {
        String gender = get("gender");
        String birthday = get("birthday");
        PageLimit pageLimit = getPageLimit();
        List<Student> students = yxStatisticsService.findStudentByBirthday(gender, birthday, pageLimit);
        put("students", students);
        return forward("students");
    }
}
