[#ftl]

[#macro leftMenu code]
	<div class="left_column_div">
		[@yx.columnLeft/]
	</div>
[#--<ul class="nav nav-tabs tabs-left">
	<li><a href="${base}/web/info/student.action" data-code="01">新生信息</a></li>
	<li><a href="${base}/web/info/dorm.action" data-code="02">宿舍入住</a></li>
	<li><a href="${base}/dorm/web/collect/dorm-bed-std-info.action" data-code="SS_02">宿舍入住</a></li>
	<li><a href="${base}/web/info/fee.action" data-code="03">费用信息</a></li>
	<li><a href="${base}/web/info/finance.action" data-code="90">在线支付</a></li>
</ul>--]
<script>
    [#--$("[data-code='${code}']").parent().addClass("active");--]
    if($(".left_column_div li.active").length == 0){
        $(".left_column_div li:first").addClass("active");
	}
</script>
[/#macro]

[#macro body code]
	[@yx.head title="信息查看"/]
<link href="${base}/static/metronic/assets/yz_yxwz_css/main6.css" rel="stylesheet" type="text/css"/>
<div class="row">
	<div class="col-md-10 col-md-offset-1" style="padding-left:0; padding-right:0;">
		<div class="portlet light clearcss" style="padding: 15px;">
			<div class="row">
				<div class="col-md-3 col-sm-3 col-xs-3" style="padding-left:0; padding-right:0;">
					[@leftMenu code=code/]
				</div>
				<div class="col-md-9 col-sm-9 col-xs-9"
				     style="border:1px solid #ddd; padding-left:0; padding-right:0;">
					<div class="tab-content" style=" min-height: 600px;">
						<div class="tab-pane active in">
							[#nested /]
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
	[@yx.foot/]
[/#macro]

[#macro index code]
	[@body code=code]
		[#nested /]
	[/@body]
[/#macro]

[#macro form code action="!save"]
	[@body code=code]
		[@c.validateJsAndCss/]
		[@b.form name="editForm" action=action]
			[#nested /]
		<div class="text-center" style="margin: 20px 0;">
			<button type="button" class="btn btn-primary edit_form_submit">保存</button>
		</div>
		<script>
			$(function () {
				var form = $("#editForm");
				form.validate();
				$(".edit_form_submit").click(function () {
					if (form.valid()) {
						form.submit();
					} else {
						alert("表单填写有误，不能提交。");
					}
				});
			});
		</script>
		[/@b.form]
	[/@body]
[/#macro]