[#ftl]
[#import "/template/comm.ftl" as c/]
[#import "/template/mobile.ftl" as m/]
[#include "../comm/macro.ftl"/]
[@m.body title=switch.name back=formBack((studentMajor.id)?? && (studentMajor.details?size gt 0))]
<form action="${b.url("!save")}" method="post" class="clothesStudentForm">
    <div class="yx-xxtx">
        <div class="yx-model-zx">
            <a href="${b.url("!majorList")}" data-role="button" class="yx-model-orangebtn"
               data-transition="slide">专业介绍</a>
        </div>
        [#assign nums=["","一","二","三","四","五","六"]/]
        [#list 1..config.num as v]
            [#assign detail = studentMajor.getDetail(v)/]
            <input type="hidden" name="detail" value="${v}"/>
            <input type="hidden" name="detail${v}.sort" value="${v}"/>
            <div class="yx-model-zx01 margin-top-em">
                <label class="yx-model-zx-label">第${nums[v]}志愿</label>
                <div class="form-right" style="width: auto; float: none;">
                    <select name="detail${v}.major.id" class="form-control v_diff" required>
                        <option value="">请选择专业</option>
                        [#list majorInfos as majorInfo]
                            <option value="${(majorInfo.major.id)!}"
                                    [#if majorInfo.major.id == (detail.major.id)!0]selected[/#if]>${(majorInfo.major.name)!}</option>
                        [/#list]
                    </select>
                </div>
            </div>
        [/#list]
        [#if config.showAdjust]
            <div class="yx-model-zx01 margin-top-em">
                <label class="yx-model-zx-label">是否服从调剂</label>
                <div class="form-right" style="width: auto; float: none;">
                    <select name="studentMajor.alterable" required>
                        <option value="1">是</option>
                        <option value="0" [#if !(studentMajor.alterable??) || !studentMajor.alterable]selected[/#if]>否</option>
                    </select>
                </div>
            </div>
        [/#if]
        <div class="yx-jiange-1em"></div>
        <div class="yx-model-btnmodel">
            <button type="button" class="yx-model-bluebtn submit">提交</button>
        </div>
    </div>
</form>
    [@m.validation/]
<script src="${base}/static/yxxt/mobile/scripts/form.js"></script>
<script>
    $(function () {
        jQuery.validator.addClassRules("v_diff", {
            v_diff: ".v_diff",
        });
        jQuery.validator.addMethod("v_diff", function (value, element, param) {
            if (value == "") {
                return true;
            }
            var count = 0;
            $(param).each(function () {
                if (value == $(this).val()) {
                    count++;
                }
            });
            return count == 1;
        }, "不能重复");
        $(".clothesStudentForm").initForm();
    });
</script>
[/@]