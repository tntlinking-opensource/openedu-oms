[#ftl]
[#import "/template/comm.ftl" as c/]
[#import "/template/mobile.ftl" as m/]
[#include "../comm/macro.ftl"/]
[@m.body title=switch.name back=formBack((dormPlanBed.id)??)]
<style>
    .bed_div .yx-model-bluebtn { display: inline-block; margin: 0; }
</style>
<div class="ui-listview yx-xxtx">
    <div class="yx-model-zx01 margin-top-em">
        <select name="zoneId" class="dorm_alloc_zone_select">
            <option value="">请选宿舍区</option>
        </select>
        <select name="buildingId" class="dorm_alloc_building_select">
            <option value="">请选择楼栋</option>
        </select>
        <select name="roomId" class="dorm_alloc_room_select">
            <option value="">请选择宿舍号</option>
        </select>
    </div>
    <div class="yx-jiange-1em"></div>
    <div class="yx-basic-tb bed_div">
    </div>
</div>
<script src="${base}/static/yxxt/welcome/scripts/dorm_alloc.js"></script>
<script>
    $(function () {
        DormAlloc.init({
            baseAction: "${base}/mobile/collect/dorm.action?method=",
        });
        $(".dorm_alloc_room_select").change(function () {
            var roomId = this.value;
            if (roomId != "") {
                $(".bed_div").load("${b.url("!findBed")}&roomId=" + roomId);
            }
        });
    });
</script>
[/@]