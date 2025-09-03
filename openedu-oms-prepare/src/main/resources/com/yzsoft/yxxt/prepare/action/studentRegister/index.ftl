[#ftl]
[@b.head/]
[@b.form name="studentSearchForm"  action="!search" target="studentlist" title="ui.searchForm" theme="search"]
    [@b.select label="招生批次" name="studentEnroll.batch.id" value=batchs[0].id items=batchs/]
    [#--[@b.textfield label="年级" name="studentEnroll.student.grade" value=grade/]--]
    [#--[@b.textfield label="学号" name="studentEnroll.student.code"/]--]
    [@b.textfield label="姓名" name="studentEnroll.student.name"/]
    [@b.select label="是否注册" name="studentEnroll.student.registed" items={'1':'是','0':'否'}/]
    [#--[@b.select label="招生类别" name="studentEnroll.student.enrollType.id" items=enrollTypes/]--]
    [#--[@b.select label="省" name="studentEnroll.student.province.id" iclass="s_province"/]--]
    [#--[@b.select label="市" name="studentEnroll.student.city.id" iclass="s_city"/]--]
    [#--[@b.select label="区县" name="studentEnroll.student.county.id" iclass="s_county"/]--]
[/@]
[@b.div id="studentlist" href="!search?studentEnroll.batch.id=${batchs[0].id}" /]
<script src="${base}/static/yxxt/scripts/area.js"></script>
<script>
    $(function () {
       $(".s_province").loadProvince().cascadeCity(".s_city").cascadeArea(".s_county");
    });
</script>
[@b.foot/]