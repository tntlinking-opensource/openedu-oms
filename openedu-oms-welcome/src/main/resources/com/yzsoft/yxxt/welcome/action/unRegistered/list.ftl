    [#ftl]
[@b.head/]
[@b.grid  items=welcomeStudentViews var="welcomeStudentView"]
    [@b.gridbar]
    bar.addItem("修改未报到原因",action.edit());
    bar.addItem("标记为“已报到”", registered(action, 1), "fa-check",  "green-haze");
    bar.addItem("标记为“未报到”", registered(action, 0), "fa-close",  "red");
    bar.addItem("导出", action.exportDataProperty("student.code:学号,student.name:姓名,student.gender.name:性别,student.department.name:院系,student.major.name:专业,student.adminClass.name:班级,student.phone:手机号码,reason.name:未报到原因",null, "fileName=未报到学生"));
    [/@]
<script>
	function registered(action, registered) {
		return action.multi("registered", null, "registered=" + registered);
	}
</script>
    [@b.row align="center"]
        [@b.boxcol/]
        [@b.col property="student.department.name" title="院系" /]
        [@b.col property="student.major.name" title="专业" /]
        [@b.col property="student.adminClass.name" title="班级" /]
        [@b.col property="student.code" title="学号" /]
        [@b.col property="student.name" title="姓名" /]
        [@b.col property="student.gender.name" title="性别" /]
        [@b.col property="student.phone" title="手机号码" /]
        [@b.col property="registered" title="是否已报到"][@c.boolean (welcomeStudentView.registered)!false/][/@]
        [@b.col property="remark" title="未报到原因" /]
    [/@]
[/@]
[@b.foot/]