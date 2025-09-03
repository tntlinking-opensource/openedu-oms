[#ftl]
[#include "columnMacro.ftl"/]
<style>
    .nav-tabs > li.active > a { cursor: pointer !important; }
</style>
<ul class="nav nav-tabs tabs-left" style="min-height: 600px;">
[#list leftColumns as c]
    <li [#if column?? && c.id == column.id]class="active"[/#if]>
        <a href="[@columnUrl c/]">${(c.name)!}</a>
    </li>
[/#list]
</ul>