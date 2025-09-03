[#ftl]
[@b.head/]
[@b.form name="financeGreenStdSearchForm"  action="!search" target="financeGreenStdList" title="ui.searchForm" theme="search"]
    [@b.textfield label="年份" name="financeGreenStd.student.year" value=year/]
    [@b.select label="学历层次" name="financeGreenStd.student.education.id" iclass="s_education"/]
    [@b.textfield label="学号" name="financeGreenStd.student.code"/]
    [@b.textfield label="姓名" name="financeGreenStd.student.name"/]
    [@b.select label="校区" name="financeGreenStd.student.campus.id" iclass="s_campus cascade"/]
    [@b.select label="院系" name="financeGreenStd.student.department.id" iclass="s_department cascade"/]
    [@b.select label="专业" name="financeGreenStd.student.major.id" iclass="s_major"/]
	[@b.select label="是否办理" name="financeGreenStd.handle" items={'1':'是','0':'否'}/]
[/@]
[@b.div id="financeGreenStdList" href="!search?financeGreenStd.student.year=${year}" /]
<script src="${base}/static/yxxt/scripts/major.js"></script>
<script>
	$(function () {
		$(".s_education").loadEducation();
		$(".s_campus").loadCampus();
		$(".s_department").loadDepartment().cascadeMajor(".s_major");
	});
</script>
[@b.foot/]