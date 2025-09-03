[#ftl]
[#include "../comm/macro.ftl"/]
[@body type="index"]
[#--
<ul class="nav nav-pills">
    [#list plates as plate]
        <li>
            <a href="${b.url("!search")}&plateId=${plate.id}"
               data-toggle="tab">${plate.name!}</a>
        </li>
    [/#list]
    <li>
        <a href="${b.url("common")}" data-toggle="tab">常见问题解答</a>
    </li>
</ul>--]
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