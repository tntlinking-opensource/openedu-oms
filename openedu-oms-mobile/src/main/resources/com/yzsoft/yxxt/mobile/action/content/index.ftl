[#ftl]
[#import "/template/mobile.ftl" as m/]

[@m.body title="新闻资讯" back="back" cache="true"]
<div class="ui-listview">
    <div class="yx-xxtx-btnarea">
        [#list columns as v]
            <a href="${b.url("!search?t=1")}&cid=${v.id}" style="padding: 2em 0;" data-role="button" data-transition="slide">
            ${(v.name)!}
            </a>
        [/#list]
    </div>
</div>
[/@]