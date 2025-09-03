[#ftl]
<table class="table table-bordered table-condensed">
    <thead>
    <th>序号</th>
    <th>名称</th>
    <th>规格</th>
    <th>数量</th>
    <th>单价</th>
    <th>金额</th>
    <th>备注</th>
    </thead>
    <tbody>
    [#list suppliesStd.items as item]
    [#assign supplies = item.supplies/]
    <tr align="center">
        <td>${item_index + 1}</td>
        <td>${supplies.name}</td>
        <td>${supplies.spec!}</td>
        <td>${item.num!}</td>
        <td>${item.price!}</td>
        <td>${item.total!}</td>
        <td>${supplies.remark!}</td>
    </tr>
    [/#list]
    <tr align="center">
        <td colspan="3" align="right">总计：</td>
        <td>${suppliesStd.num!}</td>
        <td>&nbsp;</td>
        <td>${suppliesStd.total!}</td>
        <td>&nbsp;</td>
    </tr>
    </tbody>
</table>