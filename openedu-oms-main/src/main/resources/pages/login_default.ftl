[#ftl]
<!DOCTYPE html>
<!--[if IE 8]>
<html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]>
<html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->

<head>
    <meta charset="utf-8"/>
    <title>Login</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1" name="viewport"/>
    <meta content="" name="description"/>
    <meta content="" name="author"/>
    <!-- BEGIN GLOBAL MANDATORY STYLES -->
    [#--<link href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700&subset=all" rel="stylesheet" type="text/css" />--]
    <link href="${base}/static/metronic/assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet"
          type="text/css"/>
    <link href="${base}/static/metronic/assets/global/plugins/simple-line-icons/simple-line-icons.min.css"
          rel="stylesheet" type="text/css"/>
    <link href="${base}/static/metronic/assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet"
          type="text/css"/>
    <link href="${base}/static/metronic/assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css"
          rel="stylesheet" type="text/css"/>
    <!-- END GLOBAL MANDATORY STYLES -->
    <!-- BEGIN PAGE LEVEL PLUGINS -->
    <link href="${base}/static/metronic/assets/global/plugins/select2/css/select2.min.css" rel="stylesheet"
          type="text/css"/>
    <link href="${base}/static/metronic/assets/global/plugins/select2/css/select2-bootstrap.min.css" rel="stylesheet"
          type="text/css"/>
    <!-- END PAGE LEVEL PLUGINS -->
    <!-- BEGIN THEME GLOBAL STYLES -->
    <link href="${base}/static/metronic/assets/global/css/components.min.css" rel="stylesheet" id="style_components"
          type="text/css"/>
    <link href="${base}/static/metronic/assets/global/css/plugins.min.css" rel="stylesheet" type="text/css"/>
    <!-- END THEME GLOBAL STYLES -->
    <!-- BEGIN PAGE LEVEL STYLES -->
    <link href="${base}/static/metronic/assets/pages/css/login-4.min.css" rel="stylesheet" type="text/css"/>
    <!-- END PAGE LEVEL STYLES -->
    <!-- BEGIN THEME LAYOUT STYLES -->
    <!-- END THEME LAYOUT STYLES -->
    <link rel="shortcut icon" href="favicon.ico"/>
    <script type="text/javascript">window.contextPath = "${base!}";
        var base = "${base!}";
        var tophref = location.href;</script>
</head>
<!-- END HEAD -->

<body class=" login">
<!-- BEGIN LOGO -->
<div class="logo">
    <img src="${base}/static/images/logo2.png" height="50"/>
</div>
<!-- END LOGO -->
<!-- BEGIN LOGIN -->
<div class="content">
    <!-- BEGIN LOGIN FORM -->
    <form name="loginForm" id="login_text" class="login-form" action="${base}/login.action" method="post" target="_top">
        <h3 class="form-title">欢迎登录 [#if (sc.title)??]${sc.title}[/#if]</h3>
        [#assign msg=""]
        [#list actionMessages as item]
            [#assign msg=msg+" "+item]
        [/#list]
        [#list actionErrors as item]
            [#assign msg=msg+" "+item]
        [/#list]
        <div class="alert alert-danger [#if msg==""]display-hide[/#if]">
            <button class="close" data-close="alert"></button>
            <span>[#if msg==""]请输入用户名和密码[#else]${msg!}[/#if]</span>
        </div>
        <div class="form-group">
            <!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
            <label class="control-label visible-ie8 visible-ie9">用户名</label>
            <div class="input-icon">
                <i class="fa fa-user"></i>
                <input class="form-control placeholder-no-fix" style="width:100%;" type="text" autocomplete="off"
                       placeholder="用户名" id="username" name="username"/>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label visible-ie8 visible-ie9">密码</label>
            <div class="input-icon">
                <i class="fa fa-lock"></i>
                <input class="form-control placeholder-no-fix" style="width:100%;" type="password" autocomplete="off"
                       placeholder="密码" id="password" name="password"/>
                <input name="encodedPassword" id="encodedPassword" type="hidden" value=""/>
            </div>
        </div>
        [#if needCaptcha?? && needCaptcha]
            <div class="form-group">
                <label class="control-label visible-ie8 visible-ie9">验证码</label>
                <div class="input-icon">
                    <i class="fa fa-lock"></i>
                    <input class="form-control placeholder-no-fix" style="width:50%;display:inline;" type="text"
                           autocomplete="off" placeholder="验证码" id="captcha" name="captcha"/>
                    <img src="${b.url('/security/captcha')}" title="验证码,点击更换一张" onclick="changeCaptcha(this)"
                         alt="验证码" width="80" height="34" style="vertical-align:top;"/>
                </div>
            </div>
        [/#if]
        <div class="form-actions">
            <label class="checkbox" style="padding-left: 20px;">
                <input type="checkbox" name="keepLogin" value="1"/> 记住我
            </label>
            <label style="padding-left: 20px;color: #e23838;">
                学生用户的账号为身份证号，密码为身份证后6位
            </label>
            <button type="submit" class="btn blue pull-right">
                登录 <i class="m-icon-swapright m-icon-white"></i>
            </button>
        </div>
        <div class="forget-password">
            [#--
                    <h4>忘记密码 ？</h4>
                    <p>
                        请点击 <a href="#">这里</a>
                        重置密码。
                    </p>
                    --]
        </div>
        <input type="hidden" name="OWASP_CSRFTOKEN" value="${b.csrfToken()!}">
        [#--成功后跳转地址--]
        <input type="hidden" name="backurl" value="${backurl!}"/>
    </form>
    <!-- END LOGIN FORM -->

</div>
<div style="text-align: center; font-size: 18px; color: #fff; margin-top: 50px;">
    Copyright © 2025湖北天天数链技术有限公司<br/>
    本系统软件源代码许可来源于
    <a href="https://open.tntlinking.com/communityTreaty" target="_blank"
       style="white-space: nowrap; color: #fff;">《天天开源软件（社区版）许可协议》</a>
</div>
<!-- END LOGIN -->
<!-- BEGIN COPYRIGHT -->
[#--<div class="copyright"> 2014 &copy; Metronic - Admin Dashboard Template.</div>--]
<!-- END COPYRIGHT -->
<!--[if lt IE 9]>
<script src="${base}/static/metronic/assets/global/plugins/respond.min.js"></script>
<script src="${base}/static/metronic/assets/global/plugins/excanvas.min.js"></script>
<![endif]-->
<!-- BEGIN CORE PLUGINS -->
<script src="${base}/static/metronic/assets/global/plugins/jquery.min.js" type="text/javascript"></script>
<script src="${base}/static/metronic/assets/global/plugins/bootstrap/js/bootstrap.min.js"
        type="text/javascript"></script>
<script src="${base}/static/metronic/assets/global/plugins/js.cookie.min.js" type="text/javascript"></script>
<script src="${base}/static/metronic/assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js"
        type="text/javascript"></script>
<script src="${base}/static/metronic/assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js"
        type="text/javascript"></script>
<script src="${base}/static/metronic/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="${base}/static/metronic/assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js"
        type="text/javascript"></script>
<!-- END CORE PLUGINS -->
<!-- BEGIN PAGE LEVEL PLUGINS -->
<script src="${base}/static/metronic/assets/global/plugins/select2/js/select2.full.min.js"
        type="text/javascript"></script>
<script src="${base}/static/metronic/assets/global/plugins/backstretch/jquery.backstretch.min.js"
        type="text/javascript"></script>
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN THEME GLOBAL SCRIPTS -->
<script src="${base}/static/metronic/assets/global/scripts/app.min.js" type="text/javascript"></script>
<!-- END THEME GLOBAL SCRIPTS -->
<!-- BEGIN PAGE LEVEL SCRIPTS -->
[#--<script src="${base}/static/metronic/assets/pages/scripts/login-4.js" type="text/javascript"></script>--]
<script src="${base}/static/metronic/assets/pages/scripts/login-soft.js" type="text/javascript"></script>
<script type="text/javascript" src="${base}/static/scripts/beangle/security.js"></script>
<!-- END PAGE LEVEL SCRIPTS -->
<!-- BEGIN THEME LAYOUT SCRIPTS -->
<!-- END THEME LAYOUT SCRIPTS -->
[@c.validateJsAndCss/]
<script>
    jQuery(document).ready(function () {
        App.init();
        var images = [
            base + "/static/metronic/assets/pages/img/login/1.jpg",
            base + "/static/metronic/assets/pages/img/login/2.jpg",
            base + "/static/metronic/assets/pages/img/login/3.jpg",
            base + "/static/metronic/assets/pages/img/login/4.jpg"
        ];
        Login.init(images);
    });

    var form = document.loginForm;

    function setf() {
        form['username'].focus();
    }

    setf();

    var modulus = "${modulus!}", exponent = "${exponent!}";
    var key = RSAUtils.getKeyPair(exponent, '', modulus);

    if ("${locale.language}".indexOf("en") != -1) {
        document.getElementById('local_en').checked = true;
    }

    function changeCaptcha(obj) {
        //获取当前的时间作为参数，无具体意义
        var timenow = new Date().getTime();
        //每次请求需要一个不同的参数，否则可能会返回同样的验证码
        //这和浏览器的缓存机制有关系，也可以把页面设置为不缓存，这样就不用这个参数了。
        obj.src = "${b.url('/security/captcha')}&t=" + timenow;
    }
</script>
</body>

</html>
