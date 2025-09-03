[#ftl]
[@c.echartsJs/]
<div class="tabbable-custom">
	<ul class="nav nav-tabs stu_info_manager_info" role="tablist">
		<li role="presentation">
			[@b.a href="!applyNum" target="StudentManagerInfoDiv"]申请人数统计[/@b.a]
		</li>
		<li role="presentation">
			[@b.a href="!applyItemNum" target="StudentManagerInfoDiv"]申请项目统计[/@b.a]
		</li>
		<li role="presentation">
			[@b.a href="!enabledNum" target="StudentManagerInfoDiv"]通过率统计[/@b.a]
		</li>
		<li role="presentation">
			[@b.a href="!enabledItemNum" target="StudentManagerInfoDiv"]通过项目统计[/@b.a]
		</li>
		<li role="presentation">
			[@b.a href="/web/collect/finance-green-std-manager" target="StudentManagerInfoDiv"]明细查询[/@b.a]
		</li>
	</ul>
	<script>
		$(".stu_info_manager_info li").click(function () {
			$(this).addClass("active").siblings().removeClass("active");
		}).eq(0).find("a").trigger("click");
	</script>
	<div class="tab-content">
		[@b.div id="StudentManagerInfoDiv"/]
	</div>
</div>