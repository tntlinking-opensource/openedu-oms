[#ftl]
[@b.head/]
[@b.form name="clothesStudentSizeSearchForm"  action="!search?orderBy=clothesStudentSize.student.student.code" target="clothesStudentSizeList" title="ui.searchForm" theme="search"]
    [@b.textfield label="年级" name="clothesStudentSize.student.student.grade" value=grade/]
    [@b.textfield label="学号" name="clothesStudentSize.student.student.code"/]
    [@b.textfield label="姓名" name="clothesStudentSize.student.student.name"/]
    [@b.select label="服装类型" name="clothesStudentSize.type.id" items=types/]
[/@]
[@b.div id="clothesStudentSizeList" href="!search?orderBy=clothesStudentSize.student.student.code" /]
<script src="${base}/static/yxxt/scripts/major.js"></script>
<script>
    $(function () {
        $(".s_education").loadEducation();
        $(".s_campus").loadCampus();
        $(".s_department").loadDepartment().cascadeMajor(".s_major").cascadeAdminClass(".s_adminClass");
    });
</script>
[@b.foot/]