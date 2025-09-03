[#ftl]
[@b.head/]
[@b.grid  items=collectSwitchs var="collectSwitch"]
    [@b.gridbar]
        [#if userid == 1]
        bar.addItem("新建",action.add());
        bar.addItem("修改",action.edit());
        bar.addItem("删除",action.remove());
        [/#if]
    bar.addItem("开启",action.multi("enabled", "是否确定开启?", "enabled=1"),"fa-key","green");
    bar.addItem("关闭",action.multi("enabled", "是否确定关闭?", "enabled=0"),"fa-lock","red");
    bar.addItem("可编辑",action.multi("editable", "是否确定可编辑?", "editable=1"),"fa-cog");
    bar.addItem("不可编辑",action.multi("editable", "是否确定不可编辑?", "editable=0"),"fa-check","red");
    bar.addItem("必填",action.multi("required", "是否确定必填?", "required=1"),"fa-check","green");
    bar.addItem("非必填",action.multi("required", "是否确定非必填?", "required=0"),"fa-cog");
    [#--bar.addItem("在线缴费",action.multi("payment", "是否确定在线缴费?", "payment=1"),"fa-money","green");--]
    [#--bar.addItem("不在线缴费",action.multi("payment", "是否确定不在线缴费?", "payment=0"),"fa-cog");--]
    bar.addItem("注意事项",action.edit());
    [/@]
    [@b.row]
        [@b.boxcol/]
        [@b.col title="代码" property="code"  align="center"/]
        [@b.col title="名称" property="name"  align="center"]
            [#if (collectSwitch.icon)??]
            <img width="30" src="${base}/common/download.action?uuid=${(collectSwitch.icon)!}"/>
            [/#if]
        ${(collectSwitch.name)!}
        [/@]
        [@b.col title="开启状态" property="enabled" align="center"]
            [@c.boolean collectSwitch.enabled "开启" "关闭"/]
        [/@]
        [@b.col title="编辑状态" property="editable" align="center"]
            [@c.boolean collectSwitch.editable "可编辑" "不可编辑"/]
        [/@]
        [@b.col title="必填状态" property="required" align="center"]
            [@c.boolean collectSwitch.required "必填" "非必填" "label-default"/]
        [/@]
        [#--[@b.col title="是否在线支付" property="payment" align="center"]--]
            [#--[@c.boolean collectSwitch.payment "是" "否" "label-default"/]--]
        [#--[/@]--]
        [@b.col title="注意事项" property="remark" width="40%"]
        ${(collectSwitch.remark)!}
        [/@]
    [/@]
[/@]
[@b.foot/]