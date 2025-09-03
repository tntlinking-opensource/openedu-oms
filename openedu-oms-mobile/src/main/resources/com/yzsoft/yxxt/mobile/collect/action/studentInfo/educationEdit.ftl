[#ftl]
[#import "/template/mobile.ftl" as m/]
[@m.body title="教育经历"  back=b.url("!education")]
<form action="${b.url("!educationSave")}" class="studentInfoForm" method="post">
    <div class="yx-xxtx">
        <ul data-role="listview" class="yx-xxtx-info">
            <li>
                <div class="yx-xxtx-info-a">
                    <span class="yx-xxtx-info-a-left margin-top-em">开始日期</span>
                    <div class="form-right">
                        <input name="studentEducation.startDate"
                               value="${(studentEducation.startDate?string("yyyy-MM-dd"))!}" maxlength="20"
                               placeholder="请选择开始日期" required class="v_start_date"/>
                    </div>
                </div>
            </li>
            <li>
                <div class="yx-xxtx-info-a">
                    <span class="yx-xxtx-info-a-left margin-top-em">结束日期</span>
                    <div class="form-right">
                        <input name="studentEducation.endDate"
                               value="${(studentEducation.endDate?string("yyyy-MM-dd"))!}" maxlength="20"
                               placeholder="请选择结束日期" required class="v_end_date"/>
                    </div>
                </div>
            </li>
            <li>
                <div class="yx-xxtx-info-a">
                    <span class="yx-xxtx-info-a-left margin-top-em">学校及地址</span>
                    <div class="form-right">
                        <input name="studentEducation.unit" value="${(studentEducation.unit)!}" maxlength="50"
                               placeholder="请输入学校及地址" required/>
                    </div>
                </div>
            </li>
            <li>
                <div class="yx-xxtx-info-a">
                    <span class="yx-xxtx-info-a-left margin-top-em">职位</span>
                    <div class="form-right">
                        <input name="studentEducation.post" value="${(studentEducation.post)!}" maxlength="20"
                               placeholder="请输入职位" required/>
                    </div>
                </div>
            </li>
            <li>
                <div class="yx-xxtx-info-a">
                    <span class="yx-xxtx-info-a-left margin-top-em">证明人</span>
                    <div class="form-right">
                        <input name="studentEducation.reterence" value="${(studentEducation.reterence)!}" maxlength="20"
                               placeholder="请输入证明人" required/>
                    </div>
                </div>
            </li>
        </ul>
    </div>
    <div class="yx-jiange-1em"></div>
    <div class="yx-model-btnmodel">
        <input type="hidden" name="studentEducation.id" value="${(studentEducation.id)!}"/>
        <input type="hidden" name="code" value="EDUCATION"/>
        <button type="button" class="yx-model-greenbtn submit">保存</button>
    </div>
    <div class="yx-jiange-1em"></div>
</form>
    [@m.mobiscroll/]
    [@m.validation/]
<script src="${base}/static/yxxt/mobile/scripts/validate.js"></script>
<script src="${base}/static/yxxt/mobile/scripts/form.js"></script>
<script>
    $(function () {
        $(".studentInfoForm").initForm();
        $(".v_start_date, .v_end_date").each(function () {
            //初始化日期控件
            var opt = {
                lang: 'zh',
                showNow: true,
                buttons: [
                    'set', 'clear', 'cancel'
                ],
                onSelect: function (valueText, inst) {
                    if ($(this).is(".v_start_date")) {
                        $('.v_end_date').mobiscroll('option', {
                            minDate: inst.getDate(),
                        });
                    } else {
                        $('.v_start_date').mobiscroll('option', {
                            maxDate: inst.getDate(),
                        });
                    }
                    $(this).trigger("blur");
                },
            };
            $(this).mobiscroll().date(opt);
        });
    });
</script>
[/@]