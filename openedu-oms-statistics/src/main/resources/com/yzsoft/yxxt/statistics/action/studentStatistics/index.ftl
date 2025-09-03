[#ftl]
[@c.echartsJs/]
<style>
	.tabbable-custom {overflow: visible;}
</style>
<div class="tabbable-custom">
	<ul class="nav nav-tabs stu_info_manager_info" role="tablist">
		<li role="presentation">
			[@b.a href="!department" target="DepartmentStudentDiv"]院系统计[/@b.a]
		</li>
		<li role="presentation">
			[@b.a href="!major" target="DepartmentStudentDiv"]专业统计[/@b.a]
		</li>
		<li role="nation">
			[@b.a href="!nation" target="DepartmentStudentDiv"]民族统计[/@b.a]
		</li>
		<li role="presentation">
			[@b.a href="!city" target="DepartmentStudentDiv"]生源地统计[/@b.a]
		</li>
		<li role="presentation">
			[@b.a href="!gender" target="DepartmentStudentDiv"]性别统计[/@b.a]
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