[#ftl]
[#import "/template/mobile.ftl" as m/]
[#include "../comm/macro.ftl"/]
[@m.body title=switch.name back=b.url("index?t=1")]
    <style>
        .yx-xxtx-title {
            text-align: center;
        }
    </style>
    [#if flag?string("yes","no")=="yes"]
        <h3 class="yx-xxtx-title" style="margin-top: 100px;">${message!""}</h3>
    [#else ]
        [#if studentInfo?? && studentInfo.accommodationed]
            <h3 class="yx-xxtx-title">您已选择了需要住宿</h3>
            <div class="yx-xxtx">
                [#if dormPlanBed??]
                    <div style="background-color: #fff;">
                        <table class="table table-bordered">
                            <thead>
                            <tr>
                                <th>宿舍楼</th>
                                <th>寝室号</th>
                                <th>上/下铺</th>
                                <th>床位号</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr align="center">
                                <td>${(dormPlanBed.bed.room.building.name)!}</td>
                                <td>${(dormPlanBed.bed.room.name)!}</td>
                                <td>${(dormPlanBed.bed.bunkBed.name)!}</td>
                                <td>${(dormPlanBed.bed.name)!}</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                [#else]
                    <p style="text-align: center; margin: 30px;">还没有选择床位</p>
                [/#if]
            </div>
            [#if switch?? && switch.editable]
                <div class="yx-jiange-1em"></div>
                [#-- 20201106增加是否取消宿舍功能 begin --]
                <input type="hidden" id="adflag" name="adflag" value="${(adflag)!}">
                	[#if adflag == 0 && dormPlanBed??]
                     
                    [#else]
		                [#if !(dormPlanBed??)]
		                    <div class="yx-model-btnmodel">
		                        <a href="${b.url("!edit")}" data-role="button" class="yx-model-bluebtn">修改</a>
		                    </div>
		                [/#if]
		                <div class="yx-model-btnmodel">
                            [#if bedFlag]
                                <a href="javascript:;"  data-role="button" onclick="tishi()" class="yx-model-greenbtn">选择床位</a>
                            [#else]
                                <a href="${b.url("!bedEdit")}" data-role="button" class="yx-model-greenbtn">选择床位</a>
                            [/#if]
		                </div>
		                [#if dormPlanBed??]
		                    <div class="yx-model-btnmodel">
		                        <a href="${b.url("!bedCancle")}" data-role="button" class="yx-model-redbtn"
		                           data-ajax="false">取消床位</a>
		                    </div>
		                [/#if]
		           [/#if]
		        [#-- 20201106增加是否取消宿舍功能 end --]
            [/#if]
        [#else]
            <div class="text-center" style="margin: 15px;">
                <div class="alert alert-danger" role="alert">您已选择了不需要住宿</div>
            </div>
            [#if switch?? && switch.editable]
                <div class="yx-jiange-1em"></div>
                <div class="yx-model-btnmodel">
                    <a href="${b.url("!edit")}" data-role="button" class="yx-model-bluebtn">修改</a>
                </div>
            [/#if]
        [/#if]
    [/#if]

[/@]
<script>
    function tishi() {
        alert("请选取消床位！");
    }
</script>