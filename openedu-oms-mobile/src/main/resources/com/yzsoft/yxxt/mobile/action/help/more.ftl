[#ftl]
[#include "/template/mobile/more.ftl"/]

[#list asks as ask]
<li class="yx-zxmodel-ul-li">
    <div class="yx-zxmodel-a">
        <div class="yx-zxmodel-a-zx">
            <img src="${base}/static/yxxt/images/qa_icon_03.png">
            <span class="yx-zxmodel-fonta">${(ask.content)!}</span>
        </div>
        <div class="yx-zxmodel-a-zx margin-top-em">
            <img src="${base}/static/yxxt/images/qa_icon_06.png">
            <span class="yx-zxmodel-fontb">${(ask.replyContent)!}</span>
        </div>
    </div>
</li>

[/#list]

