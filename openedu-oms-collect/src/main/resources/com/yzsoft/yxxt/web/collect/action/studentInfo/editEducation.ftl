[#ftl]
[#include "../comm/macro.ftl"/]
[@form code="01" action="!saveEducation"]
    [@c.bootstrapDatepickerJsAndCss/]
<style>
    .itable td { text-align: center; }
    .itable input { width: 100%; }
    .itable input.datepicker { text-align: center; }
    .education_table td {position: relative;}
    .education_table label.error {position: absolute;left: 9px; top: 28px;}
</style>
<div>
    <h3 class="caption">教育经历信息</h3>
    <div style="margin: 15px;">
        <table id="table_education" class="table table-bordered itable education_table">
            <thead>
            <tr>
                <th>开始日期</th>
                <th>结束日期</th>
                <th>在何地何学校</th>
                <th>任何职</th>
                <th>证明人</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
                [#list educations as education]
                    [@educationTr education/]
                [/#list]
            </tbody>
        </table>
    </div>
    <div style="padding: 0 15px;">
        <div style="display: none;">
            <textarea id="templateTr">[@educationTr educationEmpty/]</textarea>
        </div>
        <button id="btn_add_tr" type="button" class="btn_add_more">添加更多</button>
    </div>
</div>
<input type="hidden" name="code" value="EDUCATION"/>
<script>
    function beforeSubmit() {
        if ($(".education_table tbody tr:not(:hidden)").length == 0) {
            alert("至少需要添加一条教育信息");
            return false;
        }
        return true;
    }

    (function () {
        $("#btn_add_tr").click(function () {
            var tr = $($("#templateTr").val());
            tr.appendTo("#table_education tbody");
            sortTr();
            initTr();
            if ($("#table_education tr").length >= 5) {
                $("#btn_add_tr").hide();
            }
        });

        $("#table_education").on("click", "button", function () {
            $(this).closest("tr").remove();
            $("#btn_add_tr").show();
            sortTr();
        });

        function sortTr() {
            $("#table_education tbody tr").each(function (i) {
                var tr = $(this), name = "education" + i;
                tr.find(".index").val(i);
                tr.find(".sort").attr("name", name + ".sort");
                tr.find(".start_date").attr("name", name + ".startDate");
                tr.find(".end_date").attr("name", name + ".endDate");
                tr.find(".unit").attr("name", name + ".unit");
                tr.find(".post").attr("name", name + ".post");
                tr.find(".reterence").attr("name", name + ".reterence");
            });
        }

        function initTr(tr) {
            $('.datepicker', tr).datepicker({
                autoclose: true,
                language: "zh-CN",
                format: 'yyyy-mm',
                startView: 2,
                minViewMode: 1,
                clearBtn: true,
            });
            $('.start_date', tr).on("changeMonth", function (e) {
                var endDate = $(this).parent().find(".end_date");
                endDate.datepicker("setStartDate", e.date);
            });
            $('.end_date', tr).on("changeMonth", function (e) {
                var startDate = $(this).parent().find(".start_date");
                startDate.datepicker("setEndDate", e.date);
            });
        }

        $("#table_education tbody tr").each(function (i) {
            initTr($(this));
        });

        sortTr();
    })();
</script>
[/@form]

[#macro educationTr education]
    <tr>
        <td>
            <input type="hidden" name="education" class="index"/>
            <input type="hidden" class="sort"/>
            <input value="${(education.startDate?string("yyyy-MM"))!}" class="datepicker start_date" maxlength="30"
                   required/>
        </td>
        <td>
            <input value="${(education.endDate?string("yyyy-MM"))!}" class="datepicker end_date" maxlength="30"
                   required/>
        </td>
        <td>
            <input value="${(education.unit)!}" maxlength="100" required class="unit"/>
        </td>
        <td>
            <input value="${(education.post)!}" maxlength="30" required class="post"/>
        </td>
        <td>
            <input value="${(education.reterence)!}" maxlength="30" required class="reterence"/>
        </td>
        <td>
            <button type="button" class="btn_remove" style="width: 60px;">删除</button>
        </td>
    </tr>
[/#macro]