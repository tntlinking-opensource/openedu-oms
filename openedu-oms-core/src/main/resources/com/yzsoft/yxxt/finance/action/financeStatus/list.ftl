[#ftl]
[@b.head/]
[@b.grid  items=financeStudents var="financeStudent"]
	[@b.gridbar]
		[#if userid == 1]
			bar.addItem("删除",action.remove());
		[/#if]
		bar.addItem("生成缴费信息",action.method("create"));
		bar.addItem("同步",financeSync);
		bar.addItem("导入",action.method("importForm"));
		bar.addItem("导出", action.exportDataProperty());
		bar.addItem("导出明细", action.method("exportDetail", null, null, false));
	[/@]
	<script>
		function financeSync() {
			alert("敬请期待...");
		}
	</script>
	[@b.row align="center"]
		[@b.boxcol/]
		[@b.col title="院系" property="student.department.name"/]
		[@b.col title="专业" property="student.major.name"/]
		[@b.col title="班级" property="student.adminClass.name"/]
		[@b.col title="学号" property="student.code"/]
		[@b.col title="姓名" property="student.name"/]
		[@b.col title="性别" property="student.gender.name"/]
		[@b.col title="应缴金额" property="feeTotal"/]
		[@b.col title="已缴金额" property="feePaid"/]
		[@b.col title="未缴金额" property="feeOdd"/]
		[@b.col title="减免金额" property="greenMoney"/]
	[/@]
[/@]
[@b.foot/]