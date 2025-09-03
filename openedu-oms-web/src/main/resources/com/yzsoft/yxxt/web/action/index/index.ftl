[#ftl]
[#macro href url]
    [@compress]
        [#if url?starts_with("/")]${base}[/#if]${url!}
    [/@compress]
[/#macro]
[@yx.head title="首页"/]
<link href="${base}/static/metronic/assets/yz_yxwz_css/main5.css" rel="stylesheet" type="text/css"/>
<!-- 用户和大图盒子 -->
<div class="row bg_colour_1" style="margin:0;">
    <div class="col-md-10 col-md-offset-1 ">
        <div class="row" style="margin:10px;">
            <div class="portlet light" style="padding: 15px 0;">
                <div class="row">
                    <!-- 用户的信息 -->
                    <div class="col-md-3">
                    [#if user??]
                    	[#include "userinfo.ftl"/]
                	[#else]
                        [#include "login.ftl"/]
                    [/#if]
                    </div>
                    <!-- 用户的信息结束 -->
                    <!-- 大图 -->
                    <div class="col-md-9">
                        <div id="myCarousel" class="carousel slide">
                            <!-- 轮播（Carousel）指标 -->
                            <ol class="carousel-indicators">
                            [#list imgLinks as imgLink]
                                <li data-target="#myCarousel" data-slide-to="${imgLink_index}"
                                    [#if imgLink_index == 0]class="active"[/#if]></li>
                            [/#list]
                            </ol>
                            <!-- 轮播（Carousel）项目 -->
                            <div class="carousel-inner">
                            [#list imgLinks as imgLink]
                                <div class="item [#if imgLink_index == 0] active[/#if]">
                                    <a [#if imgLink.url??]href="[@href imgLink.url/]" target="_blank"
                                       [#else]style="cursor: default;" [/#if]>
                                        <img src="${base}/${(imgLink.img)!}"
                                             alt="${(imgLink.name)!}"
                                             style="width: 100%; min-height: auto; max-height: 400px;">
                                    </a>
                                </div>
                            [/#list]
                            </div>
                        </div>
                    </div>
                    <script>
                        $(function () {
                            $("#myCarousel").carousel('cycle');
                        });
                    </script>
                    <!-- 大图结束 -->
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 用户和大图盒子结束 -->
<!-- 列表和快捷盒子-->
<div class="row bg_colour_2" style="margin:0;">
    <div class="col-md-10 col-md-offset-1">
        <div class="row" style="margin:10px;">
            <!-- 列表 -->
            <div class="col-sm-8" style="padding:0;">
                <div class="portlet-title border_style_01">
                    <div class="caption"><span
                            class="caption-subject font-dark  uppercase new_style">${(column.name)!"新闻资讯"}</span>
                        <a href="${base}/web/content-list.action?columnId=${(column.id)!}"><span
                                class="more_style">更多</span></a>
                    </div>
                </div>
                <ul class="list-group">
                [#if contents??]
                    [#list contents as c]
                        <li class="list-group-item list-group-item_1">
                            <a href="${base}/web/content-detail.action?contentId=${(c.id)!}"
                               class="[#if c.stick]font-red [/#if]font-lg">
                                <i>&middot;&emsp;</i>${(c.title)!}
                                <span class="date_right">${(c.publishTime?date)!}</span>
                            </a>
                        </li>
                    [/#list]
                [#else]
                    [#list 1..8 as v]
                        <li class="list-group-item list-group-item_1">
                            <a href="#" class="[#if v lte 3]font-red [/#if]font-lg"><i>&middot;&emsp;</i>中央党校陈述教授作十八届六中全会精神辅导报告
                                <span class="date_right">2016-06-24</span></a>
                        </li>
                    [/#list]
                [/#if]

                </ul>
            </div>
            <!-- 列表结束 -->
            <!-- 快捷功能 -->
            <div class="col-sm-4">
            [#include "iconLink.ftl"/]
            </div>
            <!-- 快捷功能结束 -->
        </div>
    </div>
</div>
<!-- 列表和快捷盒子结束-->
[#if welcomeLinks?? && welcomeLinks?size gt 0]
<!-- 流程图盒子 -->
<div class="row bg_colour_3" style="margin:0;">
    <div class="col-md-10 col-md-offset-1">
        <div class="row" style="margin:10px;">
            <div class="portlet-title border_style_01">
                <div class="caption"><span
                        class="caption-subject font-dark  uppercase new_style">学校报到流程</span><a href="#"></a>
                </div>
            </div>
            <div class="portlet-body">
            [#--                <div class="row">
                                [#assign size=(12 / welcomeLinks?size)?floor/]
                                [#list welcomeLinks as welcomeLink]
                                    <div class="col-md-${size} [#if welcomeLinks?size == 5 && welcomeLink_index == 0]col-md-offset-1[/#if]"
                                         style="margin-top:15px; padding:0;">
                                        <div class="pie-chart">
                                            <a href="[@href (welcomeLink.url)!"#"/]">
                                                <div class="lc_bg_0${welcomeLink_index + 1}">
                                                    <p class="lc_font"
                                                       style="width: 160px; text-align: center; padding-left: 0;">${welcomeLink.name!}</p>
                                                </div>
                                            </a>
                                        </div>
                                    </div>
                                [/#list]
                            </div>--]
                <div class="lc_box" style="width:100%; float:left; margin-top:20px;">
                    <ul style="margin:0; padding:0; float:left;" class="lc_ul_${welcomeLinks?size}">
                        [#list welcomeLinks as welcomeLink]
                            <li>
                                <a href="[@href (welcomeLink.url)!"#empty"/]" target="_blank">
                                    <img src="${base}/static/metronic/assets/yz_yxwz_css/liucheng_bg_${welcomeLink_index + 1}.png"
                                         class="lc_img_[#if welcomeLink_index == 0]1[#else]2[/#if]"/>
                                    <p class="lc_font_[#if welcomeLink_index == 0]1[#else]2[/#if]">${welcomeLink.name!}</p>
                                </a>
                            </li>
                        [/#list]
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 流程图盒子结束 -->
[/#if]
<!-- 连接盒子 -->
<style>
    .foot_link .links .col-md-6{padding: 5px 15px;}
</style>
<div class="row bg_colour_4 foot_link" style="margin:0;">
    <div class="col-md-10 col-md-offset-1">
        <div class="row" style="margin:10px;">
        [#list footLinkGroups as group]
            <div class="col-md-6 links">
                <div class="row">
                    <div class="col-md-6 table_font_bold">${group.name!}</div>
                </div>
                <div class="row">
                    [#list footLinks as footLink]
                        [#if footLink.group.id == group.id]
                            <div class="col-md-6">
                                <a href="[@href (footLink.url)!"#"/]" class="table_font"
                                   target="_blank">${(footLink.name)!}</a>
                            </div>
                        [/#if]
                    [/#list]
                </div>
            </div>
        [/#list]
        [#--            <div class="col-md-4">
                        <table id="user" class="table" style="margin-bottom:0; float:left; margin-top:20px;">
                            <tbody>
                            <tr>
                                <td style="border:none;" class="table_font_bold">
                                <img src="${base}/static/metronic/assets/yz_yxwz_css/QR Code.png">
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>--]
        </div>
    </div>
</div>
<!-- 连接盒子结束 -->
[@yx.foot/]