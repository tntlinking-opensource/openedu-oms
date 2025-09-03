[#ftl]

[#macro webuploaderJsAndCss]
<link href="${base}/static/scripts/webuploader/webuploader.css" rel="stylesheet" type="text/css">
<script src="${base}/static/scripts/webuploader/webuploader.html5only.min.js" type="text/javascript"></script>
<script src="${base}/static/scripts/beangle/webuploader.js?v=20170705" type="text/javascript"></script>
[/#macro]
[#macro webuploaderJsAndCss2]
<link href="${base}/static/scripts/webuploader/webuploader.css" rel="stylesheet" type="text/css">
<script src="${base}/static/scripts/webuploader/webuploader.html5only.min.js" type="text/javascript"></script>
<script src="${base}/static/scripts/beangle/webuploader2.js" type="text/javascript"></script>
[/#macro]

[#macro uploadImg label value name="newImg" width=300 height=200 minWidth=100 minHeight=100 dir="imgs" help="" type="1"]
    [@b.field label=label ]
        [#if type == "1"]
            [@webuploaderJsAndCss/]
        [#else]
            [@webuploaderJsAndCss2/]
        [/#if]
    <style>
        .uploader-list img { margin: 0; }
        .thumbnail { padding: 0; border: none; }
    </style>
    <div class="row" style="padding: 0">
        [#if value != ""]
            <div class="col-xs-6" style="padding: 0">
                <img width="[#if minWidth > width]${width}[#else]${minWidth}[/#if]" src="${base}/common/download.action?uuid=${value}"/>
            </div>
        [/#if]
        <div class="col-xs-6" style="padding: 0">
            <input type="file" name="${name}" class="uploadImg"/>
        </div>
    </div>
    <input type="hidden" name="oldImg" value="${value}"/>
    <script type="text/javascript">
        $(function () {
            $("input.uploadImg").uploadImg({
                thumb: {
                    width: [#if minWidth > width]${width}[#else]${minWidth}[/#if],
                    height: [#if minHeight > height]${height}[#else]${minHeight}[/#if],
                    crop: true,
                },
                compress: {
                    width: ${width},
                    height: ${height},
                    crop: true,
                },
            });
        });
    </script>
    [/@]
[/#macro]