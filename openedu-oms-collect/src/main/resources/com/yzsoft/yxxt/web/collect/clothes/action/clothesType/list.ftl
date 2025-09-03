[#ftl]
[@b.head/]
[@b.grid  items=clothesTypes var="clothesType" sortable="false"]
    [@b.gridbar]
        [#if superadmin]
        bar.addItem("${b.text("action.new")}",action.add());
        [/#if]
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove());
    //bar.addItem("尺码管理",action.single("size"));
    //bar.addItem("衣服照片",action.method("size"));
    [/@]
    [@b.row align="center"]
        [@b.boxcol width="1%"/]
        [@b.col title="代码" property="code" /]
        [@b.col title="名称" property="name" /]
    [#-- [@b.col title="材质" property="mate" /]
     [@b.col title="图片"]
         [#if clothesType.img??]
         <img style="max-height: 100px; max-width: 100px;" src="${base}/${clothesType.img}"/>
         [/#if]
     [/@]
     [@b.col title="价格" property="price" /]--]
        [@b.col title="尺码单位" property="sizeName" /]
    [/@]
[/@]
[@b.foot/]