[#ftl]
[#include "macro.ftl"/]
<h3 class="text-center">历年申请绿色通道学生趋势图</h3>
<div id="greenStudentApplyHistory" style="height:500px;"></div>
<script type="text/javascript">
	$(function () {
		// 基于准备好的dom，初始化echarts实例
		var myChart = echarts.init(document.getElementById('applyItemNumHistory'));
		// 指定图表的配置项和数据
		var option = {
			tooltip: {
				trigger: 'axis'
			},
			legend: {
				data: [[#list items as v]'${v}', [/#list]]
			},
			grid: {
				left: '3%',
				right: '4%',
				bottom: '3%',
				containLabel: true
			},
			toolbox: {
				feature: {
					saveAsImage: {}
				}
			},
			xAxis: {
				type: 'category',
				boundaryGap: false,
				data: [[#list years as v]'${v}', [/#list]]
			},
			yAxis: {
				type: 'value'
			},
			series: [
				[#list items as item]
				{
					name: '${item}',
					type: 'line',
					stack: '总量',
					data: [[#list years as year]${getData(item, year)}, [/#list]]
				},
				[/#list]
			]
		};
		// 使用刚指定的配置项和数据显示图表。
		myChart.setOption(option);
	});
</script>