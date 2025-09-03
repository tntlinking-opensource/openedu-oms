[#ftl]
[@b.head/]
<div class="search">
[@b.form name="searchForm"  action="!search?orderBy=name" target="datalist" title="ui.searchForm" theme="search"]
    [@b.textfield label="床位编号" name="dormBed.code"/]
    [@b.textfield label="床位号" name="dormBed.name"/]
    [@b.select label="校区" name="dormBed.room.floor.building.zone.campus.id" items=[] iclass="campus_select"/]
    [@b.select label="宿舍区" name="dormBed.room.floor.building.zone.id" items=[] iclass="zone_select"/]
    [@b.select label="楼栋" name="dormBed.room.floor.building.id" items=[] iclass="building_select"/]
    [@b.select label="楼层" name="dormBed.room.floor.id" items=[] iclass="floor_select"/]
    [@b.textfield label="寝室" name="dormBed.room.name"/]
    [@b.select label="面向性别" name="dormBed.room.gender" items=["男","女"] /]
    [@b.select label="上/下铺" name="dormBed.bunkBed.id" items=bunkBeds required="true"/]
    [@b.select label="床铺方位" name="dormBed.bedType.id" items=bedTypes required="true"/]
    [@b.textfield label="学号" name="dormBed.student.code"/]
    [@b.textfield label="姓名" name="dormBed.student.name"/]
    [@b.select label="年级" name="dormBed.student.grade" items=grades /]
    [@b.select label="学院" name="dormBed.student.department.id" items=departments /]
    [@b.textfield label="班级" name="dormBed.student.adminClass.name"/]
    [@b.textfield label="辅导员" name="dormBed.student.adminClass.instructor.name"/]
    [@b.select label="床位状态" name="dormBed.state" items=["空闲","计划","入住"] /]
    [@b.select label="是否可用" name="dormBed.enabled" items={'1':'是','0':'否'} value="1"/]
[/@]
</div>
<script src="${base}/static/dorm/scripts/select.js"></script>
<script>
    $(function () {
        $(".search select.campus_select").data("title", "校区").dormLoadCampus().dormCascadeZone(".search select.zone_select");
        $(".search select.zone_select").data("title", "宿舍区").dormCascadeBuilding(".search select.building_select");
        $(".search select.building_select").data("title", "楼栋").dormCascadeFloor(".search select.floor_select");
        $(".search select.floor_select").data("title", "楼层");
    });
</script>
[@b.div id="datalist" href="!search?orderBy=name&dormBed.enabled=true" /]
[@b.foot/]

