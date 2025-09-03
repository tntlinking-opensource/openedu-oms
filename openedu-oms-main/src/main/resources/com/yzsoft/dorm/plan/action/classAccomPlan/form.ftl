[#ftl]
[@b.head/]
[@c.chosenCssAndJs/]
<div class="edit_div">
[@b.toolbar title="班级住宿计划" entityId=classAccomPlan.id!0]bar.addBack();[/@]
[@b.form action="!save" title="班级住宿计划信息" theme="form" target="classAccomPlanList"]
		[@b.select label="新生住宿计划" name="classAccomPlan.dormPlan.id" value=(classAccomPlan.deptAccomPlan.dormPlan.id)!0 items=dormPlans  required="true"/]
		[@b.select label="院系" id="departmentFormId" name="classAccomPlan.deptAccomPlan.department.id" value=(classAccomPlan.deptAccomPlan.department.id)! items=departments required="true"/]
		[@b.select label="专业" id="majorFormId" name="major.id" value=(classAccomPlan.adminClass.major.id)! items=majorList/]
		[@b.select label="班级" id="adminClassFormId" name="classAccomPlan.adminClass.id" value=(classAccomPlan.adminClass.id)! items=adminClasses  required="true"/]
		[@b.select label="寝室面向性别" required="true" name="classAccomPlan.gender"  value="${(classAccomPlan.gender)!}" items={'男':'男','女':'女'} /]
		[@b.textfield label="计划人数" id="planNum" name="classAccomPlan.planNum" value="${classAccomPlan.planNum!}" required="true" maxlength="4" check="match('unsigned')"/]
		[@b.textarea label="备注" name="classAccomPlan.remark" value="${classAccomPlan.remark!}" rows="8" cols="15" maxlength="300" /]
	[@b.formfoot]
		[@b.reset/]&nbsp;&nbsp;
		[@b.submit value="action.submit"/]
		[@b.redirectParams/]
		<input type="hidden" name="classAccomPlan.id" value="${classAccomPlan.id!}" />
	[/@]
[/@]
</div>
[@b.foot/]
<script type="text/javascript" src="${base}/static/scripts/product/cascadeMajor.js"></script>
<script src="${base}/static/dorm/scripts/cascadeAdminClass.js"></script>