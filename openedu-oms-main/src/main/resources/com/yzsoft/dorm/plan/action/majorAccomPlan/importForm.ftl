[#ftl]
[#include "/template/importForm.ftl"/]
[@importForm src="!importData" title="${(dormPlan.name)!}——专业住宿计划导入" templateFile="static/dorm/data/majorAccomPlan.xls" ext="xls"]
	<input type="hidden" name="dormPlan.id" value="${(dormPlan.id)!}">
[/@]