[#ftl]
[#include "/template/importForm.ftl"/]
[@importForm src="!importData" title="${(dormPlan.name)!}——班级住宿计划导入" templateFile="static/dorm/data/classAccomPlan.xls" ext="xls"]
	<input type="hidden" name="dormPlan.id" value="${(dormPlan.id)!}">
[/@]