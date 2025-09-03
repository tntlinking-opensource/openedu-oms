[#ftl]
[#import "/template/mobile.ftl" as m/]

[@m.body title="统计查询" back=b.url("/mobile/home?t=1") cache="true"]
	<style>
		.loading:after { content: "数据加载中..."; text-align: center; display: block; background-color: #fff; padding: 15px;}
	</style>
	<div class="zdblock">
		<h3 class="zdblock_title-2">和我同名的学生数量统计：</h3>
	</div>
	<div class="loading" data-method="name"></div>
	<div class="zdblock">
		<h3 class="zdblock_title-2">学校重名最高的前5个名字：</h3>
	</div>
	<div class="loading" data-method="name5"></div>
	[#if (student.department)??]
		<div class="zdblock">
			<h3 class="zdblock_title-2">和我同一个学院的学生统计：</h3>
		</div>
		<div class="loading" data-method="department"></div>
	[/#if]
	[#if (student.major)??]
		<div class="zdblock">
			<h3 class="zdblock_title-2">和我同一个专业的学生统计：</h3>
		</div>
		<div class="loading" data-method="major"></div>
	[/#if]
	<div class="zdblock">
		<h3 class="zdblock_title-2">和我同一天生日学生统计：</h3>
	</div>
	<div class="loading" data-method="birthday"></div>
	<div class="zdblock">
		<h3 class="zdblock_title-2">和我同一个星座的数量统计：</h3>
	</div>
	<div class="loading" data-method="constellation"></div>
	[#if (student.info.nation)??]
		<div class="zdblock">
			<h3 class="zdblock_title-2">和我同一个民族的数量统计：（${(student.info.nation.name)!}）</h3>
		</div>
		<div class="loading" data-method="nation"></div>
	[/#if]
	<div class="zdblock">
		<h3 class="zdblock_title-2">学校同一个星座最多的数量统计：<span class="constellation_span"></span></h3>
	</div>
	<div class="loading" data-method="constellationTop"></div>
	[#if batch??]
	<div class="zdblock">
		<h3 class="zdblock_title-2">我报到的顺序排名：</h3>
	</div>
	<div class="loading" data-method="enrollRank"></div>
	[/#if]
	<script>
		$(function () {
			$(".loading").each(function () {
				var method = $(this).data("method");
				$(this).load('${base}/mobile/statistics/wap-statistics.action?method=' + method, afterLoaded);
			});

			function afterLoaded() {
				$(this).removeClass("loading");
			}
		});
	</script>
[/@]