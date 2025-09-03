[#ftl]
[#list datas as d]
    [#assign type=d[0]/]
    [#assign datas=d[1]/]
    [@c.panel title=type.name+"统计"]
    <table class="table table-bordered">
        [#if datas?size gt 0]
            <thead>
            <tr>
                [#list datas[0] as v]
                    <th>${v}</th>
                [/#list]
            </tr>
            </thead>
        [/#if]
        [#if datas?size gt 1]
        <tbody>
            [#list datas as data]
                [#if data_index gt 0]
                <tr align="center">
                    [#list data as v]
                        <td>${v!0}</td>
                    [/#list]
                </tr>
                [/#if]
            [/#list]
        [/#if]
    </table>
    [/@c.panel]
[/#list]