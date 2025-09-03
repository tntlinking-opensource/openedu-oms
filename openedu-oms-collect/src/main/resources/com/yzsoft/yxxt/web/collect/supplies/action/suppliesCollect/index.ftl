[#ftl]
[#include "/com/yzsoft/yxxt/web/collect/action/comm/macro.ftl"/]
[#import "/template/form/utils.ftl" as fu/]
[@index code="04_1"]
<h3 class="caption">生活用品</h3>
<div class="row">
    [@b.form action="!edit" class="supplies_form"]
        [#if suppliesStd?? && suppliesStd.items?size gt 0]
            <div class="text-center" style="margin: 15px;">
                <div class="alert alert-info" role="alert">您已选择了购买生活用品</div>
            </div>
            <div style="margin:15px;">
                [#include "table.ftl"/]
            </div>
        [#else]
            <div class="text-center" style="margin: 15px;">
                <div class="alert alert-danger" role="alert">您已选择了不购买生活用品</div>
            </div>
        [/#if]
        [#if switch?? && switch.editable]
            <div style="margin: 30px; text-align: center;">
                <a href="${b.url("!edit")}" class="btn btn-primary">修改</a>
            </div>
        [/#if]
    [/@]
</div>
[/@index]
