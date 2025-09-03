[#ftl]
[@b.head/]
[#assign adSearchParams="department.id,major.id"]
[#assign adSearchBooleanParams="inSchooled"]
[@b.form id="stdForm" name="stdForm" action="!search" title="ui.searchForm" target="studentList" theme="search"]
    [@b.textfields names="student.code;学号"/]
    [@b.textfields names="student.name;姓名"/]
    [@b.textfields names="student.grade;年级"/]
    [@b.textfields names="student.major.name;专业"/]
    [@b.textfields names="student.adminClass.name;班级"/]
    [@b.select label="是否有照片" name="hasPhoto" items={'1':'是','0':'否'}/]
[/@]
[@b.div href="!search?student.inSchooled=1" id="studentList" class="dataList"/]
<script type="text/javascript" src="${base}/static/scripts/product/seniorMajor.js"></script>
<script type="text/javascript">
    function beforeSubmit() {
        var form = $("#stdForm");
        addMfParams(form, "department.ids", "departmentIds");
        addMfParams(form, "major.ids", "majorIds");
        addMfParams(form, "inSchooleds", "inSchooledIds");
    }
</script>
[@b.foot/]
