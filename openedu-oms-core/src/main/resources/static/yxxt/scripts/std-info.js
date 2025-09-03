(function () {
	function StdInfo() {
		var stdInfo = this, teachCalenderSelect = null, gradeSelect = null, departmentSelect = null, majorSelect = null, adminClassSelect = null;
		this.setTeachCalender = function (selector) {
			teachCalenderSelect = $(selector);
		};
		this.setGrader = function (selector) {
			gradeSelect = $(selector);
		};
		this.setDepartment = function (selector) {
			departmentSelect = $(selector);
		};
		this.setMajor = function (selector) {
			majorSelect = $(selector);
		};
		this.setAdminClass = function (selector) {
			adminClassSelect = $(selector);
		};
		this.init = function () {
			initTeachCalender();
			initGrade();
			initDepartment();
			initMajor();
		};
		function initTeachCalender() {
			if (teachCalenderSelect) {
				teachCalenderSelect.change(function () {
					if (gradeSelect) {
						gradeSelect.empty().append(emptyOption());
						if (teachCalenderSelect.val() != "") {
							var year = teachCalenderSelect.find("option:selected").text();
							console.log("year", year);
							if (year) {
								year = year.substring(0, year.indexOf("-")) * 1;
								for (var i = 0; i < 6; i++) {
									gradeSelect.append(option(year - i));
								}
							}
						}
					}
					return false;
				});
			}
		}

		function initDepartment() {
			initSelect(departmentSelect, majorSelect, base + "/student/common/student-select!major.action")
		}

		function initMajor() {
			if (majorSelect) {
				majorSelect.change(function () {
					findAdminClass();
				});
			}
		}

		function initGrade() {
			if (gradeSelect) {
				gradeSelect.change(function () {
					findAdminClass();
				});
			}
		}

		function findAdminClass() {
			if (majorSelect && gradeSelect && adminClassSelect) {
				var params = {};
				console.log("findAdminClass");
				params.grade = gradeSelect.val();
				params.majorCode = majorSelect.find(":selected").attr("data-code");
				if (params.grade && params.majorCode) {
					$.getJSON(base + "/student/common/student-select!adminClass.action", params, function (datas) {
						for (var i in datas) {
							var data = datas[i];
							adminClassSelect.append(option(data.name, data.value));
						}
					});
				}
			}
		}

		function initSelect(select1, select2, url, params) {
			if (select1) {
				select1.change(function () {
					if (select2) {
						select2.empty().append(emptyOption());
						if (select1.val() != "") {
							params = params || {};
							params.term = select1.val();
							$.getJSON(url, params, function (datas) {
								for (var i in datas) {
									var data = datas[i];
									select2.append(option(data.name, data.value).attr("data-code", data.code));
								}
							});
						}
					}
					return false;
				});
			}
		}

		function emptyOption() {
			return '<option value="">请选择</option>';
		}

		function option(text, val) {
			val = val == undefined ? text : val;
			return $('<option value=""></option>').val(val).text(text);
		}
	}

	window.StdInfo = StdInfo;
})();