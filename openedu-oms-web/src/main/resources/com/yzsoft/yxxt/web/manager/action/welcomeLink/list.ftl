[#ftl]
[@b.head/]
[@b.grid  items=welcomeLinks var="welcomeLink"]
    [@b.gridbar]
        [#if welcomeLinks?size lt 6]
        bar.addItem("${b.text("action.new")}",action.add());
        [/#if]
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove());
    bar.addItem("${b.text("排序")}", action.method('sort'));
    [/@]
    [@b.row]
        [@b.boxcol/]
        [@b.col width="10%" property="sort" title="顺序号" align="center"/]
        [@b.col width="30%" property="name" title="名称" align="center"/]
        [@b.col width="50%" property="url" title="链接" align="center"/]
        [@b.col width="10%" property="enabled" title="状态" align="center"]
            [@c.enabled welcomeLink.enabled/]
        [/@]
    [/@]
[/@]
[@b.foot/]