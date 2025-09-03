[#ftl]
[#include "lib.ftl"/]

[#function isWitchFinished switch states]
    [#list states as state]
        [#if switch.code == state.collectSwitch.code]
            [#return true/]
        [/#if]
    [/#list]
    [#return false/]
[/#function]

[@edit]
	[#if supply?? && supply.agree]
		[@panel]
			<h5>生活用品</h5>
			<table class="table table-bordered table-condensed">
				<thead>
				<th>序号</th>
				<th>物品名称</th>
				<th>物品规格</th>
				<th>订购数量</th>
				[#--
				<th>单价（元）</th>
				<th>总价（元）</th>
				--]
				</thead>
				<tbody>
		            [#list supply.items as item]
		                [#assign supplies = item.supplies/]
					<tr align="center">
						<td>${item_index + 1}</td>
						<td>${supplies.name}</td>
						<td>${supplies.spec!}</td>
						<td>${item.num!}</td>
						[#--
						<td>${item.price!}</td>
						<td>${item.total!}</td>
						--]
					</tr>
		            [/#list]
				</tbody>
			</table>
		[/@panel]
	[#else]
	    [@panel]
		    <style>
		        .row-xxx .col-xs-3{padding: 5px;}
		    </style>
			<div class="row row-xxx">
				<div class="col-xs-3" style="text-align: right;"></div>
				<div class="col-xs-3" style="text-align: left;">
					  <span style="color: red;">没有需要领取的生活用品</span>
				</div>
			</div>
	    [/@panel]
    [/#if]
[/@edit]