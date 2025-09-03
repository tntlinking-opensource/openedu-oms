[#ftl]
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>学生注册</title>
    <!-- 引入 WeUI -->
    <link rel="stylesheet" href="${base}/static/weui/weui.min.css"/>
</head>
<body>
<form id="signupForm" action="register!save.action" method="post">
    <div class="errors" style="color: red;"></div>
    <div class="weui-cells weui-cells_form">
        <style>
            .weui-cell__ft { display: none; }
            .weui-cell_warn .weui-cell__ft { display: block; }
        </style>
    [#macro  cell title name type="text"]
        <div class="weui-cell">
            <div class="weui-cell__hd"><label class="weui-label">${title}</label></div>
            <div class="weui-cell__bd">
                <input name="${name}" type="${type}" class="weui-input" placeholder="${title}" required maxlength="30"/>
            </div>
            <div class="weui-cell__ft">
                <i class="weui-icon-warn"></i>
            </div>
        </div>
    [/#macro]
    [@cell title="用户名" name="user.name"/]
    [@cell title="姓名" name="user.fullname"/]
    [@cell title="联系电话" name="user.phone"/]
    [@cell title="登录密码" name="user.password" type="password"/]
    [@cell title="重复登录密码" name="password" type="password"/]
    </div>
    <div class="weui-btn-area">
        <button class="weui-btn weui-btn_primary">提交</button>
    </div>
</form>
<script src="${base}/static/scripts/comm/jquery-latest.min.js"></script>
<script src="${base}/static/scripts/comm/jquery.form.js"></script>
[@c.validateJsAndCss/]
<script>
    $("#signupForm").validate({
        rules: {
            "user.name": {
                minlength: 6
            },
            "user.fullname": {
                minlength: 2
            },
            "user.password": {
                minlength: 6
            },
            "confirm_password": {
                equalTo: "#password"
            },
        },
        highlight: function (element, errorClass) {
            $(element).closest(".weui-cell").addClass("weui-cell_warn");
        },
        unhighlight: function (element, errorClass) {
            $(element).closest(".weui-cell").removeClass("weui-cell_warn");
        },
        errorPlacement: function(error, element) {
        }
    });
    $('#signupForm').ajaxForm({
        dataType: "json",
        success: function (data) {
            if (data.errormsg) {
                alert(data.errormsg);
            } else {
                location.href = "${b.url("!success")}";
            }
        }
    });
</script>
</body>
</html>