[#ftl]
<ul class="yz-model">
[#list asks as ask]
    <li>
        <div class="margin-top-10">
            <div class="yx-model-a">
                <img class="yx-model-a-left"
                     src="${base}/static/metronic/assets/yz_yxwz_css/qa_icon_03.png">
                <p class="yx-model-a-right">${ask.content!}</p>
            </div>
            <div class="yx-model-a margin-top-10">
                <img class="yx-model-a-left"
                     src="${base}/static/metronic/assets/yz_yxwz_css/qa_icon_06.png">
                <p class="yx-model-a-right bold">${ask.replyContent!"暂未回复！"}</p>
            </div>
        </div>
    </li>
[/#list]
</ul>
[#if asks.hasNext()]
<div class="yx-model-last text-center more_div"><p>点击查看更多</p></div>
[#else]
<div class="yx-model-last text-center"><p>没有更多咨询信息了</p></div>
[/#if]