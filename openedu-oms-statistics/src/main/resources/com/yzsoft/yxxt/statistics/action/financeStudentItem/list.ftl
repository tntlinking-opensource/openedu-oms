[#ftl]
[@b.head/]
[@b.grid  items=financeStudentItems var="financeStudentItem"]
    [@b.gridbar]
    bar.addItem("${b.text("action.export")}",action.exportData("financeStudent.student.code:学号,financeStudent.student.name:姓名,item.name:缴费项目,feeTotal:应缴金额,feePaid:已缴金额,feeOdd:未缴金额",null,"&fileName=财务缴费明细"));
    [/@]
<script>
    function printWelcome(method) {
        var form = $(".financeStudentItemPrintForm");
        form.find(".method").val(method);
        bg.form.submitId(form[0], "financeStudentItem.id", true);
    }
</script>
    [@b.row]
        [@b.boxcol/]
        [@b.col property="financeStudent.student.code" title="学号" /]
        [@b.col property="financeStudent.student.name" title="姓名" /]
        [@b.col property="item.name" title="缴费项目" /]
        [@b.col property="feeTotal" title="应缴金额" /]
        [@b.col property="feePaid" title="已缴金额" /]
        [@b.col property="feeOdd" title="未缴金额" /]
    [/@]
[/@]
[@b.form action="financeStudentItem-print" class="financeStudentItemPrintForm" target="_blank"]
<input type="hidden" name="method" value="" class="method"/>
[/@]
[@b.foot/]