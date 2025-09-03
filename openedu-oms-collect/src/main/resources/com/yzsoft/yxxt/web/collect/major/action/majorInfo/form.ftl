[#ftl]
[#import "/template/form/utils.ftl" as fu/]
[@b.head/]
[@b.toolbar title="专业信息" entityId=majorInfo.id!0]bar.addBack();[/@]
[@b.form action="!save" theme="form"]
    [@b.static label="所属${b.text('department')}"]${(majorInfo.major.department.name)!}[/@]
    [@b.static label="专业名称"]${(majorInfo.major.name)!}[/@]
    [@b.field label="专业Logo"]
        [#if majorInfo.logo??]
        <div style="margin-bottom: 5px;">
            <img width="197" src="${base}/common/download.action?uuid=${majorInfo.logo}"/>
        </div>
        [/#if]
    <input type="file" name="newimg" class="uploadImg"/>
    <input type="hidden" name="oldimg" value="${(majorInfo.logo)!}"/>
    [/@]
    [#--[@b.radios label="招生性别" name="majorInfo.gender.id" value=(majorInfo.gender.id)!0 items=genders/]--]
    [#--[@b.checkboxes label="招生类别" name="enrollTypeId" value=majorInfo.enrollTypes items=enrollTypes/]--]
    [@b.textfield label="排序" name="majorInfo.sort" value="${majorInfo.sort!}" class="v_integer" required="true" maxlength="100"/]
    [@b.field label="介绍" required="true"]
        [@fu.ckeditor id="editor1" name="majorInfo.content" value=majorInfo.content!""/]
    [/@]
    [@b.radios label="是否置顶" name="majorInfo.top" value=majorInfo.top items="1:是,0:否"/]
    [@b.radios label="是否启用" name="majorInfo.enabled" value=majorInfo.enabled items="1:启用,0:禁用"/]
    [@b.formfoot]
    <input type="hidden" name="major.id" value="${major.id!}"/>
    <input type="hidden" name="majorInfo.id" value="${majorInfo.id!}"/>
        [@b.redirectParams/]
        [@b.submit value="action.submit"/]
    [/@]
[/@]
[@c.webuploaderJsAndCss/]
<script type="text/javascript" src="${base}/static/yxxt/scripts/major.js?v=20170503"></script>
<script>
    $(function () {
        $("input.uploadImg").uploadImg({
            thumb: {
                width: 197,
                height: 154,
                crop: true,
            },
            compress: {
                width: 197,
                height: 154,
                crop: true,
            },
        });
    });
</script>
[@b.foot/]