[#ftl]
[@b.head/]
[@b.form name="studentSearchForm"  action="!search?orderBy=code" target="studentlist" title="ui.searchForm" theme="search"]
    [@b.select label="招生批次" name="studentEnroll.batch.id" value=(batchs[0].id)!0 items=batchs/]
    [@b.textfield label="学号" name="studentEnroll.code"/]
    [@b.textfield label="姓名" name="studentEnroll.student.name"/]
    [#--
    [@b.select label="招生类别" name="studentEnroll.student.enrollType.id" items=enrollTypes/]
    [@b.select label="生源性质" name="studentEnroll.student.enrollSourceType.id" items=enrollSourceTypes/]
    [@b.select label="学历层次" name="studentEnroll.student.education.id" items=educations/]
	[@b.textfield label="年级" name="student.grade" value=grade/]--]
[#--[@b.textfield label="学号" name="student.code"/]--]
	[@b.select label="学院" name="studentEnroll.student.department.id" iclass="i_department" items=departments/]
	[@b.select label="专业" name="studentEnroll.student.major.id" iclass="i_major" items=[]/]
	[@b.select label="班级" name="studentEnroll.student.adminClass.id" iclass="i_adminClass" items=[]/]
    [@b.select label="是否同意" name="studentEnroll.student.noticed" items={'1':'是','0':'否'}/]
[#--[@b.textfield label="毕业学校" name="student.schoolName"/]--]
[#--[@b.select label="是否通知" name="student.noticed" items={'1':'是','0':'否'}/]--]
[#--[@b.select label="是否录取" name="student.enrolled" items={'1':'是','0':'否'}/]--]
[#--[@b.select label="招生类别" name="student.enrollType.id" items=enrollTypes/]--]
[#--[@b.select label="省" name="student.enrollProvince.id" iclass="s_province"/]--]
[#--[@b.select label="市" name="student.enrollCity.id" iclass="s_city"/]--]
[#--[@b.select label="区县" name="student.enrollCounty.id" iclass="s_county"/]--]
[#--[@b.textfield label="父亲电话" name="student.fatherPhone"/]--]
[#--[@b.textfield label="母亲电话" name="student.motherPhone"/]--]
[/@]
[@b.div id="studentlist" href="!search?studentEnroll.batch.id=${(batchs[0].id)!}" /]
<script src="${base}/static/yxxt/scripts/area.js"></script>
<script src="${base}/static/yxxt/scripts/major.js"></script>
<script>
    $(function () {
        $(".s_province").loadProvince().cascadeCity(".s_city").cascadeArea(".s_county");
        $(".i_department").cascadeMajor(".i_major").cascadeAdminClass(".i_adminClass");
    });
</script>
[@b.foot/]