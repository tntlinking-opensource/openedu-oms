[#ftl]
[@b.head/]
[@b.grid  items=students var="student"]
    [@b.gridbar]
    bar.addItem("${b.text("action.export")}",action.exportData("code:学号,name:姓名,gender.name:性别,department.name:学院,major.name:专业,adminClass.name:班级",null,"&fileName=学生信息"));
    bar.addItem("报到单批量打印", 'printWelcome("printWelcome")');
    [/@]
<script>
    function printWelcome(method) {
        var form = $(".studentPrintForm");
        form.find(".method").val(method);
        bg.form.submitId(form[0], "student.id", true);
    }
</script>
    [@b.row align="center"]
        [@b.boxcol/]
        [@b.col property="department.name" title="院系" /]
        [@b.col property="major.name" title="专业" /]
        [@b.col property="adminClass.name" title="班级" /]
        [@b.col property="code" title="学号" /]
        [@b.col property="name" title="姓名" /]
        [@b.col property="gender.name" title="性别" /]
    [/@]
[/@]
[@b.form action="student-print" class="studentPrintForm" target="_blank"]
<input type="hidden" name="method" value="" class="method"/>
[/@]
[@b.foot/]