[#ftl]
[@b.head/]
[@b.toolbar title="床位" entityId=dormBed.id!0]bar.addBack();[/@]
<div class="edit">
[@b.form action="!save" theme="form"]
    [@b.select label="所属校区" name="campusId" items=[] iclass="campus_select select" required="true"/]
    [@b.select label="宿舍区" name="floorId" items=[] iclass="zone_select select" required="true"/]
    [@b.select label="宿舍楼" name="buildingId" items=[] iclass="building_select select" required="true"/]
    [@b.select label="楼层" name="floorId" items=[] iclass="floor_select select" required="true"/]
    [@b.select label="宿寝室" name="dormBed.room.id" items=[] required="true" iclass="edit_room_select select"/]
    [@b.select label="床位号" name="dormBed.name" value=(dormBed.name)! items=dormBedNames required="true" iclass="edit_bed_select select"/]
    [@b.textfield label="床位编号" name="dormBed.code" value="${dormBed.code!}" required="true" maxlength="30"/]
    [@b.select label="面向层次" name="dormBed.education.id" value=(dormBed.education.id)!0 items=educations/]
    [@b.select label="上/下铺" name="dormBed.bunkBed.id" value=(dormBed.bunkBed.id)!0 items=bunkBeds/]
    [@b.select label="床铺方位" name="dormBed.bedType.id" value=(dormBed.bedType.id)!0 items=bedTypes/]
    [@b.radios label="是否可用" name="dormBed.enabled" value=(dormBed.enabled?string("1","0")) items="1:是,0:否"/]
    [@b.textarea label="备注" style="resize:none;height:135px;" name="dormBed.remark" value="${dormBed.remark!}" maxlength="300"/]
    [@b.formfoot]
        <input type="hidden" name="dormBed.id" value="${dormBed.id!}"/>
        [@b.redirectParams/]
        [@b.submit value="action.submit"/]
    [/@]
[/@]
</div>
[@c.chosenCssAndJs/]
<script src="${base}/static/dorm/scripts/select.js"></script>
<script>
    $(function () {
        $(".edit select.campus_select").dormLoadCampus().dormCascadeZone(".edit select.zone_select").data("value", "${(dormBed.room.floor.building.zone.campus.id)!}");
        $(".edit select.zone_select").dormCascadeBuilding(".edit select.building_select").data("value", "${(dormBed.room.floor.building.zone.id)!}");
        $(".edit select.building_select").dormCascadeFloor(".edit select.floor_select").data("value", "${(dormBed.room.floor.building.id)!}");
        $(".edit select.floor_select").dormCascadeRoom("select.edit_room_select").data("value", "${(dormBed.room.floor.id)!}");
        $("select.edit_room_select").data("value", "${(dormBed.room.id)!}").change(function () {
            if ($(this).val() != "") {
                var num = $("option:selected", this).attr("data-code") * 1;
                var bedSelect = $("select.edit_bed_select");
                var value = bedSelect.val() || bedSelect.data("value");
                bedSelect.empty().append($("<option>").val("").text("请选择..."));
                for (var i = 1; i <= num; i++) {
                    bedSelect.append($("<option>").val(i).text(i));
                }
                if (value != "" && value * 1 <= num) {
                    bedSelect.val(value);
                }
            }
        });
        $("select.edit_bed_select").data("value", "${dormBed.name!}");
    });
</script>
[@b.foot/]
