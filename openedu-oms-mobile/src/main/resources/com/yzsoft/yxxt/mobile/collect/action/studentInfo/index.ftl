[#ftl]
[#import "/template/mobile.ftl" as m/]
[#include "macro.ftl"/]
[@m.body title=switch.name back=b.url("index?t=1")]
<div class="ui-listview">
    <div class="yx-xxtx-btnarea">
        [#list types as v]
        	[#if v.name != '未命名']
	            [#if hasConfig(v)]
	            <a href="${b.url("!info")}&id=${v.id}" data-role="button" class="yx-xxtx-btnarea-a" data-transition="slide">
	                ${v.name!}
	            </a>
	            [/#if]
            [/#if]
        [/#list]
        <a href="${b.url("!family")}" data-role="button" class="yx-xxtx-btnarea-a" data-transition="slide">
            家庭成员
        </a>
        [#if educationEnabled]
        <a href="${b.url("!education")}" data-role="button" class="yx-xxtx-btnarea-a" data-transition="slide">
            教育经历
        </a>
        [/#if]
    </div>
</div>
[/@]