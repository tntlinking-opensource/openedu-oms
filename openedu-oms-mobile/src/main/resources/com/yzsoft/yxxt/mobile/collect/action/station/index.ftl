[#ftl]
[#import "/template/mobile.ftl" as m/]
[@m.body title=switch.name back=b.url("index?t=1")]
<div class="yx-xxtx">
    <ul data-role="listview" class="yx-xxtx-info">
        <li>
            <div class="yx-xxtx-info-a">
                <span class="yx-xxtx-info-a-left">到达日期</span>
                <span class="yx-xxtx-info-a-right">${(stationStudent.arriveDate?date)!}</span>
            </div>
        </li>
        <li>
            <div class="yx-xxtx-info-a">
                <span class="yx-xxtx-info-a-left">是否按时报到</span>
                <span class="yx-xxtx-info-a-right">${(stationStudent.intime?string("是", "否"))!}</span>
            </div>
        </li>
        [#if true == (stationStudent.intime)!false]
            <li>
                <div class="yx-xxtx-info-a">
                    <span class="yx-xxtx-info-a-left">是否需要接站</span>
                    <span class="yx-xxtx-info-a-right">${(stationStudent.needPickup?string("是", "否"))!}</span>
                </div>
            </li>
            <li>
                <div class="yx-xxtx-info-a">
                    <span class="yx-xxtx-info-a-left">到达时间</span>
                    <span class="yx-xxtx-info-a-right">${(stationStudent.arriveTime)!}</span>
                </div>
            </li>
            <li>
                <div class="yx-xxtx-info-a">
                    <span class="yx-xxtx-info-a-left">交通工具</span>
                    <span class="yx-xxtx-info-a-right">${(stationStudent.vehicle.name)!}</span>
                </div>
            </li>
            <li>
                <div class="yx-xxtx-info-a">
                    <span class="yx-xxtx-info-a-left">到达站</span>
                    <span class="yx-xxtx-info-a-right">${(stationStudent.station)!}</span>
                </div>
            </li>
            <li>
                <div class="yx-xxtx-info-a">
                    <span class="yx-xxtx-info-a-left">陪同人数</span>
                    <span class="yx-xxtx-info-a-right">${(stationStudent.num)!}</span>
                </div>
            </li>
        [#else]
            <li>
                <div class="yx-xxtx-info-a">
                    <span class="yx-xxtx-info-a-left">不按时报到原因</span>
                    <span class="yx-xxtx-info-a-right">${(stationStudent.reason)!}</span>
                </div>
            </li>
        [/#if]
    </ul>
    [#if switch?? && switch.editable]
        <div class="yx-jiange-1em"></div>
        <div class="yx-model-btnmodel">
            <a href="${b.url("!edit")}" data-role="button" class="yx-model-bluebtn">修改</a>
        </div>
    [/#if]
</div>
[/@]