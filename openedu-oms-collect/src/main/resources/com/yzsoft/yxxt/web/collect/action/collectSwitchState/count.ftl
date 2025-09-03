[#ftl]
[@b.head/]
<div style="background-color: #fff; padding: 15px;">
	<style>
		.clothes_form select { margin-top: 0; }
	</style>
	<form id="clothes_form" action="${b.url("!report")}" target="clothes_count_div" class="clothes_form form-inline">
		<input name="grade" value="${grade}" class="form-control" placeholder="年份"
		       style="width: 100px; text-align: center;"/>
		<select name="campusId" class="form-control c_campus"></select>
		<select name="educationId" class="form-control c_education"></select>
		<select name="departmentId" class="form-control c_department"></select>
		<button type="button" class="btn btn-primary clothes_submit searchButton">统计</button>
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
		});
	</script>
</div>
[@b.div id="clothes_count_div" href="!report?grade=${grade}"/]
[@b.foot/]