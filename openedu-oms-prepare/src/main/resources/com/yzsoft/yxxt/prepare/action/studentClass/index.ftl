[#ftl]
[@b.head/]
[@b.form name="studentClassSearchForm"  action="!search" target="studentClasslist" title="ui.searchForm" theme="search"]
    [@b.textfield label="年份" name="studentClass.student.grade" value="${grade}"/]
    [@b.select label="学历层次" name="studentClass.student.education.id" items=educations noempty="true"/]
[#--[@b.select label="校区" name="studentClass.student.campus.id" iclass="s_campus cascade"/]--]
[#--[@b.select label="院系" name="studentClass.student.department.id" iclass="s_department cascade"/]--]
    [#if majors??]
        [@b.select label="专业" name="studentClass.student.major.id" items=majors/]
    [/#if]
[#--[@b.textfield label="学号" name="studentClass.student.enrollCode"/]--]
    [@b.textfield label="学号" name="studentClass.student.code"/]
    [@b.textfield label="姓名" name="studentClass.student.name"/]
[/@]
[@b.div id="studentClasslist" href="!search?studentClass.student.grade=${grade}&studentClass.student.education.id=${educations[0].id}" /]
[#--<script src="${base}/static/yxxt/scripts/major.js"></script>--]
<script>
    $(function () {
//       $(".s_education").loadEducation();
//       $(".s_campus").loadCampus();
//       $(".s_department").loadDepartment().cascadeMajor(".s_major");
    });
</script>
[@b.foot/]