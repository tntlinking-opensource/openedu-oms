[#ftl]
[#include "/com/yzsoft/yxxt/web/collect/action/comm/macro.ftl"/]
[#import "/template/form/utils.ftl" as fu/]
[@index code="G042"]
    [@c.colorboxJsAndCss/]
    <style>
        .table {
            margin-bottom: 0;
        }

        #cboxTitle {
            height: auto;
        }
    </style>
    <h3 class="caption">床位选择</h3>
    <div style="margin: 15px;">
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>宿舍楼</th>
                <th>寝室号</th>
[#--                <th>朝向</th>--]
                <th>床位数</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            [#list rooms as v]
                [#assign num=0]
                [#list dormPlanBeds as dormPlanBed]
                    [#if (dormPlanBed.bed.room.id)=(v.id)&&!(dormPlanBed.bed.student)??]
                        [#assign num=num+1]
                    [/#if]
                [/#list ]
                [#if num!=0]
                <tr class="text-center">
                    <td>${(v.building.name)!}</td>
                    <td>${(v.name)!}</td>
[#--                    <td>${(v.direction.name)!}</td>--]
                    <td>${num!0}</td>
                    <td>
                        <button class="btn btn-primary btn_select_bed" onclick ="alert('选择完成后就不可更改宿舍，请确认!')" data-id="${v.id}">选择床位</button>
                    </td>
                </tr>
                [/#if]
            [/#list]
            </tbody>
        </table>
    </div>
    <script>
        $(function () {
            $(".btn_select_bed").click(function () {
                var dormRoomId = $(this).data("id");
                $.colorbox({
                    title: '选择床位',
                    href: '${b.url("!bedSelect")}&dormRoomId=' + dormRoomId,
                    iframe: true,
                    width: "820px",
                    height: "550px",
                });
            });
        });
    </script>
[/@index]
