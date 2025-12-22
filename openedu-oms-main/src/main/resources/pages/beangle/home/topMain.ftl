[#ftl]
<div class="page-header navbar navbar-fixed-top">
    <!-- BEGIN HEADER INNER -->
    <div class="page-header-inner ">
        <!-- BEGIN LOGO -->
        <div class="page-logo">
            <div style="display: flex; align-items: center; color: #fff;">
                <a href="index.html">
                    <img src="${base}/static/images/logo1.png" alt="logo" class="logo-default">
                </a>
                校园迎新系统
            </div>
            <div class="menu-toggler sidebar-toggler">
                <span></span>
            </div>
        </div>
        <!-- END LOGO -->

        <!--begin main nav-->
        [#if menus??&&menus?size=1]
            <style>
                .top_menu { display: none; }
            </style>
        [/#if]
        [#if menus??&&menus?size>0]
            <ul class="dropdown top_menu" style="margin-top:0px;">
                [#list menus?if_exists as module]
                    <li class="first_li">
                        [@showModule module/]
                    </li>
                [/#list]
            </ul>
        [/#if]

        [#macro showModule module]
            [#if module.entry??]
                [#assign target = "body"/]
                [#if module.entry?contains("_top")]
                    [#assign target = "_top"/]
                [#elseif module.entry?contains("_blank")]
                    [#assign target = "_blank"/]
                [/#if]
                [@b.a href= module.entry target=target class="first_a"][@c.i18nNameTitle module/][/@]
            [#else]
                [@b.a href="!menus?menu.id=${module.id}" target="body" class="first_a"][@c.i18nNameTitle module/][/@]
            [/#if]
        [/#macro]

        <!--end main nav-->

        <!-- BEGIN RESPONSIVE MENU TOGGLER -->
        <a href="javascript:;" class="menu-toggler responsive-toggler" data-toggle="collapse"
           data-target=".navbar-collapse">
            <span></span>
        </a>
        <!-- END RESPONSIVE MENU TOGGLER -->

        <!-- BEGIN TOP NAVIGATION MENU -->
        <div class="top-menu">
            <ul class="nav navbar-nav pull-right">
                [#if user.categories?size gt 1]
                    <li class="dropdown dropdown-user" style="margin-right:0px;">
                        <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown"
                           data-close-others="true">
                            [#list user.categories as c]
                                [#if c.id == categoryId]
                                    <span class="username username-hide-on-mobile">${c.name}</span>
                                [/#if]
                            [/#list]
                            <i class="fa fa-angle-down"></i>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-default">
                            [#list user.categories as c]
                                [#if c.id != categoryId]
                                    <li>
                                        <a href="${base}/home.action?security.categoryId=${c.id}">${c.name} </a>
                                    </li>
                                [/#if]
                            [/#list]
                        </ul>
                    </li>
                [/#if]
                <!-- BEGIN USER LOGIN DROPDOWN -->
                <!-- DOC: Apply "dropdown-dark" class after below "dropdown-extended" to change the dropdown styte -->
                <li class="dropdown dropdown-user" style="margin-right:0px;">
                    <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown"
                       data-close-others="true">
                        <img alt="" class="img-circle" src="${base}/avatar/my.action?user.name=${(user.name)!}">
                        <span class="username username-hide-on-mobile">${user.fullname}</span>
                        <i class="fa fa-angle-down"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-menu-default">
                        <li>
                            [@b.a href="/security/my" target="main"]
                                <i class="icon-user"></i>我的资料
                            [/@]
                        </li>
                        [#--<li>
                            <a href="app_calendar.html">
                                <i class="icon-calendar"></i>我的日历</a>
                        </li>
                        <li>
                            <a href="app_inbox.html">
                                <i class="icon-envelope-open"></i>收件箱
                                <span class="badge badge-danger"> 3 </span>
                            </a>
                        </li>
                        <li>
                            <a href="app_todo.html">
                                <i class="icon-rocket"></i>待办事宜
                                <span class="badge badge-success"> 7 </span>
                            </a>
                        </li>--]
                        <li class="divider"></li>
                        <li><a href="#" onclick="setTheme();return false;">
                                <i class="icon-settings"></i> 主题设置</a>
                        </li>
                        <li class="divider"></li>
                        <li><a href="#" onclick="w.modifyPassword(this)"><i class="fa fa-lock"></i> 修改密码</a></li>
                        <li><a href="${base}/logout.action"><i class="icon-key"></i>注销</a></li>
                    </ul>
                </li>
                <!-- END USER LOGIN DROPDOWN -->
                [#include "styleCustomizer.ftl"]
            </ul>
        </div>
        <!-- END TOP NAVIGATION MENU -->
    </div>
    <!-- END HEADER INNER -->
</div>
<script>
    $(function () {
//		$(".top_menu a:first").addClass("current_hit").click();
        $(".top_menu a").click(function () {
            $(".top_menu a.current_hit").removeClass("current_hit");
            $(this).addClass("current_hit");
        });
        $(".top_menu a:first").trigger("click");
        [#if menus??&&menus?size=1]
        $(".top_menu a:first").hide();
        [/#if]
    });
</script>
