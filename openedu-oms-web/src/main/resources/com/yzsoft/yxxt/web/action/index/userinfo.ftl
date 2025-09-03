[#ftl]
<div class="row">
    <style>
        .user_style { padding: 8px 0; width: 100%; }
        .user_style p.user_style_p { padding-left: 0; }
        .user_style span.user_style_span { padding-left: 0; }
    </style>
[#if student??]
    <div class="col-sm-4">
        <img src="${base}/common/download.action?default=/static/yxxt/welcome/css/images/my.jpg&uuid=${student.photo!}"
             style="width:100%;">
    </div>
[/#if]
    <div class="col-sm-8">
        <div class="user_style">
            <p class="user_style_p">欢迎您！</p>
            <span class="user_style_span">${user.fullname} [#if student??]同学[#elseif teacher??]老师[/#if]</span>
        </div>
    </div>
</div>
<table id="user" class="table" style="margin-bottom:0; float:left; margin-top:20px;">
    <tbody>
    <tr>
        <td style="border:none;">
            <a class="a_change_password">
                <img src="${base}/static/metronic/assets/yz_yxwz_css/user_01.png"
                     class="zt_img">修改密码</a>
        </td>
        <script src="${base}/static/metronic/assets/global/plugins/bootbox/bootbox.min.js"></script>
        <script>
            $(function () {
                $(".a_change_password").click(function () {
                    var url = base + "/security/password!edit.action?mode=2";
                    $.get(url, function (data) {
                        var modal = bootbox.dialog({
                            title: "密码修改",
                            message: data
                        });
                    });
                });
            });
        </script>
        <td style="border:none;"><a href="${base}/logout.action">
            <img src="${base}/static/metronic/assets/yz_yxwz_css/user_02.png"
                 class="zt_img">注销</a></td>
    </tr>

    [#if student??]
    <tr>
        <td style="border:none;">
            <a href="${base}/web/collect/student-info.action">
                <img src="${base}/static/metronic/assets/yz_yxwz_css/user_03.png"
                     class="zt_img"/>信息填写</a>
        </td>
        <td style="border:none;">
            <a href="${base}/web/info/student.action?columnId=6">
                <img src="${base}/static/metronic/assets/yz_yxwz_css/user_04.png"
                    class="zt_img">信息查看</a>
        </td>
    </tr>
    [/#if]
    [#if !student??]
    <tr>
        <td style="border:none;"><a href="${base}/home.action">
            <img src="${base}/static/metronic/assets/yz_yxwz_css/user_03.png"
                 class="zt_img">后台管理</a></td>
        <td style="border:none;"></td>
    </tr>
    [/#if]
    [#--	<tr>
            <td style="border:none;"><a href="#"><img
                    src="${base}/static/metronic/assets/yz_yxwz_css/user_03.png"
                    class="zt_img">修改资料</a></td>
            <td style="border:none;"><a href="#"><img
                    src="${base}/static/metronic/assets/yz_yxwz_css/user_04.png"
                    class="zt_img">我的咨询</a></td>
        </tr>
        <tr>
            <td style="border:none;"><a href="#"><img
                    src="${base}/static/metronic/assets/yz_yxwz_css/user_04.png"
                    class="zt_img">我的咨询</a></td>
            <td style="border:none;"><a href="#"><img
                    src="${base}/static/metronic/assets/yz_yxwz_css/user_04.png"
                    class="zt_img">我的咨询</a></td>
        </tr>--]
    </tbody>
</table>