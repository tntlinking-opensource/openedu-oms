[#ftl]
[#import "/template/mobile.ftl" as m/]

[@m.body title="信息填写" back=b.url("/mobile/home?t=1") cache="true"]
<div class="ui-listview">
    <div class="yx-xxtx-btnarea">
        [#list switches as v]
            <a href="${b.url("!info")}&code=${v.code}" data-role="button" class="yx-xxtx-btnarea-a" data-transition="slide">
                [#if v.icon??]
                    <img src="${base}/common/download.action?uuid=${v.icon}">
                [/#if]
            ${v.name!}
            </a>
        [/#list]
    </div>
</div>
[/@]