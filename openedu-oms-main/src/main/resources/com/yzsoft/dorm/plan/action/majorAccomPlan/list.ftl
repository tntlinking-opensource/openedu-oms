[#ftl]
[@b.head/]
<script type="text/javascript" src="${base}/static/dorm/scripts/dorm.js"></script>
<style>
.modal-dialog {width: 1024px;}
.modal-content {width:1024px; }
</style>
[@b.grid  items=majorAccomPlans var="majorAccomPlan"]
	[@b.gridbar]
		[#if isAdmin]
		    bar.addItem("${b.text("action.new")}",action.add());
		    bar.addItem("${b.text("action.modify")}",action.edit());
		    bar.addItem("${b.text("action.delete")}",action.remove());
			[#if dormPlan??]bar.addItem("导入",action.method("importForm","","&dormPlan.id=${(dormPlan.id)!}"));[/#if]
			bar.addItem("导出",action.exportData("major.department.code:院（系）代码,major.department.name:院（系）名称,major.code:专业代码,major.name:专业名称,dormPlan.year:年份,gender:寝室面向性别,"
								+"planNum:计划人数,realNum:实际人数,allotNum:安排床位数,stdNum:已安排学生,remark:备注",null,"&fileName=专业住宿计划信息"));
		[/#if]
			bar.addItem("住宿学生",action.single("dormStudentList"),null,'blue');
			bar.addItem("确认",action.multi("confirm", "是否将选中的计划标记为确认？"),'fa-check','blue');
    		bar.addItem("取消确认",action.multi("confirmCancle", "是否将选中的计划标记为未确认？"),'fa-remove','red');
	[/@]
	[@b.row]
		[@b.boxcol/]
		[@b.col property="dormPlan.year" title="年份" align="center"/]
		[@b.col property="major.code" title="专业代码" align="center"]
			[@b.a href="!info?majorAccomPlan.id=${(majorAccomPlan.id)!}"]${(majorAccomPlan.major.code)!}[/@]
		[/@]
		[@b.col property="major.name" title="专业名称" align="center"/]
		[@b.col property="major.department.code" title="院（系）代码" align="center"/]
		[@b.col property="major.department.name" title="院（系）名称" align="center"/]
		[@b.col property="gender" title="性别" align="center"/]
		[@b.col property="planNum" title="计划人数" align="center"]
			${(majorAccomPlan.planNum)!}
		[/@]
		[@b.col title="实际人数" align="center"]
			[@b.a href="!dormStudentList?majorAccomPlan.id=${(majorAccomPlan.id)!}&planName=${(majorAccomPlan.major.name)!}-${(majorAccomPlan.gender)!}"]${(majorAccomPlan.realNum)!}[/@]
		[/@]
		[@b.col property="allotNum" title="安排床位数" align="center"]
			[@b.a href="!bedList?dormPlanBed.majorAccomPlan.id=${(majorAccomPlan.id)!}&planName=${(majorAccomPlan.major.name)!}-${(majorAccomPlan.gender)!}"]${(majorAccomPlan.allotNum)!}[/@]
		[/@]
		[@b.col property="stdNum" title="已安排学生" align="center"]
			[@b.a href="!dormPlanBedStdList?dormPlanBed.majorAccomPlan.id=${(majorAccomPlan.id)!}&planName=${(majorAccomPlan.major.name)!}-${(majorAccomPlan.gender)!}"]${(majorAccomPlan.stdNum)!}[/@]
		[/@]
		[@b.col  title="安排" align="center"]
			[#if !majorAccomPlan.confirm]
				<a href="javascript:void(0)" class="btn btn-sm blue" onclick="majorAccomPlanDormArrange(${(majorAccomPlan.id)!});return false;">床位安排</a>
			[/#if]
		[/@]
		[@b.col title="是否确认" property="confirm" align="center"][@c.boolean majorAccomPlan.confirm "已确认" "未确认"/][/@]
        [@b.col title="是否发布" property="deptAccomPlan.finish" align="center"][@c.boolean majorAccomPlan.deptAccomPlan.finish "已发布" "未发布"/][/@]
	[/@]
[/@]
[@b.form name="actionForm" id="actionForm" target="majorAccomPlanList"]
[/@]
[@b.foot/]
