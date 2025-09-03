[#ftl]
[@b.head/]
<div style="padding:20px 15px; background-color: #fff; border: 1px solid #ddd; border-top: none;">
    <h1 class="text-center">${(batch.name)!}</h1>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>地域</th>
            <th>男</th>
            <th>女</th>
        </tr>
        </thead>
        <tbody>
        [#list datas?reverse as v]
            <tr class="text-center">
                <td>${(v.name)!}</td>
                <td>${(v.maleNum)!}</td>
                <td>${(v.femaleNum)!}</td>
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
                        data: ['男', '女']
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
                    yAxis: {
                        type: 'category',
                        data: [[#list datas as v][#if v_index gt 0], [/#if]"${v.name}"[/#list]],
                    },
                    xAxis: {
                        type: 'value'
                    },
                    series: [
                        {
                            name: '男',
                            type: 'bar',
                            stack: '地域',
                            data: [[#list datas as v][#if v_index gt 0], [/#if] ${v.maleNum}[/#list]],
                        },
                        {
                            name: '女',
                            type: 'bar',
                            stack: '地域',
                            data: [[#list datas as v][#if v_index gt 0], [/#if] ${v.femaleNum}[/#list]],
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