[#ftl]
[#include "../comm/macro.ftl"/]
[@index code="03"]
<div class="tab-content">
    <div class="tab-pane active" id="tab_6_1">
        <h3 class="caption">费用信息</h3>
        <div class="row">
            <div class="col-md-12">
                [#if financeStudent??]
                    <h5>缴费情况</h5>
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th>应缴金额（元）</th>
                            <th>已缴金额（元）</th>
                            <th>未缴金额（元）</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr align="center">
                            <td>${(financeStudent.feeTotal)!}</td>
                            <td>${(financeStudent.feePaid)!}</td>
                            <td>${(financeStudent.feeOdd)!}</td>
                        </tr>
                        </tbody>
                    </table>

                    [#if financeStudent.items?size gt 0]
                        <h5>收费明细</h5>
                        <table class="table table-bordered">
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
                    [/#if]
                [#else]
                    <div class="alert alert-danger">无学杂费信息。</div>
                [/#if]
                [#if financeGreenStudents?? && financeGreenStudents?size gt 0]
                    <h5>绿色通道</h5>
                    <table class="table table-bordered">
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
                [/#if]
            </div>
        </div>
    </div>
</div>
[/@index]