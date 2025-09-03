[#ftl]
<ul class="tj_box">
	[#list datas as v]
		[#if v_index lt 9]
			[@li v v_index + 1/]
		[/#if]
	[/#list]
</ul>
<ul class="tj_box">
	[#list datas as v]
		[#if v_index gte 9]
			[@li v v_index + 1/]
		[/#if]
	[/#list]
</ul>

[#macro li v index]
	<li>
		<div class="zt_ys_1">${index}</div>
		<i class="ys_color_${index}"></i>
		<div class="zt_ys">${v.name}</div>
		<span class="zt_ys_2">${v.percent?string("0.00%")}</span>
	</li>
[/#macro]
