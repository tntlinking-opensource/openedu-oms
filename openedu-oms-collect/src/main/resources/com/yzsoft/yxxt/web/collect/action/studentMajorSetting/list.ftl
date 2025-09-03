[#ftl]
[@b.head/]
[@b.grid  items=majorInfos var="majorInfo"]
    [@b.gridbar]
	bar.addItem("新建",action.add());
	bar.addItem("修改",action.edit());
	bar.addItem("删除",action.remove());
	bar.addItem("选项",action.method("option"));
    [/@]
    [@b.row align="center"]
        [@b.boxcol width="1"/]
        [@b.col title="排序" property="sort"/]
        [@b.col title="专业" property="major.name"/]
        [@b.col title="招生性别" property="gender.name"/]
        [@b.col title="招生类别"][#list majorInfo.enrollTypes as v][#if v_index gt 0]、[/#if]${v.name}[/#list][/@]
        [@b.col title="是否置顶" property="isTop"]
            [@c.boolean majorInfo.top/]
        [/@]
        [@b.col title="状态" property="enabled"]
            [@c.enabled majorInfo.enabled/]
        [/@]
    [/@]
[/@]
[@b.foot/]