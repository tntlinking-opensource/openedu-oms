[#ftl]
[#include "../comm/macro.ftl"/]
[@body type="stu_class"]
<div id="msg_list_div" class="div_${page_time}">
    <div class="yx-model-last text-center more_div">
        <p> 点击查看更多</p>
    </div>
</div>
<script>
    $(function () {
        $("#msg_list_div").mcmore({
            dir: "up",
            url: "${b.url("!history")}&msgGroupId=${msgGroup.id}",
            params: function (params) {
                var lastMsgId = $("#msg_list_div .msg_id:first").val();
                if (lastMsgId) {
                    params.lastMsgId = lastMsgId;
                }
            }
        });
    });
</script>
<div style="max-width: 800px; margin: 20px auto;">
    <script type="text/javascript"
            src="${base}/static/metronic/assets/global/plugins/jquery-validation/js/jquery.validate.js"></script>
    <script type="text/javascript"
            src="${base}/static/metronic/assets/global/plugins/jquery-validation/js/localization/messages_zh.js"></script>
    <form id="std_group_msg_form" action="${b.url("!save")}" class="form-horizontal"
          role="form" method="post">
        <div class="form-group">
            <div class="col-sm-7">
                <textarea name="content" class="form-control stu_class_content" maxlength="200" style="height: 100px;"
                          required></textarea>
                <span class="help-block">最多只能输入200个字</span>
            </div>
            <div class="col-sm-3" style="text-align: center;">
                <input type="hidden" name="token" value="${token}"/>
                <button type="button" class="btn btn-primary stu_class_submit"
                        style="width: 100px; height: 100px; font-size: 24px;">提交
                </button>
            </div>
        </div>
        <script>
            $(function () {
                var form = $("#std_group_msg_form").validate();
                var content = $(".stu_class_content"), posting = false;
                $(".stu_class_submit").click(function () {
                    if (posting) {
                        return true;
                    }
                    posting = true;
                    if (form.valid()) {
                        $.post("${b.url("!save")}", {
                            content: content.val(),
                            token: "${token}",
                        }, function () {
                            content.val("");
                            posting = false;
                            loadMore();
                        });
                    }
                });
                var timer = null;

                function loadMore() {
                    clearTimeout(timer);
                    if ($(".div_${page_time}").length == 0) {
                        return;
                    }
                    $("#msg_list_div").mcmore({
                        url: "${b.url("!search")}&msgGroupId=${msgGroup.id}",
                        click: false,
                        params: function (params) {
                            var lastMsgId = $("#msg_list_div .msg_id:last").val();
                            if (lastMsgId) {
                                params.lastMsgId = lastMsgId;
                            }
                        },
                        success: function () {
                            timer = setTimeout(loadMore, 5000);
                        }
                    });
                }

                loadMore();
            });
        </script>
    </form>
</div>
[/@]