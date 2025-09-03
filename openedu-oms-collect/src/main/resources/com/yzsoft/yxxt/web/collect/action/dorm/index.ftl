[#ftl]
[#include "/com/yzsoft/yxxt/web/collect/action/comm/macro.ftl"/]
[#import "/template/form/utils.ftl" as fu/]
[@index code="04_1"]
<h3 class="caption">生活用品</h3>
<div class="row">
    [@b.form action="!edit" class="supplies_form"]
        [#if studentInfo?? && studentInfo.accommodationed]
            <div class="text-center" style="margin: 15px;">
                <div class="alert alert-info" role="alert">您已选择了需要住宿</div>
            </div>
            <div class="form-horizontal" style="margin: 20px;">
                <div class="form-group">
                    <label class="col-sm-3 control-label">收费标准：</label>
                    <div class="col-sm-9">
                        [#include "table.ftl"/]
                    </div>
                </div>
            </div>
        [#else]
            <div class="text-center" style="margin: 15px;">
                <div class="alert alert-danger" role="alert">您已选择了不需要住宿</div>
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
