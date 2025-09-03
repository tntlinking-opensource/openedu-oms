[#ftl]
<h4>服装尺码对照表（单位：cm）</h4>
<table class="table table-bordered">
	<tbody>
    [#assign labels = ["尺码", "身高", "胸围", "腰围", "臀围", "裤长"]/]
    [#assign names = ["name", "height", "bust", "waist", "hip", "outseam"]/]
    [#list labels as label]
	<tr align="center">
		<td>${label}</td>
        [#list clothesSizes as v]
			<td>${(v[names[label_index]])!}</td>
        [/#list]
	</tr>
    [/#list]
	</tbody>
</table>
<h4>鞋子尺码对照表（单位：cm）</h4>
<table class="table table-bordered">
	<tbody>
    [#assign labels = ["尺码", "脚长"]/]
    [#assign names = ["name", "footLength"]/]
    [#list labels as label]
	<tr align="center">
		<td>${label}</td>
        [#list shoesSizes as v]
			<td>${(v[names[label_index]])!}</td>
        [/#list]
	</tr>
    [/#list]
	</tbody>
</table>