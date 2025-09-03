[#ftl]
[#if batchs?size == 0]
<style>
    .page-content-wrapper .page-content.xxx{margin-left: 0;}
</style>
<div class="page-content-wrapper">
    <div class="page-content xxx">
        <div class="jumbotron" style="text-align: center; min-height: 600px; padding: 100px;">
            <h1>迎新工作已结束</h1>
        </div>
    </div>
</div>
[#else ]
<link href="${base}/static/yxxt/welcome/css/main2.css" rel="stylesheet" type="text/css"/>
<link href="${base}/static/yxxt/welcome/css/main4.css" rel="stylesheet" type="text/css"/>
<style>
    .mt-timeline-icon {
        margin-top: 35px;
    }
    .page-content-wrapper .page-content.xxx {
        padding: 20px;
    }
    .xxx .portlet {
        margin-bottom: 20px;
    }
</style>
<div class="page-content-wrapper">
    <div class="page-content xxx">
        <!-- BEGIN PAGE HEADER-->
        <div class="portlet box red" style="margin-bottom: 0;">
            <div class="portlet-title">
                <ul class="nav nav-tabs batch_ul">
                    [#list batchs as batch]
                        <li>
                            [@b.a href="!info?id=${batch.id}" target="batch_body"]${(batch.name)!}[/@b.a]
                        </li>
                    [/#list]
                </ul>
            </div>
            <div class="portlet-body">
                <div class="tab-content margin-top-20">
                    <div class="tab-pane active">
                        [@b.div id="batch_body"/]
                    </div>
                </div>
            </div>
            <script>
                $(function () {
                    $(".batch_ul li").click(function () {
                        $(this).addClass("active").siblings().removeClass("active");
                    });
                    $(".batch_ul a:first").click();
                });
            </script>
        </div>
        <!-- END PAGE HEADER-->
    </div>
</div>
[/#if]
<script>
    $(window).trigger("resize");
</script>
