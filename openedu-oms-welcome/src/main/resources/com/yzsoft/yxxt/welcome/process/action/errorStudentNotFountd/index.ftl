[#ftl]
<div class="panel panel-danger">
	<div class="panel-heading">出错了</div>
	<div class="panel-body">
	[#if code?? && code != ""]
		证件号“${code}”不存在
	[#else ]
		请输入证件号
	[/#if]
	</div>
</div>