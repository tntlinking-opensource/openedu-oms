[#ftl]
[@yx.head title="常见问题"/]
<link href="${base}/static/metronic/assets/yz_yxwz_css/main6.css" rel="stylesheet" type="text/css"/>
<div class="page-content">
	<div class="row">
		<div class="col-md-10 col-md-offset-1" style="padding-left:0; padding-right:0;">
			<div class="portlet light clearcss">
				<div class="row bg-light-blue yx-zixun">
					<h1 class="yx-zixun-left"><img
							src="${base}/static/metronic/assets/yz_yxwz_css/zixun_03.png">常见问题</h1>
					<div class="pull-right" style="padding: 15px 5px;">
						<form action="list.action">
							<input name="key"
							       style="height: 45px; padding: 0 10px; margin-right: 10px;vertical-align: middle;"
							       value="${key!}"/>
							<button class="btn btn-primary btn-lg">搜索</button>
						</form>
					</div>
				</div>

				<div class="portlet light">
					<div class="portlet-body">
						<div class="tab-content">
							<div class="tab-pane fade active in">
								<div id="ask_list_div">
                                [#if asks?size == 0]
                                    [#if key??]
										<div class="yx-model-last text-center"><p>没有找到和“${key!}”有关的信息。</p></div>
                                    [#else]
										<div class="yx-model-last text-center"><p>没有更多信息了</p></div>
                                    [/#if]
                                [#else]
									<ul class="yz-model">
                                        [#list asks as ask]
											<li>
												<div class="margin-top-10">
													<div class="yx-model-a">
														<img class="yx-model-a-left"
														     src="${base}/static/metronic/assets/yz_yxwz_css/qa_icon_03.png">
														<p class="yx-model-a-right">${ask.content!}</p>
													</div>
													<div class="yx-model-a margin-top-10">
														<img class="yx-model-a-left"
														     src="${base}/static/metronic/assets/yz_yxwz_css/qa_icon_06.png">
														<p class="yx-model-a-right bold">${ask.replyContent!"暂未回复！"}</p>
													</div>
												</div>
											</li>
                                        [/#list]
									</ul>
									<div>
										<ul class="pagination pagination-lg pull-right">
                                            [#assign page = asks/]
                                            [#assign href]${b.url("list")}&key=${(key)!}[/#assign]
											<li>
                                                [#if page.pageNo == 1]
													<a>
														<i class="fa fa-angle-left"></i>
													</a>
                                                [#else]
													<a href="${href}&pageNo=1">
														<i class="fa fa-angle-left"></i>
													</a>
                                                [/#if]
											</li>
                                            [#list (page.pageNo-3)..(page.pageNo+3) as v]
												<li>
                                                    [#if v gt 0 && v lte page.maxPageNo]
                                                        [#if v == page.pageNo]
															<a> ${v} </a>
                                                        [#else]
															<a href="${href}&pageNo=${v}"> ${v} </a>
                                                        [/#if]
                                                    [/#if]
												</li>
                                            [/#list]
											<li>
                                                [#if page.pageNo < page.maxPageNo]
													<a href="${href}&pageNo=${page.pageNo + 1}">
														<i class="fa fa-angle-right"></i>
													</a>
                                                [#else]
													<a>
														<i class="fa fa-angle-right"></i>
													</a>
                                                [/#if]
											</li>
										</ul>
										<div style="clear: both;"></div>
									</div>
                                [/#if]
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
[@yx.foot/]