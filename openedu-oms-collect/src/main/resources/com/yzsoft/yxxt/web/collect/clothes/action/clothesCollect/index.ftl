[#ftl]
[#include "/com/yzsoft/yxxt/web/collect/action/comm/macro.ftl"/]
[#import "/template/form/utils.ftl" as fu/]
[@index code="03_1"]
<style>
    .setting_div { text-align: center; }
    .setting_div img { width: 80%; }
    .sizes_div { }
    .sizes_div th { text-align: center; }
    .sizes_div img { width: 150px; }
    .help-block.valid { display: none; }
    span.price { color: #FF6600; }
</style>
<h3 class="caption">${switch.name}</h3>
<div style="margin: 15px;">
    <div class="sizes_div">
        [#list clothesStudent.sizes as size]
            [#assign type=size.type/]
            <div class="type_div">
                <table class="table table-bordered">
                    <tbody>
                    <tr>
                        <th rowspan="2" align="center" width="200">
                            <div style="margin: 5px 0;">${(type.name)!}</div>
                        </th>
                        [#list type.getColTitle() as title]
                            <th>${title}</th>
                        [/#list]
                    </tr>
                    <tr align="center">
                        <td>${size.size.name}</td>
                        [#list size.size.getCols() as col]
                            <td>${col}</td>
                        [/#list]
                    </tr>
                    </tbody>
                </table>
            </div>
        [/#list]
        [#if clothesStudent.remark??]
            <div>
                <div style="margin-bottom: 5px;">备注：</div>
                <div>${clothesStudent.remark?replace("\n", "<br/>")}</div>
            </div>
        [/#if]
        <div class="text-center">
            [#if switch?? && switch.editable]
            [#--[#if !((clothesStudent.locked)!false)]--]
                <a href="${b.url("!edit")}" class="btn btn-primary" style="margin-right: 100px;">修改</a>
            [#--[/#if]--]
            [/#if]
        </div>
    </div>
    [@editRemark/]
</div>
[/@index]
