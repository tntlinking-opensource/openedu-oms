[#ftl]
[#macro table panelTitle title titles names datas]
    [@c.panel title=panelTitle]
	<table class="table table-bordered">
		<thead>
		<tr>
			<th>${title}</th>
            [#list names as v]
				<th>${v}</th>
            [/#list]
		</tr>
		</thead>
		<tbody>
            [#list titles as title]
			<tr align="center">
				<td>${title}</td>
                [#list names as name]
                    [#assign num = 0/]
                    [#list datas as data]
                        [#if data[0] == title && data[1] == name]
                            [#assign num = data[2]/]
                            [#break /]
                        [/#if]
                    [/#list]
					<td>${num}</td>
                [/#list]
			</tr>
            [/#list]
		</tbody>
	</table>
    [/@c.panel]
[/#macro]
[@table "服装尺码统计" clothesTitle clothesTitles clothesNames clothesDatas/]
[@table "鞋子尺码统计" shoesTitle shoesTitles shoesNames shoesDatas/]