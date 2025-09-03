[#ftl]
[@b.head/]
[@b.toolbar title="服装类型" entityId=clothesType.id!0]bar.addBack();[/@]
[@b.form action="!save" title="服装类型" theme="form"]
    [#if superadmin]
        [@b.textfield label="代码" name="clothesType.code" value="${clothesType.code!}" required="true" maxlength="50"/]
    [#else]
        [@b.field label="代码"]${clothesType.code!}[/@]
    [/#if]
    [@b.textfield label="名称" name="clothesType.name" value="${clothesType.name!}" required="true" maxlength="50"/]
[#--[@b.textfield label="材质" name="clothesType.mate" value="${clothesType.mate!}" required="true" maxlength="100"/]--]
[#--[@b.field label="图片"]--]
[#--[#if clothesType.img??]--]
[#--<div style="margin-bottom: 5px;">--]
[#--<img width="200" src="${base}/${clothesType.img}"/>--]
[#--</div>--]
[#--[/#if]--]
[#--<input type="file" name="newimg" class="uploadImg"/>--]
[#--<input type="hidden" name="oldimg" value="${(clothesType.img)!}"/>--]
[#--[/@]--]
[#--[@b.textfield label="价格" name="clothesType.price" value="${clothesType.price!}" required="true" maxlength="50" class="v_price"/]--]
[#--[@b.textfield label="购买数量限制" name="clothesType.numLimit" value="${clothesType.numLimit!1}" required="true" maxlength="2" class="v_number"/]--]
    [@b.textfield label="尺码单位" name="clothesType.sizeName" value="${clothesType.sizeName!}" maxlength="50"/]
[#--[#if superadmin]--]
[#--[@b.textfield label="属性名" name="clothesType.propName" value="${clothesType.propName!}" required="true" maxlength="50"/]--]
[#--[/#if]--]
    [@b.radios label="common.status"  name="clothesType.enabled" value=clothesType.enabled items="1:启用,0:禁用"/]
    [@b.formfoot]
    <input type="hidden" name="clothesType.id" value="${clothesType.id!}"/>
        [@b.redirectParams/]
        [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
[#--    [@c.webuploaderJsAndCss/]
<script>
    $(function () {
        $("input.uploadImg").uploadImg({
            dir: "clothes",
            pathStyle: "none",
            compress: {
                width: 200,
            },
        });
    });
</script>--]
[/@]
[@b.foot/]