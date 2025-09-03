(function () {
    jQuery.extend({
        columnSearch: function (select1, select2) {
            select1 = $(select1).data("title", "一级栏目");
            select2 = $(select2).data("title", "二级栏目");
            var columns;
            findColumns(function (datas) {
                columns = datas;
                fileData(select1, findColumn(columns, ""));
            });
            select1.change(function () {
                if (select1.val() != "") {
                    fileData(select2, findColumn(columns, select1.val()));
                } else {
                    select2.find("option").slice(1).remove();
                    select2.val("");
                }
                return false;
            });
        }
    });
    jQuery.fn.extend({
        columnSelect: function (ipt) {
            ipt = $(ipt);
            var columns = null, div = this;
            findColumns(function (datas) {
                columns = datas;
                initSelect(ipt.val());
            });
            function initSelect(id) {
                var column = getColumn(id);
                if (column.parentId) {
                    initSelect(column.parentId);
                }
                var datas = findColumn(columns, column.parentId);
                var select = createSelect(datas);
                select.val(id);
                div.append(select);
            }

            function getColumn(id) {
                for (var i in columns) {
                    var column = columns[i];
                    if (column.id == id) {
                        return column;
                    }
                }
                return {parentId: ""};
            }

            function createSelect(datas) {
                var select = $("<select class='form-control' required>");
                select.append('<option value="">--请选择--</option>');
                for (var i in datas) {
                    var data = datas[i];
                    var option = $("<option>");
                    option.val(data.id).text(data.name);
                    select.append(option);
                }
                select.change(function () {
                    var val = $(this).val();
                    if (val != "") {
                        $(this).nextAll("select").remove();
                        var datas = findColumn(columns, val);
                        ipt.val("");
                        if (datas.length > 0) {
                            var select = createSelect(datas);
                            div.append(select);
                        } else {
                            ipt.val(val);
                        }
                    }
                });
                return select;
            }

            return this;
        }
    });
    function findColumns(fn) {
        $.getJSON(base + "/web/manager/content!findColumn.action", function (datas) {
            fn(datas);
        });
    }

    function findColumn(columns, parentId) {
        var datas = [];
        for (var i in columns) {
            var column = columns[i];
            if (column.parentId == parentId) {
                datas.push(column);
            }
        }
        return datas;
    }

    function fileData(select, datas) {
        var title = select.data("title") || "请选择...";
        select.empty().append($("<option>").val("").text(title));
        for (var i in datas) {
            var data = datas[i];
            var option = $("<option>").val(data.id).text(data.name);
            select.append(option);
        }
        select.find("option[value='" + select.data("data-value") + "']").first().prop("selected", true);
    }
})();