[#ftl]
[@b.head/]
[@b.form name="collectSwitchStateViewSearchForm"  action="!search" target="collectSwitchStateViewList" title="ui.searchForm" theme="search"]
    [@b.textfield label="年级" name="collectSwitchStateView.student.grade" value=grade/]
    [@b.textfield label="学号" name="collectSwitchStateView.student.code"/]
    [@b.textfield label="姓名" name="collectSwitchStateView.student.name"/]
    [@b.select label="采集项目" name="collectSwitchStateView.collectSwitch.id" items=collectSwitchs/]
    [@b.select label="是否填写" name="collected" items={'1':'是','0':'否'}/]
[/@]
[@b.div id="collectSwitchStateViewList" href="!search?collectSwitchStateView.student.grade=${grade}" /]
<script src="${base}/static/yxxt/scripts/major.js"></script>
<script>
	$(function () {
		$(".s_education").loadEducation();
		$(".s_campus").loadCampus();
		$(".s_department").loadDepartment().cascadeMajor(".s_major");
	});
</script>
[@b.foot/]