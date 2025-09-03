package com.yzsoft.yxxt.prepare.service.impl;

import com.yzsoft.yxxt.core.service.YxxtService;
import com.yzsoft.yxxt.prepare.model.Batch;
import com.yzsoft.yxxt.prepare.model.MajorPlan;
import com.yzsoft.yxxt.prepare.model.StudentMajor;
import com.yzsoft.yxxt.prepare.model.StudentMajorConfig;
import com.yzsoft.yxxt.prepare.service.BatchService;
import com.yzsoft.yxxt.prepare.service.MajorPlanService;
import org.beangle.commons.property.PropertyConfigFactory;
import org.beangle.ems.config.model.PropertyConfigItemBean;
import org.beangle.model.persist.EntityDao;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.product.core.model.Major;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.Transient;
import java.util.*;

@Service
public class MajorPlanServiceImpl implements MajorPlanService {

    private static final String SETTING_NUM = "yxxt.student.major.num";

    @Resource
    private EntityDao entityDao;
    @Resource
    private YxxtService yxxtService;
    @Resource
    protected BatchService batchService;
    @Resource
    private PropertyConfigFactory configFactory;

    @Override
    public void allocStudentMajor(List<MajorPlan> majorPlans) {
        allocStudentMajor(null, majorPlans);
    }

    /**
     * 给学生分配专业
     *
     * @param grade      年份
     * @param majorPlans 招生计划
     */
    @Transient
    @Override
    public void allocStudentMajor(String grade, List<MajorPlan> majorPlans) {
        initMajorPlan(grade, majorPlans);
        Integer studentMajorNum = getNum();
        for (int sort = 1; sort <= studentMajorNum; sort++) {
            alloc(grade, sort, majorPlans);
        }
        alloc(grade, majorPlans);
    }

    @Override
    public void init() {
        Batch batch = batchService.getLatestBatch();
        if (batch == null) {
            return;
        }
        String sql = "insert into YXP_STUDENT_MAJORS" +
                "  (id, batch_id, enroll_id, student_id)" +
                "  select SEQ_YXP_STUDENT_MAJORS.nextval, s.batch_id, s.id, s.student_id" +
                "    from yxp_student_enrolls s" +
                "    left join YXP_STUDENT_MAJORS sm" +
                "      on s.student_id = sm.student_id" +
                "   where s.batch_id = ?" +
                "     and s.enrolled = 1" +
                "     and sm.student_id is null";
        entityDao.executeUpdateSql(sql, batch.getId());
    }

    @Override
    public void init(String grade, Long educationId) {
        if (grade == null) {
            grade = yxxtService.getGrade();
        }
//        OqlBuilder query = OqlBuilder.from(StudentPrepare.class, "s");
//        query.where("s.grade = :grade", grade);
//        query.where("s.enrolled = true");
//        if (educationId != null) {
//            query.where("s.student.education.id = :educationId", educationId);
//        }
//        query.where("not exists (select sm.id from "
//                + StudentMajor.class.getName()
//                + " sm where sm.student.id = s.id)");
//        List<StudentPrepare> students = entityDao.search(query);
//        for (StudentPrepare student : students) {
//            StudentMajor studentMajor = new StudentMajor();
//            studentMajor.setGrade(grade);
//            studentMajor.setStudent(student);
//            studentMajor.setUser(entityDao.getEntity(User.class, "name", student.getCode()));
//            entityDao.saveOrUpdate(studentMajor);
//        }
        String sql = "insert into YXP_STUDENT_MAJORS" +
                "  (id, grade, student_id, user_id)" +
                "  select SEQ_YXP_STUDENT_MAJORS.nextval," +
                "         s.grade," +
                "         s.id                           student_id," +
                "         s.user_id                      user_id" +
                "    from cp_students s" +
                "    left join YXP_STUDENT_MAJORS sm" +
                "      on s.id = sm.student_id" +
                "   where s.grade = ?" +
                "     and s.user_id is not null" +
                "     and sm.student_id is null" +
                (educationId == null ? "" : " and s.education_id = " + educationId);
        entityDao.executeUpdateSql(sql, grade);
    }

