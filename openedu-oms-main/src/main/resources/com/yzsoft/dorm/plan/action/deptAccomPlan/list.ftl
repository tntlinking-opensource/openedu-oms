[#ftl]
[@b.head/]
<script type="text/javascript" src="${base}/static/dorm/scripts/dorm.js"></script>
<style>
.modal-dialog {width: 1024px;}
.modal-content {width:1024px; }
</style>

[#if dormPlan??]
[@b.toolbar title="${(dormPlan.name)!}--院系住宿计划"]bar.addBack();[/@]
[/#if]
[@b.grid  items=deptAccomPlans var="deptAccomPlan"]
	[@b.gridbar]
	[#if isAdmin]
	    bar.addItem("${b.text("action.new")}",action.add());
	    bar.addItem("${b.text("action.modify")}",action.edit());
	    bar.addItem("${b.text("action.delete")}",action.remove());
		[#if dormPlan??]bar.addItem("导入",action.method("importForm","","&dormPlan.id=${(dormPlan.id)!}"));[/#if]
		bar.addItem("导出1",action.exportData("department.code:院（系）代码,department.name:院（系）名称,dormPlan.year:年份,gender:寝室面向性别,"
					+"planNum:计划人数,realNum:实际人数,allotNum:安排床位数,stdNum:已安排学生,remark:备注",null,"&fileName=院（系）住宿计划信息"));
		bar.addItem("确认",action.multi("confirm", "是否将选中的计划标记为确认？"),'fa-check','blue');
    	bar.addItem("取消确认",action.multi("confirmCancle", "是否将选中的计划标记为未确认？"),'fa-remove','red');
	[/#if]
		bar.addItem("住宿学生",action.single("dormStudentList"),null,'blue');
		//bar.addItem("手工分配",action.openNew("index", "${b.url('/dorm/plan/dorm-plan-bed-alloc')}"),"fa-edit","green");
		bar.addItem("自动分配",action.single("alloc"),'fa-edit','yellow');
		bar.addItem("发布",action.single("publish", "发布后将不能修改床位，是否继续？"),'fa-edit','green');
	[/@]
	[@b.row]
		[@b.boxcol/]
		[@b.col property="dormPlan.year" title="学年度" align="center"/]
		[@b.col property="department.code" title="院（系）代码" align="center"]
			[@b.a href="!info?deptAccomPlan.id=${(deptAccomPlan.id)!}"]${(deptAccomPlan.department.code)!}[/@]
		[/@]
		[@b.col  property="department.name" title="院（系）名称" align="center"/]
		[@b.col property="gender" title="寝室面向性别" align="center"]
			${(deptAccomPlan.gender)!}
		[/@]
		[@b.col property="planNum" title="计划人数" align="center"]
			${(deptAccomPlan.planNum)!}
		[/@]
		[@b.col title="实际人数" align="center"]
			[@b.a href="!dormStudentList?deptAccomPlan.id=${(deptAccomPlan.id)!}"]${(deptAccomPlan.realNum)!}[/@]
		[/@]
		[@b.col property="allotNum" title="安排床位数" align="center"]
			[@b.a href="!bedList?dormPlanBed.deptAccomPlan.id=${(deptAccomPlan.id)!}&planName=${(deptAccomPlan.department.name)!}-${(deptAccomPlan.gender)!}"]${(deptAccomPlan.allotNum)!}[/@]
		[/@]
		[@b.col property="stdNum" title="已安排学生" align="center"]
			[@b.a href="!dormPlanBedStdList?deptAccomPlan.id=${(deptAccomPlan.id)!}"]${(deptAccomPlan.stdNum)!}[/@]
		[/@]
		[@b.col  title="安排" align="center"]
			[#if !deptAccomPlan.confirm]
				<a href="javascript:void(0)" class="btn btn-sm blue" onclick="dormArrange(${(deptAccomPlan.id)!});return false;">床位安排</a>
			[/#if]
		[/@]
		[@b.col title="是否确认" property="confirm" align="center"][@c.boolean deptAccomPlan.confirm "已确认" "未确认"/][/@]
        [@b.col title="是否发布" property="finish" align="center"][@c.boolean deptAccomPlan.finish "已发布" "未发布"/][/@]
	[/@]
[/@]
[@b.form name="actionForm" id="actionForm" target="dormPlanList"]
[/@]
[@b.foot/]
