[#ftl]
<style>
    .login_div .form-group { margin: 0 0 10px 0; }
    .login-form .btn { margin-top: 0px; }
</style>
<div class="login_div">
    <form class="login-form" action="${b.url("!login")}" method="post">
    [#if errormsg??]
        <div class="alert alert-danger" style="padding: 5px; margin-bottom: 10px;">
            <span>${errormsg!}</span>
        </div>
    [/#if]
        <div class="form-group">
            <!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
            <label class="control-label visible-ie8 visible-ie9">用户名</label>
            <div class="input-icon">
                <i class="fa fa-user"></i>
                <input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="用户名"
                       name="username" value="${username!}"/></div>
        </div>
        <div class="form-group">
            <label class="control-label visible-ie8 visible-ie9">密码</label>
            <div class="input-icon">
                <i class="fa fa-lock"></i>
                <input class="form-control placeholder-no-fix" type="password" autocomplete="off" placeholder="密码"
                       name="password"/>
            </div>
        </div>
    [#if needCaptcha??]
        <div class="form-group row">
            <div class="col-xs-6" style="padding-right: 5px;">
                <input class="form-control" type="text" placeholder="验证码" name="captcha"/>
            </div>
            <div class="col-xs-6" style="padding-left: 5px;">
                <img src="${b.url('/security/captcha')}" class="captcha" style="height: 34px;">
            </div>
        </div>
    [/#if]

        <div class="form-group">
            <button type="submit" class="btn blue btn-block"> 登录</button>
        </div>

        <script>
            $(function () {
                $(".captcha").click(function () {
                    var t = new Date().getTime();
                    this.src = "${b.url('/security/captcha')}&t=" + t;
                });
            });
        </script>
    </form>
</div>