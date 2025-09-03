(function () {
    var DormAlloc = {};
    DormAlloc.init = function (opts) {
        opts = $.extend({
            baseAction: "",
        }, opts);
        var zoneSelect = $("select.dorm_alloc_zone_select");
        var buildingSelect = $("select.dorm_alloc_building_select");
        var roomSelect = $("select.dorm_alloc_room_select");
        var bedSelect = $("select.dorm_alloc_bed_select");
        $.getJSON(opts.baseAction + "findZone", function (datas) {
            fileData(zoneSelect, datas);
        });
        cascadeSelect(zoneSelect, buildingSelect, opts.baseAction + "findBuilding");
        cascadeSelect(buildingSelect, roomSelect, opts.baseAction + "findRoom");
        cascadeSelect(roomSelect, bedSelect, opts.baseAction + "findBed");
    };


    function cascadeSelect(select1, select2, url) {
        if (select2.length == 0) {
            return;
        }
        select1.change(function () {
            if (select1.val() != "") {
                var datas = {};
                datas[select1.attr("name")] = select1.val();
                $.getJSON(url, datas, function (datas) {
                    fileData(select2, datas);
                });
            }
        });
    }

    function fileData(select, datas) {
        var title = select.data("title") || "请选择...";
        select.empty().append($("<option>").val("").text(title));
        for (var i in datas) {
            var data = datas[i];
            var option = $("<option>").val(data.id).text(data.name);
            if (data.code) {
                option.attr("data-code", data.code);
            }
            select.append(option);
        }
        select.find("option[value='" + select.data("value") + "']").first().prop("selected", true);
        if (select.val() != "") {
            select.trigger("change");
        }
    }

    this.DormAlloc = DormAlloc;
})();