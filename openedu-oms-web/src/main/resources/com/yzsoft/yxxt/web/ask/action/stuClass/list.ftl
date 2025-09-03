[#ftl]
[#if msgs?? && msgs?size gt 0]
<ul class="yz-model">
    [#list msgs as msg]
        [#if msg.user.id == userId]
            <li>
                <input type="hidden" class="msg_id" value="${msg.id}"/>
                <div class="margin-top-10">
                    <div class="yx-model-a">
                        <p class="yx-model-a-right" style="text-align: right;">我&nbsp;（${msg.createTime}）</p>
                        <img class="yx-model-a-left"
                             src="${base}/static/metronic/assets/yz_yxwz_css/qa_icon_03.png">
                    </div>
                    <div class="yx-model-a margin-top-10">
                        <p class="yx-model-a-right bold"
                           style="margin-right: 50px; text-align: right;">${(msg.content)?html}</p>
                    </div>
                </div>
            </li>
        [#else]
            <li>
                <input type="hidden" class="msg_id" value="${msg.id}"/>
                <div class="margin-top-10">
                    <div class="yx-model-a">
                        <img class="yx-model-a-left"
                             src="${base}/static/metronic/assets/yz_yxwz_css/qa_icon_03.png">
                        <p class="yx-model-a-right">${msg.user.fullname}&nbsp;（${msg.createTime}）</p>
                    </div>
                    <div class="yx-model-a margin-top-10">
                        <p class="yx-model-a-right bold" style="margin-left: 40px;">${(msg.content)?html}</p>
                    </div>
                </div>
            </li>
        [/#if]
    [/#list]
</ul>
[/#if]