    @Override
    public Long countStudent() {
        return countStudent(null, null);
    }

    @Override
    public Long countStudent(String grade, Long educationId) {
        OqlBuilder query = OqlBuilder.from(StudentMajor.class);
        query.select("count(*)");
        query.where("batch = :batch", batchService.getLatestBatch());
        if (grade != null) {
            query.where("student.grade = :grade", grade);
        }
        if (educationId != null) {
            query.where("student.education.id = :educationId", educationId);
        }
        return (Long) entityDao.search(query).get(0);
    }

    @Override
    public Long countStudentAlloced(String grade, Long educationId) {
        OqlBuilder query = OqlBuilder.from(StudentMajor.class);
        query.select("count(*)");
        query.where("student.grade = :grade", grade);
        query.where("major is not null");
        if (educationId != null) {
            query.where("student.education.id = :educationId", educationId);
        }
        return (Long) entityDao.search(query).get(0);
    }

    @Override
    public List<Object[]> countStudentByMajor() {
        return countStudentByMajor(null, null);
    }

    /**
     * 统计每个专业学生分配数
     *
     * @param grade       年份
     * @param educationId 学历层次
     * @return
     */
    @Override
    public List<Object[]> countStudentByMajor(String grade, Long educationId) {
        OqlBuilder query = OqlBuilder.from(StudentMajor.class);
        query.select("major.id, count(*)");
//        query.where("student.grade = :grade", year.toString());
        query.where("batch = :batch", batchService.getLatestBatch());
        if (grade != null) {
            query.where("student.grade = :grade", grade);
        }
        query.where("major is not null");
        if (educationId != null) {
            query.where("student.education.id = :educationId", educationId);
        }
        query.groupBy("major.id");
        return entityDao.search(query);
    }

    @Override
    public List<Long[]> countStudentByMajor1() {
        return countStudentByMajor1(null, null);
    }

    @Override
    public List<Long[]> countStudentByMajor1(String grade, Long educationId) {
        OqlBuilder query = OqlBuilder.from(StudentMajor.class, "studentMajor");
//        query.select("major1.id, count(*)");
//        query.where("major1 is not null");
//        query.where("student.grade = :grade", year.toString());
        query.select("detail.major.id, count(*)");
        query.join("studentMajor.details", "detail");
        query.where("detail.sort = :sort", 1);
        query.where("detail.major is not null");
        query.where("studentMajor.batch = :batch", batchService.getLatestBatch());
        if (grade != null) {
            query.where("studentMajor.student.grade = :grade", grade);
        }
        if (educationId != null) {
            query.where("studentMajor.student.education.id = :educationId", educationId);
        }
        query.groupBy("detail.major.id");
        return entityDao.search(query);
    }

    @Override
    public List<Major> findMajor() {
        return findMajor(null);
    }

    @Override
    public List<Major> findMajor(Long educationId) {
        OqlBuilder<Major> query = OqlBuilder.from(Major.class);
        if (educationId != null) {
            query.where("education.id = :educationId", educationId);
        }
        query.orderBy("department, code");
        query.cacheable();
        return entityDao.search(query);
    }

    @Override
    public void clean(String grade, Long educationId) {
        String sql = "update YXP_STUDENT_MAJORS" +
                "   set major_id = null" +
                " where grade = ? and student_id in (" +
                "select s.id from cp_students s where s.education_id = ?" +
                ")";
        entityDao.executeUpdateSql(sql, grade, educationId);
    }

    @Override
    public void publish() {
        Batch batch = batchService.getLatestBatch();
        //更新专业
        String hql = "update CP_STUDENTS s" +
                "   set s.major_id     =" +
                "       (select max(sm.major_id)" +
                "          from YXP_STUDENT_MAJORS sm" +
                "         where sm.student_id = s.id)," +
                "       s.department_id =" +
                "       (select max(m.department_id)" +
                "          from YXP_STUDENT_MAJORS sm" +
                "          join cp_majors m" +
                "            on sm.major_id = m.id" +
                "         where sm.student_id = s.id)" +
                " where exists (select sm.id" +
                "          from YXP_STUDENT_MAJORS sm" +
                "         where sm.student_id = s.id" +
                "           and sm.major_id is not null" +
                "           and sm.batch_id = ?)";
        entityDao.executeUpdateSql(hql, batch.getId());
//        updateDepartmentAndPrepare(grade);
    }

