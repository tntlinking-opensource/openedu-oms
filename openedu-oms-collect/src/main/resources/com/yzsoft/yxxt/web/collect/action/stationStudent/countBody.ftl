[#ftl]
[@c.panel title="按时报到统计"]
<table class="table table-bordered">
	<thead>
	<tr>
		<th>是否按时报到</th>
		<th>学生人数</th>
		<th>占比</th>
	</tr>
	</thead>
	<tbody>
        [#list intimeDatas as v]
		<tr align="center">
			<td>${v[1]}</td>
			<td>${v[2]}</td>
			<td>
                [#if intimeTotal gt 0]
                ${(v[2]*100.0/intimeTotal)?string("0.##")}%
                [/#if]
			</td>
		</tr>
        [/#list]
	</tbody>
</table>
[/@c.panel]
[@c.panel title="到站时间统计"]
    [#list stations as station]
	<h4>${station.name}</h4>
	<table class="table table-bordered">
		<thead>
		<tr>
			<th>时间段\日期</th>
            [#list arriveDates as date]
				<th>${date?string("MM-dd")}</th>
            [/#list]
		</tr>
		</thead>
		<tbody>
            [#list stationDate.times as time]
                [#if "" != time.time!""]
				<tr align="center">
					<td>${time.time}</td>
                    [#list arriveDates as date]
						<td>
                            [#assign num = 0/]
                            [#list stationDatas as data]
                            [#if data[0] == station.name && data[1]?date == date?date && data[2] == time.time]
                                [#assign num = data[3]/]
                                [#break /]
                            [/#if]
                        [/#list]
                            ${num}
						</td>
                    [/#list]
				</tr>
                [/#if]
            [/#list]
		</tbody>
	</table>
    [/#list]
[/@c.panel]