[#ftl]
[#include "/template/mobile/more.ftl"/]

[#list contents as content]
<li>
    <a href="notice.action?method=info&id=${content.id}" data-transition="slide" data-role="none">
    ${content.title!}
        <span class="ui-li-count">${(content.publishTime?string("MM-dd"))!}</span>
    </a>
</li>
[/#list]

