[#ftl]
[@b.head/]
[@b.grid  items=footLinks var="footLink"]
    [@b.gridbar]
	bar.addItem("${b.text("action.new")}",action.add());
	bar.addItem("${b.text("action.modify")}",action.edit());
	bar.addItem("${b.text("action.delete")}",action.remove());
        [#if Parameters["footLink.group.id"]??]
		bar.addItem("${b.text("排序")}", action.method('sort'));
        [/#if]
    [/@]
    [@b.row]
        [@b.boxcol/]
        [@b.col width="10%" property="sort" title="排序" align="center"/]
        [@b.col width="20%" property="name" title="名称" align="center"/]
        [@b.col width="40%" property="url" title="链接" align="center"/]
        [@b.col width="20%" property="group.name" title="链接组" align="center"/]
        [@b.col width="10%" property="enabled" title="状态" align="center"]
            [@c.enabled footLink.enabled/]
        [/@]
    [/@]
[/@]
[@b.foot/]