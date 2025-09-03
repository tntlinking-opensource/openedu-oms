[#ftl]
[@b.head/]
[@b.toolbar title="导入结果"]
bar.addBack();
[/@]
[@b.form action="!search" theme="form"]
<table class="table table-striped table-bordered table-hover" style="width: 500px; margin: auto;">
    <thead>
    <tr>
        <th colspan="2" align="center">导入结果</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td class="title">总计：</td>
        <td>${total}</td>
    </tr>
    <tr>
        <td class="title">成功：</td>
        <td>${(successNum)!}</td>
    </tr>
    <tr>
        <td class="title">失败：</td>
        <td>${(failNum)!}</td>
    </tr>
    </tbody>
</table>
    [#if (errorMessages)?? && errorMessages?size > 0]
    <div style="text-align:center; margin: 10px;">错误消息</div>
    <div style="margin-top: -20px;">
        <table class="table table-striped table-bordered table-hover" style="width: 500px; margin: auto;">
            <tbody>
                [#list errorMessages as v]
                <tr>
                    <td>${v!}</td>
                </tr>
                [/#list]
            </tbody>
        </table>
    </div>
    [/#if]
[/@]
[@b.foot/]
