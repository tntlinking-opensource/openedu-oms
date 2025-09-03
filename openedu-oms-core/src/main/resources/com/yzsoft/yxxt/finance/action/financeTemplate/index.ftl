[#ftl]
[@b.head/]
[@b.form name="financeTemplateSearchForm"  action="!search" target="financeTemplatelist" title="ui.searchForm" theme="search"]
    [@b.select label="年份" name="financeTemplate.year" value=year items=years/]
    [@b.select label="学历层次" name="financeTemplate.education.id" iclass="s_education"/]
    [@b.select label="院系" name="department_id" iclass="s_department cascade"/]
    [@b.select label="专业" name="major_id" iclass="s_major"/]
[/@]
[@b.div id="financeTemplatelist" href="!search?financeTemplate.year=${year}" /]
<script src="${base}/static/yxxt/scripts/major.js"></script>
<script>
    $(function () {
        $(".s_education").loadEducation();
        $(".s_department").loadDepartment().cascadeMajor(".s_major");
    });
</script>
[@b.foot/]