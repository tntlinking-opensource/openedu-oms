[#ftl]
[#import "/template/mobile.ftl" as m/]

[@m.body title=column.name back="back" cache="true"]
<ul data-role="listview">
    [#include "more.ftl"/]
</ul>
    [@more data=contents/]
[/@]