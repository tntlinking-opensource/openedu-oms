[#ftl]
[#if backurl??]
<div style="margin: 10px 0;">
    <a href="${backurl}" onclick="return bg.Go(this,'main',true)" class="btn btn-lg fa fa-reply blue pull-right">返回</a>
    <div class="clearfix"></div>
</div>
[/#if]
[#include "/org/beangle/print/action/printDesigner/index.ftl"/]