[#ftl]
[#import "/template/comm.ftl" as c/]
[#import "/template/mobile.ftl" as m/]
[#import "/template/form/utils.ftl" as fu/]
[@m.body title=switch.name back=b.url("index?t=1")]
<style>
    .ui-header .ui-title, .ui-footer .ui-title { margin: 0; overflow: auto; }
    .supplies_submit {color: #fff !important; font-weight: normal !important;}
    .supplies_submit.btn-primary {background-color: #337ab7;}
    .supplies_submit.btn-danger {background-color: #ed6b75;}
</style>
<form action="${b.url("!save")}" method="post" class="clothesStudentForm">
	<ul data-role="listview" class="yx-xxtx-info">
		<li>
		    <div class="yx-xxtx-info-a">
		        <span class="yx-xxtx-info-a-left margin-top-em">住宿标准</span>
		        <div class="form-right">
		        	[@fu.select id="zsbz" name="financeStudentItem.feeTotal" value=(financeStudentItem.feeTotal)!0 
									data=dormSettings valueKey="money" nameKey="money" /]
		        </div>
		    </div>
	    </li>
    </ul>
    <div class="yx-jiange-2em"></div>
    <div class="yx-model-btnmodel">
        <div class="yx-xxtx-data-a ui-grid-a">
            <div class="ui-block-a">
                <button type="button" class="btn btn-primary supplies_submit">住宿</button>
            </div>
            <div class="ui-block-b">
                <button type="button" class="btn btn-danger supplies_submit">不住宿</button>
            </div>
            <input type="hidden" name="accommodationed" value="0" class="accommodationed_ipt"/>
        </div>
    </div>
</form>
<script src="${base}/static/bpcg/student/scripts/validate.js"></script>
<script src="${base}/static/yxxt/mobile/scripts/form.js"></script>
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
[/@]