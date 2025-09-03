[#ftl]
[@b.head/]
[@b.grid  items=stations var="station"]
    [@b.gridbar]
	bar.addItem("新建",action.add());
	bar.addItem("修改",action.edit());
	bar.addItem("删除",action.remove());
    [/@]
    [@b.row align="center"]
        [@b.boxcol/]
        [@b.col title="交通工具" property="vehicle.name"/]
        [@b.col title="站名" property="name"/]
    [/@]
[/@]
[@b.foot/]