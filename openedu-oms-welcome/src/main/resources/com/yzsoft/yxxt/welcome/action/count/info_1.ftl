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
        font-size: 20px;
    }

    .table tr {
        height: 60px;
        vertical-align: middle;
    }

    .table tr td:nth-of-type(odd) {
        text-align: right;
    }

    .table span {
        color: red;
        margin: 0 10px;
    }
</style>
<div style="width: 500px; margin: 100px auto;">
    <h1 style="text-align: center;">${(batch.name)}汇总统计</h1>
    <table class="table">
        <tbody>
        <tr>
            <td width="50%">总人数</td>
            <td><span>${studentNum!}</span>人</td>
        </tr>
        <tr>
            <td>报到人数</td>
            <td><span>${completeNum!}</span>人<span>[#if studentNum == 0]
                0%[#else]${((completeNum / studentNum)?string("0.##%"))!}[/#if]</span></td>
        </tr>
        <tr>
            <td>未到人数</td>
            <td>
                <span>${(studentNum - completeNum)!}</span>人<span>[#if studentNum == 0]
                0%[#else]${((1- completeNum / studentNum)?string("0.##%"))!}[/#if]</span></span>
            </td>
        </tr>
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