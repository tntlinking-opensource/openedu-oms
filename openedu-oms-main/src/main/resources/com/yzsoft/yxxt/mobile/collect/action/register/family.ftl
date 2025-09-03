[#ftl]
[#import "/template/mobile.ftl" as m/]
[#include "macro.ftl"/]
[@m.body title="家庭成员" back=b.url("!index")]
<style>
    .empty_div {
        text-align: center; padding: 0.5em; background-color: #fff;
    }
</style>
<div class="yx-xxtx01">
    <h3 class="yx-xxtx-title">家庭成员信息</h3>
    [#if switch?? && switch.editable]
        <a href="${b.url("!familyEdit")}&cardcode=${(student.cardcode)!}" data-role="button" data-inline="true" class="yx-xxtx-bj">添加</a>
    [/#if]
    [#if families?? && families?size gt 0]
        <ul data-role="listview" class="yx-xxtx-data">
            <li>
                <div class="yx-xxtx-data-a ui-grid-b">
                    <span class="ui-block-a yx-xxtx-data-a-title">姓名</span>
                    <span class="ui-block-b yx-xxtx-data-a-title">称谓</span>
                    <span class="ui-block-c yx-xxtx-data-a-title">联系电话</span>
                </div>
            </li>
            [#list families as f]
                [#if f.name??]
                    [#if switch?? && switch.editable]
                        <li>
                            <a href="${b.url("!familyInfo")}&id=${f.id}"
                               class="yx-xxtx-data-a ui-grid-b"
                               data-transition="slide">
                                <span class="ui-block-a">${(f.name)!}</span>
                                <span class="ui-block-b">${(f.title)!}</span>
                                <span class="ui-block-c">${(f.phone)!}</span>
                            </a>
                        </li>
                    [#else]
                        <li style="padding: 0;">
                            <div class="yx-xxtx-data-a ui-grid-b">
                                <span class="ui-block-a">${(f.name)!}</span>
                                <span class="ui-block-b">${(f.title)!}</span>
                                <span class="ui-block-c">${(f.phone)!}</span>
                            </div>
                        </li>
                    [/#if]
                [/#if]
            [/#list]
        </ul>
    [#else]
        <div class="empty_div">暂无信息</div>
    [/#if]
</div>
[/@]