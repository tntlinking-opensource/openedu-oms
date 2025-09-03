[#ftl]
[@b.head/]
[@b.form name="studentMajorSearchForm"  action="!search" target="studentMajorlist" title="ui.searchForm" theme="search"]
    [@b.textfield label="年级" name="studentMajor.student.grade" value="${grade}"/]
    [@b.select label="学历层次" name="studentMajor.student.education.id" items=educations noempty="true"/]
[#--[@b.select label="校区" name="studentMajor.student.campus.id" iclass="s_campus cascade"/]--]
    [@b.select label="院系" name="studentMajor.major.department.id" iclass="s_department cascade"/]
    [@b.select label="专业" name="studentMajor.major.id" iclass="s_major"/]
    [@b.textfield label="学号" name="studentMajor.student.code"/]
    [@b.textfield label="姓名" name="studentMajor.student.name"/]
[/@]
[@b.div id="studentMajorlist" href="!search?studentMajor.student.grade=${grade}&studentMajor.student.education.id=${educations[0].id}" /]
<script src="${base}/static/yxxt/scripts/major.js"></script>
<script>
    $(function () {
        $(".s_campus").loadCampus();
        $(".s_department").loadDepartment().cascadeMajor(".s_major");
    });
</script>
[@b.foot/]