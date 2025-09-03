[#ftl]
[@b.head/]
[@b.toolbar title="友情链接" entityId=welcomeLink.id!0]bar.addBack();[/@]
<link rel="stylesheet" type="text/css" href="${base}/static/scripts/webuploader/webuploader.css">
<script type="text/javascript" src="${base}/static/scripts/webuploader/webuploader.html5only.min.js"></script>
<script type="text/javascript" src="${base}/static/scripts/webuploader/webuploader.custom.js"></script>
[@b.form action="!save" title="友情链接" theme="list"]
    [@b.textfield label="顺序号" name="welcomeLink.sort" value="${welcomeLink.sort!}" required="true" maxlength="3" check="match('unsigned')" iclass="v_integer"/]
    [@b.textfield label="名称" name="welcomeLink.name" value="${welcomeLink.name!}" required="true" maxlength="20"/]
    [@b.textfield label="链接" name="welcomeLink.url" value="${welcomeLink.url!}" maxlength="200" style="width:400px;" iclass="v_url"/]
    [@b.radios label="状态"  name="welcomeLink.enabled" value=welcomeLink.enabled items="1:启用,0:禁用"/]
    [@b.formfoot]
	<input type="hidden" name="welcomeLink.id" value="${welcomeLink.id!}"/>
        [@b.redirectParams/]
        [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
[/@]
[@b.foot/]