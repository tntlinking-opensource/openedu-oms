[#ftl]
[@b.head/]
[@b.grid  items=majors var="major"]
    [@b.gridbar title="菜单列表"]
        [#if userid == 1]
        [#--bar.addItem("${b.text("action.new")}",action.add());--]
        [#--bar.addItem("${b.text("action.delete")}",action.remove());--]
        [#--bar.addItem("${b.text("action.info")}",action.info());--]
        [#--bar.addItem("导入",action.method("importForm"));--]
        [#--bar.addItem("${b.text("action.export")}",action.exportData("code:专业代码,name:专业名称,nameEn:专业英文名称,department.code:院系代码,department.name:院系名称,education.name:学历层次名称,duration:学制,enabledStr:是否可用",null,"&fileName=专业信息"));--]
        [/#if]
    bar.addItem("同步", "syncData()","fa-random","blue");
    [#--bar.addItem("${b.text("action.edit")}",action.edit());--]
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
        [@b.col property="code" width="10%" title="专业代码" align="center"/]
        [@b.col property="name" width="20%" title="专业名称" align="center"]
            [@b.a href="!info?major.id=${major.id!}"]${(major.name)!}[/@]
        [/@]
    [#--[@b.col property="nameEn" width="15%" title="专业英文名"/]--]
    [#--[@b.col property="abbreviation" width="15%" title="专业简称"/]--]
        [@b.col property="department.name" width="20%" title="所属${b.text('department')}" align="center"/]
        [@b.col property="education.name" width="10%" title="学历层次" align="center"/]
        [@b.col property="duration" width="5%" title="学制" align="center"/]
        [@b.col property="enabled" width="10%" title="是否可用" align="center"]
            [@c.sfyx enabled=major.enabled yes="是" no="否"/]
        [/@]
        [@b.col width="10%" title="操作" align="center"]
            [@b.a href="!edit?major.id=${major.id!}" class="btn btn-primary"]${b.text("action.edit")}[/@]
        [/@]
    [/@]
[/@]
[@b.foot/]