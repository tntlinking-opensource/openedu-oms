[#ftl]
[@b.head/]
[@b.toolbar title="招生计划"]bar.addBack();[/@]
[@b.form name="majorAllocForm" action="!allocSave" theme="form"]
<div class="row" style="text-align: center; margin: 10px 0;">
    <div class="col-md-2">
        年份：${grade}
    </div>
    <div class="col-md-2">
        [#if education??]
            学历层次：<span class="plan_total">${(education.name)!}</span>
        [/#if]
    </div>
    <div class="col-md-3">计划招生人数：<span class="plan_total"></span></div>
    <div class="col-md-3">
        [#assign alloNum = 0/]
        [#list majorCount as mc]
            [#assign alloNum = alloNum + mc[1]/]
        [/#list]
        已分配人数：${alloNum}
    </div>
    <div class="col-md-2">学生报名人数：<span class="student_total">${studentTotal}</span></div>
</div>
<table class="table table-bordered i-table">
    <thead>
    <tr>
        <th>学院</th>
        <th>专业</th>
        <th>招生人数</th>
        <th>已分配人数</th>
        <th>第一志愿报名人数</th>
    </tr>
    </thead>
    <tbody>
        [#list majors as m]
        <tr align="center">
            <td>${m.department.name}</td>
            <td>${m.name}</td>
            <td class="nospan">
                <input type="hidden" name="majorPlan" value="${m.id}"/>
                <input type="hidden" name="majorPlan${m.id}.major.id" value="${m.id}"/>
                <input name="majorPlan${m.id}.total" class="id_${m.id} total_ipt" value=""/>
            </td>
            <td class="nospan">
                [#list majorCount as mc]
                    [#if mc[0] == m.id]${mc[1]}[/#if]
                [/#list]
            </td>
            <td class="nospan">
                [#list major1Count as mc]
                    [#if mc[0] == m.id]${mc[1]}[/#if]
                [/#list]
            </td>
        </tr>
        [/#list]
    </tbody>
</table>
    [@b.formfoot]
        [@b.redirectParams/]
    <input type="hidden" name="grade" value="${grade}"/>
    <input type="hidden" name="educationId" value="${educationId}"/>
        [@b.submit value="action.submit" onsubmit="submitForm"/]
        [@b.submit value="清空" onsubmit="cleanForm" buttonClass="red" style="margin-left:200px;"/]
    [/@]
[/@]
<script>
    $(function () {
        var table = $(".i-table");
        table.find("td").attr("rowspan", "1");
        var trNum = table.find("tbody tr").length;
        if (trNum == 0) {
            return;
        }
        var tdNum = table.find("tbody tr").first().find("td").length;
        for (var i = 0; i < tdNum; i++) {
            if (table.find("tbody tr").first().find("td").eq(i).is(".nospan")) {
                continue;
            }
            var prevTd = null;
            for (var j = 0; j < trNum - 1; j++) {
                var td = prevTd == null ? table.find("tbody tr").eq(j).find("td").eq(i) : prevTd;
                var nextTd = table.find("tbody tr").eq(j + 1).find("td").eq(i);
                if (td.text() == nextTd.text()) {
                    prevTd = td;
                    prevTd.attr("rowspan", prevTd.attr("rowspan") * 1 + 1);
                    nextTd.addClass("remove");
                } else {
                    prevTd = null;
                }
            }
        }
        table.find(".remove").remove();

        $(".total_ipt").keyup(function () {
            var total = 0;
            $(".total_ipt").each(function () {
                if (!isNaN(this.value)) {
                    total += this.value * 1;
                }
            });
            $(".plan_total").text(total);
        });
        setTimeout(function () {
            $(".total_ipt").eq(0).keyup();
        }, 1);

    [#list majorPlans as mp]
        $(".id_${mp.major.id}").val(${mp.total});
    [/#list]
    });

    function submitForm() {
        var total = $(".plan_total").text() * 1;
        var stotal = $(".student_total").text() * 1;
        if (total < stotal) {
            return confirm("计划数小于学生数，是否继续？");
        }
        return true;
    }

    function cleanForm(form) {
        if (confirm("是否清空所有分配数据？")) {
            form.action = "${b.url("!clean")}";
            return true;
        }
        return false;
    }
</script>
[@b.foot/]