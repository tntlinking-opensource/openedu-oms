[#ftl]
[@b.head/]
[@b.form name="itemLogStatusSearchForm"  action="!search" target="itemLogStatuslist" title="ui.searchForm" theme="search"]
    [@b.select label="批次" name="itemLogStatus.batch.id" items=batchs value=(batchId)!0 class="batch_select cascade"/]
    [@b.select label="环节" name="itemLogStatus.item.id" items=items class="item_select"/]
    [#--[@b.textfield label="年级" name="itemLogStatus.student.grade"/]--]
    [@b.textfield label="学号" name="itemLogStatus.student.code"/]
    [@b.textfield label="姓名" name="itemLogStatus.student.name"/]
    [@b.select label="院系" name="itemLogStatus.student.major.department.id" iclass="s_department cascade"/]
    [@b.select label="专业" name="itemLogStatus.student.major.id" iclass="s_major"/]
    [@b.select label="班级" name="itemLogStatus.student.adminClass.id" iclass="s_adminClass"/]
    [#--[@b.textfield label="开始日期" name="startDate"/]--]
	[@b.datepicker label="开始日期" name="startDate" id="startDate" format="date"/]
	[@b.datepicker label="结束日期" name="endDate" id="endDate" format="date"/]
    [@b.select label="办理状态" name="status" items={'1':'已办理','0':'未办理'}/]
[/@]
[@b.div id="itemLogStatuslist" href="!search?itemLogStatus.batch.id=${batchId!}" /]
<script src="${base}/static/yxxt/scripts/major.js"></script>
<script>
	$(function () {
		$(".batch_select").cascadeSelect(".item_select", "${b.url("!findItem")}").trigger("change");
		$(".s_campus").loadCampus();
        $(".s_department").loadDepartment().cascadeMajor(".s_major").cascadeAdminClass(".s_adminClass");
	});
</script>
[@b.foot/]