[#ftl]
[@b.head/]
[@b.grid  items=itemLogStatuss var="itemLogStatus"]
    [@b.gridbar]
        bar.addItem("${b.text("action.export")}",action.exportData("批次,流程环节,学号,姓名,性别,院系,专业,班级,办理状态",null,"&fileName=业务项办理明细"));
        bar.addItem("导入",action.method("importForm"));
    [/@]
<script>
    function printWelcome(method) {
        var form = $(".itemLogStatusPrintForm");
        form.find(".method").val(method);
        bg.form.submitId(form[0], "itemLogStatus.id", true);
    }
</script>
    [@b.row align="center"]
    [#--[@b.boxcol/]--]
        [@b.col property="student.department.name" title="院系" /]
        [@b.col property="student.major.name" title="专业" /]
        [@b.col property="student.adminClass.name" title="班级" /]
        [@b.col property="student.code" title="学号" /]
        [@b.col property="student.name" title="姓名" /]
        [@b.col property="student.gender.name" title="性别" /]
        [@b.col property="item.name" title="环节" /]
        [@b.col property="log.createTime" title="办理时间"]
        ${(itemLogStatus.log.createTime?datetime)!}
        [/@]
    [#--        [@b.col property="log.cancelTime" title="撤消时间" sortable="false"]
                ${(itemLogStatus.log.cancelTime?datetime)!}
            [/@]--]
        [@b.col property="log.enabled" title="办理状态" sortable="false"]
            [@c.boolean (itemLogStatus.log.enabled)!false "已办理" "未办理"/]
        [/@]
    [/@]
[/@]
[@b.form action="itemLogStatus-print" class="itemLogStatusPrintForm" target="_blank"]
<input type="hidden" name="method" value="" class="method"/>
[/@]
[@b.foot/]