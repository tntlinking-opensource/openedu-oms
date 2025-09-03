[#ftl]
[@b.head/]
[@b.toolbar title="学生信息"]bar.addBack();[/@]
[@b.form action="!search" theme="form"]
<style>
    .student_info { margin: -20px -15px; padding-bottom: 50px; }
    .student_info .caption { color: #3586c5; font-size: 16px; font-weight: bold; border-bottom: 2px solid #9acbf2; padding: 10px 0 11px; margin: 0 15px; }
</style>
<div class="student_info">
    [#include "../studentInfo/index_info.ftl"/]
</div>
[/@]
[@b.foot/]