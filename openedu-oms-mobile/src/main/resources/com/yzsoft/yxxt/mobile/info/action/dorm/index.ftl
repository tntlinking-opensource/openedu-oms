[#ftl]
[#import "/template/mobile.ftl" as m/]
[@m.body title="宿舍入住" back=b.url("index?t=1")]
<div class="yx-xxtx">
    <ul data-role="listview" class="yx-xxtx-info">
        <li>
            <div class="yx-xxtx-info-a">
                <span class="yx-xxtx-info-a-left">宿舍区</span>
                <span class="yx-xxtx-info-a-right">${(dormBed.room.building.zone.name)!}</span>
            </div>
        </li>
        <li>
            <div class="yx-xxtx-info-a">
                <span class="yx-xxtx-info-a-left">楼栋</span>
                <span class="yx-xxtx-info-a-right">${(dormBed.room.building.name)!}</span>
            </div>
        </li>
        <li>
            <div class="yx-xxtx-info-a">
                <span class="yx-xxtx-info-a-left">宿舍号</span>
                <span class="yx-xxtx-info-a-right">${(dormBed.room.name)!}</span>
            </div>
        </li>
        <li>
            <div class="yx-xxtx-info-a">
                <span class="yx-xxtx-info-a-left">床位号</span>
                <span class="yx-xxtx-info-a-right">${(dormBed.name)!}</span>
            </div>
        </li>
        <li>
            <div class="yx-xxtx-info-a">
                <span class="yx-xxtx-info-a-left">是否有空调</span>
                <span class="yx-xxtx-info-a-right">${(dormBed.room.heav?string("是", "否"))!}</span>
            </div>
        </li>
[#--        <li>
            <div class="yx-xxtx-info-a">
                <span class="yx-xxtx-info-a-left">床铺方位</span>
                <span class="yx-xxtx-info-a-right">${(dormBed.bunkBed.name)!} ${(dormBed.bedType.name)!}</span>
            </div>
        </li>--]
        <li>
            <div class="yx-xxtx-info-a">
                <span class="yx-xxtx-info-a-left">住宿标准</span>
                <span class="yx-xxtx-info-a-right">[#if (dormBed.room.rent)??]${(dormBed.room.rent)!}（元）[/#if]</span>
            </div>
        </li>
    </ul>
</div>
[/@]