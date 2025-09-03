[#ftl]
[@b.head/]
<script type="text/javascript" src="${base}/static/dorm/scripts/dorm.js"></script>
<style>
.modal-dialog {width: 1024px;}
.modal-content {width:1024px; }
</style>
[@b.grid  items=classAccomPlans var="classAccomPlan"]
	[@b.gridbar]
		[#if isAdmin]
		    bar.addItem("${b.text("action.new")}",action.add());
		    bar.addItem("${b.text("action.modify")}",action.edit());
		    bar.addItem("${b.text("action.delete")}",action.remove());
			[#if dormPlan??]bar.addItem("导入",action.method("importForm","","&dormPlan.id=${(dormPlan.id)!}"));[/#if]
			bar.addItem("导出",action.exportData("adminClass.major.department.code:院（系）代码,adminClass.major.department.name:院（系）名称,adminClass.major.code:专业代码,adminClass.major.name:专业名称,adminClass.code:班级代码,adminClass.name:班级名称,dormPlan.year:年份,gender:男女生,"
						+"planNum:计划人数,realNum:实际人数,allotNum:安排床位数,stdNum:已安排学生,remark:备注",null,"&fileName=班级住宿计划信息"));
		[/#if]
			bar.addItem("住宿学生",action.single("dormStudentList"),null,'blue');
			bar.addItem("确认",action.multi("confirm", "是否将选中的计划标记为确认？"),'fa-check','blue');
    		bar.addItem("取消确认",action.multi("confirmCancle", "是否将选中的计划标记为未确认？"),'fa-remove','red');
	[/@]
	[@b.row]
		[@b.boxcol/]
		[@b.col  property="dormPlan.year" title="年份" align="center"/]
		[@b.col property="adminClass.code" title="班级代码" align="center"]
			[@b.a href="!info?classAccomPlan.id=${(classAccomPlan.id)!}"]${(classAccomPlan.adminClass.code)!}[/@]
		[/@]
		[@b.col property="adminClass.name" title="班级名称" align="center"/]
		[@b.col property="adminClass.department.name" title="院系" align="center"/]
		[@b.col property="adminClass.major.name" title="专业" align="center"/]
		[@b.col property="gender" title="性别" align="center"/]
		[@b.col property="planNum" title="计划人数" align="center"]
			${(classAccomPlan.planNum)!}
		[/@]
		[@b.col title="实际人数" align="center"]
			[@b.a href="!dormStudentList?classAccomPlan.id=${(classAccomPlan.id)!}"]${(classAccomPlan.realNum)!}[/@]
		[/@]
		[@b.col property="allotNum" title="安排床位数" align="center"]
			[@b.a href="!bedList?dormPlanBed.classAccomPlan.id=${(classAccomPlan.id)!}&planName=${(classAccomPlan.adminClass.name)!}-${(classAccomPlan.gender)!}"]${(classAccomPlan.allotNum)!}[/@]
		[/@]
		[@b.col property="stdNum" title="已安排学生" align="center"]
			[@b.a href="!dormPlanBedStdList?classAccomPlan.id=${(classAccomPlan.id)!}&planName=${(classAccomPlan.adminClass.name)!}-${(classAccomPlan.gender)!}"]${(classAccomPlan.stdNum)!}[/@]
		[/@]
		[@b.col  title="安排" align="center"]
			[#if !classAccomPlan.confirm]
				<a href="javascript:void(0)" class="btn btn-sm blue" onclick="classAccomPlanDormArrange(${(classAccomPlan.id)!});return false;">床位安排</a>
			[/#if]
		[/@]
		[@b.col title="是否确认" property="confirm" align="center"][@c.boolean classAccomPlan.confirm "已确认" "未确认"/][/@]
        [@b.col title="是否完成" property="deptAccomPlan.finish" align="center"][@c.boolean classAccomPlan.deptAccomPlan.finish "已完成" "未完成"/][/@]
	[/@]
[/@]
[@b.form name="actionForm" id="actionForm" target="classAccomPlanList"]
[/@]
[@b.foot/]
