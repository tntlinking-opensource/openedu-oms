[#ftl]
[#include "../comm/macro.ftl"/]
[@body type="my"]
<div style="max-width: 600px; margin: 50px auto;">
    <script type="text/javascript"
            src="${base}/static/metronic/assets/global/plugins/jquery-validation/js/jquery.validate.js"></script>
    <script type="text/javascript"
            src="${base}/static/metronic/assets/global/plugins/jquery-validation/js/additional-methods.js"></script>
    <script type="text/javascript"
            src="${base}/static/metronic/assets/global/plugins/jquery-validation/js/localization/messages_zh.js"></script>
    <form id="ask_student_form" action="${b.url("!save")}" class="form-horizontal"
          role="form" method="post">
    [#--                                        <div class="form-group">
                                                <label for="inputEmail3" class="col-sm-2 control-label">咨询板块</label>
                                                <div class="col-sm-10">
                                                    <select name="ask.plate.id" class="form-control" required>
                                                        <option value="">请选择咨询板块</option>
                                                    [#list plates as plate]
                                                        <option value="${plate.id}">${plate.name}</option>
                                                    [/#list]
                                                    </select>
                                                </div>
                                            </div>--]
        <div class="form-group">
            <label for="inputPassword3" class="col-sm-2 control-label">咨询内容</label>
            <div class="col-sm-10">
                                                <textarea name="ask.content" class="form-control" maxlength="1000"
                                                          rows="5" required></textarea>
                <span class="help-block">最多只能输入1000个字</span>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <input type="hidden" name="token" value="${token}"/>
                <button type="submit" class="btn btn-primary">提交</button>
                <a href="${b.url("index")}" class="btn btn-default"
                   style="margin-left: 100px;">取消</a>
            </div>
        </div>
        <script>
            $(function () {
                $("#ask_student_form").validate();
            });
        </script>
    </form>
</div>
[/@]