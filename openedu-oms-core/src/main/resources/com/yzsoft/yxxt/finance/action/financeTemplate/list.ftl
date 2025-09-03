[#ftl]
[@b.head/]
[@b.grid  items=financeTemplates var="financeTemplate"]
    [@b.gridbar]
    bar.addItem("新增",action.add());
    bar.addItem("修改",action.edit());
    bar.addItem("删除",action.remove());
    bar.addItem("复制上一年度",action.method("copy"));
    [/@]
    [@b.row align="center"]
        [@b.boxcol/]
        [@b.col title="年份" property="year" /]
        [@b.col title="名称" property="name" /]
        [@b.col title="学历层次" property="education.name" /]
        [@b.col title="专业类别" property="majorType.name" /]
        [@b.col title="适用专业"]
            [#if financeTemplate.limitMajor]
                [#list financeTemplate.majors as major]
                    [#if major_index gt 0]、[/#if]${major.name}
                    [#if major_index gte 2]
                    （等${financeTemplate.majors?size}个专业）
                        [#break /]
                    [/#if]
                [/#list]
            [#else]
            所有专业
            [/#if]
        [/@]
        [@b.col title="总金额" property="money" /]
        [@b.col title="状态" property="enabled"]
            [@c.enabled financeTemplate.enabled/]
        [/@]
    [/@]
[/@]
[@b.foot/]