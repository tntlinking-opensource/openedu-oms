[#ftl]
[#include "macro.ftl"/]
<div id="lineChart" style="height:500px;"></div>
<script type="text/javascript">
	$(function () {
		// 基于准备好的dom，初始化echarts实例
		var myChart = echarts.init(document.getElementById('lineChart'));
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
					data: [[#list years as year]${getData(item, year)}, [/#list]]
				},
				[/#list]
			]
		};
		// 使用刚指定的配置项和数据显示图表。
		myChart.setOption(option);
	});
</script>