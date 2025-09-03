[#ftl]

[#function isWitchFinished switch states]
    [#list states as state]
        [#if switch.code == state.collectSwitch.code && state.finished]
            [#return true/]
        [/#if]
    [/#list]
    [#return false/]
[/#function]

[#macro collectMenu code]
    [#assign collectSwitches = "com.yzsoft.yxxt.web.collect.freemarker.CollectSwitches"?new()/]
    [@collectSwitches]
    <ul class="nav nav-tabs tabs-left">
        [#list switches as switch]
            <li [#if code == switch.code]class="active"[/#if]>
                <a href="${base}${switch.url}.action">${switch.name}
                    [#if switch.editable]
                        [#if isWitchFinished(switch, states)]
                            <span style="color: green;">（已填写）</span>
                        [#else]
                            <span style="color: red;">（未填写）</span>
                        [/#if]
                    [/#if]
                </a>
            </li>
        [/#list]
    </ul>
    [/@collectSwitches]
[/#macro]

[#macro body code]
    [@yx.head title="信息填写"/]
<link href="${base}/static/metronic/assets/yz_yxwz_css/main6.css" rel="stylesheet" type="text/css"/>
<div class="row">
    <div class="col-md-10 col-md-offset-1" style="padding-left:0; padding-right:0;">
        <div class="portlet light clearcss" style="padding: 15px;">
            <div class="row">
                <div class="col-md-3 col-sm-3 col-xs-3" style="padding-left:0; padding-right:0;">
                    [@collectMenu code=code/]
                </div>
                <div class="col-md-9 col-sm-9 col-xs-9"
                     style="border:1px solid #ddd; padding-left:0; padding-right:0;">
                    <div class="tab-content" style=" min-height: 600px;">
                        <div class="tab-pane active in">
                            [#nested /]
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
    [@yx.foot/]
[/#macro]

[#macro index code]
    [@body code=code]
        [#nested /]
    [/@body]
[/#macro]

[#macro form code action="!save"]
    [@body code=code]
        [@b.form name="editForm" action=action]
        <style>
            .form-group.error { color: red; }
            .form-group.error input, .form-group.error select { border: 1px solid red; }
            .form-group label.error { color: red; margin: -1px 0 0 0; position: absolute; z-index: 99; background-color: #fff; border: 1px solid red; padding: 0px 5px; }
        </style>
        <script>
            function beforeSubmit() {
                return true;
            }
        </script>
            [#nested /]
        <div class="text-center" style="margin: 20px 0;">
            <button class="btn btn-primary edit_form_submit" type="button">
                提交 <i class="fa fa-check "></i>
            </button>
        </div>
            [@editRemark/]
        <script>
            $(function () {
                var form = $("#editForm");
                form.validate({
                    errorPlacement: function (error, element) {
                        var pparent = element.parent().parent();
                        if (element.hasClass("select2")) {
                            error.insertAfter(element.next());
                        } else {
                            if (pparent.is(".mt-radio-list, .checkbox-list, .radio-list")) {
                                error.insertAfter(pparent);
                            } else if (element.attr("type") == 'checkbox' || element.parent().is(".input-group, .group")) {
                                error.insertAfter(element.parent());
                            } else {
                                error.insertAfter(element);
                            }
                        }
                    },
                    highlight: function (element, errorClass, validClass) {
                        $(element).closest(".form-group").addClass("error");
                    },
                    unhighlight: function (element, errorClass, validClass) {
                        $(element).closest(".form-group").removeClass("error");
                    }
                });
                $(".edit_form_submit").click(function () {
                    if (beforeSubmit()) {
                        if (form.valid()) {
                            form.submit();
                        } else {
                            alert("表单填写有误，不能提交。");
                        }
                    }
                });
            });
        </script>
        [/@b.form]
    [/@body]
[/#macro]

[#macro editRemark]
    [#if collectSwitch?? && collectSwitch.remark??]
    <div style="margin: 15px;">
        <div class="alert alert-info" role="alert">注意事项：<br/>${(collectSwitch.remark)!}</div>
    </div>
    [/#if]
[/#macro]

[#macro emptyMessage]
<div class="text-center" style="margin: 15px;">
    <div class="alert alert-info" role="alert">暂无内容</div>
</div>
[/#macro]

[#macro collectTitle name ]
<div class="panel-body" style="padding:0;background-color:#edf5fa;margin-bottom:15px;">
    <div class="row" style="margin:0;">
        <div class="col-md-12" style="padding-left:0; padding-right:0;">
            <div class="portlet-title border_style_01">
                <div class="caption">
                    <span class="caption-subject   uppercase font_style_01">${(name)!}</span>
                    [#nested /]
                </div>
            </div>
        </div>
[/#macro]