[#ftl]
[#import "/template/comm.ftl" as c/]
[#import "/template/mobile.ftl" as m/]
[#include "../clothesCollect/macro.ftl"/]
[@m.body title=switch.name back=b.url("/mobile/collect/index?t=1")]
<style>
    .ui-header .ui-title, .ui-footer .ui-title { margin: 0; overflow: auto; }
    .type_div table tbody tr td:first-child { font-size: 1.2em; font-weight: bold; background-color: #6CAAD9; color: #fff; }
    .ui-listview .ui-li-has-thumb > .ui-btn > img:first-child { width: 5em; height: 5em; top: 0.7em; left: 0.7em; }
    .size_span { color: #FF8000; }
</style>
<form action="${b.url("!save")}" method="post" class="clothesStudentForm" onsubmit="return beforeSubmit();"
      data-ajax="false">
    <div class="edit_div">
        <ul data-role="listview" class="type_listview">
            [#list types as type]
                [#if type.sizeName??]
                [#--                    <li class="type_li_${type.id}">
                                        <input type="hidden" name="sizeid" value="" class="hide_ipt" data-name="${type.name}"/>
                                        <a href="#popupDialog${type.id}" data-rel="popup" data-position-to="window" data-transition="pop">
                                            <h2>${type.name}</h2>
                                            <p>选择尺寸：<span class="size_span"></span></p>
                                        </a>
                                        <div id="popupDialog${type.id}" data-role="popup" data-overlay-theme="b" class="ui-content" style="width: 80vw;">
                                            <div style="overflow-x: scroll;">
                                                [@sizeTable type/]
                                            </div>
                                        </div>
                                    </li>--]
                 <li>
                     <label>请选择：${(type.name)!}</label>
                     <select name="sizeid" class="size_select" data-name="${type.name}">
                         <option value="">请选择</option>
                        [#list type.sizes as size]
                            <option value="${size.id}" [#if hasSize(size)]selected[/#if]>${size.name}</option>
                        [/#list]
                     </select>
                 </li>
                [/#if]
            [/#list]
        </ul>
    </div>
    <div class="yx-jiange-1em"></div>
    <div class="yx-model-btnmodel">
        <button type="button" class="yx-model-bluebtn submit">提交</button>
    </div>
    <div class="yx-jiange-1em"></div>
</form>
    [@m.validation/]
<script src="${base}/static/yxxt/mobile/scripts/form.js"></script>
<script>
    $(function () {
        $(".clothesStudentForm").initForm();

        $(".size_btn").click(function () {
            var btn = $(this);
            var div = btn.closest(".type_div");
            var typeid = div.attr("data-id");
            var name = btn.attr("data-name");
            selectSize(typeid, name, null, btn.attr("data-id"));
            // btn.closest("type_div").popup("close");
            // return false;
        });

        function sizeBtnClick(btn) {
            var btn = $(btn);
            var div = btn.closest(".type_div");
            var typeid = div.attr("data-id");
            var name = btn.attr("data-name");
            selectSize(typeid, name, null, btn.attr("data-id"));
            // btn.closest("type_div").popup("close");
            // return false;
        };

        function selectSize(typeid, name, value, sizeid) {
            $(".type_li_" + typeid + " .size_span").text(name);
            sizeid = sizeid || $(".type_div_" + typeid + " .size_btn[data-name='" + name + "'][data-value='" + value + "']").attr("data-id");
            $(".type_li_" + typeid + " .hide_ipt").val(sizeid);
        }
        [#list clothesStudent.sizes as size]
            selectSize("${(size.type.id)!}", "${(size.size.name)!}", null, "${(size.size.id)!}");
        [/#list]
    });

    function beforeSubmit() {
        // var attendSelect = $(".attend_select");
        // if (attendSelect.length > 0) {
        //     if (attendSelect.val() == "0") {
        //         return true;
        //     }
        // }
        // var result = true;
        // $(".clothesStudentForm .hide_ipt").each(function () {
        //     var ipt = $(this);
        //     if (ipt.val() == "") {
        //         alert("请选择" + ipt.attr("data-name") + "尺码");
        //         result = false;
        //         return false;
        //     }
        // });
        var result = true;
        $("select.size_select").each(function () {
            var select = $(this);
            if (select.val() == "") {
                alert("请选择" + select.attr("data-name") + "尺码");
                result = false;
                return false;
            }
        });
        return result;
    }
</script>
[/@]