[#ftl]
[@b.head/]
<div class="tabbable-custom" style="margin-bottom: 0;">
    [#if batches?size gt 0]
        <div class="pull-right">
            <select class="form-control batch_select" name="batchId">
                [#list batches as v]
                    <option value="${v.id}" [#if v.id == batchId]selected[/#if] >${v.name}</option>
                [/#list]
            </select>
        </div>
    [/#if]
    <ul class="nav nav-tabs item_log_status_count_ul" role="tablist">
        <li role="presentation" class="active">
            [@b.a href="!item?batchId=${batchId}" class="active" target="item_log_status_count_div"]按环节统计[/@b.a]
        </li>
        <li role="presentation">
            [@b.a href="!major?batchId=${batchId}" target="item_log_status_count_div"]按专业统计[/@b.a]
        </li>
        <li role="presentation">
            [@b.a href="!province?batchId=${batchId}" target="item_log_status_count_div"]按生源地统计[/@b.a]
        </li>
    </ul>
    <script>
        $(".item_log_status_count_ul li").click(function () {
            $(this).addClass("active").siblings().removeClass("active");
        }).eq(0).find("a").trigger("click");
        $(".batch_select").change(function () {
            var batchId = $(this).val();
            bg.Go("${b.url("!index")}?&batchId=" + batchId, 'main', true)
        });
    </script>
</div>
[@c.echartsJs/]
[@b.div href="!item?batchId=${batchId}" id="item_log_status_count_div"/]
[@b.foot/]