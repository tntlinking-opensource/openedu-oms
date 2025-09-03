(function () {
	$.fn.loadProvince = function () {
		this.each(function () {
			getJSON(base + "/core/district-select.action?method=province", $(this));
		});
		return this;
	};
	$.fn.loadCity = function (provinceId) {
		this.each(function () {
			getJSON(base + "/core/district-select.action?method=city&id=" + provinceId, $(this));
		});
		return this;
	};
	$.fn.loadCounty = function (cityId) {
		this.each(function () {
			getJSON(base + "/core/district-select.action?method=county&id=" + cityId, $(this));
		});
		return this;
	};
	$.fn.cascadeCity = function (city) {
		var citySelect = $(city);
		cascadeSelect(this, citySelect, base + "/core/district-select.action?method=city");
		return citySelect;
	};
	$.fn.cascadeCounty = function (area) {
		var areaSelect = $(area);
		cascadeSelect(this, areaSelect, base + "/core/district-select.action?method=county");
		return areaSelect;
	};

	$.fn.cascadeSelect = function (select2, url) {
		cascadeSelect(this, $(select2), url);
		return this;
	};

	function getJSON(url, select) {
		$.getJSON(url, function (datas) {
			fileData(select, datas);
			select.trigger("change");
		});
	}

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
		var value = select.val() || select.attr("datavalue");
		select.empty().append($("<option>").val("").text(title));
		for (var i in datas) {
			var data = datas[i];
			var option = $("<option>").val(data.id).text(data.name);
			if (data.code) {
				option.attr("data-code", data.code);
			}
			select.append(option);
		}
		// select.val(value);
		select.find("option[value='" + value + "']").first().prop("selected", true);
		select.trigger("change");
		if (select.selectmenu) {
			select.selectmenu('refresh', true);
		}
	}
})();