[#ftl]
[#include "../comm/macro.ftl"/]
[@body type="my"]
<div id="ask_list_div">
    <div class="yx-model-last text-center more_div">
        <p> 点击查看更多</p>
    </div>
</div>
<script>
    $(function () {
        $("#ask_list_div").mcmore({
            url: "${b.url("!search")}"
        });
    });
</script>
[/@]