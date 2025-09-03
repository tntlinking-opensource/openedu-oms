[#ftl]
[@b.head/]
[@b.toolbar title="班级管理" entityId=adminClassTemp.id!0]bar.addBack();[/@]
[@b.form action="!save" title="班级管理信息" theme="list"]
	[@b.textfield label="班级代码" name="adminClassTemp.code" value="${adminClassTemp.code!}" required="true" maxlength="100" /]
	[@b.textfield label="班级名称" name="adminClassTemp.name" value="${adminClassTemp.name!}" required="true" maxlength="100" /]
	[@b.textfield label="年级" name="adminClassTemp.grade" value="${adminClassTemp.grade!}" required="true" maxlength="100" /]
	[@b.select label="所属专业" required="true" id="majorFormId" name="adminClassTemp.major.id" value=(adminClassTemp.major.id)! items=majors option="id,name" /]
	[@b.select label="招生类型" required="true" name="adminClassTemp.enrollType.id" value=(adminClassTemp.enrollType.id)! items=enrollTypes option="id,name" /]
	[@b.textfield label="分数线" name="adminClassTemp.score" value="${(adminClassTemp.score)!}" maxlength="10" iclass="v_score_10"/]
    [@b.textfield label="班级人数" name="adminClassTemp.num" value="${(adminClassTemp.num)!}" maxlength="10" check="match('integer')"/]
    [@b.field label="辅导员"]
		<select id="instructorIds" name="adminClassTemp.instructor.id" class="select2 form-control" data-placeholder="请选择辅导员">
			[#if adminClassTemp.instructor??]
				<option value='${(adminClassTemp.instructor.id)!}' selected>${(adminClassTemp.instructor.name)!}(${(adminClassTemp.instructor.code)!})</option>
			[/#if]
		</select>
	[/@]
	[@b.formfoot]
		[@b.reset/]&nbsp;&nbsp;
		[@b.submit value="action.submit" /]
		[@b.redirectParams/]
		<input type="hidden" name="adminClassTemp.id" value="${adminClassTemp.id!}" />
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