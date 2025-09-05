[#ftl]
[#--[#include "/org/beangle/process/action/processBatch/list.ftl"]--]
[@b.head/]
[@b.grid  items=processBatchs var="processBatch"]
    [@b.gridbar title="菜单列表"]
    bar.addItem("${b.text("action.add")}",action.add());
    bar.addItem("${b.text("action.edit")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove());
    bar.addItem("流程设置",action.targetMethod("processBatchSetting", "main", null, false),"fa-wrench","yellow-crusta");
    [#--bar.addItem("数据检测",action.targetMethod("processBatchCheck", "main", null, false),"fa-wrench","purple");--]
    [#--bar.addItem("数据检测",action.method("processBatchCheck"));--]
    bar.addItem("提交",action.single("submitProcess","你确认要提交所选的流程吗，提交完成后流程将无法修改！","&submited=1"),'fa-send','green');
    bar.addItem("取消提交",action.single("submitProcess","你确认要取消所选流程的提交状态吗！","&submited=0"),'fa-remove','red');
    [/@]
    [@b.row]
        [@b.boxcol/]
        [@b.col property="year" width="15%" title="学年度" align="center"/]
        [@b.col property="name" width="20%" title="批次名称"]
            [@b.a href="!processBatchSetting?processBatch.id=${processBatch.id!}" target="main"]${(processBatch.name)!}[/@]
        [/@]
        [@b.col  width="15%" title="学历层次" ]
            [#list processBatch.educations as education]${(education.name)!}[#if education_has_next]、[/#if][/#list]
        [/@]
        [@b.col property="startTime" width="30%" title="起/止时间"]
        ${(processBatch.startTime?string("yyyy-MM-dd"))!}(${(processBatch.startTime?string("HH:mm"))!})~${(processBatch.endTime?string("yyyy-MM-dd"))!}(${(processBatch.endTime?string("HH:mm"))!})
        [/@]
        [@b.col property="started" width="15%" title="状态" class="text-center"]
            [#if processBatch.submited?string=='false']
                [#if !processBatch.started]
                    [@b.a href="?#" onclick="action.method('startProcess', '您确认要启动该流程吗？', 'processBatch.id=${processBatch.id!}').func();return false;"  target="processBatchList" style="text-decoration:none;"]
                    <button class="btn default green-meadow" type="button"><i class="fa fa-play"></i></button>
                    [/@]
                <button class="btn default" type="button"><i class="fa fa-stop"></i></button>
                [#else]
                <button class="btn default" type="button"><i class="fa fa-play"></i></button>
                    [@b.a href="?#" onclick="action.method('stopProcess', '您确认要停止该流程吗？', 'processBatch.id=${processBatch.id!}').func();return false;"  target="processBatchList" style="text-decoration:none;"]
                    <button class="btn default red-sunglo" type="button"><i class="fa fa-stop"></i></button>
                    [/@]
                [/#if]
            [#else]
            <div title="已提交">
                <button class="btn default red-sunglo" type="button"><i class="fa fa-lock"></i></button>
            </div>
            [/#if]
        [/@]
    [/@]
[/@]
[@b.foot/]