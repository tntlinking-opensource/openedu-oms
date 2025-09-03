[#ftl]
[#import "/template/mobile.ftl" as m/]

[@m.body title="个人信息" back=b.url("/mobile/home?t=1") cache="true"]

<div class="ui-listview">
    <div class="yx-xxtx-btnarea">
        <a href="${b.url("student")}" data-role="button" class="yx-xxtx-btnarea-a" data-transition="slide">
            <img src="${base}/static/yxxt/mobile/css/images/xxtx_icon_18.png">新生信息</a>
[#--        <a href="${b.url("dorm")}" data-role="button" class="yx-xxtx-btnarea-a" data-transition="slide">
            <img src="${base}/static/yxxt/mobile/css/images/xxtx_icon_20.png">宿舍入住</a>--]
        <a href="${b.url("fee")}" data-role="button" class="yx-xxtx-btnarea-a" data-transition="slide">
            <img src="${base}/static/yxxt/mobile/css/images/xxtx_icon_22.png">费用信息</a>
    </div>
</div>
[/@]