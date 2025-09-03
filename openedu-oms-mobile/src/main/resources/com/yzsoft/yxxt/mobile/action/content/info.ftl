[#ftl]
[#import "/template/mobile.ftl" as m/]
[@m.body title=column.name back="back"]
<div class="ui-content" style="min-height: 100vh; background-color: #fff;">
    <div style="text-align: center;">
        <h3 style="margin: 0.3em 0;">${content.title}</h3>
        <p style="font-size: 0.8em; margin: 0.5em;">发布时间：<span class="date">${(content.publishTime?date)!}</span></p>
    </div>
    <style>
        .content_content img { display: block; width: 100%; margin: 0.5em 0; }
    </style>
    <style>
        .article { font-size: 0.8em; }
        .article > p { margin: 0; line-height: 1.5em !important; text-indent: 2em; }
        .article img { max-width: 100%; }
        .article table { border-collapse: collapse; border-spacing: 0; }
        .article table td { border: 1px solid #ccc; padding: 5px; }
        .article table td p { margin: 0; }
    </style>
    <div class="article">
    ${content.content!}
    </div>
    <script>
        $(".article img").each(function () {
            $(this).removeAttr("height").removeAttr("style");
            $(this).closest("p").css({"text-indent": 0});
        });
        $(".article p").each(function () {
            var firstChar = $(this).text().charAt(0);
            if (firstChar == " " || firstChar == "　") {
                $(this).css("text-indent", "0");
            }
        });
        $(".article table").each(function () {
            var tr = $("tr", this).eq(0);
            var tdNum = tr.find("td").length;
            if (tdNum <= 4) {
                tr.find("td").each(function () {
                    $(this).css({width: 100 / tdNum + "%"})
                });
            }
        });
    </script>
</div>
[/@]