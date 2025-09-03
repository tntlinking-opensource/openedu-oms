[#ftl]
<table class="table table-bordered table-striped" style="max-width: 300px;">
    <thead>
    <tr>
        <th>收费项目</th>
        <th>金额（元）</th>
    </tr>
    </thead>
    <tbody>
    [#list dormSettings?sort_by("id") as v]
    <tr align="center">
        <td>${v.item.name}</td>
        <td>${v.money}</td>
    </tr>
    [/#list]
    </tbody>
</table>