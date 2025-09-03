[#ftl]
[#include "macro.ftl"/]
[@b.head/]
<style>
    .nowrap {white-space: nowrap;}
</style>
<div style="padding:20px 15px; background-color: #fff; border: 1px solid #ddd; border-top: none;">
    <h1 class="text-center">${(batch.name)!}</h1>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th rowspan="3" colspan="2">院系</th>
            <th rowspan="2" colspan="2">录取人数</th>
            [#list items as v]
                <th colspan="4">${v}</th>
            [/#list]
        </tr>
        <tr>
            [#list items as v]
                <th colspan="3">已办理</th>
                <th rowspan="2">未办理</th>
            [/#list]
        </tr>
        <tr>
            <th>男</th>
            <th>女</th>
            [#list items as v]
                <th>男</th>
                <th>女</th>
                <th>合计</th>
            [/#list]
        </tr>
        </thead>
        <tbody>
        [#list datas as department]
            [#list department.majors as major]
                <tr class="text-center">
                    [#if major_index == 0]
                        <td rowspan="${department.majors?size}" width="1%">${(department.name)!}</td>
                    [/#if]
                    <td class="nowrap">${(major.name)!}</td>
                    <td>${major.maleNum}</td>
                    <td>${major.femaleNum}</td>
                    [#list items as item]
                        [#assign v = getMajorItem(major, item)/]
                        <td>${(v.enabledMaleNum)!}</td>
                        <td>${(v.enabledFemaleNum)!}</td>
                        <td>${(v.enabledNum)!}</td>
                        <td>${(v.disabledNum)!}</td>
                    [/#list]
                </tr>
            [/#list]
        [/#list]
        </tbody>
    </table>
</div>
[@b.foot/]