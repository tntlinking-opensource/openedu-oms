[#ftl]
[#include "../comm/macro.ftl"/]
[#import "/template/form/utils.ftl" as fu/]
[@index code="06"]
<h3 class="caption">${(switch.name)!}</h3>
<div style="margin: 15px 0;">
    [@b.form action="!edit" class="supplies_form"]
        [#if studentMajor?? && studentMajor.details?size gt 0]
            <div class="row form-horizontal">
                <div class="col-md-6 col-md-offset-2">
                    [#assign nums=["一","二","三","四","五","六"]/]
                    [#list studentMajor.details as detail]
                        <div class="form-group">
                            <label class="col-md-6 control-label bold font_size_style ">第${nums[detail_index]}志愿：</label>
                            <div class="col-md-6">
                                <p class="form-control-static">
                                    <a data-toggle="modal" href="${b.url("!info")}&id=${(detail.major.id)!}"
                                       data-target="#modal">${(detail.major.name)!}（学制：${(detail.major.duration)!}年 学历层次：${(detail.major.education.name)!}）</a>
                                </p>
                            </div>
                        </div>
                    [/#list]
                    [#if config.showAdjust]
                        <div class="form-group">
                            <label class="col-md-6 control-label bold font_size_style ">是否服从调剂：</label>
                            <div class="col-md-6">
                                <p class="form-control-static">${((studentMajor.alterable!true)?string("是", "否"))!}</p>
                            </div>
                        </div>
                    [/#if]
                    [#if switch?? && switch.editable]
                        <div style="margin: 30px;">
                            <div class="col-md-6 col-md-offset-6">
                                <a href="${b.url("!edit")}" class="btn btn-primary supplies_submit">修改</a>
                            </div>
                        </div>
                    [/#if]
                </div>
            </div>
        [#else]
            [@emptyMessage/]
        [/#if]
    [/@]
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
