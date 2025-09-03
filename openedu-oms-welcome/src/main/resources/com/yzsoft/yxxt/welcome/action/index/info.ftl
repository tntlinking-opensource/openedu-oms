[#ftl]
<script src="${base}/static/scripts/echarts/echarts.common.min.js"></script>
<div class="tab-pane  active" id="portlet_tab_1">
	<div class="row">
		<div class="col-md-12">
			<div class="portlet light ">
				<div class="portlet-body">
					<!--现场说明-->
					<div class="row">
						<div style="float:left; display:block; padding-top:20px; "><img
								src="${base}/static/yxxt/welcome/css/images/xc_img.png"
								style="width:50px; ">
						</div>
						<div class="col-md-9">
							<h4 class="caption-subject bold">现场说明：</h4>
							<p style="margin:0">
								系统以流程图的方式展示了迎新现场操作菜单，每一个现场环节都是一个具体的操作窗口；点击具体的文字连接，进入相应的环节处理界面，完成相关的环节处理。</p>
							<p class="font-red-thunderbird">
								注：若没有该环节的处理权限，菜单将显示为灰色并不能点击进入。</p>
						</div>
					</div>
					<!--现场结束-->
				</div>
			</div>
		</div>
	</div>
	<!--左右2边盒-->
	<div class="row">
		<div class="col-md-12">
			<div class="row">
				<!--统计报道-->
				<div class="col-md-3" style="padding:0">
				[@b.div href="/welcome/count?id=${batch.id}"/]
				</div>
				<!--统计报道结束-->
				<div class="tab-content col-md-9"
				     style="padding-left:0; padding-right:0;">
					<div class="tab-pane active" id="portlet_tab_2">
						<div class="row">
							<div style="margin-left:15px; margin-right:15px;">
								<div class="portlet-body">
									<div class="timeline ">
										<div class="timeline-item">
											<div class="mt-timeline-icon"></div>
											<div class="timeline-body"
											     style="margin-top:0;">
												<h5 class="bg-white text-center bold">${(batch.name)!}</h5>
											</div>
										</div>
										<!-- TIMELINE ITEM -->
									[#function hasItem step]
										[#list items as item]
											[#if item.step == step]
												[#return true/]
											[/#if]
										[/#list]
										[#return false/]
									[/#function]
									[#list 1..99 as step]
										[#if !hasItem(step)]
											[#break /]
										[/#if]
										<div class="timeline-item">
											<div class="timeline-badge">
												<div class="mt-timeline-icon bg-grey">${step}</div>
											</div>
											<div class="timeline-body">
												<div class="row">
													[#list items as item]
														[#if item.step == step]
															<div class="col-md-2 col-sm-6 text-center">
																<div class="pie-chart">
																	<div style="position: relative;width: 90px; height: 90px; margin: auto;">
																		<div id="myChart_${(item.id)!}"
																		     style="width: 90px; height: 90px;">
																		</div>
																		<div style="position: absolute; color: #659BE0; top: 35px; text-align: center; width: 90px;">
																			[#if studentNum gt 0]${(((1 - (studentNum - itemNumMap[item.id?string]!0)/studentNum)) * 100)?string("0.#")}
																				%[#else]
																				0%[/#if]
																		</div>
																	</div>
																	<script>
																		$(function () {
																			// 基于准备好的dom，初始化echarts实例
																			var myChart = echarts.init(document.getElementById('myChart_${(item.id)!}'));
																			// 指定图表的配置项和数据
																			var option = {
																				color: ["#659BE0", "#E5E5E5"],
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
																						data: [${itemNumMap[item.id?string]!0}, ${studentNum - itemNumMap[item.id?string]!0}],
																					}
																				]
																			};
																			// 使用刚指定的配置项和数据显示图表。
																			myChart.setOption(option);
																		});
																	</script>
																</div>
																<p class="bold">
																	[#if item.getIntime() && item.auditGroup?? && item.auditGroup.group?? && userGroups?contains(item.auditGroup.group)]
																		[@b.a href="!deal?batchId=${(batch.id)!}&itemId=${(item.id)!}" target="batch_body"]${(item.name)!}[/@b.a]
																	[#else]
																		<span>${(item.name)!}</span>
																	[/#if]
																</p>
															</div>
														[/#if]
													[/#list]
												</div>
											</div>
										</div>
									[/#list]
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--左右2边盒结束-->
</div>