[#ftl]
[@b.head/]
[@b.grid  items=clothesStudentSizes var="clothesStudentSize"]
    [@b.gridbar]
    bar.addItem("导入",action.method("importForm"));
    bar.addItem("导出",action.exportData("student.student.grade:年级,student.student.code:学号,student.student.name:姓名,student.student.major.name:专业,student.student.adminClass.name:班级,type.name:服装类型,size.name:尺码,student.remark:备注", null,"&fileName=clothes"));
    bar.addItem("统计",action.method("count"));
    [/@]
    [@b.row align="center"]
        [@b.boxcol width="1%"/]
        [@b.col title="年级" property="student.student.grade" /]
        [@b.col title="学号" property="student.student.code" /]
        [@b.col title="姓名" property="student.student.name" /]
        [@b.col title="服装类型" property="type.name" /]
        [@b.col title="尺码" property="size.name" /]
        [@b.col title="备注" property="student.remark"  width="20%"/]
    [/@]
[/@]
[@b.foot/]