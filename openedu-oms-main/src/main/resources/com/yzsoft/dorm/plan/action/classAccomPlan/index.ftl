[#ftl]
[@b.head/]
	[@b.form action="!search" title="ui.searchForm" target="classAccomPlanList" theme="search"]
		[@b.select label="学年度" name="classAccomPlan.dormPlan.year" items=years value=(curYear.year)! options="year,year" iclass="year_select"/]
		[@b.select label="住宿计划" name="classAccomPlan.dormPlan.id" value=(dormPlan.id)! items=dormPlans iclass="dormPlan_select"/]
		[@b.select id="departmentId" name="classAccomPlan.adminClass.major.department.id" label="院（系）" items=departments option="id,name"/]
		[@b.select id="majorId" name="classAccomPlan.adminClass.major.id" label="专业" items=majorList option="id,name"/]
		[@b.select id="adminClassId" name="classAccomPlan.adminClass.id" label="班级" items=adminClasses option="id,name"/]
		[@b.select name="classAccomPlan.gender" label="寝室面向性别" items={'男':'男','女':'女'}/]
	[/@]
	[@b.div href="!search?classAccomPlan.dormPlan.year=${(curYear.year)!}&orderby=classAccomPlan.department.code,classAccomPlan.gender" id="classAccomPlanList" class="classAccomPlanList"/]
	<script src="${base}/static/dorm/scripts/select.js"></script>
	<script>
	    $(function () {
	        $(".year_select").dormCascadeDormPlanByParames(".dormPlan_select",'');
	    });
	</script>
	<script type="text/javascript" src="${base}/static/scripts/product/cascadeMajor.js"></script>
	<script src="${base}/static/dorm/scripts/cascadeAdminClass.js"></script>
[@b.foot/]
