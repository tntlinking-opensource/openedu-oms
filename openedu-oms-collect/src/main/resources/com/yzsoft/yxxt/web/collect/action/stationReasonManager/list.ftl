[#ftl]
[@b.head/]
[@b.grid  items=stationReasons var="stationReason"]
    [@b.gridbar]
	bar.addItem("新建",action.add());
	bar.addItem("修改",action.edit());
	bar.addItem("删除",action.remove());
    [/@]
    [@b.row]
        [@b.boxcol/]
        [@b.col title="不按时报到原因" property="name" width="100%" align="center"/]
    [/@]
[/@]
[@b.foot/]