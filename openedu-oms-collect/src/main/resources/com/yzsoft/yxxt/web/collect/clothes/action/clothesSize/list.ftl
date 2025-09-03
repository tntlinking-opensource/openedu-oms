[#ftl]
[@b.head/]
[@b.grid  items=clothesSizes var="clothesSize"]
    [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove());
    //bar.addItem("返回",action.method("back"));
        [#if typeId??]
        bar.addItem("批量添加",action.method("multiEdit"));
        [/#if]
    [/@]
    [@b.row align="center"]
        [@b.boxcol width="1%"/]
        [@b.col title="类型" property="type.name" /]
        [@b.col title="代码" property="code" /]
        [@b.col title="名称" property="name" /]
        [@b.col title="值" property="value" /]
    [/@]
[/@]
[@b.foot/]