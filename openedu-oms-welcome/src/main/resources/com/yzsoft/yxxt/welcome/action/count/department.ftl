[#ftl]
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
    <meta charset="utf-8"/>
    <title>${(batch.name)}明细统计</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1" name="viewport"/>
    <meta content="" name="description"/>
    <meta content="" name="author"/>
    <!-- BEGIN GLOBAL MANDATORY STYLES -->
    <link href="${base}/static/metronic/assets/global/plugins/font-awesome/css/font-awesome.min.css"
          rel="stylesheet" type="text/css"/>
    <link href="${base}/static/metronic/assets/global/plugins/bootstrap/css/bootstrap.min.css"
          rel="stylesheet" type="text/css"/>
    <!-- END GLOBAL MANDATORY STYLES -->
    <link href="${base}/static/metronic/assets/global/css/components.min.css"
          rel="stylesheet" id="style_components" type="text/css"/>
    <link href="${base}/static/metronic/assets/yz_yxwz_css/main2.css" rel="stylesheet" type="text/css"/>
    <link href="${base}/static/yxxt/welcome/css/statistic.css" rel="stylesheet" type="text/css"/>

</head>
<!-- END HEAD -->

<body>

<div class="page-container">

    <div class="container">
        <h3 class="progress-title-detail text-center">${(batch.name)}明细统计</h3>
        <p class="progress-title-total">
            录取人数：<span>${studentNum!0}</span>人，
            已报到：<span>${completeNum!0}</span>人（${(completePercent!0)?string.percent}），
            未报到：<span>${unCompleteNum!0}</span>人（${(unCompletePercent!0)?string.percent}）</p>
        <div class="statisticdetail-table">
            <table cellpadding="0" cellspacing="0" border="0" width="100%">
                <thead>
                <tr>
                    <th width="20%">院系名称</th>
                    <th width="10%">录取人数</th>
                [#list items as item]
                    <th>${(item.name)!}</th>
                [/#list]
                </tr>
                </thead>
                <tbody>
                [#list processBatchCounts as count]
                <tr>
                    <td>${(count.departmentName)!}</td>
                    <td>${(count.total)!}</td>
                    [#list items as item]
                        [#assign detail = emptyDetail/]
                        [#list count.details as d]
                            [#if d.itemId == item.id]
                                [#assign detail = d/]
                                [#break /]
                            [/#if]
                        [/#list]
                        <td>${(detail.num)!0}（${(detail.percent)!0}%）</td>
                    [/#list]
                </tr>
                [/#list]
                </tbody>
            </table>
        </div>

        <div class="text-center" style="clear:both; margin-bottom: 50px;">
            <a href="${b.url("!department")}&id=${batch.id}" class="btn btn-lg green-meadow">刷新(<span class="span_time">9</span>s)</a>
        </div>
    </div>

</div>
<script src="${base}/static/metronic/assets/global/plugins/jquery.min.js" type="text/javascript"></script>
<!-- END CORE PLUGINS -->
<script type="text/javascript">
    $(function () {
        var time = 9;
        var timer = setInterval(function () {
            time--;
            $(".span_time").html(time);
            if (time == 0) {
                location.reload();
                clearInterval(timer);
            }
        }, 1000);
    });
</script>
</body>
</html>