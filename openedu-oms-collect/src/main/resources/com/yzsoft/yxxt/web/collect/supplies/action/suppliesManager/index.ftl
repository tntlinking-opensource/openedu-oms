[#ftl]
[@b.head/]
[@b.form name="suppliesStdSearchForm"  action="!search" target="suppliesStdList" title="ui.searchForm" theme="search"]
    [@b.textfield label="年份" name="suppliesStd.student.year" value=year/]
    [#--[@b.select label="学历层次" name="suppliesStd.student.education.id" iclass="s_education"/]--]
    [@b.textfield label="学号" name="suppliesStd.student.code"/]
    [@b.textfield label="姓名" name="suppliesStd.student.name"/]
    [@b.textfield label="班级" name="suppliesStd.student.adminClass.name"/]
    [#--[@b.select label="校区" name="suppliesStd.student.campus.id" iclass="s_campus cascade"/]--]
    [#--[@b.select label="院系" name="suppliesStd.student.department.id" iclass="s_department cascade"/]--]
    [#--[@b.select label="专业" name="suppliesStd.student.major.id" iclass="s_major"/]--]
    [@b.select label="是否购买" name="suppliesStd.agree" items={'1':'是','0':'否'} value=""/]
    [@b.select label="是否领取" name="suppliesStd.used" items={'1':'是','0':'否'} value=""/]
[/@]
[@b.div id="suppliesStdList" href="!search?suppliesStd.student.year=${year}" /]
<script src="${base}/static/yxxt/scripts/major.js"></script>
<script>
	$(function () {
		$(".s_education").loadEducation();
		$(".s_campus").loadCampus();
		$(".s_department").loadDepartment().cascadeMajor(".s_major");
	});
</script>
[@b.foot/]