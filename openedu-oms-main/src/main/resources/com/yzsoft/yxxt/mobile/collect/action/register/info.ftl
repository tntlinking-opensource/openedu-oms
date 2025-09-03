[#ftl]
[#import "/template/mobile.ftl" as m/]
[#include "macro.ftl"/]
[@m.body title=type.name back=b.url("!index")]
<style>
    a.yx-xxtx-info-a .yx-xxtx-info-a-right {
        margin-right: 1em;
    }
</style>
    <div class="yx-xxtx">
    [#--<h3 class="yx-xxtx-title">${type.name!}</h3>--]
        <ul data-role="listview" class="yx-xxtx-info">
            [#list configs as config]
                [#if config.type.id == type.id]
                    [@propertyInfo config/]
                [/#if]
            [/#list]
        </ul>
    </div>

    <div class="yx-model-btnmodel">
        <a href="${b.url("!edit?id=" + type.id )}" data-role="button" class="yx-model-bluebtn">修改</a>
    </div>
[/@]