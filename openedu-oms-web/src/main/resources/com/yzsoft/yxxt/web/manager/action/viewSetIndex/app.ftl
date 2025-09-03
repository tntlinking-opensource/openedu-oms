[#ftl]
[#import "/template/form/utils.ftl" as fu/]
[@b.head/]
<style>
    body { background-color: #fff; }
</style>
[@b.toolbar title="App信息编辑"][/@]
[@b.form action="!appSave" theme="form"]
    [@fu.uploadImg2 label="App二维码" name="appImg" value=(config.appImg)! width=164 height=164/]
    [@b.textfield label="App名称" name="appName" value="${config.appName!}"/]
    [@b.formfoot]
        [@b.submit value="action.submit"/]
    [/@]
[/@]
[@b.foot nofooter="1"/]