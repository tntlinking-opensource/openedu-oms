[#ftl]
[@b.head/]
[@b.toolbar title="专业"]bar.addBack();[/@]
[@b.form action="!save" title="专业信息" theme="info"]
    [@b.static label="所属${b.text('department')}"]${(majorInfo.major.department.name)!}[/@]
    [@b.static label="专业名称"]${(majorInfo.major.name)!}[/@]
    [#if majorInfo.logo??]
        [@b.field label="专业Logo"]
        <div style="margin-bottom: 5px;">
            <img width="197" src="${base}/common/download.action?uuid=${majorInfo.logo}"/>
        </div>
        [/@]
    [/#if]
    [@b.static label="排序"]${(majorInfo.sort)!}[/@]
    [@b.static label="介绍"]${(majorInfo.content)!}[/@]
    [@b.static label="是否置顶"]${(majorInfo.top?string("是", "否"))!}[/@]
    [@b.static label="是否启用"]${(majorInfo.enabled?string("是", "否"))!}[/@]
[/@]
[@b.foot/]