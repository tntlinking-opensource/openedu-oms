[#ftl]
[@b.head/]
[@b.form name="stationStudentSearchForm"  action="!search" target="stationStudentList" title="ui.searchForm" theme="search"]
    [@b.textfield label="年份" name="stationStudent.student.grade" value=grade/]
    [#--[@b.select label="学历层次" name="stationStudent.student.education.id" iclass="s_education"/]--]
    [@b.textfield label="学号" name="stationStudent.student.code"/]
    [@b.textfield label="姓名" name="stationStudent.student.name"/]
    [@b.select label="是否按时报到" name="stationStudent.intime" items={'1':'是','0':'否'}/]
    [#--[@b.select label="校区" name="stationStudent.student.campus.id" iclass="s_campus cascade"/]--]
    [#--[@b.select label="院系" name="stationStudent.student.department.id" iclass="s_department cascade"/]--]
    [#--[@b.select label="专业" name="stationStudent.student.major.id" iclass="s_major"/]--]
[/@]
[@b.div id="stationStudentList" href="!search?stationStudent.student.grade=${grade}" /]
[#--<script src="${base}/static/yxxt/scripts/major.js"></script>--]
<script>
	$(function () {
//		$(".s_education").loadEducation();
//		$(".s_campus").loadCampus();
//		$(".s_department").loadDepartment().cascadeMajor(".s_major");
	});
</script>
[@b.foot/]