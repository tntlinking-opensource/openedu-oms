[#ftl]
[@b.head/]
[@b.grid  items=studentLeaves var="studentLeave"]
    [@b.gridbar]
	window.stuentAction = action;
	bar.addItem("请假",action.single("edit"));
	bar.addItem("销假",action.multi("reportBack"));
	bar.addItem("取消请假",action.remove());
	bar.addItem("导出", action.exportDataProperty("student.code:学号,student.name:姓名,student.gender.name:性别,student.department.name:院系,student.major.name:专业,leave.days:请假天数,leave.reason:请假原因,leave.state:状态",null, "fileName=新生请假"));
    [/@]
    [@b.row align="center"]
        [@b.boxcol/]
        [@b.col property="student.code" title="学号"]
            [#if studentLeave.leave??]
                [@b.a href="!info?studentLeave.id=${(studentLeave.id)!}"]${(studentLeave.student.code)!}[/@]
            [#else ]
            ${(studentLeave.student.code)!}
            [/#if]
        [/@]
        [@b.col property="student.name" title="姓名" /]
        [@b.col property="student.gender.name" title="性别" /]
        [@b.col property="student.department.name" title="院系" /]
        [@b.col property="student.major.name" title="专业" /]
        [@b.col property="leave.days" title="请假天数" /]
        [@b.col property="leave.state" title="状态" /]
    [/@]
[/@]
[@b.foot/]