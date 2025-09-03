[#ftl]
[#import "/template/mobile.ftl" as m/]
[#include "/com/yzsoft/yxxt/mobile/collect/action/comm/macro.ftl"/]
[@m.body title=switch.name back=b.url("/mobile/collect/index?t=1")]
    [#if suppliesStd.items?size gt 0]
    <h3 class="yx-xxtx-title" style="text-align: center;">您已选择了购买生活用品</h3>
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
                    <span class="yx-xxtx-data-a ui-grid-b">
                        <span class="ui-block-a">${item.supplies.name}</span>
                        <span class="ui-block-b">${item.price!}</span>
                        <span class="ui-block-b">${item.num!}</span>
                    </span>
                </li>
            [/#list]
            <li class="yx-bg-lightblue">
                <div class="yx-model-zx">
                    <span class="yx-word-right">合计${suppliesStd.total!}元</span>
                </div>
            </li>
        </ul>
    </div>
        [#if switch?? && switch.editable]
        <div class="yx-jiange-1em"></div>
        <div class="yx-model-btnmodel">
            <a href="${b.url("!save?agree=0")}" data-role="button" class="yx-model-redbtn" data-ajax="false">我不购买</a>
        </div>
        [/#if]
    [#else]
    <h3 class="yx-xxtx-title" style="text-align: center; color: red; background-color: #fff; padding: 1em;">您已选择了不购买生活用品</h3>
        [#if switch?? && switch.editable]
        <div class="yx-jiange-1em"></div>
        <div class="yx-model-btnmodel">
            <a href="${b.url("!save?agree=1")}" data-role="button" class="yx-model-bluebtn" data-ajax="false">我要购买</a>
        </div>
        [/#if]
    [/#if]
[/@]