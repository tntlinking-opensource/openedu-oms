[#ftl]
[[#list datas as d][#if d_index gt 0],[/#if]{"id":${d.id}, "name":"${d.name!}", "code":"${d.code!}"}[/#list]]