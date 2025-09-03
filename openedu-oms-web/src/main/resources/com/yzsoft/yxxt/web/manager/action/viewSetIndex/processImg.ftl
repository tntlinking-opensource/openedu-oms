[#ftl]
[#import "/template/form/utils.ftl" as fu/]
[@b.head/]
<style>
    body { background-color: #fff; }
</style>
[@b.form title="报到流程图片" action="!processImgSave" theme="form"]
	[#if type?? && type]
    	[@fu.uploadImg2 label="报到流程图片" name="processImg" value=(config.processGzImg)! width=166 height=166/]
    [#else]
    	[@fu.uploadImg2 label="报到流程图片" name="processImg" value=(config.processZzImg)! width=166 height=166/]
    [/#if]
    <input type="hidden" name="type" value="${(type?string("1","0"))!}">
    [@b.formfoot]
        [@b.submit value="action.submit"/]
    [/@]
[/@]
[@b.foot nofooter="1"/]