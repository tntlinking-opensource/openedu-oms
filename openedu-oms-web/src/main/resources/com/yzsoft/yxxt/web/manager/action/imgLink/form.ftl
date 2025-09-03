[#ftl]
[@b.head/]
[@b.toolbar title="友情链接" entityId=imgLink.id!0]bar.addBack();[/@]
[@b.form action="!save" title="友情链接" theme="list"]
    [@b.textfield label="顺序号" name="imgLink.sort" value="${imgLink.sort!}" required="true" maxlength="3" check="match('unsigned')" iclass="v_integer"/]
    [@b.textfield label="名称" name="imgLink.name" value="${imgLink.name!}" required="true" maxlength="20"/]
    [@b.textfield label="链接" name="imgLink.url" value="${imgLink.url!}" maxlength="200" style="width:400px;" iclass="v_url"/]
    [@b.field label="图片"]
        [#if imgLink.img??]
        <div style="margin-bottom: 5px;">
        [#--<img width="120" src="${base}/common/download.action?uuid=${imgLink.img}"/>--]
            <img width="120" src="${base}/${imgLink.img}"/>
        </div>
        [/#if]
    <input type="file" name="newimg" class="uploadImg"/>
    [#--<input type="hidden" name="oldimg" value="${(imgLink.img)!}"/>--]
    <p>请上传大小为320×120或等比的图片</p>
    [/@]
    [@b.radios label="状态"  name="imgLink.enabled" value=imgLink.enabled items="1:启用,0:禁用"/]
    [@b.formfoot]
    <input type="hidden" name="imgLink.id" value="${imgLink.id!}"/>
        [@b.redirectParams/]
        [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
    [@c.webuploaderJsAndCss2/]
<script>
    $(function () {
        $("input.uploadImg").uploadImg({
            dir: "imgLink",
            thumb: {
                width: 261,
                height: 68,
                crop: true,
            },
            compress: {
                width: 1920,
//                height: 224,
//                crop: true,
            },
        });
    });
</script>
[/@]
[@b.foot/]