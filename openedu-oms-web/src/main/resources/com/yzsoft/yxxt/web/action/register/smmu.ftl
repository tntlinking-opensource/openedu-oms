[#ftl]
[#import "/org/beangle/smmu/tssp/comm/main.ftl" as m/]
[#include "/org/beangle/tssp/mobile/view/action/comm/main.ftl"]
[@apply_body formid="loginForm" title="用户注册" action=b.url("!save") backName="登录" back="${base}/login.action"]
<style>
    .ui-listview > .ui-li-static.suser_code_li { display: none; }
</style>
<div style="padding: 1em; color:red;">
    注意：注册成功后用户名将会和微信号绑定，请不要使用他人的微信进行注册。
</div>
    [@listview height=0]
        [@li_field label="人员类别"]
        <select name="suser.type.id" class="suser_type_select" required>
            <option value="">请选择人员类别</option>
            [#list types as type]
                <option value="${type.id}">${type.name}</option>
            [/#list]
        </select>
        [/@]
        [@li_input label="用户名" name="user.name"  maxlength=20 /]
        [@li_input label="学号" name="suser.code"  maxlength=20 liclass="suser_code_li"/]
        [@li_input label="密码" name="user.password" id="password1"  maxlength=20 type="password"/]
        [@li_input label="重复密码" name="password2" id="password2"  maxlength=20 type="password"/]
    [/@]
    [@listview]
        [@li_input label="姓名" name="user.fullname"  maxlength=10/]
        [@li_field label="性别"]
        <style>
            .c_gender_set .ui-controlgroup-controls .ui-radio:nth-child(2) { margin-left: 2em; }
        </style>
        <fieldset data-role="controlgroup" data-type="horizontal" class="rightchose adjust_type_fieldset c_gender_set">
            <label for="id_gender_0">男</label>
            <input type="radio" name="suser.gender" id="id_gender_0"
                   value="男" class="t_item_1" required/>
            <label for="id_gender_1">女</label>
            <input type="radio" name="suser.gender" id="id_gender_1"
                   value="女" class="t_item_1"/>
        </fieldset>
        [/@]
        [@li_input label="身份证号码" name="suser.idnumber"  maxlength=18 class="idnumber"/]
        [@li_input label="出生日期" name="suser.birthday"  maxlength=12 class="c_date_input id_birthday"/]

    [/@]
    [@listview]
        [@li_field label="单位"]
            [@m.departmentSelect/]
        [/@]
    [#--[@li_input label="职务" name="suser.post"  maxlength=20 required=""/]--]
    [/@]
    [@listview]
        [@li_input label="手机" name="user.phone"  maxlength=11/]
        [@li_input label="电子邮箱" name="user.mail"  maxlength=30 class="email" required=""/]
    [/@]
    [@mobiscroll/]
<script>
    jQuery.validator.addClassRules("idnumber", {
        idnumber: true
    });
    jQuery.validator.addMethod("idnumber", function (value, element, param) {
        if (!/^[\d]{15}$|^[\d]{18}$|^[\d]{17}[X]$/.test(value)) {
            return false;
        }
        var sum = 0;
        for (var i = 1; i <= 17; i++) {
            sum += (Math.pow(2, 18 - i) % 11) * parseInt(value.charAt(i - 1), 11);
        }
        sum %= 11;
        var valideCode = [1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2];
        sum = valideCode [sum];
        sum = sum == 10 ? sum = "X" : sum + "";
        var last = value.charAt(17).toUpperCase();
        console.log(sum);
        if (sum != last) {
            return false;
        }
        return true;
    }, $.validator.format("身份证号码输入有误"));
    var loginForm_rules = {
        "user.name": {
            remote: "${b.url("!username")}"
        },
        "user.password": {
            minlength: 6
        },
        "password2": {
            equalTo: "#password1"
        },
        "suser.idnumber": {
            minlength: 18
        },
        "user.phone": {
            minlength: 11,
            number: true
        }
    };
    var loginForm_messages = {
        "user.name": {
            remote: "用户名已存在"
        }
    };
    loginForm.onsubmit = function () {
        if ($(loginForm).valid()) {
            $.mobile.loading('show');
            $("#password2").prop("disabled", "disabled");
        }
    };
    $(".c_gender_set input").change(function () {
        $("[name='suser.gender']").valid();
    });
    $("[name='suser.birthday']").change(function () {
        $(this).valid()
    });
    $(".idnumber").keypress(function () {
        var birthday = $(".id_birthday");
        if (birthday.val() == "" && this.value.length >= 14) {
            var date = this.value.substring(6, 14);
            birthday.val(date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8));
        }
    });
    $(".suser_type_select").change(function () {
        var suser_code_li = $(".suser_code_li");
        if (this.value == "6004") {
            suser_code_li.show();
        } else {
            suser_code_li.hide();
        }
    });
</script>
[/@]
