[#ftl]
[@b.head/]
[@b.form action="!save" class="form-horizontal"]
    [@c.panel title="接站起止日期"]
	<div style="max-width: 500px; margin: auto;">
		<div class="row">
			<div class="col-sm-6">
				<div class="form-group">
					<label class="col-sm-5 control-label">开始日期：</label>
					<div class="col-sm-7">
						<input name="stationDate.startDate" value="${(stationDate.startDate?date)!}"
						       class="form-control v-datepicker"/>
					</div>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
					<label class="col-sm-5 control-label">结束日期：</label>
					<div class="col-sm-7">
						<input name="stationDate.endDate" value="${(stationDate.endDate?date)!}"
						       class="form-control v-datepicker"/>
					</div>
				</div>
			</div>
		</div>
	</div>
    [/@]
    [@c.panel title="接站时间段设置"]
	<div style="max-width: 500px; margin: auto;">
        [#list stationDate.times as time]
			<input type="hidden" name="time" value="${time_index}"/>
			<div class="form-group">
				<label class="col-sm-4 control-label">时间${(time.sort)}：</label>
				<div class="col-sm-8">
					<input name="time${time_index}.time" value="${(time.time)!}" class="form-control" maxlength="30"/>
				</div>
			</div>
        [/#list]
	</div>
    [/@]
<div class="text-center" style="margin: 20px;">
	<input type="hidden" name="stationDate.id" value="${stationDate.id!}"/>
    [@b.submit value="action.submit"/]
</div>

    [@c.bootstrapDatepickerJsAndCss/]
<script>
	$('.v-datepicker').datepicker({
		autoclose: true,
		todayHighlight: true,
		format: "yyyy-mm-dd",
		language: "zh-CN",
	});
</script>
[/@b.form]
[@b.foot/]