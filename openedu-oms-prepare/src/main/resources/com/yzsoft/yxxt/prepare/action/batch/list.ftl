[#ftl]
[@b.head/]
[@b.grid  items=batchs var="batch"]
    [@b.gridbar]
    bar.addItem("新增",action.add());
    bar.addItem("修改",action.edit());
    bar.addItem("删除",action.remove());
    [/@]
    [@b.row align="center"]
        [@b.boxcol width="1%"/]
        [@b.col title="名称" property="name"/]
        [@b.col title="学年" property="year"/]
        [@b.col title="学期" property="term.name" /]
        [@b.col title="开始日期" property="startTime"]${(batch.startTime?date)!}[/@]
        [@b.col title="结束日期" property="endTime"]${(batch.endTime?date)!}[/@]
        [@b.col title="状态"]
            [#assign status = batch.status/]
            [#if status == "进行中"]
            <span style="color: green;">进行中</span>
            [#elseif status == "已结束"]
            <span style="color: red;">已结束</span>
            [#else]
            <span style="color: #999;">未开始</span>
            [/#if]
        [/@]
        [@b.col title="备注" property="remark" align="left"]${(batch.remark?replace("\n", "<br/>"))!}[/@]
    [/@]
[/@]
[@b.foot/]