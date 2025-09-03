[#ftl]
[@b.head/]
[@b.toolbar title="绿色通道情况"]bar.addBack();[/@]
[@b.form action="!search" theme="info"]
    [@b.textfield label="学号" value=(financeGreenStd.student.code)!"" /]
    [@b.textfield label="姓名" value=(financeGreenStd.student.name)!"" /]
    [@b.textfield label="院系" value=(financeGreenStd.student.department.name)!"" /]
    [@b.textfield label="专业" value=(financeGreenStd.student.major.name)!"" /]
    [@b.textfield label="班级" value=(financeGreenStd.student.adminClass.name)!"" /]
    [@b.field label="明细"]
        [#if financeGreenStd.financeGreens?? && financeGreenStd.financeGreens?size gt 0]
        <table class="table table-bordered table-condensed">
            <thead>
            <th>申请项目</th>
            <th>项目说明</th>
            </thead>
            <tbody>
                [#list financeGreenStd.financeGreens as item]
                <tr align="center">
                    <td>${item.name!}</td>
                    <td>${item.remark!}</td>
                </tr>
                [/#list]
            </tbody>
        </table>
        [#else ]
        无
        [/#if]
    [/@]
[/@]
[@b.foot/]