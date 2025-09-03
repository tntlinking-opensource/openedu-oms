[#ftl]
[@b.head/]
[@b.toolbar title="生活物品情况"]bar.addBack();[/@]
[@b.form action="!search" theme="info"]
    [@b.textfield label="学号" value=(suppliesStd.student.code)!"" /]
    [@b.textfield label="姓名" value=(suppliesStd.student.name)!"" /]
    [@b.textfield label="院系" value=(suppliesStd.student.department.name)!"" /]
    [@b.textfield label="专业" value=(suppliesStd.student.major.name)!"" /]
    [@b.textfield label="班级" value=(suppliesStd.student.adminClass.name)!"" /]
    [@b.textfield label="订购数量" value=(suppliesStd.num)!"" /]
    [@b.textfield label="订购金额（元）" value=(suppliesStd.total)!"" /]
    [@b.textfield label="是否购买" value=(suppliesStd.agree?string("是","否"))!"" /]
    [@b.textfield label="是否领取" value=(suppliesStd.used?string("是","否"))!"" /]
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
            [#list suppliesStd.items as item]
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