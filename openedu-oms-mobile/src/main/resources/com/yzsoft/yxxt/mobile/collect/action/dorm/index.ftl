[#ftl]
[#import "/template/mobile.ftl" as m/]
[@m.body title=switch.name back=b.url("index?t=1")]
<div class="yx-xxtx">
    <ul data-role="listview" class="yx-xxtx-info">
        <li>
            <div class="yx-xxtx-info-a">
                <span class="yx-xxtx-info-a-left">宿舍区</span>
                <span class="yx-xxtx-info-a-right">${(dormPlanBed.bed.room.building.zone.name)!}</span>
            </div>
        </li>
        <li>
            <div class="yx-xxtx-info-a">
                <span class="yx-xxtx-info-a-left">楼栋</span>
                <span class="yx-xxtx-info-a-right">${(dormPlanBed.bed.room.building.name)!}</span>
            </div>
        </li>
        <li>
            <div class="yx-xxtx-info-a">
                <span class="yx-xxtx-info-a-left">宿舍号</span>
                <span class="yx-xxtx-info-a-right">${(dormPlanBed.bed.room.name)!}</span>
            </div>
        </li>
        <li>
            <div class="yx-xxtx-info-a">
                <span class="yx-xxtx-info-a-left">床位号</span>
                <span class="yx-xxtx-info-a-right">${(dormPlanBed.bed.name)!}</span>
            </div>
        </li>
        <li>
            <div class="yx-xxtx-info-a">
                <span class="yx-xxtx-info-a-left">住宿标准</span>
                <span class="yx-xxtx-info-a-right">${(dormPlanBed.bed.room.rent)!}（元）</span>
            </div>
        </li>

    </ul>
</div>
    [#if switch?? && switch.editable]
    <div class="yx-jiange-1em"></div>
    <div class="yx-model-btnmodel">
        <a href="${b.url("!edit")}" data-role="button" class="yx-model-bluebtn">修改</a>
    </div>
    [/#if]
[/@]