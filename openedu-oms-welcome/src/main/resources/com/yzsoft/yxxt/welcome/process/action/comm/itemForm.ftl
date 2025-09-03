[#ftl]

[#macro form]
<div class="form-horizontal">
    [#list itemForms as itemForm]
        <div class="form-group">
            <label class="col-sm-3 control-label">${itemForm.title!}：</label>
            <div class="col-sm-9">
                <input type="hidden" name="itemForm" value="${itemForm.id}"/>
                [#if itemForm.type.name == "单行文本"]
                    <input name="itemForm${itemForm.id}" class="form-control" maxlength="100"
                           value="${value(itemForm)}">
                [#elseif itemForm.type.name == "多行文本"]
                    <textarea name="itemForm${itemForm.id}" class="form-control">${value(itemForm)}</textarea>
                [#elseif itemForm.type.name == "复选框"]
                    [@inputs itemForm "checkbox"/]
                [#elseif itemForm.type.name == "单选框"]
                    [@inputs itemForm "radio"/]
                [/#if]
            </div>
        </div>
    [/#list]
</div>
[/#macro]

[#macro inputs itemForm type]
    [#list itemForm.options?split("、") as option]
    <label class="${type}-inline">
        <input name="itemForm${itemForm.id}" type="${type}" value="${option}" [#if hasValue(itemForm, option)]checked[/#if]>${option}
    </label>
    [/#list]
[/#macro]

[#function value itemForm]
    [#list itemFormValues as v]
        [#if itemForm.id == v.form.id]
            [#return v.value/]
        [/#if]
    [/#list]
    [#return ""/]
[/#function]

[#function hasValue itemForm option]
    [#list itemFormValues as v]
        [#if itemForm.id == v.form.id]
            [#list v.value?split("、") as o]
                [#if o == option]
                    [#return true/]
                [/#if]
            [/#list]
        [/#if]
    [/#list]
    [#return false/]
[/#function]

[#macro info]
<div class="form-horizontal">
    [#list itemForms as itemForm]
        <div class="form-group">
            <label class="col-sm-3 control-label">${itemForm.title!}：</label>
            <div class="col-sm-9">
                <p class="form-control-static">${value(itemForm)}</p>
            </div>
        </div>
    [/#list]
</div>
[/#macro]
