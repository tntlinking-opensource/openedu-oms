[#ftl]
[#include "/template/mobile/more.ftl"/]

[#list contents as content]
<li>
    <a href="${b.url("!info?t=1")}&id=${content.id}" data-transition="slide">
    ${content.title!}
        <span class="ui-li-count">${(content.publishTime?string("MM-dd"))!}</span>
    </a>
</li>
[/#list]

