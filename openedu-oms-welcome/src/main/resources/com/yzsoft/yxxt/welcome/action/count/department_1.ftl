[#ftl]
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>迎新统计</title>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no">
</head>
<body>
<style>
    .table {
        width: 100%;
        font-size: 14px;
    }

    .table tr {
        vertical-align: middle;
    }

    .table tr td, .table tr th {
        padding: 3px 5px;
        border: 1px solid #555;
        text-align: center;
    }

    .table span {
        color: red;
        margin: 0 10px;
    }
</style>
<div style="width: 800px; margin: 100px auto;">
    <h1 style="text-align: center;">${(batch.name)}明细统计</h1>
    <h4 style="text-align: center;">
    [#if studentNum == 0]
        录取人数：0人
    [#else]
        录取人数：${studentNum}人，已报到：${completeNum}人（${(completeNum / studentNum)?string.percent}
        ），未报到：${studentNum - completeNum}人（${((1 - completeNum / studentNum)?string.percent)}）
    [/#if]
    </h4>
    <table class="table" cellpadding="0" cellspacing="0">
        <thead>
        <tr>
            <th>院系名称</th>
            <th>录取人数</th>
        [#list items as item]
            <th>${(item.name)!}</th>
        [/#list]
        </tr>
        </thead>
        <tbody>
        [#list processBatchCounts as count]
        <tr>
            <td>${(count.departmentName)!}</td>
            <td>${(count.total)!}</td>
            [#list items as item]
                [#assign detail = emptyDetail/]
                [#list count.details as d]
                    [#if d.itemId == item.id]
                        [#assign detail = d/]
                        [#break /]
                    [/#if]
                [/#list]
                <td>${(detail.num)!0}（${(detail.percent)!0}%）</td>
            [/#list]
        </tr>
        [/#list]
        </tbody>
    </table>
</div>
<script>
    setTimeout(function () {
        location.reload();
    }, 10000);
</script>
</body>
</html>