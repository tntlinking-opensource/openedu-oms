[#ftl]
<ul class="live-box-2" id="live-box">
	[#list datas as v]
		<li style="margin-right: 2%;">
			<div class="option-row ">
				<div class="option-content">
					<div class="option-no">
						[#if v.name?length gte 6]
							${(v.name?substring(0, 6))!}
						[#else]
							${(v.name)!}
						[/#if]
					</div>
					<div class="progress-box">
						<div class="progress-value" data-department="${(v.department)!}" style="width:${((v.percent)?string("0%"))!};"></div>
					</div>
					<div class="option-stat">${(v.completeNum)!0} / ${((v.percent)?string("0%"))!}</div>
				</div>
			</div>
		</li>
	[/#list]
	<script>
		(function () {
			var colors = [
				['#658aa7', '#128ae7'],
				['#58bcbc', '#00aaaa'],
				['#d0a295', '#e96f4f'],
				['#fcce10', '#e87c25'],
				['#d6ca97', '#fcce10'],
				['#d7df8a', '#b5c334'],
				['#eb7676', '#f51b1b'],
			].reverse();
			var departments = [];
			[#list datas as v]
			[#if v.department??]
			if (departments.indexOf('${v.department}') < 0) {
				departments.push('${v.department}');
			}
			[/#if]
			[/#list]
			console.log(departments)
			$(".progress-value").each(function () {
				var department = $(this).data("department");
				if (!department) {
					return;
				}
				var color = colors[departments.indexOf(department)];
				$(this).css({
					background: 'linear-gradient(to right, ' + color[0] + ' 0%, ' + color[1] + ' 100%)'
				})
			});
		})();
	</script>
</ul>