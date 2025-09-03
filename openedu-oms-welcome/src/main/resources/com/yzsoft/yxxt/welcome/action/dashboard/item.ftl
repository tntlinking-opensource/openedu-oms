[#ftl]
<style>
	.item_li .list-cell-1 {line-height: 40px;}
	.item_li .cell-other {margin: 0;}
	.item_li .list-cell-2 {line-height: 20px;}
	.item_li .list-cell-3 {line-height: 40px;}
	.item_percent {position: absolute; color: #44D1C9; top: 0px; text-align: center; width: 100%;}
	.over_hide { overflow: hidden; white-space: nowrap; text-overflow: ellipsis; }
	.item_percent_0 {color: #4BE3D9}
	.item_percent_1 {color: #b0c7fb}
	.item_percent_2 {color: #87eb8a}
	.item_percent_3 {color: #c8bb7b}
	.item_percent_4 {color: #41baf1}
	.item_percent_5 {color: #d67878}
	.item_percent_6 {color: #fdab37}
</style>
<ul>
	[#list datas as v]
		[#assign percent = v.num / studentNum/]
		<li class="item_li">
			<div class="list-cell-1">
				<p class="cell-other over_hide" style=""><span>${v.itemName}</span></p>
			</div>
			<div class="list-cell-2">
				<p class="cell-other"><span>已办理</span><span>${v.num} 人</span></p>
				<p class="cell-other"><span>未办理</span><span>${studentNum - v.num} 人</span></p>
			</div>
			<div class="list-cell-3">
				<div style="position: relative;">
					<div id="item_chart_${v_index}" style="width: 40px; height: 40px;"></div>
					<div class="item_percent item_percent_${v_index % 7}">${(percent?string("0%"))!}</div>
				</div>
				<script>
					$(function () {
						var colors = [
							["#4be2d9", "#2e4465"],
							["#d68eeb ", "#2e4465"],
							["#50f954", "#2e4465"],
							["#f5d744", "#2e4465"],
							["#a0b6fd", "#2e4465"],
							["#fc6d6d", "#2e4465"],
							["#ff8f21", "#2e4465"],
						];
						var myChart = echarts.init(document.getElementById('item_chart_${v_index}'));
						// 指定图表的配置项和数据
						var option = {
							tooltip: {
								show: false,
							},
							color: colors['${v_index}' * 1 % colors.length],
							series: [
								{
									type: 'pie',
									radius: ['80%', '100%'],
									silent: true,
									label: {
										normal: {
											show: false,
										},
									},
									data: [${percent}, ${1 - percent}],
								}
							]
						};
						// 使用刚指定的配置项和数据显示图表。
						myChart.setOption(option);
					});
				</script>
			</div>
		</li>
	[/#list]
</ul>