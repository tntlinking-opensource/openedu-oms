[#ftl]
[#include "../comm/macro.ftl"/]
[@form code="01" action="!saveFamily"]
<style>
    .itable td { text-align: center; }
    .itable input { width: 100%; }
    .family_table td {position: relative;}
    .family_table label.error {position: absolute;left: 9px; top: 28px;}
</style>
<div>
    <h3 class="caption">家庭成员信息</h3>
    <div style="margin: 15px;">
        <table id="table_family" class="table table-bordered itable family_table">
            <thead>
            <tr>
                <th>姓名</th>
                <th>称谓</th>
                <th>年龄</th>
                <th>工作单位</th>
                [#--<th>职位</th>--]
                <th>政治面貌</th>
                <th>联系电话</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
                [#list families as family]
                    [@familyTr family/]
                [/#list]
            </tbody>
        </table>
    </div>
    <div style="padding: 0 15px;">
        <div style="display: none;">
            <textarea id="templateTr">[@familyTr familyEmpty/]</textarea>
        </div>
        <button id="btn_add_tr" type="button">添加更多</button>
    </div>
</div>
<input type="hidden" name="code" value="FAMILY"/>
<script>
    function beforeSubmit() {
        if ($(".family_table tbody tr:not(:hidden)").length == 0) {
            alert("至少需要添加一个家庭成员");
            return false;
        }
        return true;
    }

    $(function () {
        jQuery.validator.addClassRules("v_age", {
            number: true,
            min: 0,
            max: 150,
        });
    });

    (function () {
        $("#btn_add_tr").click(function () {
            var tr = $($("#templateTr").val());
            tr.appendTo("#table_family tbody");
            sortTr();
            if ($("#table_family tr").length >= 3) {
                $("#btn_add_tr").hide();
            }
        });

        $("#table_family").on("click", "button", function () {
            $(this).closest("tr").remove();
            $("#btn_add_tr").show();
            sortTr();
        });

        function sortTr() {
            $("#table_family tbody tr").each(function (i) {
                var tr = $(this), name = "family" + i;
                tr.find(".index").val(i);
                tr.find(".sort").attr("name", name + ".sort");
                tr.find(".name").attr("name", name + ".name");
                tr.find(".title").attr("name", name + ".title");
                tr.find(".age").attr("name", name + ".age");
                tr.find(".unit").attr("name", name + ".unit");
                // tr.find(".post").attr("name", name + ".post");
                tr.find(".political").attr("name", name + ".political.id");
                tr.find(".phone").attr("name", name + ".phone");
            });
        }

        sortTr();

        if ($("#table_family tr").length >= 3) {
            $("#btn_add_tr").hide();
        }
    })();
</script>
[/@form]

[#macro familyTr family]
    <tr>
        <td>
            <input type="hidden" name="family" class="index"/>
            <input type="hidden" class="sort"/>
            <input value="${(family.name)!}" maxlength="20" required class="name"/>
        </td>
        <td>
            <input value="${(family.title)!}" maxlength="20" required class="title"/>
        </td>
        <td>
            <input value="${(family.age)!}" maxlength="3" class="v_age age" required style="width: 50px;"/>
        </td>
        <td>
            <input value="${(family.unit)!}" maxlength="100" required class="unit"/>
        </td>
        [#--<td>--]
            [#--<input value="${(family.post)!}" maxlength="20" required class="post"/>--]
        [#--</td>--]
        <td>
            <select required class="political">
                <option value="">请选择</option>
                [#list politicals as p]
                    <option value="${p.id}"
                            [#if p.id == (family.political.id)!0]selected[/#if]>${p.name}</option>
                [/#list]
            </select>
        </td>
        <td>
            <input value="${(family.phone)!}" maxlength="20" required class="phone"/>
        </td>
        <td>
            <button type="button" class="btn_remove" style="width: 60px;">删除</button>
        </td>
    </tr>
[/#macro]
