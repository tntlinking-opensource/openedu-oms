[#ftl]
[@b.head/]
[@b.form name="clothesStudentSearchForm"  action="!search" target="clothesStudentList" title="ui.searchForm" theme="search"]
    [@b.textfield label="年份" name="clothesStudent.student.year" value=year/]
    [@b.select label="学历层次" name="clothesStudent.student.education.id" iclass="s_education"/]
    [@b.textfield label="学号" name="clothesStudent.student.code"/]
    [@b.textfield label="姓名" name="clothesStudent.student.name"/]
    [@b.select label="校区" name="clothesStudent.student.campus.id" iclass="s_campus cascade"/]
    [@b.select label="院系" name="clothesStudent.student.department.id" iclass="s_department cascade"/]
    [@b.select label="专业" name="clothesStudent.student.major.id" iclass="s_major"/]
[/@]
[@b.div id="clothesStudentList" href="!search?clothesStudent.student.year=${year}" /]
<script src="${base}/static/yxxt/scripts/major.js"></script>
<script>
	$(function () {
		$(".s_education").loadEducation();
		$(".s_campus").loadCampus();
		$(".s_department").loadDepartment().cascadeMajor(".s_major");
	});
</script>
[@b.foot/]