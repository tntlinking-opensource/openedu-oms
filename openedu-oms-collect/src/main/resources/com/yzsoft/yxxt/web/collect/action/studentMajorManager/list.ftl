[#ftl]
[@b.head/]
[#assign nums=["","一","二","三","四","五","六"]/]
[@b.grid  items=studentMajors var="studentMajor"]
    [@b.gridbar]
	bar.addItem("新建",action.add());
	bar.addItem("修改",action.edit());
	bar.addItem("删除",action.remove());
	bar.addItem("统计",action.method("count"));
	bar.addItem("导出",action.exportData("student.code:学号,student.name:姓名[#list 1..majorNum as v],major${v}.name:第${nums[v]}志愿[/#list],alterable:是否服从调剂",null,"&fileName=学生自选专业"));
    [/@]
    [@b.row align="center"]
        [@b.boxcol width="1"/]
        [@b.col title="学号" property="student.code"/]
        [@b.col title="姓名" property="student.name"/]
        [@b.col title="招生类别" property="student.enrollType.name"/]
        [#list 1..majorNum as v]
            [@b.col title="第"+nums[v]+"志愿"]
                ${(studentMajor.getDetail(v).major.name)!}
            [/@]
        [/#list]
        [@b.col property="alterable" title="是否服从调剂"]
            [#if  studentMajor.alterable??]
                [@c.boolean studentMajor.alterable/]
            [/#if]
        [/@]
    [/@]
[/@]
[@b.foot/]