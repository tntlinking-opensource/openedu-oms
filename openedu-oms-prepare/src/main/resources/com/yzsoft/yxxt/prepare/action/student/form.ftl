[#ftl]
[#import "/template/form/utils.ftl" as fu/]
[@b.head/]
[@b.toolbar title="学生信息" entityId=student.id!0]bar.addBack();[/@]
[@b.form action="!save" theme="list"]
	[@b.tabs]
		[@b.tab label="基本信息" theme="list" hasTable=1]
			[@b.textfield label="年级" name="student.grade" value="${student.grade!}" required="true" maxlength="10"/]
			[@b.select label="院系" name="student.department.id" value=(student.department.id)! items=[] iclass="e_department" style="width:100%;"/]
			[@b.select label="专业" name="student.major.id"  value=(student.major.id)! style ="width:100%;" iclass="e_major" items=[] option="id,name" /]
			[@b.select label="班级" name="student.adminClass.id"  value=(student.adminClass.id)! style ="width:100%;" iclass="e_adminClass" items=[] option="id,name" /]
			[@b.textfield label="学号" name="student.code" value="${student.code!}" required="true" maxlength="20"/]
		[#--            [@b.field label="姓名"][@fu.text]${(student.name)!}[/@][/@]--]
		[#--            [@b.field label="性别"][@fu.text]${(student.gender.name)!}[/@][/@]--]
		[#--            [@b.field label="身份证号"][@fu.text]${(student.cardcode)!}[/@][/@]--]
		[#--            [@b.field label="招生类别"][@fu.text]${(student.enrollType.name)!}[/@][/@]--]
		[#--            [@b.field label="培养类型"][@fu.text]${(student.trainType.name)!}[/@][/@]--]
		[#--            [@b.field label="学历层次"][@fu.text]${(student.education.name)!}[/@][/@]--]
			[@b.textfield label="姓名" name="student.name" value="${student.name!}" required="true" maxlength="20"/]
			[@b.radios label="性别" name="student.gender.id" value=(student.gender.id)! items=genders/]
			[@b.textfield label="身份证号" name="student.cardcode" value="${student.cardcode!}" required="true" maxlength="20"/]
			[@b.select label="招生类别" name="student.enrollType.id" value=(student.enrollType.id)! items=enrollTypes style="width:100%;"/]
			[@b.select label="培养类型" name="student.trainType.id" value=(student.trainType.id)! items=trainTypes style="width:100%;"/]
			[@b.select label="学历层次" name="student.education.id" value=(student.education.id)! items=educations style="width:100%;"/]
			[@b.datepicker label="出生日期" name="student.birthday" value=student.birthday format="date"/]
			[@b.textfield label="毕业学校" name="student.graduateSchool" value="${student.graduateSchool!}" maxlength="30"/]
			[@b.select label="省" name="student.enrollProvince.id" value=(student.enrollProvince.id)! items=provinces iclass="e_province" style="width:100%;"/]
			[@b.select label="市" name="student.enrollCity.id" value=(student.enrollCity.id)! items=citys iclass="e_city" style="width:100%;"/]
			[@b.select label="县" name="student.enrollCounty.id" value=(student.enrollCounty.id)! items=countys iclass="e_county" style="width:100%;"/]
			[@b.textarea label="备注" name="student.remark" value=(student.remark)! cols="50" rows="3"  maxlength="300"/]
		[/@]
		[@b.tab label="联系方式" theme="list" hasTable=1]
			[@b.textfield label="学生电话" name="student.phone" value="${student.phone!}" maxlength="20" iclass="v_telephone"/]
			[@b.textfield label="母亲电话" name="contact.motherPhone" value="${contact.motherPhone!}" maxlength="20" iclass="v_telephone"/]
			[@b.textfield label="父亲电话" name="contact.fatherPhone" value="${contact.fatherPhone!}" maxlength="20" iclass="v_telephone"/]
		[/@]
		[@b.tab label="学习成绩" theme="list" hasTable=1]
			[@b.textfield label="语文" name="score.chinese" value="${(score.chinese)!}" maxlength="10" iclass="v_score_10"/]
			[@b.textfield label="数学" name="score.math" value="${(score.math)!}" maxlength="10" iclass="v_score_10"/]
			[@b.textfield label="英语" name="score.english" value="${(score.english)!}" maxlength="10" iclass="v_score_10"/]
			[@b.textfield label="物理" name="score.physics" value="${(score.physics)!}" maxlength="10" iclass="v_score_10"/]
			[@b.textfield label="化学" name="score.chemistry" value="${(score.chemistry)!}" maxlength="10" iclass="v_score_10"/]
			[@b.textfield label="生物" name="score.biology" value="${(score.biology)!}" maxlength="10" iclass="v_score_10"/]
			[@b.textfield label="政治" name="score.politics" value="${(score.politics)!}" maxlength="10" iclass="v_score_10"/]
			[@b.textfield label="地理" name="score.geography" value="${(score.geography)!}" maxlength="10" iclass="v_score_10"/]
			[@b.textfield label="历史" name="score.history" value="${(score.history)!}" maxlength="10" iclass="v_score_10"/]
			[@b.textfield label="体育" name="score.sports" value="${(score.sports)!}" maxlength="10" iclass="v_score_10"/]
			[@b.textfield label="艺术" name="score.art" value="${(score.art)!}" maxlength="10" iclass="v_score_10"/]
			[@b.textfield label="加分" name="score.plus" value="${(score.plus)!}" maxlength="10" iclass="v_score_10"/]
		[/@]
	[/@]
	[@b.formfoot]
		<input type="hidden" name="studentEnroll.id" value="${studentEnroll.id!}"/>
		<input type="hidden" name="student.id" value="${student.id!}"/>
		<input type="hidden" name="score.id" value="${(score.id)!}"/>
		<input type="hidden" name="contact.id" value="${(contact.id)!}"/>
		[@b.redirectParams/]
		[@b.submit value="action.submit"/]
	[/@]
[/@]
<script src="${base}/static/yxxt/scripts/district.js"></script>
<script src="${base}/static/yxxt/scripts/major.js"></script>
<script>
	$(function () {
		jQuery.validator.addClassRules("v_duration", {
			number: true,
			min: 0.5,
			max: 8
		});
		$(".e_province").loadProvince().cascadeCity(".e_city").cascadeCounty(".e_county");
		$(".e_department").loadDepartment().cascadeMajor(".e_major").data("value", "${(student.department.id)!}");
		$(".e_major").cascadeAdminClass(".e_adminClass").data("value", "${(student.major.id)!}");
		$(".e_adminClass").data("value", "${(student.adminClass.id)!}");
	});
</script>
[@b.foot/]