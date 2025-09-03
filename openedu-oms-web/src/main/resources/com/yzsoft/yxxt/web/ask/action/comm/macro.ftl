[#ftl]
[#macro body type]
    [@yx.head title="交流问答"/]
<link href="${base}/static/metronic/assets/yz_yxwz_css/main6.css" rel="stylesheet" type="text/css"/>

<div class="page-content">
    <div class="row">
        <div class="col-md-10 col-md-offset-1" style="padding-left:0; padding-right:0;">
            <div class="portlet light clearcss">
                <div class="row bg-light-blue yx-zixun">
                    <h1 class="yx-zixun-left"><img
                            src="${base}/static/metronic/assets/yz_yxwz_css/zixun_03.png">在线咨询</h1>
                    <a href="${b.url("my!edit")}"
                       class="btn btn-primary btn-lg yx-zixun-right margin-top-15"
                       role="button">我要咨询</a>
                </div>
                <div class="portlet light">
                    [@nav type=type/]
                    <div class="portlet-body">
                        <div class="tab-content">
                            <div class="tab-pane  active" id="portlet_tab1">
                                <div class="row">
                                    <div class="portlet-body">
                                        <div class="tab-content">
                                            <div class="tab-pane fade active in">
                                                [#nested /]
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
    [@yx.foot/]
[/#macro]

[#macro nav type]
<div class="portlet-title tabbable-line">
    <ul class="nav nav-tabs ask_nav">
        <li class="index">
            <a href="index.action">常见问题</a>
        </li>
        <li class="student">
            <a href="student.action">学生咨询</a>
        </li>
        <li class="my">
            <a href="my.action">我的咨询</a>
        </li>
        <li class="stu_class">
            <a href="stu-class.action">同班交流</a>
        </li>
    </ul>
</div>
<script>
    $(".ask_nav .${type}").addClass("active");
</script>
[/#macro]