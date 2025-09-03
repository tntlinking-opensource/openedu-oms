[#ftl]
[@b.head/]
<div style="padding:20px 15px; background-color: #fff; border: 1px solid #ddd; border-top: none;">
    <h1 class="text-center">${(batch.name)!}</h1>
    <table class="table table-bordered">
        <tbody>
        <tr>
            <th class="text-right" width="20%">录取总人数</th>
            <th width="13%">${studentNum!0}</th>
            <th class="text-right" width="20%">已报到人数</th>
            <th width="13%">${completeNum!0}</th>
            <th class="text-right" width="20%">未报到人数</th>
            <th width="13%">${unCompleteNum!0}</th>
        </tr>
        </tbody>
    </table>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th rowspan="2" width="25%">环节</th>
            <th colspan="3">已办理</th>
            <th colspan="3">未办理</th>
        </tr>
        <tr>
            <th>男</th>
            <th>女</th>
            <th>合计</th>
            <th>男</th>
            <th>女</th>
            <th>合计</th>
        </tr>
        </thead>
        <tbody>
        [#list datas as v]
            <tr class="text-center">
                <td>${(v.name)!}</td>
                <td>${(v.enabledMaleNum)!}</td>
                <td>${(v.enabledFemaleNum)!}</td>
                <td>${(v.enabledNum)!}</td>
                <td>${(v.disabledMaleNum)!}</td>
                <td>${(v.disabledFemaleNum)!}</td>
                <td>${(v.disabledNum)!}</td>
            </tr>
        [/#list]
        </tbody>
    </table>
    <div>
        <div id="echart" style="width:1000px; height: 400px; margin: auto;"></div>
        <script type="text/javascript">
            $(function () {
                // 基于准备好的dom，初始化echarts实例
                var myChart = echarts.init(document.getElementById('echart'));
                // 指定图表的配置项和数据
                var option = {
                    color: ['#0066B2', '#FF3300', '#6CAAD9', '#FF9980'],
                    toolbox: {
                        show: true,
                        feature: {
                            saveAsImage: {show: true}
                        }
                    },
                    legend: {
                        data: ['已报到：男', '已报到：女', '未报到：男', '未报到：女']
                    },
                    calculable: true,
                    grid: {
                        borderWidth: 0,
                        y: 50,
                        y2: 50
                    },
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                            type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                        }
                    },
                    yAxis: [
                        {
                            type: 'value'
                        }
                    ],
                    xAxis: [
                        {
                            type: 'category',
                            data: [[#list datas as v][#if v_index gt 0], [/#if]"${v.name}"[/#list]],
                            axisTick: {
                                alignWithLabel: true
                            },
                            axisLabel: {
                                interval: 0
                            }
                        }
                    ],
                    series: [
                        {
                            name: '已报到：男',
                            type: 'bar',
                            stack: '已报到',
                            data: [[#list datas as v][#if v_index gt 0], [/#if] ${v.enabledMaleNum}[/#list]],
                        },
                        {
                            name: '已报到：女',
                            type: 'bar',
                            stack: '已报到',
                            data: [[#list datas as v][#if v_index gt 0], [/#if] ${v.enabledFemaleNum}[/#list]],
                        },
                        {
                            name: '未报到：男',
                            type: 'bar',
                            stack: '未报到',
                            data: [[#list datas as v][#if v_index gt 0], [/#if] ${v.disabledMaleNum}[/#list]],
                        },
                        {
                            name: '未报到：女',
                            type: 'bar',
                            stack: '未报到',
                            data: [[#list datas as v][#if v_index gt 0], [/#if] ${v.disabledFemaleNum}[/#list]],
                        },
                    ]
                };
                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
            });
        </script>
    </div>
</div>
[@b.foot/]