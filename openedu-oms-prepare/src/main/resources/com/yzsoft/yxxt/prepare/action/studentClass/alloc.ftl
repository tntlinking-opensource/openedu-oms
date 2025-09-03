[#ftl]
[#import "/template/bootstrap.ftl" as bs/]
[@b.head/]
[@b.toolbar title="分班计划"]bar.addBack();[/@]
[@b.form action="!allocSave" validate="true" theme="form"]
    [@bs.panel title="分班规则"]
        [#macro rule label code]
            [@bs.formGroup label=label]
            <div class="mt-radio-list">
                [#list rules as rule]
                    [#if rule.type == code]
                    [@bs.checkbox name="rule.${code}" required="required" value=rule.id]${rule.name}[/@bs.checkbox]
                [/#if]
                [/#list]
            </div>
            [/@bs.formGroup]
        [/#macro]
        [@rule label="班级名称" code="NAME"/]
        [@rule label="班级代码" code="CODE"/]
        [@rule label="性别" code="GENDER"/]
        [@rule label="成绩" code="SCORE"/]
    [/@bs.panel]
    [@bs.panel title="分班计划"]
    <div style="width: 600px; margin: auto;">
        <div>
            <div style="float: right">
                <style>
                    .i_right > div { float: right; }
                </style>
                <div class="form-group form-inline i_right">
                    <div>
                        <button type="button" class="btn id_num_btn">计算班级数量</button>
                    </div>
                    <div>
                        <input class="form-control v_integer id_num_ipt" style="width: 120px;"/>
                    </div>
                    <div style="padding: 8px 5px;"><label>班级最多人数：</label></div>
                </div>
            </div>
        </div>
        <table class="table table-bordered table-striped has-error id_table">
            <thead>
            <tr>
                <th width="28%">专业</th>
            [#--<th width="28%">专业方向</th>--]
                <th width="14%">学生人数</th>
                <th width="30%">分班数量</th>
            </tr>
            </thead>
            <tbody>
                [#list studentNums as v]
                <tr align="center">
                    <td>${v[1]!}</td>
                [#--<td>${v[3]!}</td>--]
                    <td><span class="num">${v[2]!}</span></td>
                    <td>
                        <input type="hidden" name="plan" value="${v_index}"/>
                        <input type="hidden" name="plan${v_index}.majorId" value="${v[0]}"/>
                    [#--<input type="hidden" name="plan${v_index}.directionId" value="${v[2]!}"/>--]
                        <input name="plan${v_index}.num" class="v_integer id_major_num_put" required
                               style="width: 80px;"/>
                    </td>
                </tr>
                [/#list]
            </tbody>
        </table>
    </div>
    [/@bs.panel]

    [@b.formfoot]
        [@b.redirectParams/]
        [@b.submit value="action.submit" onsubmit="submitForm"/]
    [/@]
[/@]
<script>
    $(function () {
        $(".id_num_btn").click(function () {
            var num = $(".id_num_ipt").val();
            if (num == "" || isNaN(num) || num * 1 < 0) {
                return;
            }
            num = num * 1;
            $(".id_table tbody tr").each(function () {
                var inum = $(".num", this).text() * 1;
                $(".id_major_num_put", this).val(Math.ceil(inum / num))
            });
        });

        $(".mt-radio-list").each(function () {
            var radios = $("input", this);
            if (radios.length == 1) {
                radios.prop("checked", true);
            }
        });
    });

    function submitForm(form) {
        return $(form).valid();
    }
</script>
[@b.foot/]