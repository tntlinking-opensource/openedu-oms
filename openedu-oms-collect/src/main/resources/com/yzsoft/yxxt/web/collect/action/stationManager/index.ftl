[#ftl]
[@b.head/]

[@b.form name="searchForm"  action="!search" target="stationList" title="ui.searchForm" theme="search"]
    [@b.select label="交通工具" name="station.vehicle.id" items=vehicles/]
    [@b.textfield label="站名" name="station.name"/]
[/@]

[@b.div id="stationList" href="!search" /]
[@b.foot/]