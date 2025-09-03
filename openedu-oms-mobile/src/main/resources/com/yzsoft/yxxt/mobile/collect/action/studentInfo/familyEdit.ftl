[#ftl]
[#import "/template/mobile.ftl" as m/]
[@m.body title="家庭成员"  back=b.url("!family")]
<form action="${b.url("!familySave")}" class="studentInfoForm" method="post">
    <div class="yx-xxtx">
        <ul data-role="listview" class="yx-xxtx-info">
            <li>
                <div class="yx-xxtx-info-a">
                    <span class="yx-xxtx-info-a-left margin-top-em">姓名</span>
                    <div class="form-right">
                        <input name="studentFamily.name" value="${(studentFamily.name)!}" maxlength="20"
                               placeholder="请输入姓名" required>
                    </div>
                </div>
            </li>
            <li>
                <div class="yx-xxtx-info-a">
                    <span class="yx-xxtx-info-a-left margin-top-em">称谓</span>
                    <div class="form-right">
                        <input name="studentFamily.title" value="${(studentFamily.title)!}" maxlength="10"
                               placeholder="请输入称谓" required>
                    </div>
                </div>
            </li>
            <li>
                <div class="yx-xxtx-info-a">
                    <span class="yx-xxtx-info-a-left margin-top-em">年龄</span>
                    <div class="form-right">
                        <input name="studentFamily.age" value="${(studentFamily.age)!}" maxlength="3"
                               placeholder="请输入年龄" class="v_integer" required>
                    </div>
                </div>
            </li>
            <li>
                <div class="yx-xxtx-info-a">
                    <span class="yx-xxtx-info-a-left margin-top-em">工作单位</span>
                    <div class="form-right">
                        <input name="studentFamily.unit" value="${(studentFamily.unit)!}" maxlength="50"
                               placeholder="请输入工作单位" required/>
                    </div>
                </div>
            </li>
[#--            <li>
                <div class="yx-xxtx-info-a">
                    <span class="yx-xxtx-info-a-left margin-top-em">职位</span>
                    <div class="form-right">
                        <input name="studentFamily.post" value="${(studentFamily.post)!}" maxlength="10"
                               placeholder="请输入职位" required/>
                    </div>
                </div>
            </li>--]
            <li>
                <div class="yx-xxtx-info-a">
                    <span class="yx-xxtx-info-a-left margin-top-em">政治面貌</span>
                    <div class="form-right">
                        <select name="studentFamily.political.id" required>
                            <option value="">请选择政治面貌</option>
                            [#list politicals as v]
                                <option value="${v.id}"
                                        [#if v.id == (studentFamily.political.id)!0]selected[/#if]>${v.name}</option>
                            [/#list]
                        </select>
                    </div>
                </div>
            </li>
            <li>
                <div class="yx-xxtx-info-a">
                    <span class="yx-xxtx-info-a-left">联系电话</span>
                    <div class="form-right">
                        <input name="studentFamily.phone" value="${(studentFamily.phone)!}" maxlength="11"
                               placeholder="请输入联系电话" required class="v_phone">
                    </div>
                </div>
            </li>
        </ul>
    </div>
    <div class="yx-jiange-1em"></div>
    <div class="yx-model-btnmodel">
        <input type="hidden" name="studentFamily.id" value="${(studentFamily.id)!}"/>
        <input type="hidden" name="code" value="FAMILY"/>
        <button type="button" class="yx-model-greenbtn submit">保存</button>
    </div>
    <div class="yx-jiange-1em"></div>
</form>
<script src="${base}/static/scripts/jquery-validation/jquery.validate.min.js"></script>
<script src="${base}/static/scripts/jquery-validation/additional-methods.min.js"></script>
<script src="${base}/static/scripts/jquery-validation/additional-methods.js"></script>
<script src="${base}/static/yxxt/mobile/scripts/validate.js"></script>
<script src="${base}/static/yxxt/mobile/scripts/form.js"></script>
<script>
    $(function () {
        $(".studentInfoForm").initForm();
    });
</script>
[/@]