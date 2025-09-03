[#ftl]
[@b.head/]
<div style="background-color: #fff; padding: 15px;">
	<style>
		.clothes_form select { margin-top: 0; }
	</style>
	<form id="clothes_form" action="${b.url("!report")}" target="clothes_count_div" class="clothes_form form-inline">
		<input name="year" value="${year}" class="form-control" placeholder="年份"
		       style="width: 100px; text-align: center;"/>
		<select name="campusId" class="form-control c_campus"></select>
		<select name="educationId" class="form-control c_education"></select>
		<select name="departmentId" class="form-control c_department"></select>
		<button type="button" class="btn btn-primary clothes_submit searchButton">统计</button>
		<button type="button" data-href="${b.url("!print")}" class="btn btn-info id_open_btn">打印</button>
		<button type="button" data-href="${b.url("!exportExcel")}" class="btn btn-info id_open_btn">导出</button>
		<button type="button" class="btn btn-primary pull-right" onclick="$('.search_items .searchButton').click();">返回</button>
	</form>
	<script src="${base}/static/yxxt/scripts/major.js"></script>
	<script>
		$(function () {
			$(".c_campus").loadCampus();
			$(".c_education").loadEducation();
			$(".c_department").loadDepartment();
			$(".clothes_submit").click(function () {
				bg.form.submit('clothes_form');
			});
			$(".id_open_btn").click(function () {
				bg.form.submit('clothes_form', $(this).attr("data-href"), "_blank");
			});
		});
	</script>
</div>
[@b.div id="clothes_count_div" href="!report?year=${year}"/]
[@b.foot/]