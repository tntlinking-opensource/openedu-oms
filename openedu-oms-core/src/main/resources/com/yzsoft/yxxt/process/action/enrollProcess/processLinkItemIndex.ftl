[#ftl]
[#include "macro.ftl"/]

[#if !process.submited]
    [#assign editMode = true/]
[/#if]

[@b.head/]
<style>
    .color-demo .color-view {
        padding: 0;
        font-size: 14px;
        text-align: center;
    }
    .color-demo {
        padding: 0 20px 20px;
    }
    .timeline .timeline-body {
        margin-top: 0;
    }
    .timeline:before {
        top: 75px;
    }
    .timeline .timeline-body .bg-white {
        margin: 0;
    }
    .table.table-striped.table-bordered > tbody > tr > td.table_padding_42 {
        padding-left: 42px;
        padding-right: 42px;
    }
</style>
[#if processBatchId??]
<div class="pull-right" style="position: relative;">
    <div style="position: absolute; right: 0; top:0px;">
        [@b.a href="!processBatchSetting?processBatchId=${processBatchId!}"
        class="btn btn-lg fa fa-reply blue" target="main"]返回[/@]
    </div>
</div>
[/#if]
<div class="row">
    <div class="col-md-9">
    [@b.div href="!processLinkItemEdit?processId=${processId}&processBatchId=${processBatchId!}"/]
    </div>
    <div class="col-md-3" style="padding-left:0; padding-right:0; margin-top: 55px;">
        <div class="color-demo bg-white margin-top-20">
            <h3 class="color-view bg-font-white bold">系统参数</h3>
        [#if editMode??]
            [@b.a href="process-link?backurl=${b.url('!processLinkItemIndex')?url}%26processId=${processId}" class="btn btn-default btn-block margin-top-10" target="main"]
                环节数据维护
            [/@b.a]
        [/#if]
            <button type="button" class="btn btn-default btn-block margin-top-10 process_form_preview">预览业务项配置</button>
        [#if editMode??]
            [@b.a href="process-print-config?backurl=${b.url('!processLinkItemIndex')?url}%26processId=${processId}%26processBatchId=${processBatchId!}" class="btn btn-default btn-block margin-top-10" target="main"]
                环节业务打印项配置
            [/@b.a]
        [/#if]
        </div>
    [@b.div id="mutexs_div" href="process-link-item-mutex?processId=${processId}"/]
    [@b.div id="closes_div" href="process-link-item-close?processId=${processId}"/]
    </div>
</div>
<div id="myModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<script>
    $(function () {
    [#if editMode??]
        $("#mutexs_div").click(function () {
            $('#myModal').modal({
                remote: "${b.url("process-link-item-mutex!edit")}&processId=${processId}",
            });
        });
        $("#closes_div").click(function () {
            $('#myModal').modal({
                remote: "${b.url("process-link-item-close!edit")}&processId=${processId}",
            });
        });
    [#else]
        setTimeout(function () {
            $("#mutexs_div button, #closes_div button").addClass("disabled");
        }, 1000);
    [/#if]
        $(".process_form_preview").click(function () {
            $('#myModal').modal({
                remote: "${b.url("process-link-item-form!info")}&processId=${processId}",
            });
        });
    });
</script>
[@b.foot/]