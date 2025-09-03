[#ftl]
[#import "/template/comm.ftl" as c/]
[#import "/template/mobile.ftl" as m/]
[#include "../comm/macro.ftl"/]
[@m.body title=switch.name back=b.url("index?t=1")]
<style>
	.div_img{width: 50%;float: left;padding: .7em;}
	.div_img div{margin-top: -21px;margin-left: 105px;}
</style>
<div class="yx-xxtx">
    [#if financeGreenStd??]
        <ul data-role="listview" class="yx-xxtx-info">
            <li>
                <div class="yx-xxtx-info-a">
                    <span class="yx-xxtx-info-a-left">是否办理</span>
                    <span class="yx-xxtx-info-a-right">${ financeGreenStd.handle?string("是", "否")}</span>
                </div>
            </li>
        </ul>
        [#list financeGreenStd.financeGreens as v]
            <h3 class="yx-xxtx-title">${(v.name)!}</h3>
            <p style="padding: 1em; background-color: #fff; font-size: 0.8em;">${(v.remark)!}</p>
        [/#list]
        [#if student.stdInfoFile?? && student.stdInfoFile?size > 0]
	        <h3 class="yx-xxtx-title">上传附件</h3>
	        <ul data-role="listview" class="yx-xxtx-info">
		        <li>
	            	<div class="img-list yx-xxtx-info-a" style="text-align: center;">
						[#list student.stdInfoFile as v]
							<div class="div_img">
								<input type="hidden" name="uuids" value="${(v.uuid)!}" />
				                <img style="height:auto;width:100%;" src="${base}/common/download.action?uuid=${(v.uuid)!}"/>
			                </div>
			        	[/#list]
		            </div>
	            </li>
            </ul>
        [/#if]
    [#else]
        [@emptyMessage/]
    [/#if]
</div>
    [#if switch?? && switch.editable]
    <div class="yx-jiange-1em"></div>
    <div class="yx-model-btnmodel">
        <a href="${b.url("!edit")}" data-role="button" class="yx-model-bluebtn">修改</a>
    </div>
    <div class="yx-jiange-1em"></div>
    [/#if]
[/@]