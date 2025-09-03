[#ftl]
[#import "/template/mobile.ftl" as m/]
[#include "../comm/macro.ftl"/]
[@m.body title=switch.name back=b.url("index?t=1")]
<style>

</style>
    [#if studentInfo?? && studentInfo.accommodationed]
        <h3 class="yx-xxtx-title">您已选择了需要住宿</h3>
        <div class="yx-xxtx">
            [#include "table.ftl"/]
        </div>
    [#else]
        <div class="text-center" style="margin: 15px;">
            <div class="alert alert-danger" role="alert">您已选择了不需要住宿</div>
        </div>
    [/#if]
    [#if switch?? && switch.editable]
        <div class="yx-jiange-1em"></div>
        <div class="yx-model-btnmodel">
            <a href="${b.url("!edit")}" data-role="button" class="yx-model-bluebtn">修改</a>
        </div>
    [/#if]
[/@]