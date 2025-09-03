[#ftl]
[@b.head/]
[@b.toolbar title="友情链接" entityId=footLink.id!0]bar.addBack();[/@]
<link rel="stylesheet" type="text/css" href="${base}/static/scripts/webuploader/webuploader.css">
<script type="text/javascript" src="${base}/static/scripts/webuploader/webuploader.html5only.min.js"></script>
<script type="text/javascript" src="${base}/static/scripts/webuploader/webuploader.custom.js"></script>
[@b.form action="!save" title="友情链接" theme="list"]
    [@b.select label="链接组" name="footLink.group.id" value=(footLink.group.id)! items=groups
        required="true" option="id,name" empty="请选择..."/]
    [@b.textfield label="排序" name="footLink.sort" value="${footLink.sort!}" required="true" maxlength="3" check="match('unsigned')" iclass="v_integer"/]
    [@b.textfield label="名称" name="footLink.name" value="${footLink.name!}" required="true" maxlength="20"/]
    [@b.textfield label="链接" name="footLink.url" value="${footLink.url!}" maxlength="200" style="width:400px;" iclass="v_url"/]
    [@b.radios label="状态"  name="footLink.enabled" value=footLink.enabled items="1:启用,0:禁用"/]
    [@b.formfoot]
	<input type="hidden" name="footLink.id" value="${footLink.id!}"/>
        [@b.redirectParams/]
        [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
[/@]
[@b.foot/]