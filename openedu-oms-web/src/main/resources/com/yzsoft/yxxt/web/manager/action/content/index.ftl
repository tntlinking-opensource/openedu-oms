[#ftl]
[@b.head/]
[@b.form name="contentSearchForm"  action="!search" target="contentlist" title="ui.searchForm" theme="search"]
    [@b.select label="一级栏目" name="column1" class="s_column1"/]
    [@b.select label="二级栏目" name="column2" class="s_column2"/]
    [@b.textfields names="content.title;名称"/]
    [@b.select name="content.enabled" label="是否发布" value="" empty="..." items={'1':'是','0':'否'}/]
[/@]
[@b.div id="contentlist" href="!search" /]
<script src="${base}/static/yxxt/scripts/column.js"></script>
<script>
	$(function () {
		$.columnSearch(".s_column1", ".s_column2");
	});
</script>
[@b.foot/]