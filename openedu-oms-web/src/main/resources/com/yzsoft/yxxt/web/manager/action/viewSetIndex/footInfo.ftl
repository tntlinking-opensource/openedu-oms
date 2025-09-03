[#ftl]
[#import "/template/form/utils.ftl" as fu/]
[@b.head/]
<style>
    body { background-color: #fff; }
</style>
[@b.form title="页脚信息编辑" action="!footInfoSave" theme="form"]
    [@fu.uploadImg2 label="页脚水印Logo" name="footBgImg" value=(config.footBgImg)! width=166 height=166/]
    [@b.textarea label="页脚信息" name="footInfo" value="${config.footInfo!}" maxlength="500"/]
    [@b.formfoot]
        [@b.submit value="action.submit"/]
    [/@]
[/@]
[@b.foot nofooter="1"/]