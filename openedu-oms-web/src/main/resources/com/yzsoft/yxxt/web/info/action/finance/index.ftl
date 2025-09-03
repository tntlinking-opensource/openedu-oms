[#ftl]
[#include "../comm/macro.ftl"/]
[#import "/template/form/utils.ftl" as fu/]
[@index code="90"]
<h3 class="caption">在线缴费</h3>
<div class="row">
	<div style="margin:15px;">
		<table class="table table-bordered">
			<thead>
			<th>订单号</th>
			<th>名称</th>
			<th>金额</th>
			<th>状态</th>
			<th>创建时间</th>
			</thead>
			<tbody>
				[#list financeOrders as financeOrder]
				<tr align="center">
					<td>
						<a href="${b.url("!info")}&id=${financeOrder.id}">${financeOrder.code}</a>
					</td>
					<td>${financeOrder.name!}</td>
					<td>${financeOrder.money!}</td>
					<td>${financeOrder.state!}</td>
					<td>${financeOrder.createTime!}</td>
				</tr>
				[/#list]
			</tbody>
		</table>
	</div>
</div>
[/@index]
