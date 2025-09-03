[#ftl]
[@b.head/]
[@c.panel title="采集统计"]
<table class="table table-bordered">
    [#if datas?size gt 0]
		<thead>
		<tr>
            [#list datas[0] as v]
				<th>${v}</th>
            [/#list]
		</tr>
		</thead>
    [/#if]
    [#if datas?size gt 1]
	<tbody>
        [#list datas as data]
            [#if data_index gt 0]
			<tr align="center">
                [#list data as v]
					<td>${v!0}</td>
                [/#list]
			</tr>
            [/#if]
        [/#list]
    [/#if]
</table>
[/@c.panel]
[@b.foot/]