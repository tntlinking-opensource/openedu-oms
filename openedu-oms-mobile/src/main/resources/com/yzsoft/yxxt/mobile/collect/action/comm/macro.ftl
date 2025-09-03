[#ftl]

[#macro emptyMessage]
<div class="text-center" style="padding: 15px; text-align: center; background-color: #fff; min-height: 100vh;">
    <div class="alert alert-info" role="alert"
         style="background-color: #D7E8F5; padding: 15px;">暂无内容
    </div>
</div>
[/#macro]

[#function formBack isSave]
    [#if isSave]
        [#return b.url("!index")/]
    [#else ]
        [#return b.url("index?t=1")/]
    [/#if]
[/#function]