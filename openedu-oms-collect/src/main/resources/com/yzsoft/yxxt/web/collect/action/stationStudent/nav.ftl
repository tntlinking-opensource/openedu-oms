[#ftl]
[@b.head/]
<div style="padding:20px 15px; background-color: #fff;">
    <div>
        年级：
        <select class="grade_select" style="height: 34px; vertical-align: middle;">[#list grades as v]
            <option>${v}</option>[/#list]
        </select>
        <a href="${b.url("!print")}&grade=${grades[0]}" class="btn btn-info id_print_btn"
           target="_blank">打印</a>
        <a href="${b.url("!exportExcel")}&grade=${grades[0]}" class="btn btn-info id_export_btn"
           target="_blank">导出</a>
        <button type="button" class="btn btn-primary pull-right" onclick="$('.search_items .searchButton').click();">
            返回
        </button>
    </div>
    <hr/>
    <script>
        $(".grade_select").change(function () {
            bg.Go("${b.url("!count")}&grade=" + this.value, "station_count_div");
            $(".id_print_btn").attr("href", "${b.url("!print")}?&grade=" + this.value);
            $(".id_export_btn").attr("href", "${b.url("!exportExcel")}?&grade=" + this.value);
        });
    </script>
    [@b.div id="station_count_div" href="!count?grade=${grades[0]}"/]
</div>
[@b.foot/]