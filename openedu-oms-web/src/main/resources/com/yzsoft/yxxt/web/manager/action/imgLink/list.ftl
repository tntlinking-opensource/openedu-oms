[#ftl]
[@b.head/]
[@b.grid  items=imgLinks var="imgLink"]
	[@b.gridbar]
		bar.addItem("${b.text("action.new")}",action.add());
		bar.addItem("${b.text("action.modify")}",action.edit());
		bar.addItem("${b.text("action.delete")}",action.remove());
    	bar.addItem("${b.text("排序")}", action.method('sort'));
	[/@]
	[@b.row]
		[@b.boxcol/]
		[@b.col width="10%" property="sort" title="顺序号" align="center"/]
		[@b.col width="20%" property="img" title="图片"  align="center"]
        	<img src="${base}/${(imgLink.img)!}" style="height: 30px;"/>
		[/@]
		[@b.col width="20%" property="name" title="名称" align="center"/]
		[@b.col width="40%" property="url" title="链接" align="center"/]
		[@b.col width="10%" property="enabled" title="状态" align="center"]
			[@c.enabled imgLink.enabled/]
		[/@]
	[/@]
[/@]
[@b.foot/]