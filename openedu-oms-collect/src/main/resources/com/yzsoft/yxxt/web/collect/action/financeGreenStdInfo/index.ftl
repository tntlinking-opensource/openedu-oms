[#ftl]
[#include "../comm/macro.ftl"/]
[#import "/template/form/utils.ftl" as fu/]
[@index code="05"]
<h3 class="caption">绿色通道</h3>
<div>
    [@b.form action="!edit" class="supplies_form"]
        [#if financeGreenStd??]
            <div style="min-height: 300px;">
                <div class="form-horizontal" style="margin: 20px;">
                    <div class="form-group">
                        <label class="col-sm-5 control-label">是否需要办理绿色通道：</label>
                        <div class="col-sm-7">
                            <p class="form-control-static">${financeGreenStd.handle?string("是", "否")}</p>
                        </div>
                    </div>
                </div>
                [#if financeGreenStd.handle]
                    <div style="max-width: 800px; margin: auto;">
                        <table class="table table-bordered">
                            <thead>
                            <tr>
                                <th>申请项目</th>
                                <th>项目说明</th>
                            </tr>
                            </thead>
                            <tbody>
                                [#list financeGreenStd.financeGreens as v]
                                <tr align="center">
                                    <td>${(v.name)!}</td>
                                    <td>${(v.remark)!}</td>
                                </tr>
                                [/#list]
                            </tbody>
                        </table>
                    </div>
                [/#if]
                [#if student.stdInfoFile?? && student.stdInfoFile?size > 0]
	                <div id="file_div" class="form-horizontal" style="margin: 20px;">
						<div class="form-group">
							<label class="col-sm-3 control-label">附件：</label>
							<div class="col-sm-9">
								<div class="row" style="padding: 0">
										[#list student.stdInfoFile as v]
						            <div class="col-xs-6" style="width:auto">
							                <img style="height:160px;width:auto" src="${base}/common/download.action?uuid=${(v.uuid)!}"/>
						            </div>
							        	[/#list]
							    </div>
							</div>
						</div>
					</div>
				[/#if]
            </div>
        [#else]
            [@emptyMessage/]
        [/#if]
        [#if switch?? && switch.editable]
            <div style="margin: 30px; text-align: center;">
                <button type="submit" class="btn btn-primary supplies_submit">修改</button>
            </div>
        [/#if]
    [/@]
</div>
[/@index]
