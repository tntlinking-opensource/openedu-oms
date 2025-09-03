[#ftl]
[@b.head/]
[@b.grid  items=financeGreenStds var="financeGreenStd"]
    [@b.gridbar]
	//bar.addItem("新建",action.add());
	//bar.addItem("修改",action.edit());
	//bar.addItem("删除",action.remove());
	//bar.addItem("统计",action.method("count"));
	bar.addItem("${b.text("action.export")}", action.exportData("student.department.name:院系,student.major.name:专业,student.adminClass.name:班级," +
	    "student.code:学号,student.name:姓名,student.gender.name:性别," +
	    "handleStr:是否办理,items:申请项目",null,"&fileName=绿色通道明细数据"));
    [/@]
    [@b.row align="center"]
        [#--[@b.boxcol/]--]
	    [@b.col title="院系" property="student.department.name"/]
	    [@b.col title="专业" property="student.major.name"/]
	    [@b.col title="班级" property="student.adminClass.name"/]
        [@b.col title="学号" property="student.code"]
            [@b.a href="!info?id=${financeGreenStd.id}"]${(financeGreenStd.student.code)!}[/@]
        [/@]
        [@b.col title="姓名" property="student.name" /]
	    [@b.col title="性别" property="student.gender.name"/]
        [@b.col title="是否办理" property="handle"][@c.boolean financeGreenStd.handle/][/@]
        [@b.col title="申请项目" property="items" sortable="false"/]
    [/@]
[/@]
[@b.foot/]