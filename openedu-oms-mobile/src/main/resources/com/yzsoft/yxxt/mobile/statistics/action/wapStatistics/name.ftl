[#ftl]
[#include "macro.ftl"/]
<div class="ui-grid-a qddata">
	<div class="ui-block-a qddata-cell" style="border-right:1px solid #f0f0f0;">
		<p class="qddata-cell-title">男生统计</p>
		<p class="qddata-cell-data fontblue">
			[#assign num = getData("男")/]
			[#if num gt 0 && hasFindStudent!false]
				<a href="${b.url("!findStudent")}&gender=男&name=${name!}&department=${department!}&major=${major!}&birthday=${birthday!}"
				   style="text-decoration: underline;">${num}</a>
			[#else]${num}[/#if]
		</p>
	</div>
	<div class="ui-block-b qddata-cell">
		<p class="qddata-cell-title">女生统计</p>
		<p class="qddata-cell-data fontred">
			[#assign num = getData("女")/]
			[#if num gt 0  && hasFindStudent!false]
				<a href="${b.url("!findStudent")}&gender=女&name=${name!}&department=${department!}&major=${major!}&birthday=${birthday!}"
				   style="text-decoration: underline; color: #fb3232;">${num}</a>
			[#else]${num}[/#if]
		</p>
	</div>
</div>