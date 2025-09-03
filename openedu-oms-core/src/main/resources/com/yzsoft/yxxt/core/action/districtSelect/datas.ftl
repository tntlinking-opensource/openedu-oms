[#ftl]
[[#list datas as v][#if v_index gt 0],[/#if]{"name":"${v.name?js_string}", "id":"${v.id}", "code":"${(v.code)!}"}[/#list]]