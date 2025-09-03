[#ftl]
[#import "/template/mobile.ftl" as m/]

[@m.body title=column.name back="back" cache="true"]
<ul class="yx-xxtx-info" data-role="listview">
    [#include "more.ftl"/]
</ul>
    [@more data=contents params="cid=${column.id}"/]
[/@]