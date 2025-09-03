(function () {
    $.extend({
        majorSetting: {
            baseurl: base + "/core/student-select.action?method=",
        }
    });
    $.fn.loadCampus = function (city) {
        var select = this;
        select.attr("title", "校区");
        $.getJSON($.majorSetting.baseurl + "campus", function (datas) {
            fileData(select, datas);
            select.trigger("change");
        });
        return this;
    };
    $.fn.cascadeDepartment = function (department) {
        var departmentSelect = $(department);
        departmentSelect.attr("title", "院系");
        cascadeSelect(this, departmentSelect, $.majorSetting.baseurl + "department");
        return departmentSelect;
    };
    $.fn.loadDepartment = function () {
        var select = this;
        select.attr("title", "院系");
        $.getJSON($.majorSetting.baseurl + "department", function (datas) {
            fileData(select, datas);
            // select.trigger("change");
        });
        return this;
    };
    $.fn.cascadeMajor = function (major) {
        var majorSelect = $(major);
        cascadeSelect(this, majorSelect, $.majorSetting.baseurl + "major");
        return majorSelect;
    };
    $.fn.cascadeDirection = function (direction) {
        var directionSelect = $(direction);
        cascadeSelect(this, directionSelect, $.majorSetting.baseurl + "direction");
        return directionSelect;
    };
	$.fn.cascadeAdminClass = function (adminClass) {
		var adminClassSelect = $(adminClass);
		cascadeSelect(this, adminClassSelect, $.majorSetting.baseurl + "adminClass");
		return adminClassSelect;
	};
    $.fn.loadEducation = function () {
        var select = this;
        select.attr("title", "学历层次");
        $.getJSON($.majorSetting.baseurl + "education", function (datas) {
            fileData(select, datas);
        });
        return this;
    };
    $.fn.cascadeSelect = function (select2, url) {
        $(this).addClass("cascade");
        cascadeSelect(this, $(select2), url);
        return this;
    };
    function cascadeSelect(select1, select2, url) {
        select1.change(function () {
            if (select1.val() != "") {
                $.getJSON(url, {id: select1.val()}, function (datas) {
                    fileData(select2, datas);
                });
            } else {
                select2.val("");
            }
            return false;
        });
    }

    function fileData(select, datas) {
        var title = select.attr("title") || "请选择...";
        var value = select.val() || select.attr("data-val");
        select.removeAttr("data-val")
        select.empty().append($("<option>").val("").text(title));
        for (var i in datas) {
            var data = datas[i];
            var option = $("<option>").val(data.id).text(data.name);
            if (data.code) {
                option.attr("data-code", data.code);
            }
            select.append(option);
        }
        if (value != "" && value != "0") {
            console.log("value", value);
            select.val(value);
            select.trigger("change");
        }
    }
})();