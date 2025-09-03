[#ftl]
[#import "/template/mobile.ftl" as m/]
[#include "../comm/macro.ftl"/]
[@m.body title=switch.name back=b.url("index?t=1")]
<style>

</style>
    [#if suppliesStd.items?size gt 0]
    <h3 class="yx-xxtx-title">已订购的物品</h3>
    <div class="yx-xxtx">
        <ul data-role="listview" class="yx-xxtx-data">
            <li>
                <div class="yx-xxtx-data-a ui-grid-b">
                    <span class="ui-block-a yx-xxtx-data-a-title">物品名称</span>
                    <span class="ui-block-b yx-xxtx-data-a-title">单价（元）</span>
                    <span class="ui-block-c yx-xxtx-data-a-title">数量</span>
                </div>
            </li>
            [#list suppliesStd.items as item]
                <li>
                    [#if switch?? && switch.editable]
                        <a href="${b.url("!edit")}&id=${item.supplies.id}" data-transition="slide"
                           class="yx-xxtx-data-a yx-xxtx-data-a-hover ui-grid-b">
                            <span class="ui-block-a">${item.supplies.name}</span>
                            <span class="ui-block-b">${item.price!}</span>
                            <span class="ui-block-b">${item.num!}</span>
                        </a>
                    [#else]
                        <span class="yx-xxtx-data-a ui-grid-b">
                            <span class="ui-block-a">${item.supplies.name}</span>
                            <span class="ui-block-b">${item.price!}</span>
                            <span class="ui-block-b">${item.num!}</span>
                        </span>
                    [/#if]
                </li>
            [/#list]
            <li class="yx-bg-lightblue">
                <div class="yx-model-zx">
                    <span class="yx-word-right">合计${suppliesStd.total!}元</span>
                </div>
            </li>
        </ul>
    </div>
    [/#if]
    [#if switch?? && switch.editable && suppliesStd.items?size lte suppliess?size]
    <h3 class="yx-xxtx-title">可以选购的物品</h3>
    <div class="yx-xxtx">
        <ul data-role="listview" class="yx-xxtx-data">
            <li>
                <div class="yx-xxtx-data-a ui-grid-b">
                    <span class="ui-block-a yx-xxtx-data-a-title">物品名称</span>
                    <span class="ui-block-b yx-xxtx-data-a-title">单价（元）</span>
                    <span class="ui-block-c yx-xxtx-data-a-title">限购数量</span>
                </div>
            </li>
            [#function hasSupplies supplies]
                [#list suppliesStd.items as item]
                    [#if item.supplies.id == supplies.id]
                        [#return true/]
                    [/#if]
                [/#list]
                [#return false/]
            [/#function]
            [#list suppliess as supplies]
                [#if !hasSupplies(supplies)]
                    <li>
                        <a href="${b.url("!edit")}&id=${supplies.id}" data-transition="slide"
                           class="yx-xxtx-data-a yx-xxtx-data-a-hover ui-grid-b">
                            <span class="ui-block-a">${supplies.name}</span>
                            <span class="ui-block-b">${supplies.price!}</span>
                            <span class="ui-block-b">${supplies.maxNum!}</span>
                        </a>
                    </li>
                [/#if]
            [/#list]
        </ul>
    </div>
    [#elseif suppliesStd.items?size == 0]
        [@emptyMessage/]
    [/#if]
[/@]