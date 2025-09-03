[#ftl]
[@b.head/]
[@b.grid  items=askPlates var="askPlate"]
    [@b.gridbar]
    bar.addItem("新增",action.add());
    bar.addItem("修改",action.edit());
    bar.addItem("删除",action.remove());
    bar.addItem("启用",activate(1),'fa-key','green');
    bar.addItem("停用",activate(0),'fa-lock','red');
    bar.addItem("咨询迁移",action.method("move"));
    [/@]
<script>
    function activate(isActivate) {
        return action.multi("activate", "确定操作?", "isActivate=" + isActivate);
    }
</script>
    [@b.row]
        [@b.boxcol/]
        [@b.col title="代码" property="code"/]
        [@b.col title="名称" property="name" /]
        [@b.col title="状态" property="enabled"]
            [@c.enabled askPlate.enabled/]
        [/@]
    [/@]
[/@]
[@b.foot/]