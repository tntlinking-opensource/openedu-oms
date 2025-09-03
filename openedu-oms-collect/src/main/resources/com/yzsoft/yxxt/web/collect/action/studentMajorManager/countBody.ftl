[#ftl]
[#function studentNum major count]
    [#list count as c]
        [#if c[0] == major.id]
            [#return  c[1]/]
        [/#if]
    [/#list]
    [#return 0/]
[/#function]
<div class="panel">
	<div class="panel-body">
		<table class="table table-bordered">
			<thead>
			<tr>
				<th>专业名称</th>
            [#assign nums=["","一","二","三","四","五","六"]/]
            [#list 1..majorNum as v]
				<th>第${nums[v]}志愿人数</th>
            [/#list]
			</tr>
			</thead>
			<tbody>
            [#list majors as major]
			<tr align="center">
				<td>${(major.name)!}</td>
                [#list 1..majorNum as v]
					<td>
                        [#if printMode??]
                        ${studentNum(major, datas["major"+v+"Count"])}
                        [#else ]
							<a href="${b.url("!students")}&majorId=${major.id}&sort=${v}" data-toggle="modal"
							   data-target="#modal">
                            ${studentNum(major, datas["major"+v+"Count"])}
							</a>
                        [/#if]
					</td>
                [/#list]
			</tr>
            [/#list]
		</table>
	</div>
</div>