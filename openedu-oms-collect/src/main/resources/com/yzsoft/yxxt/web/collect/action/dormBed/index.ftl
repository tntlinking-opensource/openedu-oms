[#ftl]
[#include "/com/yzsoft/yxxt/web/collect/action/comm/macro.ftl"/]
[#import "/template/form/utils.ftl" as fu/]
[@index code="G042"]
    <style>
        .table {margin-bottom: 0;}
        .btn {margin-right: 50px;}
    </style>
    <h3 class="caption">床位选择</h3>
    <div class="row">
        [@b.form action="!edit" class="supplies_form"]
            [#if studentInfo?? && studentInfo.accommodationed]
                <div class="text-center" style="margin: 15px;">
                    <div class="alert alert-info" role="alert">您已选择了需要住宿</div>
                </div>
                <div class="form-horizontal" style="margin: 20px;">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">床位信息：</label>
                        <div class="col-sm-9">
                            [#if dormPlanBed??]
                                <table class="table table-bordered table-striped">
                                    <thead>
                                    <tr>
                                        <th>宿舍楼</th>
                                        <th>寝室号</th>
[#--                                        <th>朝向</th>--]
                                        <th>床位号</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr align="center">
                                        <td>${(dormPlanBed.bed.room.building.name)!}</td>
                                        <td>${(dormPlanBed.bed.room.name)!}</td>
[#--                                        <td>${(dormPlanBed.bed.room.direction.name)!}</td>--]
                                        <td>${(dormPlanBed.bed.name)!}</td>
                                    </tr>
                                    </tbody>
                                </table>
                            [#else]
                                <p class="form-control-static">无</p>
                            [/#if]
                        </div>
                    </div>
                </div>
                [#if switch?? && switch.editable]
                    <div class="col-sm-9 col-sm-offset-3">
                    [#-- 20201106增加是否取消宿舍功能 begin --]
                    <input type="hidden" id="adflag" name="adflag" value="${(adflag)!}">
                     [#if adflag == 0 && dormPlanBed??]
                     
                     [#else]
                        [#if !(dormPlanBed??)]
                            <a href="${b.url("!edit")}" class="btn btn-primary">修改</a>
                        [/#if]
                         [#if bedFlag]
                                <a href="javascript:;" onclick="tishi()" class="btn btn-info">选择床位</a>
                             [#else]
                                 <a href="${b.url("!bedEdit")}&adflag=${adflag}"  class="btn btn-info">选择床位</a>
                         [/#if]
                        [#if dormPlanBed??]
                            <a href="${b.url("!bedCancle")}" class="btn btn-danger">取消床位</a>
                        [/#if]
                     [/#if]
                    [#-- 20201106增加是否取消宿舍功能 end --]
                    </div>
                [/#if]
            [#else]
                <div class="text-center" style="margin: 15px;">
                    <div class="alert alert-danger" role="alert">您已选择了不需要住宿</div>
                </div>
                [#if switch?? && switch.editable]
                    <div class="text-center">
                        <a href="${b.url("!edit")}" class="btn btn-primary">修改</a>
                    </div>
                [/#if]
            [/#if]
        [/@]
    </div>
[/@index]
<script>
    function tishi() {
        alert("请选取消床位！");
    }
</script>
