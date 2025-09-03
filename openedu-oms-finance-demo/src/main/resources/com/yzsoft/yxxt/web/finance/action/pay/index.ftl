[#ftl]
[#include "/com/yzsoft/yxxt/web/collect/action/comm/macro.ftl"/]
[#import "/template/form/utils.ftl" as fu/]
[@index code=""]
<h3 class="caption">在线支付</h3>
<div class="row">
	[@b.form action="!edit" class="supplies_form"]
		<div style="margin:15px;">
			<table class="table table-bordered table-condensed">
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
		</div>
	[/@]
	<div style="margin: 20px; text-align: center;">
		<a href="${b.url("!notice")}&code=${financeOrder.code}" class="btn btn-primary">立即支付</a>
		<a href="${base}/web/finance/finance-order-info!cancle.action?id=${financeOrder.id}" class="btn btn-danger pull-right">取消支付</a>
	</div>
</div>
[/@index]
