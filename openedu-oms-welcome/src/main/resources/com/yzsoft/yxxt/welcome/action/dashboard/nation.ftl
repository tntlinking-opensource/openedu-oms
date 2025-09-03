[#ftl]
<div id="nationTop5" style="height: 210px;"></div>
<script>
    $(function () {
        var myChart = echarts.init(document.getElementById('nationTop5'));
        var option = {
            color: ["#46E7FE", '#80A1E9', '#E2E17D', '#E56564', '#FBF4E0'],
            series: [
                {
                    type: 'pie',
                    radius: ['50%', '70%'],
                    label: {
                        normal: {
                            formatter: '{b}  {d}%  ',
                        }
                    },
                    data: [
                        [#list datas as v]
                        {value: ${v.completeNum}, name: '${v.nationName}'},
                        [/#list]
                    ]
                }
            ]
        };
        myChart.setOption(option);
    });
</script>