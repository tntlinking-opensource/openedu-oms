[#ftl]
[#if b.isMobile()]
    [#include "index_mobile.ftl"/]
[#else]
    [@b.head/]
<style>
    body { background-color: #fff; }
</style>
<div class="container">
    <div class="page-header">
        <h1>学生注册</h1>
    </div>
    <div class="panel panel-default">
        <div class="panel-body">
            <form id="signupForm" action="register!save.action" class="form-horizontal" method="post">
                <div class="form-group">
                    <label class="col-sm-2 control-label">用户名</label>
                    <div class="col-sm-10">
                        <input name="user.name" class="form-control" placeholder="用户名" maxlength="20" required/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">姓名</label>
                    <div class="col-sm-10">
                        <input name="user.fullname" class="form-control" placeholder="姓名" maxlength="20" required/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">联系电话</label>
                    <div class="col-sm-10">
                        <input name="user.phone" class="form-control" placeholder="联系电话" maxlength="20" required/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">密码</label>
                    <div class="col-sm-10">
                        <input id="password" name="user.password" type="password" class="form-control" placeholder="密码" maxlength="20" required/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">重复密码</label>
                    <div class="col-sm-10">
                        <input name="confirm_password" type="password" class="form-control" placeholder="重复密码" maxlength="20" required/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-default">注册</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
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
    [@b.foot/]
[/#if]