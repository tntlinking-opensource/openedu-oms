[#ftl]

[#import "itemForm.ftl" as itemForm/]

[#macro body title]
    [@b.head/]
    <div class="row">
        <!--登录-->
        <div class="col-md-3" style="padding-right: 10px;">
            <div class="portlet light bordered">
                <div class="portlet-title">
                    <div class="caption"><span class="caption-subject font-dark sbold uppercase">学号(扫描)输入</span></div>
                </div>
                <div class="portlet-body form">
                    <form id="dealForm" action="${b.url("!edit")}" class="form-horizontal" method="post"
                          target="datalist">
                        <div class="form-body" style="padding-bottom:0;">
                            <div class="form-group">
                                <label class="col-md-4 control-label">学号</label>
                                <div class="col-md-8">
                                    <input type="text" name="code" value="" class="form-control spinner code_ipt"
                                           placeholder="请输入学号" id="student_code_ipt">
                                </div>
                            </div>
                            [#--						<div class="form-group">
                                                        <label class="col-md-4 control-label">状态</label>
                                                        <div class="col-md-8">
                                                            <select class="form-control">
                                                                <option>空闲</option>
                                                                <option>拥挤</option>
                                                            </select>
                                                        </div>
                                                    </div>--]
                            <div class="form-group">
                                <div class="col-sm-8 col-sm-offset-4">
                                    <button type="submit" class="btn btn-primary searchButton"
                                            onclick="bg.form.submit(this.form.id);setTimeout(function (){$('.code_ipt').val('');}, 1000);return false;">
                                        提交
                                    </button>
                                    <button type="reset" class="btn btn-default pull-right">重置</button>
                                    <input type="hidden" name="batchId" value="${batchId}"/>
                                    <input type="hidden" name="itemId" value="${itemId}"/>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            [@b.div href="/welcome/count?id=${batch.id}"/]
        </div>
        <div class="col-md-9" style="padding-left: 10px;">
            <ol class="breadcrumb" style="margin-bottom: 10px;">
                <li>[@b.a href="/welcome/index!info?id=${batch.id}"]迎新现场[/@b.a]</li>
                [#--<li class="active">${title}</li>--]
                <li class="active">[@b.a href="/welcome/index!deal?batchId=${batchId}&itemId=${itemId}"]${title}[/@b.a]</li>
            </ol>
            [@b.div id="deal_body"]
                [#nested /]
            [/@b.div]
        </div>
    </div>
    <script>
        $(function () {
            $("#student_code_ipt").focus();
        });
    </script>
    [@b.foot/]
[/#macro]

[#macro panel title=""]
    <div class="panel panel-default">
        [#if title != ""]
            <div class="panel-heading">${title}</div>
        [/#if]
        <div class="panel-body">
            [#nested /]
        </div>
    </div>
[/#macro]

[#macro index title=""]
    [#assign _title][#if title == ""]${(item.name)!}[#else]${title}[/#if][/#assign]
    [@body title=_title]
        [@b.form id="studentSearchForm" name="searchForm"  action="!search" target="datalist" title="ui.searchForm" theme="search"]
            [@b.textfield label="学号" name="student.code"/]
            [@b.textfield label="姓名" name="student.name"/]
            <input type="hidden" name="batchId" value="${batchId}"/>
            <input type="hidden" name="itemId" value="${itemId}"/>
        [/@]
        <div style="margin-top: 10px;"></div>
        [@b.div id="datalist" href="!search?batchId=${batchId}&itemId=${itemId}" /]
    [/@body]
[/#macro]

[#macro datalist]
    [@b.head/]
    [@b.grid  items=students var="student"]
        [@b.row align="center"]
            [@b.col title="学号" property="code"/]
            [@b.col title="姓名" property="name"/]
            [@b.col title="性别" property="gender.name"/]
            [@b.col title="学院" property="department.name"/]
            [@b.col title="专业" property="major.name"/]
            [@b.col title="班级" property="adminClass.name"/]
            [@b.col title="操作"]
                [@b.a href="!edit?code=${(student.code)}&batchId=${batchId}&itemId=${itemId}"]办理[/@]
            [/@]
        [/@]
    [/@]
    [@b.foot/]
[/#macro]

[#macro edit]
    [@b.head/]
    <style>
        .table-titled > tbody > tr > td:nth-of-type(odd) { text-align: right; }
    </style>
    <script>
        $(function () {
            [#if !Parameters["noscroll"]??]
                window.scrollTo(0, 83);
            [/#if]
            [#if save??]
                $("#student_code_ipt").focus();
            [/#if]
        });
    </script>
    <div class="row" style="margin: 15px -15px;">
        <div class="col-md-8">
            [@studentInfoPortlet/]
            <div>
                [@b.messages formId="formId"/]
            </div>
            <style>
                #process_form label.error {margin-top: 5px;}
            </style>
            [@c.validateJsAndCss/]
            <script>
                function initProcessForm(form) {
                    return form.validate({
                        errorPlacement: function (error, element) {
                            var parent = element.parent();
                            if (parent.is(".radio-inline")) {
                                error.insertAfter(parent.parent());
                            } else {
                                error.insertAfter(element);
                            }
                        }
                    });
                }

                function checkProcessForm(form) {
                    return $("#process_form").valid();
                }
            </script>
            <form id="process_form" action="${b.url("!save")}" method="post" enctype="multipart/form-data">
                [#--没有错误消息、没有办理日志或办理未通过--]
                [#if !handleable?? &&  (!processLinkItemLog?? || !processLinkItemLog.enabled)]
                    [#assign editMode = true/]
                [/#if]
                [#if editMode??]
                    <style>
                        .edit_div { display: block; }
                        .info_div { display: none; }
                        .welcome .form-group { margin-bottom: 5px; }
                    </style>
                [#else]
                    <style>
                        .edit_div { display: none; }
                        .info_div { display: block; }
                        .welcome .form-group { margin-bottom: 5px; }
                    </style>
                [/#if]
                [#--环节描述--]
                [#if processLinkItemLog?? && processLinkItemLog.enabled]
                    <div class="alert alert-success" role="alert">
                        该学生已领取
                    </div>
                [/#if]
                [#if processLinkItem.description??]
                    [@panel title="环节描述"]${(processLinkItem.description)!}[/@panel]
                [/#if]
                [#--业务项--]
                [#if itemForms?? && itemForms?size gt 0]
                    <div class="welcome">
                        [@panelInfo "业务项"]
                            [@itemForm.info/]
                        [/@panelInfo]
                        [@panelEdit "业务项"]
                            [@itemForm.form/]
                        [/@panelEdit]
                    </div>
                [/#if]
                [#--打印--]
                [#if prints?? && prints?size gt 0]
                    [@panelEdit]
                        [#list prints as print]
                            <a href="${b.url("print")}&printId=${print.id!}&studentId=${studentId}" class="btn blue"
                               target="_blank">${(print.template.name)!}</a>
                        [/#list]
                    [/@panelEdit]
                [/#if]
                [#nested /]
                [#if handleable??]
                    <div class="alert alert-danger" role="alert">
                        ${handleable}
                    </div>
                [#else]
                    <div class="col-md-12 text-center">
                        [#if processLinkItemLog?? && processLinkItemLog.enabled && processLinkItem.revoked]
                            <button type="button" class="btn red btn_cancel" style="margin-right:15px;">撤销领取</button>
                        [/#if]
                        [#if !processLinkItemLog?? || !processLinkItemLog.enabled]
                            [#--<button type="button" class="btn green btn_save" style="margin-right:15px;">保存</button>--]
                             [#if supply?? && supply.agree]
								  <button type="button" class="btn blue btn_pass" style="margin-right:15px;">确认领取</button>
				             [#else]
				             [/#if]
                        [/#if]
                        <button type="button" class="btn default btn_list">返回列表</button>
                        <input type="hidden" name="pass" value="1" class="ipt_pass"/>
                        <input type="hidden" name="save" value="0" class="ipt_save"/>
                        <input type="hidden" name="studentId" value="${student.id}"/>
                        <input type="hidden" name="batchId" value="${batchId}"/>
                        <input type="hidden" name="itemId" value="${itemId}"/>
                        <input type="hidden" name="params"
                               value="batchId=${batchId}&itemId=${itemId}&code=${(student.code)!}"/>
                    </div>
                [/#if]
            </form>
            <script>
                $(function () {
                    var validate = initProcessForm($("#process_form"));
                    $(".btn_save").click(function () {
                        $(".ipt_save").val(1);
                        submitForm(this.form);
                    });
                    $(".btn_pass").click(function () {
                        submitForm(this.form);
                    });
                    $(".btn_cancel").click(function () {
                        var form = this.form;
                        $(".ipt_pass").val(0);
                        validate.destroy();
                        bg.form.submit(form);
                    });
                    $(".btn_list").click(function () {
                        bg.Go("${b.url("!index")}&batchId=${batchId}&itemId=${itemId}", "batch_body");
                    });

                    function submitForm(form) {
                        if (checkProcessForm()) {
                            bg.form.submit(form);
                        }
                    }
                });
            </script>
        </div>
        <div class="col-md-4">
            [@b.div href="/welcome/process/process-log?studentId=${student.id}&batchId=${batchId}"/]
        </div>
    </div>
    [@b.foot/]
[/#macro]

[#macro studentInfoPortlet]
    <div class="portlet light">
        <div class="row">
            <div class="col-md-2" style="padding:0;">
                <img src="${base}/common/download.action?uuid=${(student.photo)!"static/yxxt/welcome/css/images/my.jpg"}"
                     style="width:100%;">
            </div>
            <div class="col-md-10">
                [@studentInfoPanel/]
            </div>
        </div>
    </div>
[/#macro]

[#macro studentInfoPanel]
    <style>
        .table-striped-1 > tbody > tr:nth-of-type(odd) {
            background-color: #f3f6f9;
        }
        .table-striped-1 > tbody > tr > td:nth-of-type(odd) {
            text-align: right;
            width: 20%;
        }
        .table-striped-1 > tbody > tr > td:nth-of-type(even) {
            width: 30%;
        }
        .table-striped-1 > tbody > tr > td { border: none; }

    </style>
    <table id="user" class="table table-striped-1">
        <tbody>
        <tr>
            <td>学号：</td>
            <td>${(student.code)!}</td>
            <td>身份证：</td>
            <td>${(student.cardcode)!}</td>
        </tr>
        <tr>
            <td>姓名：</td>
            <td>${(student.name)!}</td>
            <td>性别：</td>
            <td>${(student.gender.name)!}</td>
        </tr>
        <tr>
            <td>学院：</td>
            <td>${(student.department.name)!}</td>
            <td>专业：</td>
            <td>${(student.major.name)!}</td>
        </tr>
        <tr>
            <td>班级：</td>
            <td>${(student.adminClass.name)!}</td>
            <td>手机号：</td>
            <td>${(student.phone)!}</td>
        </tr>
        </tbody>
    </table>
[/#macro]

[#macro  panelEdit title=""]
    <div class="edit_div">
        [@panel title=title]
            [#nested /]
        [/@panel]
    </div>
[/#macro]

[#macro  panelInfo title=""]
    <div class="info_div">
        [@panel title=title]
            [#nested /]
        [/@panel]
    </div>
[/#macro]