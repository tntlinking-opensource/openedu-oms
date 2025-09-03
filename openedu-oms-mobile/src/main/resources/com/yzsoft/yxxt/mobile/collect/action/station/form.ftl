[#ftl]
[#import "/template/comm.ftl" as c/]
[#import "/template/mobile.ftl" as m/]
[#include "../comm/macro.ftl"/]
[@m.body title=switch.name back=formBack((stationStudent.id)??)]
<div class="yx-xxtx">
    <form action="${b.url("!save")}" method="post" class="stationStudentForm">
        [#macro li label liClass="" text=false]
            <li class="${liClass}">
                <div class="yx-xxtx-info-a">
                    <span class="yx-xxtx-info-a-left [#if !text]margin-top-em[/#if]">${label}</span>
                    <div class="form-right yx-xxtx-info-a-right">
                        [#nested /]
                    </div>
                </div>
            </li>
        [/#macro]
        <ul data-role="listview" class="yx-xxtx-info">
            [@li label="到达日期"]
                <input name="stationStudent.arriveDate"
                       value="${(stationStudent.arriveDate?string("yyyy-MM-dd"))!}" type="date" maxlength="20"
                       placeholder="请选择到达日期" required/>
            [/@]
            [@li label="是否按时报到"]
                <select name="stationStudent.intime" data-role="flipswitch">
                    <option value="0">否</option>
                    <option value="1" [#if stationStudent.intime?? && stationStudent.intime]selected[/#if]>是</option>
                </select>
            [/@]
            [@li label="是否需要接站" liClass="intime_li"]
                <select name="stationStudent.needPickup" data-role="flipswitch">
                    <option value="0" [#if !(stationStudent.needPickup?? && stationStudent.needPickup)]selected[/#if]>
                        否
                    </option>
                    <option value="1" [#if stationStudent.needPickup?? && stationStudent.needPickup]selected[/#if]>是
                    </option>
                </select>
            [/@]
            [@li label="接站起止日期" liClass="intime_li" text=true]
            ${(stationDate.startDate?string("MM-dd"))!}<span
                    style="margin: 0 10px;">至</span>${(stationDate.endDate?string("MM-dd"))!}
            [/@]
            [@li label="到达时间" liClass="intime_li"]
                [@c.select name="stationStudent.arriveTime" value="${(stationStudent.arriveTime)!}" valueKey="time" nameKey="time"
                data=stationDate.times required="required" empty="请选择到达时间" noOption=true; time]
                    [#if time.time??]
                        <option [#if time.time == stationStudent.arriveTime!""]selected[/#if]>${time.time}</option>
                    [/#if]
                [/@]
            [/@]
            [@li label="交通工具" liClass="intime_li"]
                [@c.select name="stationStudent.vehicle.id" value="${(stationStudent.vehicle.id)!0}"
                data=vehicles required="required" class="vehicle_select" empty="请选择交通工具"/]
            [/@]
            [@li label="到达站" liClass="intime_li"]
                [@c.select name="stationStudent.station" value="${(stationStudent.station)!}"
                data=stations nameKey="name" required="required" class="station_select" empty="请选择到达站" noOption=true; station]
                    <option [#if station.name == stationStudent.station!""]selected[/#if]
                            data-vehicle-id="${(station.vehicle.id)!}">${station.name}</option>
                [/@]
            [/@]
            [@li label="陪同人数" liClass="intime_li"]
                <input name="stationStudent.num"
                       value="${(stationStudent.num)!}"
                       placeholder="陪同人数" required class="v_station_num"/>
            [/@]
            [@li label="不按时报到原因" liClass="not_intime_li"]
                [@c.select name="stationStudent.reason" value="${(stationStudent.reason)!}"
                data=reasons valueKey="name" required="required" empty="请选择不按时报到原因"/]
            [/@]
        </ul>
        <div class="yx-jiange-1em"></div>
        <div class="yx-model-btnmodel">
            <input type="hidden" name="stationStudent.id" value="${(stationStudent.id)!}"/>
            <button type="button" class="yx-model-greenbtn submit">提交</button>
        </div>
    </form>
</div>
    [@m.validation/]
<script src="${base}/static/yxxt/mobile/scripts/form.js"></script>
<script>
    $(function () {
        jQuery.validator.addClassRules("v_station_num", {
            number: true,
            min: 0,
            max: 6,
        });
        $(".vehicle_select").change(function (e, station) {
            station = station || "";
            var id = this.value;
            $(".station_select").val(station).find("option").hide().each(function () {
                if (id == $(this).attr("data-vehicle-id")) {
                    $(this).show();
                }
            });
        }).trigger("change", "${(stationStudent.station)!}");
        $("[name='stationStudent.intime']").change(function (e, time) {
            time = time == undefined ? 500 : time;
            if (this.value == "1") {
                $(".intime_li").slideDown(time);
                $(".not_intime_li").slideUp(time);
            } else {
                $(".intime_li").slideUp(time);
                $(".not_intime_li").slideDown(time);
            }
        }).trigger("change", 0);
        $(".stationStudentForm").initForm();
    });
</script>
[/@]