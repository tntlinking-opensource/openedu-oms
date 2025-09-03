[#ftl]
[@b.head/]
[@c.chosenCssAndJs/]
<div class="edit_div">
[@b.toolbar title="专业住宿计划" entityId=majorAccomPlan.id!0]bar.addBack();[/@]
[@b.form action="!save" title="专业住宿计划信息" theme="list"]
		[@b.select label="新生住宿计划" name="majorAccomPlan.dormPlan.id" value=(majorAccomPlan.deptAccomPlan.dormPlan.id)!0 items=dormPlans  required="true"/]
		[@b.select label="院系" id="departmentFormId" name="department.id" value=(majorAccomPlan.major.department.id)!0 items=departments iclass="major_select select" required="true"/]
		[@b.select label="专业" id="majorFormId" name="majorAccomPlan.major.id" value=(majorAccomPlan.major.id)!0 items=majorList iclass="major_select select" required="true"/]
		[@b.select label="寝室面向性别" required="true" name="majorAccomPlan.gender"  value="${(majorAccomPlan.gender)!}" items={'男':'男','女':'女'} /]
		[@b.textfield label="计划人数" id="planNum" name="majorAccomPlan.planNum" value="${majorAccomPlan.planNum!}" required="true" maxlength="4" check="match('unsigned')"/]
		[@b.textarea label="备注" name="majorAccomPlan.remark" value="${majorAccomPlan.remark!}" rows="8" cols="15" maxlength="300" /]
	[@b.formfoot]
		[@b.reset/]&nbsp;&nbsp;
		[@b.submit value="action.submit"/]
		[@b.redirectParams/]
		<input type="hidden" name="majorAccomPlan.id" value="${majorAccomPlan.id!}" />
		<input type="hidden" name="majorAccomPlan.allotNum" value="${(majorAccomPlan.allotNum)!}" />
	[/@]
[/@]
</div>
[@b.foot/]
<script type="text/javascript" src="${base}/static/scripts/product/cascadeMajor.js"></script>
