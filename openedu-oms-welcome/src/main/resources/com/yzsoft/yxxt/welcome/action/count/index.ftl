[#ftl]
<div class="portlet light ">
	<div class="portlet-body">
		<div class="row" style=" margin-top:5px;">
			[#--<div class="col-md-4  text-center" style="float:left;">
				<div class="pie-chart">
					<a href="${b.url("dashboard?batchId=" + id)}" target="_blank">
						<img src="${base}/static/yxxt/welcome/css/images/xc_tj.png"/>
					</a>
				</div>
				<p style=" margin:0;">大屏显示</p>
			</div>--]
			<div class="col-md-4  text-center" style="float:left;">
				<div class="pie-chart">
					<a href="${b.url("!info?id=" + id)}" target="_blank">
						<img src="${base}/static/yxxt/welcome/css/images/xc_tj.png"/>
					</a>
				</div>
				<p style=" margin:0;">汇总统计</p>
			</div>
			<div class="col-md-4  text-center"
			     style="float: right;">
				<div class="pie-chart">
					<a href="${b.url("!department?id=" + id)}" target="_blank">
						<img src="${base}/static/yxxt/welcome/css/images/xc_mx.png"/>
					</a>
				</div>
				<p style=" margin:0;">明细统计</p>
			</div>
			<div class="row">
				<div class="col-md-12">
					[#--<h5>学生总人数：${studentNum!}人</h5>--]
					<ul class="stat-list-1">
						[#list processBatchCounts as count]
							<li class="list_bg">
								<div class="progress-mini-1"
								     style="width:[#if studentNum gt 0]${(1 - (studentNum - count.num!0)/studentNum)?string.percent}[#else]0%[/#if];">
								</div>
								<i class="progress-style-01"> ${(count.itemName)!}：${(count.num)!}人</i>
							</li>
						[/#list]
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>