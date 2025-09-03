[#ftl]
[@b.head/]
[@b.grid  items=shoesSizes var="shoesSize"]
    [@b.gridbar]
    bar.addItem("新建",action.add());
	bar.addItem("修改",action.edit());
	bar.addItem("删除",action.remove());
    [/@]
    [@b.row]
        [@b.boxcol width="1"/]
        [@b.col title="代码" property="code" align="center"/]
        [@b.col title="尺码" property="name" align="center"/]
        [@b.col title="脚长" property="footLength" align="center"/]
    [/@]
[/@]
[@b.foot/]