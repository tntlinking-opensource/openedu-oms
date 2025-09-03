[#ftl]
[#include "../comm/macro.ftl"/]
[#import "/template/form/utils.ftl" as fu/]
[@index code="05"]
<h3 class="caption">绿色通道</h3>
<div>
    [@b.form action="!save" class="supplies_form"]
		<div style="min-height: 300px;">
			<div class="form-horizontal" style="margin: 20px;">
				<div class="form-group">
					<label class="col-sm-5 control-label">是否需要办理绿色通道：</label>
					<div class="col-sm-7">
						<label class="radio-inline">
							<input type="radio" name="financeGreenStd.handle" value="1"
                                   [#if financeGreenStd.handle]checked[/#if] class="handle_ipt"> 是
						</label>
						<label class="radio-inline">
							<input type="radio" name="financeGreenStd.handle" value="0"
                                   [#if !financeGreenStd.handle]checked[/#if] class="handle_ipt"> 否
						</label>
					</div>
				</div>
			</div>
			<div style="max-width: 800px; margin: auto;">
				<table class="table table-bordered">
					<thead>
					<tr>
						<th>&nbsp;</th>
						<th>申请项目</th>
						<th>项目说明</th>
					</tr>
					</thead>
					<tbody>
                        [#function hasFinanceGreen financeGreen]
                            [#list financeGreenStd.financeGreens as v]
                                [#if v.id == financeGreen.id]
                                    [#return true/]
                                [/#if]
                            [/#list]
                            [#return false/]
                        [/#function]
                        [#list financeGreens as v]
						<tr align="center">
							<td>
								<input name="financeGreenId" value="${v.id}" type="radio"
								       class="form-control finance_green_id_ipt" [#if hasFinanceGreen(v)]checked[/#if]>
							</td>
							<td>${(v.name)!}</td>
							<td>${(v.remark)!}</td>
						</tr>
                        [/#list]
					</tbody>
				</table>
			</div>
		</div>
		
		<div id="file_div" class="form-horizontal" style="margin: 20px;">
			<div class="form-group">
				<label class="col-sm-3 control-label">上传附件：</label>
				<div class="col-sm-9">
					<input type="hidden" name="studentId" value="${studentId}"/>
					[@fu.fileinputs id="assetRepairFile" name="fileData" value="" type="uuid" uploadFiles=(student.stdInfoFile)! folder="repair/assetRepair" maxFileCount=5 accept="image/png,image/gif,image/jpeg" allowedFileExtensions="'jpg','png','gif'"/]
				</div>
			</div>
		</div>
		
		<div style="margin: 30px; text-align: center;">
			<input type="hidden" name="financeGreenStd.id" value="${(financeGreenStd.id)!}"/>
			<button type="button" class="btn btn-primary finance_green_std_submit">
				提交 <i class="fa fa-check "></i>
			</button>
		</div>
    [/@]
	[@editRemark/]
</div>
<script>
	$(function () {
		$(".handle_ipt").click(function () {
			if (this.value == "1") {
				$(".finance_green_id_ipt").prop("disabled", false);
				$("#file_div").show();
			} else {
				$(".finance_green_id_ipt").prop("disabled", true);
				$("#file_div").hide();
			}
		});
		$(".handle_ipt:checked").triggerHandler("click");
		$(".finance_green_std_submit").click(function () {
			if ($(".handle_ipt[value='1']:checked").length == 1) {
				if ($(".finance_green_id_ipt:checked").length == 0) {
					alert("请选择需要办理的项目");
					return;
				}
			}
			this.form.submit();
		});
	});
</script>
[/@index]
