[#ftl]
[#import "/template/comm.ftl" as c/]
[#import "/template/mobile.ftl" as m/]
[@m.body title="专业列表" back=b.url("!edit")]
<div class="yx-xxtx">
    <div class="yx-model-zx">
        [#list majorInfos as v]
            <a href="${b.url("!majorInfo")}&majorInfoId=${v.id}" data-role="button" class="yx-model-orangebtn"
               data-transition="slide">
            ${(v.major.name)!}
            </a>
        [/#list]
    </div>
</div>
[/@]