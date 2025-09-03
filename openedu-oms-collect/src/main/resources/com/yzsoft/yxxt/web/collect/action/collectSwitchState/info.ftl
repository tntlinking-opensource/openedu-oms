[#ftl]
[@b.head/]
[@b.toolbar title="报到情况"]bar.addBack();[/@]
[@b.form action="!search" theme="info"]
    [@b.textfield label="学号" value=(financeGreenStd.student.code)!"" /]
    [@b.textfield label="姓名" value=(financeGreenStd.student.name)!"" /]
    [@b.textfield label="院系" value=(financeGreenStd.student.department.name)!"" /]
    [@b.textfield label="专业" value=(financeGreenStd.student.major.name)!"" /]
    [@b.textfield label="班级" value=(financeGreenStd.student.adminClass.name)!"" /]
    [@b.textfield label="订购数量" value=(financeGreenStd.num)!"" /]
    [@b.textfield label="订购金额（元）" value=(financeGreenStd.total)!"" /]
    [@b.field label="明细"]
	<table class="table table-bordered table-condensed">
		<thead>
		<th>序号</th>
		<th>物品名称</th>
		<th>物品规格</th>
		<th>单价（元）</th>
		<th>订购数量</th>
		<th>总价（元）</th>
		</thead>
		<tbody>
            [#list financeGreenStd.items as item]
                [#assign supplies = item.supplies/]
			<tr align="center">
				<td>${item_index + 1}</td>
				<td>${supplies.name}</td>
				<td>${supplies.spec!}</td>
				<td>${item.price!}</td>
				<td>${item.num!}</td>
				<td>${item.total!}</td>
			</tr>
            [/#list]
		</tbody>
	</table>
    [/@]
[/@]
[@b.foot/]