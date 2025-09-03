[#ftl]
[@b.head/]
[@b.grid  items=clothesStudents var="clothesStudent"]
    [@b.gridbar]
    //bar.addItem("新建",action.add());
    //bar.addItem("修改",action.edit());
    //bar.addItem("删除",action.remove());
    bar.addItem("统计",action.method("count"));
    [/@]
    [@b.row]
        [@b.boxcol/]
        [@b.col title="学号" property="student.code"]
            [@b.a href="!info?id=${clothesStudent.id}"]${(clothesStudent.student.code)!}[/@]
        [/@]
        [@b.col title="姓名" property="student.name" /]
        [@b.col title="学院" property="student.department.name"/]
        [@b.col title="专业" property="student.major.name"/]
        [@b.col title="班级" property="student.adminClass.name"/]
        [@b.col title="服装尺码" property="clothesSize"/]
        [@b.col title="鞋子尺码" property="shoesSize"/]
    [/@]
[/@]
[@b.foot/]