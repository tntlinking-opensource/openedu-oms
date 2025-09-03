[#ftl]
[@b.head/]
[@b.grid  items=financeItems var="financeItem"]
    [@b.gridbar]
    bar.addItem("新增",action.add());
    bar.addItem("修改",action.edit());
    bar.addItem("删除",action.remove());
    bar.addItem("可缓交",delayable(1),'fa-key','green');
    bar.addItem("不可缓交",delayable(0),'fa-lock','red');
    bar.addItem("可贷款",loanable(1),'fa-key','green');
    bar.addItem("不可贷款",loanable(0),'fa-lock','red');
    bar.addItem("启用",activate(1),'fa-key','green');
    bar.addItem("禁用",activate(0),'fa-lock','red');
    [/@]
<script>
    function activate(val) {
        var msg = "是否批量标记为“" + (val == 1 ? "启用" : "禁用") + "”?";
        return action.multi("activate", msg, "isActivate=" + val);
    }
    function delayable(val) {
        var msg = "是否批量标记为“" + (val == 1 ? "可缓交" : "不可缓交") + "”?";
        return action.multi("delayable", msg, "delayable=" + val);
    }
    function loanable(val) {
        var msg = "是否批量标记为“" + (val == 1 ? "可贷款" : "不可贷款") + "”?";
        return action.multi("loanable", msg, "loanable=" + val);
    }
</script>
    [@b.row align="center"]
        [@b.boxcol/]
        [@b.col title="代码" property="code"/]
        [@b.col title="名称" property="name" /]
        [@b.col title="是否可缓交" property="delayable"]
            [@c.boolean financeItem.delayable/]
        [/@]
        [@b.col title="是否可贷款" property="loanable"]
            [@c.boolean financeItem.loanable/]
        [/@]
        [@b.col title="状态" property="enabled"]
            [@c.enabled financeItem.enabled/]
        [/@]
    [/@]
[/@]
[@b.foot/]