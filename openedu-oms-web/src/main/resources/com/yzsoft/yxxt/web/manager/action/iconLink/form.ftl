[#ftl]
[@b.head/]
[@b.toolbar title="友情链接" entityId=iconLink.id!0]bar.addBack();[/@]
<link rel="stylesheet" type="text/css" href="${base}/static/scripts/webuploader/webuploader.css">
<script type="text/javascript" src="${base}/static/scripts/webuploader/webuploader.html5only.min.js"></script>
<script type="text/javascript" src="${base}/static/scripts/webuploader/webuploader.custom.js"></script>
[@b.form action="!save" title="友情链接" theme="list"]
    [@b.textfield label="顺序号" name="iconLink.sort" value="${iconLink.sort!}" required="true" maxlength="3" check="match('unsigned')" iclass="v_integer"/]
    [@b.textfield label="名称" name="iconLink.name" value="${iconLink.name!}" required="true" maxlength="20"/]
    [@b.textfield label="链接" name="iconLink.url" value="${iconLink.url!}" maxlength="200" style="width:400px;" iclass="v_url"/]
    [@b.field label="图标"]
        [#if iconLink.img??]
		<div style="margin-bottom: 5px;">
            [#--<img width="50" src="${base}/common/download.action?uuid=${iconLink.img}"/>--]
            <img width="50" src="${base}/${iconLink.img}"/>
		</div>
        [/#if]
	<input type="file" name="file" class="uploadImg"/>
	[#--<input type="hidden" name="oldimg" value="${(iconLink.img)!}"/>--]
	<p style="margin: 5px 0;">请上传大小为50×50的PNG格式图标</p>
    [/@]
    [@b.radios label="状态"  name="iconLink.enabled" value=iconLink.enabled items="1:启用,0:禁用"/]
    [@b.formfoot]
	<input type="hidden" name="iconLink.id" value="${iconLink.id!}"/>
        [@b.redirectParams/]
        [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
[/@]
[@b.foot/]