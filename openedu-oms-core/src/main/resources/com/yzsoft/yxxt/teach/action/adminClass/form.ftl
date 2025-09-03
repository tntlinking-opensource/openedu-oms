[#ftl]
[@b.head/]
[@b.toolbar title="班级管理" entityId=adminClass.id!0]bar.addBack();[/@]
[@b.form action="!save" title="班级管理信息" theme="list"]
	[@b.textfield label="班级代码" name="adminClass.code" value="${adminClass.code!}" required="true" maxlength="100" /]
	[@b.textfield label="班级名称" name="adminClass.name" value="${adminClass.name!}" required="true" maxlength="100" /]
	[@b.textfield label="年级" name="adminClass.grade" value="${adminClass.grade!}" required="true" maxlength="100" /]
	[@b.select label="学院" required="true" id="departmentFormId" name="adminClass.department.id" value=(adminClass.department.id)!  items=departments?sort_by("code") option="id,name" /]
	[@b.select label="所属专业" required="true" id="majorFormId" name="adminClass.major.id" value=(adminClass.major.id)! items=majors option="id,name" /]
    [@b.field label="辅导员"]
		<select id="instructorIds" name="instructorIds" class="select2 form-control" multiple data-placeholder="请选择辅导员">
			[#list (adminClass.instructors)! as instructor]
				<option value='${instructor.id}' selected>${instructor.name}(${instructor.code})</option>
			[/#list]
		</select>
	[/@]
    [@b.radios label="是否可用"  name="adminClass.enabled" value=adminClass.enabled items="1:是,0:否"/]	
	[@b.formfoot]
		[@b.reset/]&nbsp;&nbsp;
		[@b.submit value="action.submit" /]
		[@b.redirectParams/]
		<input type="hidden" name="adminClass.id" value="${adminClass.id!}" />
	[/@]
[/@]
[@b.foot/]
<script type="text/javascript" src="${base}/static/scripts/product/cascadeMajor.js"></script>
<script type="text/JavaScript">
$(function () {
   $('#instructorIds').select2Remote({ 
       url:'${b.url('/core/teacher-search!findByLike')}'
   });
});
</script>