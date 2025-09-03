[#ftl]
[#import "/template/comm.ftl" as c/]
[#import "/template/mobile.ftl" as m/]
[#include "../comm/macro.ftl"/]
[@m.body title=switch.name back=b.url("index?t=1")]
<div class="yx-xxtx">
    <ul data-role="listview" class="yx-xxtx-info">
        [#assign nums=["一","二","三","四","五","六"]/]
        [#list studentMajor.details as detail]
            <li>
                <div class="yx-xxtx-info-a">
                    <span class="yx-xxtx-info-a-left">第${nums[detail_index]}志愿</span>
                    <span class="yx-xxtx-info-a-right">${(detail.major.name)!}</span>
                </div>
            </li>
        [/#list]
        [#if config.showAdjust]
            <li>
                <div class="yx-xxtx-info-a">
                    <span class="yx-xxtx-info-a-left">是否服从调剂</span>
                    <span class="yx-xxtx-info-a-right">${((studentMajor.alterable!true)?string("是", "否"))!}</span>
                </div>
            </li>
        [/#if]
    </ul>

    [#if switch?? && switch.editable]
        <div class="yx-jiange-1em"></div>
        <div class="yx-model-btnmodel">
            <a href="${b.url("!edit")}" data-role="button" class="yx-model-bluebtn">修改</a>
        </div>
    [/#if]
</div>
[/@]