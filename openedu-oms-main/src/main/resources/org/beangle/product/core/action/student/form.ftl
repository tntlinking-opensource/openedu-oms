[#ftl]
[#import "/template/form/utils.ftl" as fu/]
[@b.head/]
[@b.toolbar title="学生管理" entityId=student.id!0]bar.addBack();[/@]
[@b.form action="!save" title="学生管理信息" theme="list"]
	[@b.classConfig configs=formConfig]
        [@b.field label="学号"][@fu.text]${student.code!}[/@][/@]
		[#--[@b.textfield label="学号" name="student.code" value="${student.code!}" style="width:350px;" required="true" maxlength="100" /]--]
		[@b.textfield label="姓名" name="student.name" value="${student.name!}" style="width:350px;" required="true" maxlength="100" /]
		[@b.textfield label="英文姓名" name="student.nameEn" value="${student.nameEn!}" style="width:350px;" maxlength="100" /]
		[@b.textfield label="曾用名" name="student.usedName" value="${student.usedName!}" style="width:350px;" maxlength="100" /]
		[@b.select label="性别" required="true" name="student.gender.id" empty="请选择..." value=(student.gender.id)!  style="width:350px;" items=genders option="id,name"/]
		[#if deptAdmin?? && deptAdmin]
        	[@b.field label="学历层次"][@fu.text]${(student.education.name)!}[/@][/@]
        [#else]
        	[@b.select label="学历层次" required="true" name="student.education.id" empty="请选择..." value=(student.education.id)!  style="width:350px;" items=educations option="id,name"/]
        [/#if]
		
		[#if deptAdmin?? && deptAdmin]
        	[@b.field label="校区"][@fu.text]${(student.campus.name)!}[/@][/@]
        	[@b.field label="学院"][@fu.text]${(student.department.name)!}[/@][/@]
        	[@b.field label="所属专业"][@fu.text]${(student.major.name)!}[/@][/@]
        	[@b.field label="所属专业方向"][@fu.text]${(student.direction.name)!}[/@][/@]
        [#else]
        	[@b.select label="校区" required="true" name="student.campus.id" text=(student.campus.id)!  value=(student.campus.id)! style ="width:350px" items=campuses option="id,name" /]
			[@b.select label="学院" iclass="e_department" name="student.department.id" text=(student.department.id)!  value=(student.department.id)! style ="width:350px" items=departments option="id,name" /]
			[@b.select label="所属专业" iclass="e_major" id="majorFormId" name="student.major.id" text=(student.major.id)! value=(student.major.id)! style ="width:350px" items=majors option="id,name" /]
			[@b.select label="所属专业方向"  name="student.direction.id" text=(student.direction.id)! value=(student.direction.id)! style ="width:350px" items=directions option="id,name" /]
        [/#if]
        
		[@b.textfield label="年级" name="student.grade" value="${student.grade!}" style="width:350px;" required="true" maxlength="100" /]
		[@b.textfield label="学制" name="student.duration" value="${student.duration!}" style="width:350px;" required="true" maxlength="100" check="match('number')" /]
		[@b.select label="班级" name="student.adminClass.id" text=(student.adminClass.id)! value=(student.adminClass.id)! style ="width:350px" items=adminClasss option="id,name" /]
		[@b.datepicker label="出生日期" name="student.birthday" value=(student.birthday)! style="width:350px;" format="date"/]
		[@b.select label="入学方式" name="student.enrollType.id" text=(student.enrollType.name)!  value=(student.enrollType.id)! style ="width:350px" items=enrollTypes option="id,name" /]
		[@b.select label="培养类型" name="student.trainType.id" text=(student.trainType.name)!  value=(student.trainType.id)! style ="width:350px" items=trainTypes option="id,name" /]
		[@b.datepicker label="入学日期" name="student.enrollmentDate" value=(student.enrollmentDate)! style="width:350px;" format="date"/]
		[@b.datepicker label="离校日期" name="student.leaveDate" value=(student.leaveDate)! style="width:350px;" format="date"/]
		[@b.textfield label="籍贯" name="student.nativePlace" value="${student.nativePlace!}" style="width:350px;" maxlength="100" /]
		[@b.textfield label="民族" name="student.nation" value="${student.nation!}" style="width:350px;" maxlength="100" /]
		[@b.textfield label="国家/地区" name="student.country" value="${student.country!}" style="width:350px;" maxlength="100" /]
		[@b.textfield label="证件类型" name="student.cardType" value="${student.cardType!}" style="width:350px;" maxlength="100" /]
		[@b.textfield label="证件号" name="student.cardcode" value="${student.cardcode!}" style="width:350px;" maxlength="100" disabled="disabled"/]
		[@b.textfield label="婚姻状况" name="student.marital" value="${student.marital!}" style="width:350px;" maxlength="100" /]
		[@b.textfield label="政治面貌" name="student.political" value="${student.political!}" style="width:350px;"  maxlength="100" /]
		[@b.textfield label="联系电话" name="student.phone" value="${student.phone!}" style="width:350px;" maxlength="100" /]
		[@b.textfield label="电子邮箱" name="student.email" value="${student.email!}" style="width:350px;"  maxlength="100" /]
		[@b.textfield label="家庭住址" name="student.homeAddress" value="${student.homeAddress!}" style="width:350px;"  maxlength="100" /]
		[@b.textfield label="家庭邮编" name="student.postcode" value="${student.postcode!}" style="width:350px;"  maxlength="100" /]
		[@b.textfield label="家庭电话" name="student.homePhone" value="${student.homePhone!}" style="width:350px;"  maxlength="100" /]
		[@b.textfield label="户口所在地" name="student.domicilePlace" value="${student.domicilePlace!}" style="width:350px;" maxlength="100" /]
		[@b.textfield label="毕业学校" name="student.graduateSchool" value="${student.graduateSchool!}" style="width:350px;"  maxlength="100" /]
		[@b.textfield label="学籍状态" name="student.status" value="${student.status!}" style="width:350px;" maxlength="100" /]
		[@b.radios label="是否有学籍"  name="student.registed" value=student.registed items="1:是,0:否"/]
		[@b.radios label="是否在校"  name="student.inSchooled" value=student.inSchooled items="1:是,0:否"/]
		[@b.textfield label="考生号" name="student.enrollNumber" value="${student.enrollNumber!}" style="width:350px;" maxlength="20" /]
		[@b.textfield label="生源地" name="student.enrollSource" value="${student.enrollSource!}" style="width:350px;" maxlength="20" /]
	[/@]
	[@b.formfoot]
		[@b.reset/]&nbsp;&nbsp;
		[@b.submit value="action.submit" /]
		[@b.redirectParams/]
		<input type="hidden" name="student.id" value="${student.id!}" />
		<input type="hidden" name="department.id" value="${(student.department.id)!}" />
		<input type="hidden" name="major.id" value="${(student.major.id)!}" />
		<input type="hidden" name="adminClass.id" value="${(student.adminClass.id)!}" />
	[/@]
[/@]
[@b.foot/]
<script src="${base}/static/yxxt/scripts/area.js"></script>
<script src="${base}/static/yxxt/scripts/major.js"></script>
<script>
    $(function () {
        $(".e_province").loadProvince().cascadeCity(".e_city").cascadeArea(".e_county");
        $(".e_department").cascadeMajor(".e_major").cascadeDirection(".e_direction");
    });
</script>