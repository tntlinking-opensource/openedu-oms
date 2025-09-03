[#ftl]
[@b.head/]
[@b.grid  items=studentClasss var="studentClass"]
    [@b.gridbar]
    window.stuentAction = action;
    [#if teacher??]
    bar.addItem("分班",action.method("alloc"), "fa-edit", "blue");
    bar.addItem("修改",action.edit());
    [#--bar.addItem("编学号",action.method("generateCode"));--]
    [#--bar.addItem("删除",action.remove());--]
    bar.addItem("导入",action.method("importForm"));
    bar.addItem("发布",action.method("publish"));
    [/#if]
    bar.addItem("导出",action.exportDataProperty("学号,姓名,院系代码,院系名称,专业代码,专业名称,班级代码,班级名称",null, "fileName=学生班级信息"));
    [/@]
    [@b.row align="center"]
        [@b.boxcol width="1%"/]
        [@b.col property="student.department.name" title="院系" /]
        [@b.col property="student.major.name" title="专业" /]
        [@b.col property="adminClass.name" title="班级" /]
        [@b.col property="student.code" title="学号" /]
        [@b.col property="student.name" title="姓名" /]
        [@b.col property="student.gender.name" title="性别" /]
        [#--[@b.col property="student.campus.name" title="校区" /]--]
    [/@]
[/@]
[@b.foot/]