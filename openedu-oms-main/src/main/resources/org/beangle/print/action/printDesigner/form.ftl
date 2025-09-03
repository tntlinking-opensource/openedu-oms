[#ftl]
[@b.head/]
[@b.toolbar title="模版管理" entityId=printTemplate.id!0]bar.addBack();[/@]
[@b.form action="!save" title="模版管理" theme="list"]
	[@b.textfield label="模版名称" name="printTemplate.name" value="${printTemplate.name!}" required="true" maxlength="100" /]
	[@b.select label="模版类型" name="printTemplate.templateType.id" value=(printTemplate.templateType.id)!  required="true" items=templateTypes option="id,name" empty="请选择..."/]
	[@b.textfield label="模型名称" name="printTemplate.templateModel" value="${printTemplate.templateModel!}"  required="true" maxlength="100" /]
	[@b.textarea label="common.remark" cols="50" rows="8" name="printTemplate.remark" value="${printTemplate.remark!}" maxlength="200"/]
	[@b.field label="背景图片（<1M）"]
        <div style="margin-bottom: 5px; background-color: #043a64;">
            <img width="197" src="${base}/print/print-designer!downTemplateBackground.action?printTemplateId=${(printTemplate.id)!}&t=${time!}"/>
        </div>
    	<input type="file" name="iconPath" class="uploadImg"/>
    [/@]
	[@b.radios label="是否可用"  name="printTemplate.enabled" value=printTemplate.enabled items="1:是,0:否" required="true"/]	
	[@b.formfoot]
		[@b.reset/]&nbsp;&nbsp;
		[@b.submit value="action.submit" /]
		[@b.redirectParams/]
		<input type="hidden" name="printTemplate.id" value="${printTemplate.id!}" />
	[/@]
[/@]
[@c.webuploaderJsAndCss2/]
<script>
    $(function () {
        $("input.uploadImg").uploadImg({
            thumb: {
                width: 200,
            },
            compress: false,
        });
    });
</script>
[@b.foot/]