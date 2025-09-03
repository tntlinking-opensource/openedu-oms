[#ftl]
[#include "../comm/macro.ftl"/]
[#import "/template/form/utils.ftl" as fu/]
[@index code="04"]
<h3 class="caption">生活用品</h3>
<div class="row">
    [@b.form action="!save" class="supplies_form"]
        <div style="margin:15px;">
            <table class="table table-bordered table-condensed">
                <thead>
                <th>序号</th>
                <th>物品名称</th>
                <th>物品规格</th>
                <th>单价（元）</th>
                <th>是否预订</th>
                <th width="250">订购数量/最大订购数量</th>
                </thead>
                <tbody>
                    [#function hasSupplies supplies]
                        [#list suppliesStd.items as item]
                            [#if supplies.id = item.supplies.id]
                                [#return  true/]
                            [/#if]
                        [/#list]
                        [#return  false/]
                    [/#function]
                    [#function suppliesNum supplies]
                        [#list suppliesStd.items as item]
                            [#if item.num?? && supplies.id = item.supplies.id]
                                [#return  item.num/]
                            [/#if]
                        [/#list]
                        [#return  1/]
                    [/#function]
                    [#list suppliess as supplies]
                        [#assign name]suppliesStdItem${supplies_index}[/#assign]
                    <tr align="center">
                        <td>${supplies_index + 1}</td>
                        <td>${supplies.name}</td>
                        <td>${supplies.spec!}</td>
                        <td>${supplies.price!}</td>
                        <td>
                            <input type="checkbox" name="suppliesStdItem" value="${supplies_index}"
                                   class="form-control supplies_checkbox"
                                   [#if hasSupplies(supplies)]checked[/#if]>
                            <input type="hidden" name="${name}.supplies.id" value="${supplies.id}"/>
                        </td>
                        <td>
                            <div class="input-group" style="width: 150px;">
                                <input name="${name}.num" value="${suppliesNum(supplies)}"
                                       class="form-control num_ipt v_supplies_num"
                                       data-maxNum="${supplies.maxNum!1}"/>
                                <span class="input-group-addon">/${supplies.maxNum!1}</span>
                            </div>
                        </td>
                    </tr>
                    [/#list]
                </tbody>
            </table>
        </div>
        <div style="margin: 30px; text-align: center;">
            <button type="button" class="btn btn-primary supplies_submit">
                提交 <i class="fa fa-check "></i>
            </button>
        </div>
    [/@]

    [@editRemark/]
</div>
<script>
    $(function () {
        jQuery.validator.addClassRules("v_supplies_num", {
            required: true,
            suppliesNum: true,
            digits: true,
            min: 1,
        });
        jQuery.validator.addMethod("suppliesNum", function (value, element, param) {
            var maxNum = $(element).attr("data-maxNum") * 1;
            return this.optional(element) || value * 1 <= maxNum;
        }, $.validator.format("订购数量超过上限"));
        //		$(".supplies_form").validate();
        ValidateForm($(".supplies_form"));
        $(".supplies_checkbox").click(function () {
            var ipt = $(this).closest("tr").find(".input-group");
            if (this.checked) {
                ipt.fadeIn().focus();
            } else {
                ipt.fadeOut();
            }
        }).each(function () {
            var ipt = $(this).closest("tr").find(".input-group");
            if (this.checked) {
                ipt.show();
            } else {
                ipt.hide();
            }
        });
        $(".supplies_submit").click(function () {
            var form = $(this.form);
            if (form.valid()) {
                form.submit();
            }
        });
    })
    ;
</script>
[/@index]
