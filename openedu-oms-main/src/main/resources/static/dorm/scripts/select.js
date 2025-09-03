(function () {
    $.fn.dormLoadCampus = function (zone) {
        var select = this;
        $.getJSON(base + "/dorm/common/dorm-select.action?method=campus", function (datas) {
            fileData(select, datas);
            if (zone) {
                select.trigger("change");
            }
        });
        if (zone) {
            var zoneSelect = $(zone);
            $(this).change(function () {
                if (select.val() != "") {
                    $.getJSON(base + "/dorm/common/dorm-select.action?method=zone", {id: select.val()}, function (datas) {
                        fileData(zoneSelect, datas);
                    });
                }
            });
        }
        return this;
    };
    $.fn.dormLoadDepartments = function () {
        loadOption(this, base + "/dorm/common/dorm-select.action?method=department");
		return this;
    };
    $.fn.dormCascadeZone = function (zone) {
        cascadeSelect(this, $(zone), base + "/dorm/common/dorm-select.action?method=zone");
        return this;
    };
    $.fn.dormCascadeBuilding = function (building) {
        cascadeSelect(this, $(building), base + "/dorm/common/dorm-select.action?method=building");
        return this;
    };
    $.fn.dormCascadeFloor = function (floor) {
        cascadeSelect(this, $(floor), base + "/dorm/common/dorm-select.action?method=floor");
        return this;
    };
    $.fn.dormCascadeRoom = function (room) {
        cascadeSelect(this, $(room), base + "/dorm/common/dorm-select.action?method=room");
        return this;
    };
    
    $.fn.dormCascadeMajor = function (major) {
        cascadeSelect(this, $(major), base + "/dorm/common/dorm-select.action?method=major");
        return this;
    };
    
    $.fn.dormCascadeBuildingByParames = function (building,parames) {
        cascadeSelect(this, $(building), base + "/dorm/common/dorm-select.action?method=queryBuildings&"+parames);
        return this;
    };
    
    $.fn.dormCascadeRoomByParames = function (room,parames) {
        cascadeSelect(this, $(room), base + "/dorm/common/dorm-select.action?method=queryRooms&"+parames);
        return this;
    };
    
    $.fn.dormCascadeDormPlanByParames = function (room,parames) {
        cascadeSelect(this, $(room), base + "/dorm/common/dorm-select.action?method=queryDormPlans&"+parames);
        return this;
    };

    $.fn.cascadeSelect = function (select2, url) {
        cascadeSelect(this, $(select2), url);
        return this;
    };

    function cascadeSelect(select1, select2, url) {
        select1.change(function () {
            if (select1.val() != "") {
                $.getJSON(url, {id: select1.val()}, function (datas) {
                    fileData(select2, datas);
                });
            }
        });
    }

	function loadOption(select, url){
		$.getJSON(url, function (datas) {
			fileData(select, datas);
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
})();