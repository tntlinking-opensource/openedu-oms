[#ftl]
<table cellpadding="0" cellspacing="0" border="0" class="yx-basic-table">
    <thead>
    <tr>
        <th>楼栋</th>
        <th>寝室号</th>
        <th>床位号</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    [#list datas as v]
    <tr>
        <td>${(v.room.building.name)!}</td>
        <td>${(v.room.name)!}</td>
        <td>${(v.name)!}</td>
        <td>
            <a href="${b.url("!save?planBedId=" + v.id)}"
               data-role="button" class="yx-model-bluebtn ui-btn ui-shadow ui-corner-all">选择</a>
        </td>
    </tr>
    [/#list]
    </tbody>
</table>