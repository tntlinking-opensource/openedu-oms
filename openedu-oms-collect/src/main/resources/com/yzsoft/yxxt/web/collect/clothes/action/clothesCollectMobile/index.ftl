[#ftl]
[#import "/template/mobile.ftl" as m/]
[@m.body title=switch.name back=b.url("/mobile/collect/index?t=1")]
<div class="yx-xxtx">
    <ul data-role="listview" class="yx-xxtx-info">
        [#list clothesStudent.sizes as size]
            <li>
                <div class="yx-xxtx-info-a">
                    <span class="yx-xxtx-info-a-left">${(size.type.name)!}</span>
                    <span class="yx-xxtx-info-a-right">${(size.size.name)!}</span>
                </div>
            </li>
        [/#list]
    </ul>
    [#if switch?? && switch.editable && !financeOrder??]
        <div class="yx-jiange-1em"></div>
        <div class="yx-model-btnmodel">
            <a href="${b.url("!edit")}" data-role="button" class="yx-model-bluebtn">修改</a>
        </div>
    [/#if]
</div>
[/@]