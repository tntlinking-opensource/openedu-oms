[#ftl]
[#include "../comm/macro.ftl"/]
[#import "/template/form/utils.ftl" as fu/]
[@index code="02"]
<h3 class="caption">报到情况</h3>
<div class="row" style="margin: 0 15px;">
    [@b.form action="!save" theme="form" boxClass="xxx"]
        [@b.datepicker label="到达日期" name="stationStudent.arriveDate" value=stationStudent.arriveDate required="true"/]
        [@b.radios label="是否按时报到" name="stationStudent.intime" value=stationStudent.intime items="1:是,0:否"/]
        <div class="arrive_div" [#if !stationStudent.intime]style="display: none;" [/#if]>
            [@b.radios label="是否需要接站" name="stationStudent.needPickup" value=stationStudent.needPickup items="1:是,0:否"/]
            [@b.field label="接站起止日期"]
                [@fu.text]
                ${(stationDate.startDate?date)!}<span style="margin: 0 10px;">至</span>${(stationDate.endDate?date)!}
                [/@]
            [/@]
            [@b.field label="到达时间"]
                <select class="form-control" name="stationStudent.arriveTime" data-placeholder="请选择到达时间" required>
                    <option value="">请选择到达时间</option>
                    [#list stationDate.times as time]
                        [#if time.time??]
                            <option [#if time.time == stationStudent.arriveTime!""]selected[/#if]>${time.time}</option>
                        [/#if]
                    [/#list]
                </select>
            [/@]
            [@b.select label="交通工具" name="stationStudent.vehicle.id" value=(stationStudent.vehicle.id)!0
            items=vehicles required="true" class="vehicle_select"/]
            [@b.field label="到达站"]
                <select class="form-control station_select" name="stationStudent.station" data-placeholder="请选择..."
                        required>
                    <option value="">请选择...</option>
                    [#list stations as station]
                        <option [#if station.name == stationStudent.station!""]selected[/#if]
                                data-vehicle-id="${(station.vehicle.id)!}">${station.name}</option>
                    [/#list]
                </select>
            [/@]
            [@b.textfield label="陪同人数" name="stationStudent.num" value="${stationStudent.num!}" required="true" maxlength="2" class="v_station_num"/]
        </div>
        <div class="reason_div" [#if stationStudent.intime]style="display: none;" [/#if]>
            [@b.field label="不按时报到原因"]
                <select class="form-control" name="stationStudent.reason" data-placeholder="请选择..." required>
                    <option value="">请选择...</option>
                    [#list reasons as reason]
                        <option [#if reason.name == stationStudent.reason!""]selected[/#if]>${reason.name}</option>
                    [/#list]
                </select>
            [/@]
        </div>
        [@b.formfoot]
            <input type="hidden" name="stationStudent.id" value="${stationStudent.id!}"/>
            [@b.submit value="action.submit"/]
        [/@]
        [@editRemark/]
    [/@]
</div>
<script>
    $(function () {
        $(".alert-danger").remove();
        jQuery.validator.addClassRules("v_station_num", {
            number: true,
            min: 0,
            max: 6,
        });
        $(".vehicle_select").change(function (e) {
            vehicleChange();
        });
        $(".vehicle_select").val();
        function vehicleChange() {
            var id = $(".vehicle_select").val();
            $(".station_select").find("option").hide().each(function () {
                if (id == $(this).attr("data-vehicle-id")) {
                    $(this).show();
                }
            });
        }
        vehicleChange();
        $(".station_select").val("${(stationStudent.station)!}")

        $("[name='stationStudent.intime']").click(function () {
            if (this.value == "1") {
                $(".reason_div").slideUp();
                $(".arrive_div").slideDown();
            } else {
                $(".reason_div").slideDown();
                $(".arrive_div").slideUp();
            }
        });
    });
</script>
[/@index]
