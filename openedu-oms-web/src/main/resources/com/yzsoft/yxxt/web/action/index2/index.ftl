[#ftl]

[#macro href url]
    [@compress]
        [#if url?starts_with("/")]${base}[/#if]${url!}
    [/@compress]
[/#macro]

<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
    <meta charset="utf-8"/>
    <title>首页</title>
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
    <link href="${base}/static/metronic/assets/layouts/layout/css/themes/darkblue.min.css" rel="stylesheet" type="text/css"/>
    <!-- END THEME LAYOUT STYLES -->
    <link href="${base}/static/yxxt/css/main4.css" rel="stylesheet" type="text/css"/>
    <link href="${base}/static/yxxt/css/main5.css" rel="stylesheet" type="text/css"/>
    <script src="${base}/static/scripts/comm/jquery-latest.min.js" type="text/javascript"></script>
	<link href="${base}/static/metronic/assets1/yz_css/main5.css" rel="stylesheet" type="text/css"/>
    <script src="${base}/static/scripts/colorbox/jquery.colorbox-min.js" type="text/javascript"></script>
    <link href="${base}/static/scripts/colorbox/colorbox.css" rel="stylesheet" type="text/css"/>
</head>
<!-- END HEAD -->

<body class="page-header-fixed page-sidebar-closed-hide-logo page-content-white">
<!-- BEGIN HEADER -->
<div class="page-header navbar ">
    <!-- BEGIN HEADER INNER -->
    <div class="page-header-inner ">
        <div class="row">
            <div class="container">
                <div class="page-logo"><img src="${base}/static/images/logo2.png" alt="logo" class="logo-default" height="40" style="height: 50px;"/></div>
                <div style="margin-top:7px; float: right;">
	                [#if user??]
	                    <div class="user_box">欢迎您！${user.fullname} [#if student??]同学[#elseif teacher??]老师[/#if]</div>
	                    [#if !student??]
	                        <a href="${base}/home.action" class="btn blue" style="margin-right: 10px;">后台管理</a>
	                    [/#if]
	                    <a href="${base}/logout.action" class="btn blue">退出</a>
	                [#else]
	                    <a href="${base}/login.action?backurl=${base}/web/index2.action" class="btn blue" style="margin-right:10px; width:82px;">登录</a>
	[#--                    [#if systemConfig.get("yxxt.web.register.enabled") == "true"]
	                        <a href="${base}/web/register.action" class="btn blue" style="margin-right:10px; width:82px;">注册</a>
	                    [/#if]--]
	                [/#if]
	                [#--<button type="button" class="btn blue" style="width:82px;">查找学号</button>--]
	                [#if student?? && !student.noticed]
		                <p><a class='inline' href="#inline_content">Inline HTML</a></p>
		                <div style='display:none'>
						    <div id='inline_content' style='padding:10px; background:#fff;'>
						    	<div class="content_detail" style="padding: 0 15px;">
						    		[#if contents??]
						    			${(contents[0].content)!}
						    		[/#if]
						    	</div>
						    	<p>点击“同意”，即表示您阅读并同意上述内容。</p>
						    	<button type="button" class="submit">同意</button>
					        </div>
						</div>
		                <script>
		                	 $(function () {
		                        $(".inline").colorbox({
		                        	title:"报到须知",
		                        	overlayClose:false,
		                        	inline:true,
		                        	width:"50%",
		                        	open:true,
		                        });

		                        $(".submit").click(function(){
		                        	$.ajax({
							            url: '${base}/web/index2.action?method=noticed',
							            type: 'POST'
							        });

		                        	$("#cboxClose").click();
		                        })

		                        $("#cboxClose").hide();
		                    });
		                </script>
		            [/#if]
                </div>
            </div>
        </div>

    </div>
    <!-- END HEADER INNER -->
</div>
<!-- END HEADER -->
<!-- BEGIN HEADER & CONTENT DIVIDER -->
<div class="clearfix"></div>
<!-- END HEADER & CONTENT DIVIDER -->
<!-- BEGIN CONTAINER -->
<div class="page-container">
    <!-- BEGIN SIDEBAR -->
    <div class="page-sidebar-wrapper">
        <div class="page-sidebar navbar-collapse collapse">
        </div>
    </div>
    <!-- END SIDEBAR -->
    <!-- BEGIN CONTENT -->
    <div class="page-content-wrapper">
        <!-- BEGIN CONTENT BODY -->
        <div class="page-content">
            <!-- 用户和大图盒子 -->
            <div class="row" style="margin:0;">
                <div class="col-md-12 ">
                    <div class="row">
                        <!-- 大图 -->
                        <div class="col-md-12">
                            <div class="row">
                                <div id="myCarousel" class="carousel slide">
                                    <!-- 轮播（Carousel）指标 -->
                                    <ol class="carousel-indicators">
                                    [#list imgLinks as v]
                                        <li data-target="#myCarousel" data-slide-to="${v_index}" [#if v_index==0]class="active"[/#if]></li>
                                    [/#list]
                                    </ol>
                                    <!-- 轮播（Carousel）项目 -->
                                    <div class="carousel-inner">
                                    [#list imgLinks as m]
                                        <div class="item [#if m_index==0]active[/#if]">
                                            [#if m.url??]
                                                <a href="[@href m.url/]" target="_blank">
                                                    <img src="${base}/${(m.img)!}">
                                                </a>
                                            [#else]
                                                <img src="${base}/${(m.img)!}">
                                            [/#if]
                                        </div>
                                    [/#list]
                                    </div>
                                </div>
                                <script>
                                    $(function () {
                                        $('.carousel').carousel({
                                            interval: 5000
                                        });
                                    });
                                </script>
                            </div>
                        </div>
                        <!-- 大图结束 -->
                    </div>
                </div>
            </div>
            <!-- 用户和大图盒子结束 -->
            <!-- 学生导航 -->
            <div class="row " style="margin:0;">
                <div class="container" style="margin-top:30px;">
                    <div class="row" style="margin-top:14px;">
                        <div class="col-md-12" style="padding:0;">
                            <div class="portlet-body">
                                <div class="row">
                                [#list iconLinks as v]
                                    <div class="col-md-2  text-center icon_box_width" style="float:left;">
                                        <div class="pie-chart">
                                            <a href="[@href (v.url)!"#"/]"><img src="${base}/${v.img}"></a></div>
                                        <p class="icon_font">${v.name}</p>
                                    </div>
                                [/#list]
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- 学生导航结束 -->
            <!-- 流程和快捷盒子-->
            <div class="row " style="margin:0;">
                <div class="container" style="margin-top:30px;">
                    <div class="row">
                        <div class="col-md-12" style="padding:0;">
                            <div class="row">
                                <!-- 流程 -->
							      <div class="col-md-8" style="width:63%;margin-right:3.66%;">
							        <div class="yx-model-block">
							            <p class="new_style"><img src="${base}/static/metronic/assets1/yz_img/icon-s_03.png">报到须知<a href="${base}/web/content-list.action?columnId=8" class="yx-morelink">>> 更多</a></p>
							            <div class="block-content">
							            	<ul>
							                	<!--<li>
							                    	<div class="row">
							                        	<span class="col-md-2 block-content-time">2017.05.07</span>
							                            <a href="#" class="col-md-10 block-content-title">第81期“文澜小剧场”之“京韵百花”京剧专场演出公告第81期第81期“文澜小剧场”之“京韵百花”京剧专场演出公告第81期</a>
							                        </div>
							                    </li>-->
							                     [#list  contents as content]
							                     [#if content_index<7]
							                      	<li>
								                    	<div class="row">
								                        	<span class="col-md-2 block-content-time">${(content.publishTime)!}</span>
								                            <a href="${base}/web/content-detail.action?contentId=${(content.id)!}" class="col-md-10 block-content-title">${(content.title)!}</a>
								                        </div>
								                    </li>
								                 [/#if]
							                    [/#list]
							                </ul>
							            </div>
							        </div>
							      </div>
							    <!-- 流程结束 -->

							    <!-- 快捷功能 -->
							      <div class="col-md-4" style="background-color:#f1efe3;">
							      	<div class="yx-model-block">
							        <p class="new_style"><img src="${base}/static/metronic/assets1/yz_img/icon-s_05.png">移动迎新</p>
							        <div class="block-codearea">
							        <img width="50%" height="50%" src="${base}/common/system-icon-download.action?code=	WECHAT_QR_CODE"><span class="block-notice">微信扫描二维码进入移动迎新</span>
							        </div>
							        </div>
							      <div >
                                <!-- 快捷功能结束 -->
                            </div>
                        </div>
                    </div>
                </div>
                </div>
            </div>
            <!-- 列表和快捷盒子结束-->

			<!--页脚-->
			<div class="row" style="margin-top:40px;">
			  <div class="col-md-12 footer-bg foot_info_div" style="line-height: 80px;">
                  Copyright © 2025&nbsp;湖北天天数链技术有限公司&nbsp;
                  本系统软件源代码许可来源于
                  <a href="https://open.tntlinking.com/communityTreaty" target="_blank"
                     style="white-space: nowrap;">《天天开源软件（社区版）许可协议》</a>
			   <img src="${base}/static/metronic/assets1/yz_img/btm-hw_03.png" class="bottompattern-2">
			   <img src="${base}/static/metronic/assets1/yz_img/btm-hw_04.png" class="bottompattern-1">
			  </div>
			</div>
			<!--页脚结束 -->

        </div>
        <!-- END CONTAINER -->
        <!-- BEGIN FOOTER -->

        <!-- END FOOTER -->
        <!--[if lt IE 9]>
        [#--<!--<script src="../assets/global/plugins/respond.min.js"></script>-->--]
        [#--
        <script src="../assets/global/plugins/excanvas.min.js"></script>--]
        <![endif]-->
        <!-- BEGIN CORE PLUGINS -->
    [#--<script src="../assets/global/plugins/jquery.min.js" type="text/javascript"></script>--]
        <script src="${base}/static/metronic/assets/global/plugins/bootstrap/js/bootstrap.min.js"></script>
    [#--<script src="../assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" type="text/javascript"></script>--]
        <!-- END CORE PLUGINS -->
        <!-- BEGIN PAGE LEVEL PLUGINS -->
    [#--<script src="../assets/global/plugins/morris/morris.min.js" type="text/javascript"></script>--]
        <!-- END PAGE LEVEL PLUGINS -->
    [#--<script src="../assets/global/plugins/echarts/echarts.js" type="text/javascript"></script>--]
        <!-- BEGIN THEME GLOBAL SCRIPTS -->
    [#--<script src="../assets/global/scripts/app.min.js" type="text/javascript"></script>--]
        <!-- END THEME GLOBAL SCRIPTS -->
    [#--<script src="../assets/pages/scripts/charts-echarts.min.js" type="text/javascript"></script>--]
        <!-- BEGIN THEME LAYOUT SCRIPTS -->
    [#--<script src="../assets/layouts/layout/scripts/layout.min.js" type="text/javascript"></script>--]
    [#--<script src="../assets/layouts/layout/scripts/demo.min.js" type="text/javascript"></script>--]
        <!-- BEGIN PAGE LEVEL PLUGINS -->
    [#--<script src="../assets/global/plugins/morris/morris.min.js" type="text/javascript"></script>--]
        <!-- END PAGE LEVEL PLUGINS -->

        <!-- BEGIN THEME GLOBAL SCRIPTS -->
    [#--<script src="../assets/global/scripts/app.min.js" type="text/javascript"></script>--]
        <!-- END THEME GLOBAL SCRIPTS -->

        <!-- BEGIN THEME LAYOUT SCRIPTS -->
    [#--<script src="../assets/layouts/layout/scripts/layout.min.js" type="text/javascript"></script>--]
    [#--<script src="../assets/layouts/layout/scripts/demo.min.js" type="text/javascript"></script>--]


</body>
</html>
