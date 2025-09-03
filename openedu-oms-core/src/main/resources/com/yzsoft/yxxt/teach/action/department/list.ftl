[#ftl]
[@b.head/]
[@b.grid  items=departments var="department"]
    [@b.gridbar title="菜单列表"]
        [#if userid == 1]
        bar.addItem("${b.text("action.new")}",action.add());
        bar.addItem("${b.text("action.edit")}",action.edit());
        bar.addItem("${b.text("action.delete")}",action.remove());
        [/#if]
    [#--bar.addItem("${b.text("action.info")}",action.info());--]
    [#--bar.addItem("导入",action.method("importForm"));--]
    [#--bar.addItem("${b.text("action.export")}",action.exportData("code:${b.text("department")}代码,name:${b.text("department")}名称,nameEn:英文名,departmentType.name:部门类型,enabledStr:是否可用",null,"&fileName=${b.text("department")}"));--]
    bar.addItem("同步", "syncData()","fa-random","blue");
    [/@]
<script>
    function syncData() {
        jQuery.colorbox({
            overlayClose: false,
            iframe: true,
            width: "500px",
            height: "170px",
            href: "${b.url('!sync')}"
        });
    }
</script>
    [@b.row]
        [@b.boxcol width="1%"/]
        [@b.col property="code"  title="department.code" align="center"/]
        [@b.col property="name" title="department.name" align="center"]
            [@b.a href="!info?department.id=${department.id!}"]${(department.name)!}[/@]
        [/@]
    [#--[@b.col property="nameEn"  title="department.nameEn"/]--]
        [#if b.text("department.departmentType.enabled") == "true"]
            [@b.col property="departmentType.name" title="department.departmentType.name" align="center"/]
        [/#if]
        [@b.col property="enabled" title="是否可用" align="center"]
            [@c.sfyx enabled=department.enabled yes="是" no="否"/]
        [/@]
    [/@]
[/@]
[@b.foot/]