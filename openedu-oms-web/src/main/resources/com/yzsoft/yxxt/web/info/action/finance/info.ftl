[#ftl]
[#include "../comm/macro.ftl"/]
[#import "/template/form/utils.ftl" as fu/]
[@index code="90"]
<h3 class="caption">在线支付</h3>
<div class="row" style="margin-top: 15px;">
	<div class="col-sm-6">订单号：${financeOrder.code}</div>
	<div class="col-sm-6 text-right">状态：${financeOrder.state}</div>
</div>
<div class="row">
	[@b.form action="!edit" class="supplies_form"]
		<div style="margin:15px;">
			<table class="table table-bordered">
				<thead>
				<th>序号</th>
				<th>物品名称</th>
				<th>订购数量</th>
				<th>总价（元）</th>
				</thead>
				<tbody>
					[#list financeOrder.items as item]
					<tr align="center">
						<td>${item_index + 1}</td>
						<td>${item.name}</td>
						<td>${item.num!}</td>
						<td>${item.money!}</td>
					</tr>
					[/#list]
				<tr align="center">
					<td colspan="3" align="right">总计：</td>
					<td>${financeOrder.money!}</td>
				</tr>
				</tbody>
			</table>
			<div class="text-right">
				创建时间：${financeOrder.createTime?datetime}
			</div>
		</div>
	[/@]
	<div class="text-center" style="margin: 15px;">
		<a href="${b.url("!index")}" class="btn btn-default">返回</a>
	</div>
</div>
[/@index]
