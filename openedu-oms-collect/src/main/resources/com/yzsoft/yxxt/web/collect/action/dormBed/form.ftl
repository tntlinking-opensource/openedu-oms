[#ftl]
[#include "/com/yzsoft/yxxt/web/collect/action/comm/macro.ftl"/]
[#import "/template/form/utils.ftl" as fu/]
[@index code="G042"]
	<h3 class="caption">是否住宿</h3>
	<div class="row">
		[@b.form action="!save" class="supplies_form"]
		[#-- <div style="margin:15px;">
			 <div class="form-horizontal ifrom" style="margin: 20px;">
				 <div class="col-sm-12" style="line-height: 30px; min-height: 34px;">
					 <div class="col-sm-6" style="text-align: right;">住宿标准</div>
					 <div class="col-sm-6">
						 [@fu.select id="zsbz" name="financeStudentItem.feeTotal" value=(financeStudentItem.feeTotal)!0
							 data=dormSettings valueKey="money" nameKey="money" /]
					 </div>
				 </div>
			 </div>
		 </div>--]

			<div style="margin: 30px; text-align: center; word-spacing: 100px;">
				[#if flag?string("yes","no")=="no"]
					<button type="button" class="btn btn-primary supplies_submit">
						住宿 <i class="fa fa-thumbs-o-up"></i>
					</button>
					<button type="button" class="btn btn-danger supplies_submit">
						不住宿 <i class="fa fa-thumbs-o-down"></i>
					</button>
				[#else]
					<div class="text-center" style="margin: 15px;">
						<div class="alert alert-info" role="alert">${message!""}</div>
					</div>
				[/#if]
			</div>
			<input type="hidden" name="accommodationed" value="0" class="accommodationed_ipt"/>
		[/@]

		[@editRemark/]
	</div>
	<script>
		$(function () {
			$(".supplies_submit").click(function () {
				if ($(this).hasClass("btn-primary")) {
					$(".accommodationed_ipt").val(1);
					$("#zsbz").addClass("v_required");
				}

				if ($(this).hasClass("btn-danger")) {
					$(".accommodationed_ipt").val(0);
					$("#zsbz").removeClass();
				}

				var form = $(this.form);
				if (form.valid()) {
					form.submit();
				}
			});
		});
	</script>
[/@index]
