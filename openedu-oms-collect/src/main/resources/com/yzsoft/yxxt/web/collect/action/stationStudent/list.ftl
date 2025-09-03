[#ftl]
[@b.head/]
[@b.grid  items=stationStudents var="stationStudent"]
    [@b.gridbar]
    //bar.addItem("新建",action.add());
    //bar.addItem("修改",action.edit());
    //bar.addItem("删除",action.remove());
    bar.addItem("统计",action.method("nav"));
    [/@]
    [@b.row align="center"]
        [@b.boxcol/]
        [@b.col title="学号" property="student.code"]
            [@b.a href="!info?id=${stationStudent.id}"]${(stationStudent.student.code)!}[/@]
        [/@]
        [@b.col title="姓名" property="student.name" /]
        [@b.col title="学院" property="student.department.name"/]
        [@b.col title="专业" property="student.major.name"/]
        [@b.col title="班级" property="student.adminClass.name"/]
        [@b.col title="是否按时报到" property="intime" align="center"]
            [@c.boolean stationStudent.intime/]
        [/@]
    [/@]
[/@]
[@b.foot/]