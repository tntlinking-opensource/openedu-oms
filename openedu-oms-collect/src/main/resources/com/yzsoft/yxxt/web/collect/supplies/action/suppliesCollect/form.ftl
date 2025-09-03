[#ftl]
[#include "/com/yzsoft/yxxt/web/collect/action/comm/macro.ftl"/]
[#import "/template/form/utils.ftl" as fu/]
[@index code="04_1"]
<h3 class="caption">生活用品</h3>
<div class="row">
    [@b.form action="!save" class="supplies_form"]
        <div style="margin:15px;">
            [#include "table.ftl"/]
        </div>
        <div style="margin: 30px; text-align: center; word-spacing: 100px;">
            <button type="button" class="btn btn-primary supplies_submit">
                购买 <i class="fa fa-thumbs-o-up"></i>
            </button>
            <button type="button" class="btn btn-danger supplies_submit">
                不购买 <i class="fa fa-thumbs-o-down"></i>
            </button>
        </div>
        <input type="hidden" name="agree" value="0" class="agree_ipt"/>
    [/@]

    [@editRemark/]
</div>
<script>
    $(function () {
        $(".supplies_submit").click(function () {
            if ($(this).hasClass("btn-primary")) {
                $(".agree_ipt").val(1);
            }
            var form = $(this.form);
            if (form.valid()) {
                form.submit();
            }
        });
    })
    ;
</script>
[/@index]
