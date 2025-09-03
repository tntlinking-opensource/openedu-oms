[#ftl]
<h3 class="text-center">当前年份通过申请的各个绿色通道项目与总人数对比图</h3>
<div id="applyItemNumOfAll" style="height:500px;"></div>
<script type="text/javascript">
	$(function () {
		// 基于准备好的dom，初始化echarts实例
		var myChart = echarts.init(document.getElementById('applyItemNumOfAll'));
		// 指定图表的配置项和数据
		var option = {
			toolbox: {
				show: true,
				feature: {
					saveAsImage: {show: true}
				}
			},
			calculable: true,
			legend: {
				data: [[#list datas as v][#if v_index gt 0], [/#if]"${v[0]}"[/#list]]
			},
			tooltip: {
				formatter: "{b} : {c} ({d}%)",
			},
			series: [
				{
					type: 'pie',
					radius: '55%',
					center: ['50%', '60%'],
					label: {
						normal: {
							formatter: "{b} : {c} ({d}%)",
						}
					},
					data: [[#list datas as v][#if v_index gt 0], [/#if] {
						name: '${v[0]}',
						value: ${v[1]}
					}[/#list]],
				}
			]
		};
		// 使用刚指定的配置项和数据显示图表。
		myChart.setOption(option);
	});
</script>