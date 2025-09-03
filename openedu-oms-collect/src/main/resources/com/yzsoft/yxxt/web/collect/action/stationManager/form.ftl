[#ftl]
[@b.head/]
[@b.toolbar title="到达站" entityId=station.id!0]bar.addBack();[/@]
[@b.form action="!save" theme="form"]
    [@b.select label="交通工具" name="station.vehicle.id" value=(station.vehicle.id)! items=vehicles
        required="true" option="id,name" empty="请选择..."/]
    [@b.textfield label="站名" name="station.name" value="${station.name!}" required="true" maxlength="30"/]
    [@b.formfoot]
	<input type="hidden" name="station.id" value="${station.id!}"/>
        [@b.redirectParams/]
        [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
[/@]
[@b.foot/]