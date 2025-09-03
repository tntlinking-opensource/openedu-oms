[#ftl]
[#include "/com/yzsoft/yxxt/web/collect/action/comm/macro.ftl"/]
[#include "macro.ftl"/]
[#function typeNum type]
    [#list clothesStudent.sizes as size]
        [#if size.type.id == type.id]
            [#return size.num/]
        [/#if]
    [/#list]
    [#return 1/]
[/#function]
[@form code="03_1"]
<style>
    .attend_div { font-size: 24px; padding: 30px; }
    .attend_div label { margin: 0 20px; }
    .attend_div input { width: 30px; height: 30px; display: inline-block; vertical-align: bottom; margin: 0 10px; }
    .setting_div { text-align: center; }
    .setting_div img { width: 200px; }
    .sizes_div { display: none; }
    .sizes_div th { text-align: center; }
    .sizes_div img { width: 150px; }
    .type_div2 { border: 1px solid #ededed; padding: 5px; margin-bottom: 15px; text-align: center; }
    .type_div2 .img_div { height: 150px; width: 150px; margin: auto; overflow: hidden; }
    .type_div2 .img_div img { width: 100%; }
    .type_div2 .title { margin: 5px 0; }
    .type_div2 .title { color: #FF6600; text-align: center; }
    .type_div2 .name { white-space: nowrap; overflow: hidden; margin: 10px 0; }
    .type_div2 .mate { margin: 5px 0; height: 40px; overflow: hidden; }
    .size_btn span { display: none; }
    .size_btn.btn-primary span { display: inline; }
    .type_div { display: none; margin: 15px; }
    .type_div .btn { padding: 3px 5px; }
    .type_div table { background-color: #fff; }
    .v_num_ipt { height: 30px; width: 50px; vertical-align: middle; text-align: center; }
</style>
<h3 class="caption">${switch.name}</h3>
<div style="padding: 15px 0;">
    <div class="edit_div">
        <div class="row type_row">
            [#list types as type]
            [#--[#if !type.sizeName??]--]
                <div class="col-sm-4">
                    <div class="type_div2 type_div2_${type.id}">
                    [#--<div class="img_div">--]
                    [#--<img src="${base}/${type.img}"/>--]
                    [#--</div>--]
                    [#--<div class="title">--]
                    [#--<span class="price">¥${type.price}</span>--]
                    [#--</div>--]
                        <div class="name">${type.name}</div>
                    [#--<div class="mate">${type.mate}</div>--]
                        [#if type.sizeName??]
                            <div class="text-center" style="margin: 5px;">
                                <button type="button" class="btn btn-primary size_name_btn">未选择</button>
                            </div>
                            <div class="text-center" style="margin: 5px;">
                                <button type="button" class="btn btn-default show_size_table_btn" data-id="${type.id}">选择尺码</button>
                            </div>
                        [#else]
                            <div style="height: 37px;"></div>
                        [/#if]
                    [#--                        <div>
                                                <span style="margin-left: -73px;">选购数量：</span>
                                                <button type="button" class="btn btn-default btn-sm btn_minus">
                                                    <span class="glyphicon glyphicon-minus"></span>
                                                </button>
                                                <input name="num_${type.id}" value="${typeNum(type)}" class="v_num_ipt" maxlength="1" data-limit="${type.numLimit!1}"/>
                                                <button type="button" class="btn btn-default btn-sm btn_plus">
                                                    <span class="glyphicon glyphicon-plus"></span>
                                                </button>
                                            </div>--]
                    </div>
                </div>
            [#--[/#if]--]
            [/#list]
        </div>
        <div class="sizes_div">
            [#list types as type]
                [@sizeTable type/]
            [/#list]
        </div>
        <div style="margin: 15px;">
            <div style="margin-bottom: 5px;">备注：</div>
            <div>
                <textarea name="remark" maxlength="300" class="form-control">${clothesStudent.remark!}</textarea>
            </div>
        </div>
        <script>
            $(function () {
                function splitRow() {
                    var row = $(".type_row").last();
                    var types = row.find(".col-sm-4");
                    if (types.length > 3) {
                        var newRow = $("<div class='row type_row'></div>");
                        newRow.insertAfter(row);
                        types.slice(3).appendTo(newRow);
                        splitRow();
                    }
                }

                splitRow();

                $(".show_size_table_btn").click(function () {
                    var typeDiv = $(".type_div_" + $(this).attr("data-id"));
                    $(".type_div:visible").slideUp();
                    typeDiv.insertAfter($(this).closest(".row")).slideDown();
                });
                $(".size_btn").click(function () {
                    var div = $(this).closest(".type_div");
                    var div2 = $(".type_div2_" + div.attr("data-id"));
                    div.find(".hide_ipt").val($(this).attr("data-id"));
                    div.find(".size_btn").removeClass("btn-primary");
                    $(".size_name_btn", div2).text($(this).attr("data-name"));
                    $(this).addClass("btn-primary");
                    div.slideUp();
                });

                $(".type_div2 .btn_minus").click(function () {
                    var ipt = $(this).next("input");
                    ipt.val(+ipt.val() - 1);
                    numChange(ipt);
                });

                $(".type_div2 .btn_plus").click(function () {
                    var ipt = $(this).prev("input");
                    ipt.val(+ipt.val() + 1);
                    numChange(ipt);
                });

                function numChange(ipt) {
                    var num = +ipt.val(), limit = +ipt.attr("data-limit");
                    num = Math.min(limit, num);
                    num = Math.max(1, num);
                    ipt.val(num);
                    ipt.prev("button").prop("disabled", num <= 1);
                    ipt.next("button").prop("disabled", num >= limit);
                }

                $(".v_num_ipt").each(function () {
                    numChange($(this));
                }).keyup(function () {
                    numChange($(this));
                });

                function selectSize(typeid, sizeid) {
                    $(".type_div_" + typeid + " .size_btn").filter(function () {
                        return sizeid == $(this).attr("data-id");
                    }).trigger("click");
                }
                [#list clothesStudent.sizes as size]
                    selectSize("${(size.type.id)!}", "${(size.size.id)!}");
                [/#list]
            });
            function beforeSubmit() {
                var result = true;
                $(".hide_ipt").each(function () {
                    var ipt = $(this);
                    if (ipt.val() == "") {
                        alert("请选择" + ipt.attr("data-name") + "尺码");
                        result = false;
                        return false;
                    }
                });
                return result;
            }
        </script>
    </div>
</div>
[/@form]
