[#ftl]
[#include "../comm/lib.ftl"/]
[#if financeStudent??]
    [@edit]
        [@panelInfo title="住宿状态"]
        <div class="form-horizontal">
            <div class="form-group">
                <label class="col-sm-2 control-label">是否需要住宿：</label>
                <div class="col-sm-10">
                    <p class="form-control-static">${(studentInfo.accommodationed?string("是", "否"))!}</p>
                </div>
            </div>
        </div>
        [/@panelInfo]
        [#if studentInfo.accommodationed]
            [@panelInfo title="住宿收费"]
                [#if dormSettings?? && dormSettings?size gt 0]
                    [#include "table.ftl"/]
                [#else]
                <div class="alert alert-danger">没有配置住宿收费项。</div>
                [/#if]
            [/@panelInfo]
        [/#if]
        [@panelEdit title="请选择"]
        <div class="form-horizontal">
            <div class="form-group">
                <label class="col-sm-2 control-label">是否需要住宿：</label>
                <div class="col-sm-10">
                    <label class="radio-inline">
                        <input type="radio" name="accommodationed" value="1" [#if studentInfo.accommodationed!false]checked[/#if]> 是
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="accommodationed" value="0" [#if !studentInfo.accommodationed!true]checked[/#if]> 否
                    </label>
                </div>
            </div>
        </div>
        [/@panelEdit]
        [@panelEdit title="住宿收费"]
            [#if dormSettings?? && dormSettings?size gt 0]
                [#include "table.ftl"/]
            [#else]
            <div class="alert alert-danger">没有配置住宿收费项。</div>
            [/#if]
        [/@panelEdit]

    [/@edit]
[#else]
<div class="alert alert-danger">没有找到该学生的学费信息，无法操作。</div>
[/#if]