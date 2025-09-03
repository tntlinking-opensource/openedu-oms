[#ftl]
[@b.head/]
[@b.grid  items=dictDatas var="dictData"]
    [@b.gridbar]
    bar.addItem("新建",action.add());
    bar.addItem("修改",action.edit());
    bar.addItem("删除",action.remove());
    bar.addItem("启用",activate(1),'fa-key','green');
    bar.addItem("禁用",activate(0),'fa-lock','red');
    bar.addItem("导入",action.method("importForm"));
    bar.addItem("导出", action.exportDataProperty());
    [/@]
<script>
    function activate(val) {
        var msg = "是否批量标记为“" + (val == 1 ? "启用" : "禁用") + "”?";
        return action.multi("activate", msg, "isActivate=" + val);
    }
</script>
    [@b.row align="center"]
        [@b.boxcol width="1%"/]
        [@b.col title="代码" property="gbxb" /]
        [@b.col title="名称" property="name" /]
        [@b.col title="状态" property="enabled"]
            [@c.enabled dictData.enabled/]
        [/@]
    [/@]
[/@]
[@b.foot/]