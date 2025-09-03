[#ftl]
[@b.head/]
[@b.grid  items=askCommons var="askCommon"]
    [@b.gridbar]
    bar.addItem("新增",action.add());
    bar.addItem("修改",action.edit());
    bar.addItem("删除",action.remove());
    bar.addItem("启用",activate(1),'fa-key','green');
    bar.addItem("禁用",activate(0),'fa-lock','red');
    [/@]
<script>
    function activate(isActivate) {
        return action.multi("activate", "确定操作?", "isActivate=" + isActivate);
    }
</script>
    [@b.row]
        [@b.boxcol/]
        [@b.col title="排序" property="sort" /]
        [@b.col title="咨询内容" property="content" /]
        [@b.col title="回复内容" property="replyContent" /]
        [@b.col title="是否置顶" property="top" align="center"]
            [@c.boolean askCommon.top/]
        [/@]
        [@b.col title="状态" property="enabled" align="center"]
            [@c.enabled askCommon.enabled/]
        [/@]
    [/@]
[/@]
[@b.foot/]