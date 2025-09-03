[#ftl]
[@b.head/]
[@b.form name="majorInfoSearchForm"  action="!search" target="majorInfoList" title="ui.searchForm" theme="search"]
    [@b.select label="院系" name="majorInfo.major.department.id" iclass="s_department cascade"/]
    [@b.textfield label="专业名称" name="majorInfo.major.name"/]
[/@]
[@b.div id="majorInfoList" href="!search" /]
<script type="text/javascript" src="${base}/static/yxxt/scripts/major.js"></script>
<script>
	$(function () {
		$(".s_department").loadDepartment();
	});
</script>
[@b.foot/]