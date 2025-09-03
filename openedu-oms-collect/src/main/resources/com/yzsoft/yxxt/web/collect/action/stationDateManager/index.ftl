[#ftl]
[@b.head/]
[@b.form action="!edit" class="form-horizontal"]
    [@c.panel title="接站起止日期"]
	<div style="max-width: 500px; margin: auto;">
		<div class="row">
			<div class="col-sm-6">
				<div class="form-group">
					<label class="col-sm-5 control-label">开始日期：</label>
					<div class="col-sm-7">
						<p class="form-control-static">${(stationDate.startDate?date)!"未设置"}</p>
					</div>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
					<label class="col-sm-5 control-label">结束日期：</label>
					<div class="col-sm-7">
						<p class="form-control-static">${(stationDate.endDate?date)!"未设置"}</p>
					</div>
				</div>
			</div>
		</div>
	</div>
    [/@]
    [@c.panel title="接站时间段设置"]
	<div style="max-width: 500px; margin: auto;">
        [#list stationDate.times as time]
			<div class="form-group">
				<label class="col-sm-4 control-label">时间${(time.sort)}：</label>
				<div class="col-sm-8">
					<p class="form-control-static">${(time.time)!}</p>
				</div>
			</div>
        [/#list]
	</div>
    [/@]
<div class="text-center" style="margin: 20px;">
    [@b.a href="!edit" class="btn blue"]编辑[/@]
</div>
[/@b.form]
[@b.foot/]