    @Override
    public void publishCancle() {
        Batch batch = batchService.getLatestBatch();
        //更新专业
        String hql = "update CP_STUDENTS s" +
                "   set s.major_id = null, s.department_id = null" +
                " where exists (select sm.id" +
                "          from YXP_STUDENT_MAJORS sm" +
                "         where sm.student_id = s.id" +
                "               and sm.batch_id = ?)";
        entityDao.executeUpdateSql(hql, batch.getId());
    }

    private void updateDepartmentAndPrepare(String grade) {
        //更新学院
        String hql = "update CP_STUDENTS s" +
                "   set s.department_id =" +
                "       (select max(m.department_id) from cp_majors m where m.id = s.major_id)" +
                " where s.major_id is not null" +
                "   and s.grade = ?";
        entityDao.executeUpdateSql(hql, grade);
        //更新预报到学生专业信息
        hql = "update yxp_student_prepares sp" +
                "   set sp.major_id     =" +
                "       (select max(s.major_id) from cp_students s where sp.code = s.code)," +
                "       sp.department_id =" +
                "       (select max(s.department_id)" +
                "          from cp_students s" +
                "         where sp.code = s.code)" +
                " where sp.grade = ?";
        entityDao.executeUpdateSql(hql, grade);
    }

    @Override
    public void allocMajor1() {
        Batch batch = batchService.getLatestBatch();
        //更新专业
        String hql = "update YXP_STUDENT_MAJORS s" +
                "   set s.major_id =" +
                "       (select max(smd.major_id)" +
                "          from YXP_STUDENT_MAJOR_DETAILS smd" +
                "         where smd.student_major_id = s.id" +
                "           and smd.sort = 1)" +
                " where s.major_id is null" +
                "   and s.batch_id = ?";
        entityDao.executeUpdateSql(hql, batch.getId());
    }

    @Override
    public void allocClean() {
        Batch batch = batchService.getLatestBatch();
        //更新专业
        String hql = "update YXP_STUDENT_MAJORS s" +
                "   set s.major_id = null " +
                " where s.batch_id = ?";
        entityDao.executeUpdateSql(hql, batch.getId());
    }

    /**
     * 初始化招生计划
     * 减掉已安排人数
     *
     * @param grade      年份
     * @param majorPlans 招生计划
     */
    private void initMajorPlan(String grade, List<MajorPlan> majorPlans) {
//		List<Major> majors = majorService.findAllMajor();
        List<Object[]> countNum = countStudentByMajor(grade, null);
        for (MajorPlan majorPlan : majorPlans) {
            majorPlan.setRemainder(majorPlan.getTotal());
//			for (Major major : majors) {
//				if (major.equals(majorPlan.getMajor())) {
//					majorPlan.setMajor(major);
//				}
//			}
            for (Object[] oo : countNum) {
                if (majorPlan.getMajor().getId().equals(oo[0])) {
                    majorPlan.setRemainder(majorPlan.getTotal() - ((Long) oo[1]).intValue());
                }
            }
        }
    }

    /**
     * 将志愿外的学生按成绩排序，分配到各专业
     *
     * @param grade
     * @param majorPlans
     */
    private void alloc(String grade, List<MajorPlan> majorPlans) {
        majorPlans = filterRemainder(majorPlans);
        //计划剩余总人数
        Integer remainder = getRemainder(majorPlans);
        if (remainder <= 0) {
            return;
        }
        List<StudentMajor> students = findStudentMajor(grade, null, null, remainder);
        for (int i = 0; i < students.size(); i++) {
            StudentMajor student = students.get(i);
            MajorPlan majorPlan = majorPlans.get(i % majorPlans.size());
            student.setMajor(majorPlan.getMajor());
            student.setWishOrder(StudentMajor.WishOrder.get(null));
            majorPlan.setRemainder(majorPlan.getRemainder() - 1);
            if (majorPlan.getRemainder() <= 0) {
                majorPlans.remove(majorPlan);
            }
        }
        entityDao.saveOrUpdate(students);
    }

