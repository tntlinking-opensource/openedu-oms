[#ftl]
[@b.head/]
[@b.form name="financeStatusSearchForm"  action="!search" target="financeStatuslist" title="ui.searchForm" theme="search"]
    [@b.textfield label="年份" name="financeStudent.year" value=year/]
    [@b.textfield label="学号" name="financeStudent.student.code"/]
    [@b.textfield label="姓名" name="financeStudent.student.name"/]
    [@b.select label="院系" name="financeStudent.student.department.id" iclass="s_department cascade"/]
    [@b.select label="专业" name="financeStudent.student.major.id" iclass="s_major cascade"/]
    [@b.select label="是否缴清" name="finished"items={'1':'是','0':'否'}/]
[/@]
[@b.div id="financeStatuslist" href="!search?financeStudent.year=${year}" /]
<script src="${base}/static/yxxt/scripts/major.js"></script>
<script>
    $(function () {
        $(".s_department").loadDepartment().cascadeMajor(".s_major");
    });
</script>
[@b.foot/]