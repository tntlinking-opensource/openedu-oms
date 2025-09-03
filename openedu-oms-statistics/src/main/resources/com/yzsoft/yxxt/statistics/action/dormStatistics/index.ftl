[#ftl]
[@c.echartsJs/]
<style>
	.tabbable-custom {overflow: visible;}
</style>
<div class="tabbable-custom">
	<ul class="nav nav-tabs stu_info_manager_info" role="tablist">
		<li role="presentation">
			[@b.a href="!student" target="DepartmentStudentDiv"]住宿情况统计[/@b.a]
		</li>
		<li role="presentation">
			[@b.a href="!room" target="DepartmentStudentDiv"]寝室使用率统计[/@b.a]
		</li>
		<li role="nation">
			[@b.a href="!bed" target="DepartmentStudentDiv"]床位利用率统计[/@b.a]
		</li>
	</ul>
	<script>
		$(".stu_info_manager_info li").click(function () {
			$(this).addClass("active").siblings().removeClass("active");
		}).eq(0).find("a").trigger("click");
	</script>
	<div class="tab-content">
		[@b.div id="DepartmentStudentDiv"/]
	</div>
</div>