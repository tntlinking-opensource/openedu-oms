[#ftl]
[#include "macro.ftl"/]
<style>
	.name5_div {display: flex; flex-wrap: wrap;}
	.name5_div > div {width: 33.33%; padding: 5px 0;}
</style>
<div class="ui-grid-a qddata_2 name5_div">
	[#list datas as v]
		<div>${v}</div>
	[/#list]
</div>