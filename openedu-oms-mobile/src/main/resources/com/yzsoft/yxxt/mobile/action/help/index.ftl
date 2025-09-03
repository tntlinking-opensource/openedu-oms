[#ftl]
[#import "/template/mobile.ftl" as m/]

[@m.body title="常见问题" back="back"]
<div class="yx-zxmodel01">
    <ul data-role="listview">
        [#include "more.ftl"/]
    </ul>
    [@more data=asks/]
</div>
[/@]