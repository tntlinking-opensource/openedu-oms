[#ftl]
[@b.head/]
[#assign adSearchParams="department.id,major.id,adminClass.id"]
[#assign adSearchBooleanParams="inSchooled"]
[@b.form id="stdForm" name="stdForm" action="!search?student.inSchooled=1" title="ui.searchForm" target="studentList" theme="msearch"]
	[@b.field]
		[@b.textfields names="student.code;学号"/]
		[@b.textfields names="student.name;姓名"/]
		[@b.textfields names="student.grade;年级"/]
		[@b.textfields names="student.major.name;专业"/]
		[@b.textfields names="student.adminClass.name;班级"/]
	[/@]
	[@b.fieldSelect]
		[@b.select id="gradeId" name="gradeIds" label="年级" items=grades option="name,name"/]
		[@b.select id="majorId" name="majorIds" label="所属专业" items=majors empty=""/]
	[/@]
	<input type="hidden" name="adSearchParams" value="${(adSearchParams)!}">
	<input type="hidden" name="adSearchBooleanParams" value="${(adSearchBooleanParams)!}">
[/@]
[@b.div href="!search?student.inSchooled=1" id="studentList" class="dataList"/]
<script type="text/javascript" src="${base}/static/scripts/product/seniorMajor.js"></script>
<script type="text/javascript" src="${base}/static/scripts/product/seniorAdminClass.js"></script>
<script type="text/javascript">
	function beforeSubmit(){
		var form = $("#stdForm");
		addMfParams(form,"department.ids","departmentIds");
		addMfParams(form,"major.ids","majorIds");
		addMfParams(form,"adminClass.ids","adminClassIds");
		addMfParams(form,"inSchooleds","inSchooledIds");
	}
</script>
[@b.foot/]
