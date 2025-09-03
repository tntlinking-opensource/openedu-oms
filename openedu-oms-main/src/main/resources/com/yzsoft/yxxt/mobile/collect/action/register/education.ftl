[#ftl]
[#import "/template/mobile.ftl" as m/]
[#include "macro.ftl"/]
[@m.body title="教育经历" back=b.url("!index")]
<style>
    .empty_div {
        text-align: center; padding: 0.5em; background-color: #fff;
    }
</style>
<div class="yx-xxtx01">
    <h3 class="yx-xxtx-title">教育经历信息</h3>
    [#if switch?? && switch.editable]
        <a href="${b.url("!educationEdit")}" data-role="button" data-inline="true" class="yx-xxtx-bj">添加</a>
    [/#if]
    [#if educations?? && educations?size gt 0]
        <ul data-role="listview" class="yx-xxtx-data">
            <li>
                <div class="yx-xxtx-data-a ui-grid-a">
                    <span class="ui-block-a yx-xxtx-data-a-title">起止时间</span>
                    <span class="ui-block-b yx-xxtx-data-a-title">教育经历</span>
                </div>
            </li>
            [#list educations as e]
                [#if switch?? && switch.editable]
                <li>
                    <a href="${b.url("!educationInfo")}&id=${e.id}"
                       class="yx-xxtx-data-a ui-grid-a"
                       data-transition="slide">
                        <span class="ui-block-a">
                        ${(e.startDate?string("yyyy.M"))!} - ${(e.endDate?string("yyyy.M"))!}
                        </span>
                        <span class="ui-block-b">${(e.unit)!}</span>
                    </a>
                </li>
                [#else]
                <li style="padding: 0;">
                    <div class="yx-xxtx-data-a ui-grid-a">
                        <span class="ui-block-a">
                        ${(e.startDate?string("yyyy.M"))!} - ${(e.endDate?string("yyyy.M"))!}
                        </span>
                        <span class="ui-block-b">${(e.unit)!}</span>
                    </div>
                </li>
                [/#if]
            [/#list]
        </ul>
    [#else]
        <div class="empty_div">暂无信息</div>
    [/#if]
</div>
[/@]