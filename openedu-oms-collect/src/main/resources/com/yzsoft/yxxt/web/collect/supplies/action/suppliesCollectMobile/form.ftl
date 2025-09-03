[#ftl]
[#import "/template/mobile.ftl" as m/]
[#include "/com/yzsoft/yxxt/mobile/collect/action/comm/macro.ftl"/]
[@m.body title=switch.name back=b.url("/mobile/collect/index?t=1")]
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
    <div class="yx-model-btnmodel" style="margin-top: 15px;">
        <div class="ui-grid-a">
            <div class="ui-block-a">
                <a href="${b.url("!save?agree=1")}" data-role="button" class="yx-model-bluebtn" data-ajax="false"
                   style="letter-spacing:0; text-indent: 0;">我要购买</a>
            </div>
            <div class="ui-block-b">
                <a href="${b.url("!save?agree=0")}" data-role="button" class="yx-model-redbtn" data-ajax="false"
                   style="letter-spacing:0;text-indent: 0;">我不购买</a>
            </div>
        </div>
    </div>
    [#if collectSwitch?? && collectSwitch.remark??]
    <div style="margin: 15px;">
        <div class="alert alert-info" role="alert">注意事项：${(collectSwitch.remark)!}</div>
    </div>
    [/#if]
[/@]