[#ftl]
[#import "/template/mobile.ftl" as m/]
[@m.body]
<link rel="stylesheet" type="text/css" href="${base}/static/mobile/logininnew.css">
<div class="login_mian">
    <form id="cform" action="${base}/login.action" method="post" onsubmit="return checkLogin(this);">
        <div class="logo_box">
            <img class="logo_pic" src="${base}/common/system-icon-download.action?code=WEIXIN_LOGIN"
                 style="width: 60%"/>
        </div>
        [#if actionErrors?size gt 0]
            <div class="warningBox">
                <img src="${base}/static/tssp/mobile/images/warn_icon_07.png"/>
                <span>${actionErrors[0]}</span>
            </div>
        [/#if]
        <div class="login_box" style="margin: 0 1em; width: auto;">
            <div class="countinputbox">
                <input name="username" class="user_input" type="text" value="${Parameters["username"]!}"
                       placeholder="用户名"/>
            </div>
            <div class="countinputbox">
                <input name="password" id="password" class="user_input password_input" type="password" value=""
                       placeholder="密码"/>
                <input name="encodedPassword" class="encodedPassword" type="hidden"/>
            </div>
            [#if needCaptcha]
                <div class="countinputbox codeBox">
                    <input name="captcha" class="user_input yzcode_input" type="text" placeholder="请输入验证码"/>
                    <img class="codepic" src="${b.url('/security/captcha')}?t=${.now?long}"
                         onclick="changeCaptcha(this)"/>
                </div>
            [/#if]
        </div>
        <div>
        	<label style="padding-left: 20px;color: #e23838;margin-top: 2em;">
            	学生用户的账号为身份证号，密码为身份证后6位
            </label>
        </div>
        <div class="loginbtn_box" style="width: auto; margin: 2em 1em;">
            <button class="login_btn ui-btn ui-shadow ui-corner-all">
                登 录
            </button>
        </div>
        <input type="hidden" name="backurl" value="${backurl!}"/>
    </form>
    <script>
        $(".codepic").css("height", $(".yzcodeBox .ui-input-text").outerHeight());
    </script>
    <div class="otherway" style="display: none;">
    [#--<a class="otherway_a" href="#">忘记密码</a>--]
        <a class="otherway_b" href="${base}/comm/signup.action" data-transition="slide">新用户</a>
    </div>

</div>

<script type="text/javascript" src="${base}/static/scripts/beangle/security.js"></script>
<script type="text/javascript">
    if (location.href.indexOf("login.action") < 0) {
        location.href = "${base}/login.action";
    }
    function checkLogin(form) {
        if (!form['username'].value) {
            alert("用户名称不能为空");
            form['username'].focus();
            return false;
        }
        if (!form['password'].value) {
            alert("密码不能为空");
            form['password'].focus();
            return false;
        }
        //[#if needCaptcha]
            if (!form['captcha'].value) {
                alert("验证码不能为空");
                form['captcha'].focus();
                return false;
            }
            //[/#if]
        var modulus = "${modulus!}", exponent = "${exponent!}";
        var key = RSAUtils.getKeyPair(exponent, '', modulus);
        var pwd = RSAUtils.encryptedString(key, $(".password_input", form).val());
        $(".encodedPassword", form).val(pwd);
        $(".password_input", form).attr("disabled", "disabled");
        return true;
    }
    function changeCaptcha(obj) {
        obj.src = "${b.url('/security/captcha')}?d=" + new Date().getTime();
    }
</script>
[/@]
