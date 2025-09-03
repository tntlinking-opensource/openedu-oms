[#ftl]
[#import "/template/mobile.ftl" as m/]
[@m.body title="首页" cache="true"]
    <div data-theme="a">
        <style>
            .ui-page-theme-a .ui-header {
                border: none;
            }

            .ui-page-theme-a a {
                font-weight: normal;
            }

            .userbox {
                background-color: #22abde;
                padding: 15px;
                width: 100vw;
                color: #fff;
                font-size: 0.8em;
                line-height: 2em;
                background-image: url("${base}/static/yxxt/mobile/css/images/yd_bg.png");
                background-size: cover;
            }

            .userbox a {
                color: #fff;
            }

            .userbox a:visited, .userbox a:hover, .userbox a:active {
                color: #fff;
            }

            .userbox_l {
                width: 25%;
                margin: 0 auto;
            }

            .userbox_l img.radius_1 {
                width: 100%;
                margin-bottom: 0.2rem;
                border: 2px solid #fff;
                border-radius: 50%;
            }

            .text-right {
                text-align: right;
            }

            .userbox .ui-grid-a > div {
                text-overflow: ellipsis;
                white-space: nowrap;
                overflow: hidden;
            }

            .inquiryBox {
                padding: 0;
            }

            .inquiryBox01 {
                clear: both;
            }

            .inquiryBox01 .cell_li {
                border-right: 1px solid #e7e7e7;
                border-bottom: 1px solid #e7e7e7;
            }

            .self_inquiry {
                margin: 0;
            }

            .service_cell p {
                height: 30px;
                margin-top: 5px;
            }

            @media (max-width: 320px) {
                .userbox {
                    font-size: 0.6em;
                    line-height: 1.6em;
                }
            }
        </style>
        <div class="userbox">
            <div class="userbox_l">
                <img src="${base}/common/download.action?default=/static/yxxt/welcome/css/images/my.jpg&uuid=${(photo)!}"
                     class="radius_1">
            </div>
            <div style="text-align: center; font-size: 1.6em; line-height: 200%; font-weight: bold;">
                ${(student.name)!} 同学，欢迎你入学！
            </div>
            <div class="m-grids-2">
                <div class="ui-grid-a">
                    <div style="font-size: 20px;text-align: center">
                        学号：${(student.code)!}
                    </div>
                    <div class="ui-block-a" style="width: 40%">
                        <div class="ui-grid-a">
                            <div class="ui-block-a text-right">学院：</div>
                            <div class="ui-block-b">${(student.department.name)!}</div>
                            <div class="ui-block-a text-right">班级：</div>
                            <div class="ui-block-b">${(student.adminClass.name)!}</div>
                            <div class="ui-block-a text-right">辅导员：</div>
                            <div class="ui-block-b">${(student.adminClass.instructor.name)!}</div>
                        </div>
                    </div>
                    <div class="ui-block-b" style="width: 60%">
                        <div class="ui-grid-a">
                            <div class="ui-block-a text-right">专业：</div>
                            <div class="ui-block-b">${(student.major.name)!}</div>
                            <div class="ui-block-a text-right">宿舍：</div>
                            <div class="ui-block-b">
                                [#if dormPlanBed??]
                                    ${(dormPlanBed.bed.room.building.name)!}#${(dormPlanBed.bed.room.name)!}-${(dormPlanBed.bed.name)!}
                                [/#if]
                            </div>
                            <div class="ui-block-a text-right">辅导员电话：</div>
                            <div class="ui-block-b">
                                <a href="tel:${(student.adminClass.instructor.mobile)!}">${(student.adminClass.instructor.mobile)!}</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="self_inquiry">
            <div class="inquiryBox">
                <ul class="inquiryBox01">
                    [#if noticeColumn??]
                        <li class="cell_li" id="step1">
                            <a href="${base}/mobile/notice.action" class="service_cell" data-transition="slide">
                                <img src="${base}/static/yxxt/mobile/css/images/2_03_std.png"/>
                                <p>${(noticeColumn.name)!}</p>
                            </a>
                        </li>
                    [/#if]
                    <li class="cell_li" id="step2">
                        <a href="${base}/mobile/content.action" class="service_cell" data-transition="slide">
                            <img src="${base}/static/yxxt/mobile/css/images/2_03-notice.png"/>
                            <p>学校公告</p>
                        </a>
                    </li>
                    <li class="cell_li" id="step3">
                        <a href="${base}/mobile/help.action" class="service_cell" data-transition="slide">
                            <img src="${base}/static/yxxt/mobile/css/images/2_03-help.png"/>
                            <p>常见问题</p>
                        </a>
                    </li>
                </ul>
                <!--第二行-->
                <ul class="inquiryBox01">
                    <li width="25%" class="cell_li" id="step4">
                        <a href="${base}/mobile/collect/index.action" class="service_cell" data-transition="slide">
                            <img src="${base}/static/yxxt/mobile/css/images/2_03-edit.png"/>
                            <p>信息填写</p>
                        </a>
                    </li>
                    <li class="cell_li" id="step5">
                        <a href="${base}/mobile/collect/index!info.action?code=01" class="service_cell"
                           data-transition="slide">
                            <img src="${base}/static/yxxt/mobile/css/images/2_03-enroll.png"/>
                            <p>个人信息</p>
                        </a>
                    </li>
                    <li class="cell_li" id="step6">
                        <a href="http://guide.wisdom.autewifi.com/cehui/#/c/network/step" class="service_cell"
                           data-transition="slide">
                            <img src="${base}/static/yxxt/mobile/css/images/wifi.png"/>
                            <p>开通校园网</p>
                        </a>
                    </li>
                </ul>
                <ul class="inquiryBox01">
                    <li class="cell_li" id="step6">
                        <a href="${base}/mobile/collect/index!info.action?code=G042" class="service_cell"
                           data-transition="slide">
                            <img src="${base}/static/yxxt/mobile/css/images/2_03-dorm.png"/>
                            <p>宿舍选择</p>
                        </a>
                    </li>
                    <li class="cell_li" id="step10">
                        <a href="${base}/mobile/welcome.action" class="service_cell" data-transition="slide">
                            <img src="${base}/static/yxxt/mobile/css/images/2_03-enroll.png"/>
                            <p>现场报到</p>
                        </a>
                    </li>
                    <li class="cell_li" id="step11">
                        <a href="${base}/mobile/welcome!info.action" class="service_cell" data-transition="slide">
                            <img src="${base}/static/yxxt/mobile/css/images/2_03-qrcode.png"/>
                            <p>二维码</p>
                        </a>
                    </li>

                </ul>
                <ul class="inquiryBox01">
                    <li class="cell_li" id="step8">
                        <a href="${base}/mobile/statistics/wap-statistics.action" class="service_cell"
                           data-transition="slide">
                            <img src="${base}/static/yxxt/mobile/css/images/2_03-count.png"/>
                            <p>统计查询</p>
                        </a>
                    </li>
                    <li class="cell_li" id="step8">
                        <a href="${base}/mobile/msg/student-of-same-city.action" class="service_cell"
                           data-transition="slide">
                            <img src="${base}/static/yxxt/mobile/css/images/2_03-friend.png"/>
                            <p>同城学生</p>
                        </a>
                    </li>
                    <li class="cell_li" id="step8">
                        <a href="${base}/mobile/msg/wap-message.action" class="service_cell"
                           data-transition="slide">
                            <img src="${base}/static/yxxt/mobile/css/images/2_03-message.png"/>
                            <p>消息</p>
                        </a>
                    </li>
                </ul>
                <ul class="inquiryBox01">
                    <li class="cell_li" id="step12">
                        <a href="${base}/logout.action" class="service_cell" data-ajax="false">
                            <img src="${base}/static/yxxt/mobile/css/images/2_03-logout.png"/>
                            <p>帐号退出</p>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
        [#--        <div class="cancel_btn">
                    <a href="${base}/logout.action" data-role="button" data-shadow="false" class="cancel_login">退　出</a>
                </div>--]


        <script src="${base}/static/scripts/colorbox/jquery.colorbox-min.js" type="text/javascript"></script>
        <link href="${base}/static/scripts/colorbox/colorbox.css" rel="stylesheet" type="text/css"/>
        [#if student?? && !student.noticed]
            <p style='display:none'><a class='inline' href="#inline_content">Inline HTML</a></p>
            <div style='display:none'>
                <div id='inline_content' style='padding:10px; background:#fff;'>
                    <div class="ui-content" style="min-height: 100vh; background-color: #fff;">
                        <div style="text-align: center;">
                            [#if contents??]
                                ${(contents[0].content)!}
                            [/#if]
                        </div>
                    </div>
                    <div style="text-align: center;">
                        <p style="color: red;font-size: 20px;">点击“同意”，即表示您阅读并同意上述内容。</p>
                        <button style="background-color: #3598dc;" type="button" class="yx-model-greenbtn submit">同意
                        </button>
                    </div>
                </div>
            </div>

            <script>
                $(function () {
                    $(".inline").colorbox({
                        title: "报到须知",
                        overlayClose: false,
                        inline: true,
                        width: "90%",
                        open: true,
                    });

                    $(".submit").click(function () {
                        $.ajax({
                            url: '${base}/web/index2.action?method=noticed',
                            type: 'POST'
                        });

                        $("#cboxClose").click();

                        [#if !homeHelp??]
                        startIntro();
                        [/#if]
                    })

                    $("#cboxClose").hide();
                });

            </script>
        [/#if]
        [#include "homeHelp.ftl"/]
    </div>
    [#include "birthday.ftl"/]
[/@m.body]