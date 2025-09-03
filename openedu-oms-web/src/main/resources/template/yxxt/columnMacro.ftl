[#ftl]

[#macro columnUrl column]
    [@compress]
        [#if column.url??]
            [#if column.url?starts_with("/")]${base}[/#if]${column.url!}[#if column.url?contains("?")]&[#else]?[/#if]columnId=${column.id}
        [#else]
        ${base}/web/content-list.action?columnId=${column.id}
        [/#if]
    [/@compress]
[/#macro]