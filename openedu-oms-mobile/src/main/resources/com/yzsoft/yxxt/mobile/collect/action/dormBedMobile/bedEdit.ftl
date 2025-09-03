[#ftl]
[#import "/template/comm.ftl" as c/]
[#import "/template/mobile.ftl" as m/]
[#import "/template/form/utils.ftl" as fu/]
[@m.body title=switch.name back=b.url("!index")]
    <style>
        button.btn_select_bed {
            background-color: #4694d2;
        }
    </style>
    <div style="background-color: #fff;">
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>宿舍楼</th>
                <th>寝室号</th>
                <th>上/下铺</th>
                <th>床位号</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            [#list dormPlanBeds as v]
                <tr align="center">
                    <td>${(v.bed.room.building.name)!}</td>
                    <td>${(v.bed.room.name)!}</td>
                    <td>${(v.bed.bunkBed.name)!}</td>
                    <td>${(v.bed.name)!}</td>
                    <td style="width: 125px">
                        <button class="btn btn-small btn_select_bed" onclick ="alert('选择完成后就不可更改宿舍，请确认!')" data-id="${v.id}">选择床位</button>
                    </td>
                </tr>
            [/#list]
            </tbody>
        </table>
    </div>
    <script>
        $(function () {
            $(".btn_select_bed").click(function () {
                var dormPlanBedId = $(this).data("id");
                $.post("${b.url("!bedSave")}", {
                    "dormPlanBedId": dormPlanBedId,
                }, function (data) {
                    if (data.error) {
                        alert(data.error);
                        location.reload();
                    } else {
                        // alert("操作成功");
                        location.href = "${b.url("!index")}";
                    }
                }, "json");
            });
        });
    </script>
[/@]