[#ftl]
[#include "columnMacro.ftl"/]
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
    <meta charset="utf-8"/>
    <title>迎新系统[#if tag .title??] - ${tag.title!}[/#if]</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1" name="viewport"/>
    <meta content="" name="description"/>
    <meta content="" name="author"/>
    <!-- BEGIN GLOBAL MANDATORY STYLES -->
    <link href="${base}/static/metronic/assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
    <link href="${base}/static/metronic/assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <!-- END GLOBAL MANDATORY STYLES -->
    <!-- BEGIN THEME GLOBAL STYLES -->
    <link href="${base}/static/metronic/assets/global/css/components.min.css" rel="stylesheet" id="style_components" type="text/css"/>
    <link href="${base}/static/metronic/assets/global/css/plugins.min.css" rel="stylesheet" type="text/css"/>
    <!-- END THEME GLOBAL STYLES -->
    <!-- BEGIN THEME LAYOUT STYLES -->
    <link href="${base}/static/metronic/assets/layouts/layout/css/layout.min.css" rel="stylesheet" type="text/css"/>
    <link href="${base}/static/metronic/assets/layouts/layout/css/themes/darkblue.min.css" rel="stylesheet"
          type="text/css" id="style_color"/>
    <!-- END THEME LAYOUT STYLES -->
    <link href="${base}/static/metronic/assets/yz_yxwz_css/main2.css" rel="stylesheet" type="text/css"/>
    <link href="${base}/static/metronic/assets/yz_yxwz_css/main4.css" rel="stylesheet" type="text/css"/>
    <link href="${base}/static/metronic/assets/yz_yxwz_css/custom.css" rel="stylesheet" type="text/css"/>
    <!-- BEGIN CORE PLUGINS -->
    <script>var base = "${base}", contextPath = "${base}";</script>
    <script src="${base}/static/scripts/comm/jquery-latest.min.js" type="text/javascript"></script>
</head>
<!-- END HEAD -->
<body class="page-sidebar-closed-hide-logo page-content-white">
<!-- BEGIN HEADER -->
<div class="page-header navbar">
    <!-- BEGIN HEADER INNER -->
    <div class="page-header-inner ">
        <div class="row">
            <div class="col-md-10 col-md-offset-1">
                <div class="page-logo">
                    <a href="${base}/index.jsp">
                        <img src="${base}/static/images/logo3.png" alt="Logo" style="max-height: 50px;"/>
                    </a>
                </div>
            [#if userid??]
                <div class="pull-right">
                    <a href="${base}/logout.action" style="color: #fff;">退出登录</a>
                </div>
            [/#if]
            </div>
        </div>
        <div class="row border_top_01">
            <div class="col-md-10 daohang_box col-md-offset-1">
                <ul class="dropdown">
                [#list topColumns as column]
                    <li class="first_li">
                        <a class="first_a" href="[@columnUrl column/]">
                            <span>${(column.name)!}</span>
                        </a>
                    </li>
                [/#list]
                </ul>
                <script>
                    $("ul.dropdown a").each(function () {
                        if ($("span", this).text() == "${tag.title!}") {
                            $(this).addClass("current_hit");
                            return false;
                        }
                    });
                </script>
            </div>
        </div>
    </div>
</div>
<div class="clearfix" style="height: 0;"></div>
<div class="page-container">
[#--    <div class="page-sidebar-wrapper">
        <div class="page-sidebar navbar-collapse collapse">
        </div>
    </div>--]

    <div class="page-content">
