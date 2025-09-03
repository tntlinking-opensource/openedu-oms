[#ftl]
[#include "../comm/macro.ftl"/]
[@index code="02"]
<div class="tab-content">
	<div class="tab-pane active" id="tab_6_1">
		<h3 class="caption text-center">住宿信息</h3>
		<div class="row">
			<div class="col-md-12">
				<div style="height: 20px;"></div>
				[#if bed??]
					<table class="table table-bordered">
						<thead>
						<tr>
							<th>校区</th>
							<th>楼栋</th>
							<th>楼层</th>
							<th>宿舍号</th>
							<th>床位号</th>
							<th>收费标准（元）</th>
						</tr>
						</thead>
						<tbody>
							<tr align="center">
								<td>${(bed.room.building.zone.campus.name)!}</td>
								<td>${(bed.room.building.name)!}</td>
								<td>${(bed.room.floor)!}</td>
								<td>${(bed.room.name)!}</td>
								<td>${(bed.name)!}</td>
								<td>${(bed.room.rent)!}</td>
							</tr>
						</tbody>
					</table>
				[#else]
					<div class="alert alert-warning text-center" style="margin: 50px;">
						没有住宿信息
					</div>
				[/#if]
			</div>
		</div>
	</div>
</div>
[/@index]