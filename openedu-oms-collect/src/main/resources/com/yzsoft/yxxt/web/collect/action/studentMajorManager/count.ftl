[#ftl]
[@b.head/]
[@b.toolbar title="学生志愿统计" entity=student]bar.addBack();[/@]
<div style="padding: 0 15px; background: #fff;">
	<a href="${b.url("!print")}" class="btn btn-info" target="_blank">打印</a>
	<a href="${b.url("!exportExcel")}" class="btn btn-info" target="_blank">导出</a>
</div>
[#include "countBody.ftl"/]

<div id="modal" class="modal fade" tabindex="-1" role="dialog">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content" style="padding: 15px;">
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
[@b.foot/]