[#ftl]
[#include "../comm/macro.ftl"/]
[#import "/template/form/utils.ftl" as fu/]
[@index code="06"]
<h3 class="caption">${(switch.name)!}</h3>
<div style="margin: 15px 0;">
    [@b.form action="!save" class="student_major_form"]
        <div class="row form-horizontal">
            <div class="col-md-6 col-md-offset-2">
                [#assign nums=["","一","二","三","四","五","六"]/]
                [#list 1..config.num as v]
                    [#assign detail = studentMajor.getDetail(v)/]
                    <input type="hidden" name="detail" value="${v}"/>
                    <input type="hidden" name="detail${v}.sort" value="${v}"/>
                    <div class="form-group">
                        <label class="col-md-6 control-label bold font_size_style ">第${nums[v]}志愿：</label>
                        <div class="col-md-6">
                            <select name="detail${v}.major.id" class="form-control f_major f_major${v} v_diff" required>
                                <option value="">请选择专业</option>
                                [#list majorInfos as majorInfo]
                                    <option value="${(majorInfo.major.id)!}" data-duration="${(majorInfo.major.duration)!}" data-education="${(majorInfo.major.education.name)!}"
                                            [#if majorInfo.major.id == (detail.major.id)!0]selected[/#if]>${(majorInfo.major.name)!}</option>
                                [/#list]
                            </select>
                            <p style="display: none; margin: 5px 0;"></p>
                        </div>
                    </div>
                [/#list]
                [#if config.showAdjust]
                    <div class="form-group">
                        <label class="col-md-6 control-label bold font_size_style ">是否服从调剂：</label>
                        <div class="col-md-6">
                            <label class="radio-inline">
                                <input type="radio" name="studentMajor.alterable" value="1" [#if studentMajor.alterable]checked[/#if]> 是
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="studentMajor.alterable" value="0" [#if !studentMajor.alterable]checked[/#if]> 否
                            </label>
                        </div>
                    </div>
                [/#if]
                <div style="margin: 30px;">
                    <div class="col-md-6 col-md-offset-6">
                        <button type="button" class="btn btn-primary student_major_submit">
                            提交 <i class="fa fa-check "></i>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    [/@]
    [@editRemark/]
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
            ValidateForm($(".student_major_form"));
            $(".student_major_submit").click(function () {
                var form = $(this.form);
                if (form.valid()) {
                    form.submit();
                }
            });
            $(".f_major").change(function () {
                var option = $("option:selected", this);
                var p = $("p", $(this).parent());
                if (option.val() != "") {
                    p.html("学制：" + option.attr("data-duration") + "年  学历层次：" + option.attr("data-education"));
                    p.show();
                } else {
                    p.hide();
                }
            }).trigger("change");
        });
    </script>
</div>
<h3 class="caption">专业介绍</h3>
<div class="row" style="margin:15px 0;">
    [#list majorInfos as majorInfo]
        <div class="col-md-4  text-center">
            <div class="pie-chart">
                <a data-toggle="modal" href="${b.url("!info")}&id=${(majorInfo.major.id)!}"
                   data-target="#modal">
                    <img src="${base}/common/download.action?uuid=${(majorInfo.logo)!"empty"}"
                         style="width:197px; height: 154px;">
                    <p class="profession_font">${(majorInfo.major.name)!}</p>
                </a>
            </div>
        </div>
    [/#list]
</div>

<!-- Modal -->
<div id="modal" class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content xx">
        </div>
    </div>
</div>
[/@index]
