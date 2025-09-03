[#ftl]
<div class="portlet light bordered">
	<div class="portlet-title">
		<div class="caption"><span class="caption-subject font-dark sbold uppercase">报到状态</span></div>
		
	</div>
	<div class="portlet-body">
		<div class="row">
			<div class="col-md-12">
				<style>
					.zt_img_2{height: 12px; margin: 0 5px;}
				</style>
				<table id="user" class="table">
					<tbody>
					[#function logEnabled item]
						[#list logs as log]
							[#if log.item.id == item.id && log.enabled]
								[#return true/]
							[/#if]
						[/#list]
						[#return false/]
					[/#function]
					[#list items as item]
					<tr>
						<td style="border:none;">${(item.name)!}</td>
						<td style="border:none;">
							[#if logEnabled(item)]
								<img src="${base}/static/yxxt/welcome/css/images/zt_g.png" class="zt_img_2">已办理
							[#else ]
								<img src="${base}/static/yxxt/welcome/css/images/zt_r.png" class="zt_img_2">未办理
							[/#if]
						</td>
					</tr>
					[/#list]
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>