[#ftl]
[@b.head/]

<script type="text/javascript" src="${base}/static/scripts/jquery-validation/jquery.validate.min.js"></script>
<script type="text/javascript" src="${base}/static/scripts/jquery-validation/additional-methods.min.js"></script>
<script type="text/javascript" src="${base}/static/scripts/jquery-validation/additional-methods.js"></script>

<script type="text/javascript"
        src="${base}/static/metronic/assets/global/plugins/bootstrap-maxlength/bootstrap-maxlength.min.js"></script>
<script type="text/javascript"
        src="${base}/static/metronic/assets/global/plugins/bootstrap-touchspin/bootstrap.touchspin.js"></script>
<script type="text/javascript" src="${base}/static/scripts/beangle/beangle.validate.js"></script>

[#import "/template/form/utils.ftl" as lu/]
[#import "/org/beangle/process/action/comm.ftl" as lc/]
[#assign started = false]
[#if processBatch??]
	[#assign started = processBatch.started]
[/#if]
<div class="portlet light">
	<div class="portlet-body">
		<form id="processLinkItemForm" name="processLinkItemForm" action="${b.url('!saveProcessLinkItem')}"
		      method="post" target="main">
			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label bold">环节名称<span class="required"
						                                            aria-required="true"> * </span></label>
						<select name="processLinkItem.processLink.id" class="form-control" required
						        [#if started]disabled[/#if]>
							<option value="">...</option>
							[#list processLinks as processLink]
								<option value="${(processLink.id)!}"
								        [#if processLinkItem??&&(processLinkItem.processLink??&&processLinkItem.processLink.id==processLink.id)]selected[/#if]>${(processLink.name)!}</option>
							[/#list]
						</select>
					</div>
				</div>
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label bold">环节起止日期</label>
						<div class="input-group input-large date-picker input-daterange" data-date="10/11/2012"
						     data-date-format="mm/dd/yyyy">
							[@b.startend id="reportrange" label="开始时间,结束时间" name="processLinkItem.startTime,processLinkItem.endTime" required="false,false" start=processLinkItem.startTime end=processLinkItem.endTime format="datetime"/]
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label bold">审核的角色<span class="required"
						                                             aria-required="true"> * </span></label>
						<div class="color-demo">
							<div class="color-view bg-grey-cararra bg-font-grey-cararra">
								<div class="mt-radio-list">
									[#list auditGroups as auditGroup]
										<label class="mt-radio mt-radio-outline">
											<input type="radio" class="form-control" name="processLinkItem.auditGroup.id"
											       value="${(auditGroup.id)!}"
											       [#if processLinkItem??&&(processLinkItem.auditGroup??&&processLinkItem.auditGroup.id==auditGroup.id)]checked[/#if]
											       required [#if started]disabled[/#if]>
											${(auditGroup.name)!}<span></span>
										</label>
									[/#list]
								</div>
								[#--<div class="md-radio-list">
								[#list auditGroups as auditGroup]
									<div class="md-radio">
										<input id="auditGroup_${auditGroup.id}" type="radio" class="form-control"
											   name="processLinkItem.auditGroup.id"
											   value="${(auditGroup.id)!}"
											   [#if processLinkItem??&&(processLinkItem.auditGroup??&&processLinkItem.auditGroup.id==auditGroup.id)]checked[/#if]
											   required [#if started]disabled[/#if]/>
										<label for="auditGroup_${auditGroup.id}">
											<span></span>
											<span class="check"></span>
											<span class="box"></span> ${(auditGroup.name)!} </label>
									</div>
								[/#list]
								</div>--]
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-6">
					[#if processLinkItems?size gt 0]
						<label class="control-label bold">必须完成的上一环节<font color="red">(只能勾选必须通过的环节)</font></label>
						<div class="color-demo">
							<div class="color-view bg-grey-cararra bg-font-grey-cararra">
								<div class="mt-radio-list">
									[#list processLinkItems as lli]
										[#if lli.mustPassed]
											<label class="mt-checkbox mt-checkbox-outline">
												<input type="checkbox" id="processLinkIds${lli.id}" name="processLinkIds"
												       value="${(lli.id)!}"
												       [#if (processLinkItem??&&processLinkItem.processLinks?seq_contains(lli))]checked="checked"[/#if]
														[#if started||!lli.mustPassed]disabled[/#if]>
												${(lli.processLink.name)!}[#if lli.mustPassed]<font
													color="red">（*必须通过）</font>[/#if]<span></span>
											</label>
										[/#if]
									[/#list]
								</div>
							</div>
						</div>
					[/#if]
				</div>
			</div>

			<div class="row">
				<div class="col-md-12">
					<label class="control-label bold">环节描述</label>
					[@lu.ckeditor id="lcsm" name="processLinkItem.description" value="${(processLinkItem.description?string)!}"/]
				</div>
			</div>

			<div class="row">
				<div class="col-md-6">
					<div class="mt-radio-list">
						<label class="mt-checkbox mt-checkbox-outline">
							<input type="checkbox" name="processLinkItem.mustPassed" value="1"
							       [#if processLinkItem.mustPassed?string=='true']checked=""[/#if]
									[#if started]disabled[/#if]>
							该节点不通过，则流程不能通过<span></span>
						</label>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label bold">
							环节所属部门
							[#--有些环节，没有所属部门--]
							[#--<span class="required" ria-required="true"> * </span>--]
						</label>
						<select id="departmentId" name="processLinkItem.department.id" class="form-control select2 "
						        [#if started]disabled[/#if]>
							<option value="">...</option>
							[#list departments as department]
								<option value="${(department.id)!}"
								        [#if processLinkItem??&&(processLinkItem.department??&&processLinkItem.department.id==department.id)]selected[/#if]>${(department.name)!}</option>
							[/#list]
						</select>
					</div>
				</div>
			</div>
			[@lc.radios "可打印" "kdy" "processLinkItem.printed" processLinkItem.printed?default(false) started/]
			[@lc.radios "可撤销" "zcx" "processLinkItem.revoked" processLinkItem.revoked started/]
			[@lc.radios "是否关键环节" "keyLinked" "processLinkItem.keyLinked" processLinkItem.keyLinked started/]

			<div class="text-center margin-top-20">
				<input type="hidden" name="step" value="${(step)!}">
				<input type="hidden" name="process.id" value="${(process.id)!}">
				<input type="hidden" name="processLinkItem.id" value="${(processLinkItem.id)!}">
				<input type="hidden" name="processLinkItem.process.id" value="${process.id!}"/>
				<input type="hidden" name="processBatch.id" value="${(processBatch.id)!}"/>
				[#if !started]
					<button type="button" onclick="formSubmit();" class="btn btn-lg fa fa-save green-meadow">保存</button>
				[/#if]
			</div>
		</form>
	</div>
</div>
<script type="text/javascript">
	var form = $("#processLinkItemForm");
	var rules = {};
	ValidateForm(form, rules);
	function formSubmit() {
		if (!form.valid()) {
			return;
		}
		bg.form.submit("processLinkItemForm");
	}
	jQuery(document).ready(function () {
		if (jQuery().select2) {
			$('#departmentId').select2({
				placeholder: "请选择环节所属部门",
				allowClear: true
			});
		}
	});
</script>
[#--

<script>
	$('#reportrange').daterangepicker({
				opens: ('left'),
				startDate: startD != null && endD != "" ? startD : moment(),
				endDate: endD != null && endD != "" ? endD : moment(),
				showDropdowns: true,
				showWeekNumbers: true,
				timePicker: showtime,
				timePickerIncrement: 1,
				timePicker12Hour: true,
				ranges: {
					'今天': [moment(), moment()],
					'近七天': [moment(), moment().subtract('days', -6)],
					'近三十天': [moment(), moment().subtract('days', -29)],
					'本月': [moment().startOf('month'), moment().endOf('month')],
					'下月': [moment().subtract('month', -1).startOf('month'), moment().subtract('month', -1).endOf('month')]
				},
				buttonClasses: ['btn'],
				applyClass: 'green',
				cancelClass: 'default',
				format: dateF,
				separator: ' to ',
				locale: {
					format: "YYYY-MM-DD HH:mm",
					applyLabel: '确定',
					cancelLabel: '取消',
					fromLabel: '开始',
					toLabel: '截止',
					customRangeLabel: '自定义',
					daysOfWeek: ['日', '一', '二', '三', '四', '五', '六'],
					monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
					firstDay: 1
				}
			},
			function (start, end) {
				console.log("Callback has been called!");
				if (start != null) {
					$('#reportrange span').html(start.format(dateF) + ' - ' + end.format(dateF));
				}
				$('#reportrange input').each(function (index) {
					if (index == 0) {
						$(this).val(start.format(dateF));
					} else {
						$(this).val(end.format(dateF));
					}
				});
			}
	);
</script>--]
