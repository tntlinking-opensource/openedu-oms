[#ftl]
[#import "/template/comm.ftl" as c/]
[#import "/template/mobile.ftl" as m/]
[@m.body title="专业介绍" back=b.url("!majorList")]
<div class="yx-xxtx">
    <div class="yx-xxtx-jieshao">
        <h3 class="yx-xxtx-jieshao-title">${(majorInfo.major.name)!}</h3>
        <div>${(majorInfo.content)!}</div>
    </div>
    <div class="yx-jiange-1em"></div>
    <div class="yx-model-zx">
        [#if nextMajorInfo??]
            <a href="${b.url("!majorInfo")}&majorInfoId=${(nextMajorInfo.id)!}" data-role="button"
               class="yx-model-lightgreenbtn">下一个专业</a>
        [/#if]
[#--
        [#assign nums=["","一","二","三","四","五","六"]/]
        [#list 1..studentMajorNum as v]
            [#assign url]${b.url("!selectMajor")}&sort=${v}&majorInfoId=${majorInfo.id}[/#assign]
            [#assign detail = studentMajor.getDetail(v)/]
            [#if (detail.major)??]
                <a href="${url}" data-role="button" class="yx-model-lightbluebtn">
                ${(detail.major.name)!}
                    （[#if majorInfo.major.id == (detail.major.id)!0]已选[#else]替换[/#if]）
                </a>
            [#else]
                <a href="${url}" data-role="button" class="yx-model-lightorangebtn">
                    添加到第${nums[v]}志愿
                </a>
            [/#if]
        [/#list]--]
    </div>
</div>

[/@]