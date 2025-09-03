[#ftl]
[#if rank??]
<div class="ui-grid-a qddata">
	<div class="ui-block-a qddata-cell" style="border-right:1px solid #f0f0f0;">
		<p class="qddata-cell-title">我的排名</p>
		<p class="qddata-cell-data fontblue">${rank!"-"}</p>
	</div>
	<div class="ui-block-b qddata-cell">
		<p class="qddata-cell-title">总人数</p>
		<p class="qddata-cell-data fontred">${studentNum}</p>
	</div>
</div>
[/#if]