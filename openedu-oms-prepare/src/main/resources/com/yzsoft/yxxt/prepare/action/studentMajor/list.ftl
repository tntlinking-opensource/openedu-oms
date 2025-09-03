[#ftl]
[@b.head/]
<div style="margin: 10px 0;">
    年级：${grade}&nbsp;&nbsp;[#if education??]学历层次：${(education.name)!}[/#if]&nbsp;&nbsp;已分配人数/学生总人数：${studentAlloced}/${studentTotal}（[#if studentTotal gt 0]${(studentAlloced/studentTotal)?string("percent")}[#else]0%[/#if]）
</div>
[@b.grid  items=studentMajors var="studentMajor"]
    [@b.gridbar]
    window.stuentAction = action;
    bar.addItem("分系",action.method("alloc"), "fa-edit", "blue");
    bar.addItem("修改",action.edit());
    [#--bar.addItem("删除",action.remove());--]
    bar.addItem("导入",action.method("importForm"));
    bar.addItem("导出",action.exportDataProperty("学号,姓名,专业代码,专业名称",null, "fileName=学生专业信息"));
    bar.addItem("默认第一专业",action.method("publishMajor1", "是否将所有学生的第一志愿设置为学生的专业？"));
    bar.addItem("发布",action.method("publish", "是否确定发布？"));
    [/@]
    [@b.row align="center"]
        [@b.boxcol/]
        [@b.col property="student.code" title="学号" /]
        [@b.col property="student.name" title="姓名" /]
    [#--[@b.col property="student.campus.name" title="校区" /]--]
    [#--[@b.col property="major.department.name" title="院系" /]--]
        [@b.col property="major.name" title="专业" /]
        [@b.col property="wishOrder" title="志愿顺序" /]
        [#assign nums=["","一","二","三","四","五","六"]/]
        [#list 1..majorNum as v]
        [#--[@b.col title="第"+nums[v]+"志愿" property="major"+v+".name"/]--]
            [@b.col title="第"+nums[v]+"志愿"]
            ${(studentMajor.getDetail(v).major.name)!}
            [/@]
        [/#list]
    [#--        [@b.col property="major1.name" title="第一志愿" /]
            [@b.col property="major2.name" title="第二志愿" /]
            [@b.col property="major3.name" title="第三志愿" /]--]
        [@b.col property="alterable" title="是否服从调剂"]
            [#if  studentMajor.alterable??]
                [@c.boolean studentMajor.alterable/]
            [/#if]
        [/@]
    [/@]
[/@]
[@b.foot/]