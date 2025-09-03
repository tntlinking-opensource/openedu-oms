[#ftl]
[#import "/template/mobile.ftl" as m/]
[@m.body title="费用信息" back=b.url("index?t=1")]
<div class="yx-xxtx">
    [#if financeStudent??]
        <h3 class="yx-xxtx-title">缴费情况</h3>
        <div class="yx-basic-tb">
            <table cellpadding="0" cellspacing="0" border="0" class="yx-basic-table">
                <thead>
                <tr>
                    <th>应缴金额（元）</th>
                    <th>已缴金额（元）</th>
                    <th>未缴金额（元）</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>${(financeStudent.feeTotal)!}</td>
                    <td>${(financeStudent.feePaid)!}</td>
                    <td>${(financeStudent.feeOdd)!}</td>
                </tr>
                </tbody>
            </table>
        </div>
    [/#if]
    [#if financeStudent.items?size gt 0]
        <h3 class="yx-xxtx-title">收费明细</h3>
        <div class="yx-basic-tb">
            <table cellpadding="0" cellspacing="0" border="0" class="yx-basic-table">
                <thead>
                <tr>
                    <th>费用名称</th>
                    <th>应缴金额（元）</th>
                </tr>
                </thead>
                <tbody>
                    [#list financeStudent.items as item]
                    <tr align="center">
                        <td>${(item.item.name)!}</td>
                        <td>${(item.feeTotal)!}</td>
                    </tr>
                    [/#list]
                </tbody>
            </table>
        </div>
    [#else]
        <div class="alert alert-warning text-center" style="margin: 50px;">
            没有费用信息
        </div>
    [/#if]
    [#if financeGreenStudents?? && financeGreenStudents?size gt 0]
        <h3 class="yx-xxtx-title">绿色通道</h3>
        <div class="yx-basic-tb">
            <table cellpadding="0" cellspacing="0" border="0" class="yx-basic-table">
                <thead>
                <tr>
                    <th>项目名称</th>
                    <th>金额（元）</th>
                </tr>
                </thead>
                <tbody>
                    [#list financeGreenStudents as fgs]
                    <tr align="center">
                        <td>${(fgs.green.name)!}</td>
                        <td>${(fgs.money)!}</td>
                    </tr>
                    [/#list]
                </tbody>
            </table>
        </div>
    [/#if]
</div>
[/@]