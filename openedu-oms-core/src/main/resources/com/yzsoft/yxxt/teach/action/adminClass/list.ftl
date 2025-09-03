[#ftl]
[@b.head/]
[@b.grid  items=adminClasss var="adminClass"]
    [@b.gridbar title="菜单列表"]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.edit")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove());
    [#--bar.addItem("${b.text("action.info")}",action.info());--]
    bar.addItem("导入",action.method("importForm"));
    bar.addItem("${b.text("action.export")}",action.exportData("code:专业方向代码,name:专业方向名称,nameEn:专业方向英文名称,grade:年级,major.code:所属专业代码,major.name:所属专业,department.code:所属院系代码,department.name:所属院系,enabledStr:是否可用",null,"&fileName=班级信息"));
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
        [@b.boxcol/]
        [@b.col property="code" title="班级代码"]
            [@b.a href="!info?adminClass.id=${adminClass.id!}"]${(adminClass.code)!}[/@]
        [/@]
        [@b.col property="name" title="班级名称"/]
        [@b.col property="grade" title="年级"/]
        [@b.col property="department.name" title="所属${b.text('department')}"/]
        [@b.col property="major.name" title="所属专业"/]
        [@b.col property="instructor.name" title="辅导员"]
            [#list adminClass.instructors as instructor]
            ${(instructor.name)!}(${(instructor.code)!})
            [/#list]
        [/@]
        [@b.col property="enabled" title="是否可用" align="center"]
            [@c.sfyx enabled=adminClass.enabled yes="是" no="否"/]
        [/@]
    [/@]
[/@]
[@b.foot/]