(function () {
    $.fn.mcmore = function (opts) {
        var div = this, page = 0, moreDiv;
        opts.dir = opts.dir || "down";
        opts.click = opts.click == undefined ? true : false;
        function more() {
            page++;
            if (opts.click) {
                moreDiv = div.find(".more_div");
                moreDiv.addClass("loading_div").find("p").html("加载中...");
            }
            var params = {};
            opts.params && opts.params(params);
            $.post(opts.url + "&pageNo=" + page, params, function (html) {
                html = $(html).hide();
                if (opts.dir == "down") {
                    div.append(html);
                } else {
                    div.prepend(html);
                }
                html.slideDown();
                if (opts.click) {
                    moreDiv.remove();
                }
                opts.success && opts.success();
            });
        }

        if (opts.click) {
            div.on("click", ".more_div", function () {
                more();
            });
        }
        more();
    };
})();