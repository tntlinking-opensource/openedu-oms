[#ftl]
[@b.head/]
[@b.form name="studentLeaveSearchForm"  action="!search" target="studentLeavelist" title="ui.searchForm" theme="search"]
    [@b.textfield label="年级" name="studentLeave.student.grade" value=grade/]
    [@b.textfield label="学号" name="studentLeave.student.code"/]
    [@b.textfield label="姓名" name="studentLeave.student.name"/]
    [@b.select label="性别" name="studentLeave.student.gender.id" items=genders option="id,name"/]
    [@b.select label="院系" name="studentLeave.student.department.id" iclass="s_department"/]
    [@b.select label="专业" name="studentLeave.student.major.id" iclass="s_major"/]
    [@b.select label="是否请假" name="isLeave" items={'1':'是','0':'否'}/]
    [@b.select label="是否销假" name="studentLeave.leave.state" items={'未销假':'未销假','已销假':'已销假'}/]
[/@]
[@b.div id="studentLeavelist" href="!search" /]
<script src="${base}/static/yxxt/scripts/major.js"></script>
<script>
    $(function () {
        $(".s_department").loadDepartment().cascadeMajor(".s_major").cascadeDirection(".s_direction");
    });
</script>
[@b.foot/]