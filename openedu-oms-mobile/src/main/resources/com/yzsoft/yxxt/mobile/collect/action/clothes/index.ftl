[#ftl]
[#import "/template/mobile.ftl" as m/]
[@m.body title=switch.name back=b.url("index?t=1")]
<div class="yx-xxtx">
    <ul data-role="listview" class="yx-xxtx-info">
        <li>
            <div class="yx-xxtx-info-a">
                <span class="yx-xxtx-info-a-left">服装尺码</span>
                <span class="yx-xxtx-info-a-right">${(clothesStudent.clothesSize)!}</span>
            </div>
        </li>
        <li>
            <div class="yx-xxtx-info-a">
                <span class="yx-xxtx-info-a-left">鞋子尺码</span>
                <span class="yx-xxtx-info-a-right">${(clothesStudent.shoesSize)!}</span>
            </div>
        </li>
    </ul>
    [#if switch?? && switch.editable]
        <div class="yx-jiange-1em"></div>
        <div class="yx-model-btnmodel">
            <a href="${b.url("!edit")}" data-role="button" class="yx-model-bluebtn">修改</a>
        </div>
    [/#if]
</div>
[/@]