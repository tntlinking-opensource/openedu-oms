[#ftl]
[#include "../comm/lib.ftl"/]
[@edit]
    [#if dormPlanBed??]
        [@panel]
		<h5>当前分配床位</h5>
		<table class="table table-bordered table-striped table-titled">
			<tr>
				<td>宿舍区</td>
				<td>${(dormPlanBed.bed.room.building.zone.name)!}</td>
				<td>楼栋</td>
				<td>${(dormPlanBed.bed.room.building.name)!}</td>
			</tr>
			<tr>
				<td>寝室号</td>
				<td>${(dormPlanBed.bed.room.name)!}</td>
				<td>床位号</td>
				<td>${(dormPlanBed.bed.name)!}</td>
			</tr>
			<tr>
				<td>住宿标准</td>
				<td colspan="3">${(dormPlanBed.bed.room.rent)!}（元）</td>
			</tr>
		</table>
        [/@panel]
    [/#if]
    [@panelEdit]
	[#--<h5>
		可选床位有：<span style="color: red;">${totalNum!0}</span>张，空闲床位有：<span style="color: green;">${freeNum!}</span>张。
	</h5>--]
	<style>
		.table-dorm select { width: 100%; }
		.table-dorm > tbody > tr > td { vertical-align: top; }
	</style>
	<table class="table table-bordered table-striped table-dorm">
		<thead>
		<tr>
			<th width="25%">宿舍区</th>
			<th width="25%">楼栋</th>
			<th width="25%">寝室号</th>
			<th width="25%">床位号</th>
		</tr>
		</thead>
		<tbody>
		<tr align="center" valign="top">
			<td valign="top"><select name="zoneId" class="dorm_alloc_zone_select"></select></td>
			<td><select name="buildingId" class="dorm_alloc_building_select"></select></td>
			<td><select name="roomId" class="dorm_alloc_room_select"></select></td>
			<td><select name="planBedId" class="dorm_alloc_bed_select" [#if !dormPlanBed??]required[/#if]></select></td>
		</tr>
		</tbody>
	</table>
	<script src="${base}/static/yxxt/welcome/scripts/dorm_alloc.js"></script>
    <script>
        $(function (){
            DormAlloc.init({
                baseAction : "${base}/welcome/process/dorm-alloc.action?studentId=${student.id}&method=",
            });
        });
    </script>
    [/@panelEdit]
[/@edit]