    /**
     * 过滤还有名额的专业，并随机排序
     *
     * @param majorPlans
     * @return
     */
    private List<MajorPlan> filterRemainder(List<MajorPlan> majorPlans) {
        List<MajorPlan> temp = new ArrayList<MajorPlan>();
        for (MajorPlan majorPlan : majorPlans) {
            if (majorPlan.getRemainder() > 0) {
                majorPlan.setAlternateNum(majorPlan.getRemainder());
                temp.add(majorPlan);
            }
        }
        final Random random = new Random();
        Collections.sort(temp, new Comparator<MajorPlan>() {
            @Override
            public int compare(MajorPlan o1, MajorPlan o2) {
                return random.nextInt();
            }
        });
        return temp;
    }

    private Integer getRemainder(List<MajorPlan> majorPlans) {
        Integer remainder = 0;
        for (MajorPlan majorPlan : majorPlans) {
            remainder += majorPlan.getRemainder();
        }
        return remainder;
    }

    /**
     * 分配专业
     *
     * @param grade      年份
     * @param sort       志愿排次
     * @param majorPlans 招生计划
     */
    private void alloc(String grade, Integer sort, List<MajorPlan> majorPlans) {
        for (MajorPlan majorPlan : majorPlans) {
            if (majorPlan.getRemainder() <= 0) {
                continue;
            }
            List<StudentMajor> students = findStudentMajor(grade, sort, majorPlan.getMajor(), majorPlan.getRemainder());
            for (StudentMajor student : students) {
                student.setMajor(majorPlan.getMajor());
                student.setWishOrder(StudentMajor.WishOrder.get(sort));
            }
            entityDao.saveOrUpdate(students);
            majorPlan.setRemainder(majorPlan.getRemainder() - students.size());
        }
    }

    /**
     * 根据志愿排次查询未分配专业学生
     *
     * @param grade
     * @param sort
     * @param major
     * @param remainder
     * @return
     */
    private List<StudentMajor> findStudentMajor(String grade, Integer sort, Major major, Integer remainder) {
        if (remainder < 1) {
            return new ArrayList<StudentMajor>();
        }
        OqlBuilder query = OqlBuilder.from(StudentMajor.class, "studentMajor");
        query.where("studentMajor.batch = :batch", batchService.getLatestBatch());
        if (grade != null) {
            query.where("studentMajor.student.grade = :grade", grade);
        }
        query.where("studentMajor.major is null");
        if (sort != null) {
            query.join("studentMajor.details", "detail");
            query.where("detail.sort = :sort", sort);
            query.where("detail.major = :major", major);
        }
        query.orderBy("studentMajor.score");
        query.limit(1, remainder);
        return entityDao.search(query);
    }


    @Override
    public Integer getNum() {
        return getConfig().getNum();
    }

    @Override
    public StudentMajorConfig getConfig() {
        OqlBuilder query = OqlBuilder.from(StudentMajorConfig.class);
        query.cacheable();
        List<StudentMajorConfig> configs = entityDao.search(query);
        if (configs.isEmpty()) {
            StudentMajorConfig config = new StudentMajorConfig();
            return config;
        }
        return configs.get(0);
    }

    @Override
    public void wish() {
        Batch batch = batchService.getLatestBatch();
        //更新专业
        String hql = "update CP_STUDENTS s" +
                "   set s.major_id     =" +
                "       (select max(sm.major_id)" +
                "          from YXP_STUDENT_MAJORS sm" +
                "         where sm.student_id = s.id)," +
                "       s.department_id =" +
                "       (select max(m.department_id)" +
                "          from YXP_STUDENT_MAJORS sm" +
                "          join cp_majors m" +
                "            on sm.major_id = m.id" +
                "         where sm.student_id = s.id)" +
                " where s.major_id is null and exists (select sm.id" +
                "          from YXP_STUDENT_MAJORS sm" +
                "         where sm.student_id = s.id" +
                "           and sm.major_id is not null" +
                "           and sm.batch_id = ?)";
        entityDao.executeUpdateSql(hql, batch.getId());
    }
}
