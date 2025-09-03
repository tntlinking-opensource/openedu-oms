[#ftl]
[#import "/template/mobile.ftl" as m/]

[@m.body title="现场报到" back="back" cache="true"]
    [#assign steps = ["", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十"]/]
[#--步骤是否完成--]
    [#function finished step]
        [#list items as item]
            [#if item.step == step && item.mustPassed]
                [#if !finishedItem(item)]
                    [#return false/]
                [/#if]
            [/#if]
        [/#list]
        [#return true/]
    [/#function]
    [#function finishedItem item]
        [#list logs as log]
            [#if log.item.id == item.id && log.enabled]
                [#return true/]
            [/#if]
        [/#list]
        [#return false/]
    [/#function]
<!--流程图-->
[#if items??]
<div class="intro-flow">
    <div style="padding:10px 5px 0px 5px; width:97.4%; float:left; margin-bottom:35px;">
        [#list 1..process.step as step]
            <div class="intro-list-right [#if finished(step)]finish[/#if]">
                <span class="span_color"></span>
                <div class="intro-list-left">步骤${steps[step]}</div>
                <div class="intro-list-content">
                    <ul>
                        [#list items as item]
                            [#if item.step == step]
                                [#if finishedItem(item)]
                                    <li class="li_color_1">${(item.name)!}<i></i></li>
                                [#else]
                                    <li class="li_color_2">${(item.name)!}</li>
                                [/#if]
                            [/#if]
                        [/#list]
                    </ul>
                </div>
            </div>
        [/#list]
    </div>
[/#if]
[#--    <div class="yx-btnarea">
        <a href="${b.url("!info")}" style="width:auto" data-role="button" data-inline="true" class="yx-btnarea-btnb" data-transition="slide">查看我的二维码</a>
    </div>--]
</div>
[/@]