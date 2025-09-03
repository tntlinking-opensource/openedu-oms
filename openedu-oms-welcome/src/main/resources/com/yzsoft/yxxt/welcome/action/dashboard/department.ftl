[#ftl]
<div id="majorTop5" style="height: 210px;"></div>
<script>
	$(function () {
		var colors = [
			['#658aa7', '#128ae7'],
			['#58bcbc', '#00aaaa'],
			['#d0a295', '#e96f4f'],
			['#fcce10', '#e87c25'],
			['#d6ca97', '#fcce10'],
			['#d7df8a', '#b5c334'],
			['#eb7676', '#f51b1b'],
		];
		for (var i in colors) {
			var color = {
				type: 'linear',
				x: 0,
				y: 0,
				x2: 1,
				y2: 0,
				colorStops: [{
					offset: 0, color: colors[i][0] // 0% 处的颜色
				}, {
					offset: 1, color: colors[i][1] // 100% 处的颜色
				}],
			};
			colors[i] = color;
		}
		// 基于准备好的dom，初始化echarts实例
		var myChart = echarts.init(document.getElementById('majorTop5'));
		// 指定图表的配置项和数据
		var option = {
			color: colors,
			grid: {
				left: '30%',
				top: '10px',
				bottom: '30px',
			},
			xAxis: {
				type: 'value',
				axisLabel: {
					color: '#fff'
				},
				axisLine: {
					lineStyle: {
						color: '#fff'
					},
				},
			},
			yAxis: {
				type: 'category',
				axisLabel: {
					color: '#fff'
				},
				axisLine: {
					lineStyle: {
						color: '#fff'
					},
				},
				data: [[#list datas as v]"${v.name!}", [/#list]]
			},
			series: [{
				type: 'bar',
				data: [[#list datas as v]${v.completeNum}, [/#list]],
				label: {
					normal: {
						show: true,
						position: 'insideRight',
						offset: [-10, 0]
					}
				},
				itemStyle: {
					normal: {
						color: function (params) {
							var num = colors.length;
							return colors[params.dataIndex % num]
						},
					}
				},
			},],
		};
		myChart.setOption(option);
	});
</script>