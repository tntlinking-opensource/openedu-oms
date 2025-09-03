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
		<div class="zt_ys">[#if v.name?length gt 6]${v.name?substring(0, 6)}[#else]${v.name}[/#if]</div>
		<span class="zt_ys_2">${v.percent?string("0%")}</span>
	</li>
[/#macro]