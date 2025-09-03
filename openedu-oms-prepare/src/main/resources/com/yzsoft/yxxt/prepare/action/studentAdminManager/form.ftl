[#ftl]
[@b.head/]
[@b.toolbar title="学生信息" entityId=student.id!0]bar.addBack();[/@]
[@b.form action="!save" title="学生信息" theme="form"]
	[@b.classConfig configs=formConfig]
		[@b.static label="年级"]${(student.grade)!}[/@b.static]
		[@b.select label="院系" name="student.department.id" value=(student.department.id)! items=departments class="s_department"/]
		[@b.select label="专业" name="student.major.id" value=(student.major.id)! items=majors class="s_major"/]
		[@b.static label="班级"]${(student.adminClass.name)!}[/@b.static]
		[@b.static label="学号"]${(student.code)!}[/@b.static]
		[@b.static label="姓名"]${(student.name)!}[/@b.static]
		[@b.static label="性别"]${(student.gender.name)!}[/@b.static]
		[@b.textfield label="备注" name="student.remark" value="${student.remark!}" maxlength="100" /]
	[/@]
	[@b.formfoot]
		[@b.redirectParams/]
		[@b.submit value="action.submit" /]
		<input type="hidden" name="student.id" value="${student.id!}"/>
	[/@]
[/@]

<script src="${base}/static/yxxt/scripts/major.js"></script>
<script>
	$(function () {
		$(".s_department").loadDepartment().cascadeMajor(".s_major");
	});
</script>
[@b.foot/]