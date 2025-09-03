[#ftl]
[@b.head/]
[@b.head title="流程环节项"/]
<div style="margin: 0; position: relative; float: right;">
    <div style="position: absolute; right: 0;">
    [#if processBatchId??]
        [@b.a href="enroll-process-batch!processLinkItemIndex?process.id=${process.id!}&processBatch.id=${(processBatch.id)!}&processBatchId=${processBatchId!}"
        class="btn btn-lg fa fa-reply blue" target="main"]返回[/@]
    [#else]
        [@b.a href="enroll-process!processLinkItemIndex?process.id=${process.id!}"
        class="btn btn-lg fa fa-reply blue" target="main"]返回[/@]
    [/#if]
    </div>
</div>
[@b.tabs theme="tab/tabFirstHref"]
    [@b.tab label="基本设置" title="active" href="!edit?process.id=${process.id!}&processLinkItem.id=${(processLinkItem.id)!}&processBatch.id=${(processBatch.id)!}&step=${(step)!}"/]
    [#if processLinkItem?? && processLinkItem.id??]
        [@b.tab label="业务项配置" href="process-link-item-form?processLinkItemId=${(processLinkItem.id)!}&processBatchId=${(processBatch.id)!}"/]
        [#if processLinkItem.printed]
            [@b.tab label="打印项配置" href="process-link-item-print?processLinkItem.id=${(processLinkItem.id)!}&processBatchId=${(processBatch.id)!}"/]
        [/#if]
    [/#if]
[/@]
[@b.foot/]