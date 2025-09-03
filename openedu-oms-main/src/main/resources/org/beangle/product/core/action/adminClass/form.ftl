[#ftl]
[@b.head/]
[@b.toolbar title="班级管理" entityId=adminClass.id!0]bar.addBack();[/@]
[@b.form action="!save" title="班级管理信息" theme="list"]
	[@b.textfield label="班级代码" name="adminClass.code" value="${adminClass.code!}" required="true" maxlength="100" /]
	[@b.textfield label="班级名称" name="adminClass.name" value="${adminClass.name!}" required="true" maxlength="100" /]
	[@b.textfield label="年级" name="adminClass.grade" value="${adminClass.grade!}" required="true" maxlength="100" /]
	[@b.select label="学院" required="true" iclass="e_department" name="adminClass.department.id" value=(adminClass.department.id)! items=departs?sort_by("code") option="id,name" /]
	[@b.select label="所属专业" required="true" iclass="e_major" name="adminClass.major.id" value=(adminClass.major.id)! items=[] option="id,name" /]
    [@b.checkboxes label="招生类型" required="true" name="enrollTypeId" value=adminClass.enrolls items=enrollTypes/]
    [@b.field label="辅导员"]
		<select id="instructorIds" name="instructorIds" class="select2 form-control" multiple data-placeholder="请选择辅导员">
			[#list (adminClass.instructors)! as instructor]
				<option value='${instructor.id}' selected>${instructor.name}(${instructor.code})</option>
			[/#list]
		</select>
	[/@]
    [@b.textfield label="班级人数" name="adminClass.num" value="${(adminClass.num)!}" required="true" maxlength="3" check="match('integer')"/]
    [@b.radios label="是否可用"  name="adminClass.enabled" value=adminClass.enabled items="1:是,0:否"/]	
	[@b.formfoot]
		[@b.reset/]&nbsp;&nbsp;
		[@b.submit value="action.submit" /]
		[@b.redirectParams/]
		<input type="hidden" name="adminClass.id" value="${adminClass.id!}" />
	[/@]
[/@]
[@b.foot/]
<script src="${base}/static/yxxt/scripts/major.js"></script>
<script type="text/JavaScript">
$(function () {
   $('#enrollIds').select2Remote({ 
       url:'${b.url('/dictdata/district!findByLike')}'
   });
   
   $('#instructorIds').select2Remote({ 
       url:'${b.url('/core/teacher-search!findByLike')}'
   });
   $(".e_department").loadDepartment().cascadeMajor(".e_major");
});
</script>