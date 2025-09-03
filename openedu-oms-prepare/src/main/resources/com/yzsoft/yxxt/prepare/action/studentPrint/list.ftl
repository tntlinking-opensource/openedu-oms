[#ftl]
[@b.head/]
[@b.grid  items=studentPrintViews var="studentPrintView"]
    [@b.gridbar]
	window.studentAction = action;
	bar.addItem("打印通知单", 'printNotice("printNotice")');
	bar.addItem("打印封面", 'printNotice("printFace")');
    [/@]
<script>
	function printNotice(method) {
		var form = $(".studentPrintViewPrintForm");
		form.find(".method").val(method);
		bg.form.submitId(form[0], "studentPrintView.id", true);
	}
</script>
    [@b.row align="center"]
        [@b.boxcol/]
        [#--[@b.col property="student.grade" title="年级" /]--]
	    [@b.col title="学院" property="student.department.name"/]
	    [@b.col title="专业" property="student.major.name"/]
	    [@b.col title="班级" property="student.adminClass.name"/]
        [@b.col property="student.letterCode" title="考生号" /]
        [@b.col property="student.code" title="学号" /]
        [@b.col property="student.name" title="姓名" /]
        [@b.col property="student.gender.name" title="性别" /]
[#--        [@b.col property="student.cardcode" title="身份证号" /]--]
[#--        [@b.col property="student.graduateSchool" title="毕业学校" /]--]
[#--        [@b.col property="student.education.name" title="学历层次" /]--]
        [@b.col property="student.enrollType.name" title="招生类别" /]
[#--        [@b.col property="student.trainType.name" title="培养类型" /]--]
        [@b.col property="printNotice" title="通知单是否打印" align="center"][@c.boolean (studentPrintView.print.printNotice)!false/][/@]
        [@b.col property="printFace" title="封面是否打印" align="center"][@c.boolean (studentPrintView.print.printFace)!false/][/@]
    [/@]
[/@]
[@b.form action="student-print" class="studentPrintViewPrintForm" target="_blank"]
<input type="hidden" name="method" value="" class="method"/>
[/@]
[@b.foot/]