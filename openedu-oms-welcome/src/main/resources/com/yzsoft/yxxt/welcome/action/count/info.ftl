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
    <title>${(batch.name)}汇总统计</title>
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
<div id="loginbg" style="position:absolute; z-index:-1;">
    <img src="${base}/static/yxxt/welcome/css/images/login_bg.jpg" height="100%" width="100%">
</div>
<!-- BEGIN HEADER -->

<!-- END HEADER -->
<div class="page-container">

    <div class="container">
        <h3 class="progress-title text-center">${(batch.name)}汇总统计</h3>

        <p class="pull-right">总人数：<strong class="font-blue">${studentNum!0}</strong></p>

        <style>
            .progress .progress-bar { min-width: 15%; max-width: 85%; white-space: nowrap; }
        </style>
        <div class="progress">
            <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="33"
                 aria-valuemin="0" aria-valuemax="100"
                 style="width:${(completePercent!0)}%;">
                <i class="statistic-progress">报到 ${(completePercent!0)?string("0.##")}%</i>
            </div>
            <div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="67"
                 aria-valuemin="0" aria-valuemax="100"
                 style="width:${(unCompletePercent!0)}%;">
                <i class="statistic-progress">未报到 ${(unCompletePercent!0)?string("0.##")}%</i>
            </div>
        </div>
        <div class="clearfix">
            <p>
                <img class="color-block" src="${base}/static/yxxt/welcome/css/images/color-block_03.png"/>
                报到人数<span class="bold font-green-sharp"> ${completeNum!0}</span>人
            </p>
            <p>
                <img class="color-block" src="${base}/static/yxxt/welcome/css/images/color-block_06.png"/>
                未报到人数<span class="bold font-red-haze"> ${(studentNum - completeNum)!0}</span>人
            </p>
        </div>
        <div class="text-center">
            <a href="${b.url("!info")}&id=${batch.id}" class="btn btn-lg green-meadow">
                刷新(<span class="span_time">9</span>s)
            </a>
        </div>
    </div>

</div>

<!-- BEGIN CORE PLUGINS -->
<script src="${base}/static/metronic/assets/global/plugins/jquery.min.js" type="text/javascript"></script>
<script src="${base}/static/metronic/assets/global/plugins/bootstrap/js/bootstrap.min.js"></script>
<!-- END CORE PLUGINS -->
<script type="text/javascript">
    $(function () {
        $(window).resize(function () {
            $('#loginbg').height($(window).height());
            $('#loginbg').width($(window).width());
        }).trigger("resize");

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