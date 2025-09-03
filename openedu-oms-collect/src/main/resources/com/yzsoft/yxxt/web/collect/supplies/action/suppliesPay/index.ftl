[#ftl]
[#include "../comm/macro.ftl"/]
[#import "/template/form/utils.ftl" as fu/]
[@index code="04"]
<h3 class="caption">生活用品</h3>
<div class="row">
    [@b.form action="!edit" class="supplies_form"]
		<div style="margin:15px;">
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
                <tr align="center">
                    <td colspan="4" align="right">总计：</td>
	                <td>${suppliesStd.num!}</td>
	                <td>${suppliesStd.total!}</td>
                </tr>
				</tbody>
			</table>
		</div>
        [#if switch?? && switch.editable]
			<div style="margin: 30px; text-align: center;">
				<button type="submit" class="btn btn-primary supplies_submit">修改</button>
				[#if switch.payment]
					<a href="${b.url("supplies-pay")}" class="btn btn-info" style="margin-left: 100px;">在线支付</a>
				[/#if]
			</div>
        [/#if]
    [/@]
</div>
[/@index]
