[#ftl]
[#macro ckeditor id name value]
<textarea id="${id}" name="${name}" class="ickeditor">${value}</textarea>
<script type="text/javascript" src="${base}/static/scripts/ckeditor/ckeditor.js"></script>
<script type="text/javascript">
    setTimeout(function () {
        CKEDITOR.replace("${id}", {baseFloatZIndex: 19900});
    }, 1)
</script>
[/#macro]

[#macro ckeditorSource id name value]
<textarea id="${id}" name="${name}" class="ickeditor">${value}</textarea>
<script type="text/javascript" src="${base}/static/scripts/ckeditor/ckeditor.js"></script>
<script type="text/javascript">
    setTimeout(function () {
        CKEDITOR.replace("${id}", {
            toolbar: [['NewPage', 'Preview', 'Maximize']],
            allowedContent: true,
            startupMode: 'source',
            shiftEnterMode: CKEDITOR.ENTER_P
        });
    }, 1)
</script>
[/#macro]

[#macro text label=""]
    [#if label != ""]
        [@b.field label=label][@fu.text][#nested /][/@][/@]
    [#else]
    <p class="form-control-static">[#nested /]</p>
    [/#if]
[/#macro]


[#macro select data=[] value=0 valueKey="id" nameKey="name" noOption=false empty="请选择..."  extra...]
<select [#list extra?keys as attr] ${attr}="${extra[attr]}"[/#list]>
<option value="">${empty}</option>
    [#list data as v]
        [#if noOption]
            [#nested v/]
        [#else]
        <option value="${v[valueKey]}" title="${v[nameKey]}"
                [#if value==v[valueKey]]selected=selected[/#if]>[@c.substring str=v[nameKey] mx=20/]</option>
        [/#if]
    [/#list]
</select>
[/#macro]

[#macro radios name value data  extra...]
    [#list data as v]
    <label class="radio-inline">
        <input type="radio" name="${name}"
               value="${v.id}" [#if v.id == value]checked[/#if]  [#list extra?keys as attr] ${attr}="${extra[attr]}"[/#list]/>
    ${v.name}
    </label>
    [/#list]
[/#macro]

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

[#macro uploadImg label value name="newImg" width=300 height=200 minWidth=100 minHeight=100 dir="imgs" help=""]
    [@uploadImgs label value name width height minWidth minHeight dir help/]
[/#macro]
[#macro uploadImg2 label value name="newImg" width=300 height=200 minWidth=100 minHeight=100 dir="imgs" help=""]
    [@uploadImgs label value name width height minWidth minHeight dir help "2"/]
[/#macro]

[#macro uploadImgs label value name="newImg" width=300 height=200 minWidth=100 minHeight=100 dir="imgs" help="" type="1"]
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
                [#if type == "1"]
                <img width="[#if minWidth > width]${width}[#else]${minWidth}[/#if]" src="${base}/common/download.action?uuid=${value}"/>
                [#else]
                <img width="[#if minWidth > width]${width}[#else]${minWidth}[/#if]" src="${base}/${value}"/>
                [/#if]
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

[#macro fileinput id name value folder type accept allowedFileExtensions uploadFile maxFileSize=5120 uuidId="uuid" uuidName="uuid" required="false"]
<script src="${base}/static/metronic/assets/global/plugins/bootstrap-fileinput/fileinput.js" type="text/javascript"></script>
<script src="${base}/static/metronic/assets/global/plugins/bootstrap-fileinput/locales/zh.js" type="text/javascript"></script>
<link href="${base}/static/metronic/assets/global/plugins/bootstrap-fileinput/css/fileinput.min.css" rel="stylesheet" type="text/css" />

<div class="fileinput fileinput-new" data-provides="fileinput">
 	<input type="file" id="${id!}" name="${name!}" accept="${accept!}"> 
 	请上传后缀名为${(allowedFileExtensions?replace("'",""))!},并且小于${(maxFileSize/1024)!}M的文件
</div>
<div id="${id!}kv-avatar-errors" class="center-block" style="display:none"></div>
<input type="hidden" id="${uuidId!}" name="${uuidId!}" value="${(value)!}" />
	    
<script>
	[#if uploadFile??&&uploadFile.id??]
	   var preList = new Array();
	   //判断是否图片类型
	   [#assign extension=(uploadFile.extension?string)!]
	   [#if extension=='jpg'||extension=='png']
	   		preList[0]= "<img src='${base}/common/download.action?uuid=${(uploadFile.uuid)!}' class='file-preview-image' style='width:160px;'>"  
	   [#else]
	   		preList[0]= "<div class='kv-preview-data file-preview-other-frame'><div class='file-preview-other'><span class='file-other-icon'><i class='glyphicon glyphicon-file'></i></span></div></div>"  
	   [/#if]
	   var previewJson = preList;
	   var preConfigList = new Array();
	   var tjson = {caption: "${(uploadFile.name)!}", // 展示的文件名  
		               width: '120px',   
		               //url: '${base}/common/file/remove', // 删除url  
		               key: "${(uploadFile.uuid)!}", // 删除是Ajax向后台传递的参数  
		               extra: {id: 100}  
	               };  
	   preConfigList[0] = tjson;
	[/#if]
    $("#${id!}").fileinput({
    	language: 'zh', //设置语言
    	dropZoneEnabled:false,
    	uploadUrl: "${base}/common/file!upload.action?folder=${(folder)!}&type=${type}", //上传的地址
	    uploadAsync: true,
	    overwriteInitial: true,
	    maxFileSize: ${(maxFileSize)!},//单位kb
	    maxFileCount:1,
	    showUpload: false, 
	    showClose: false,
	    showCaption: false,
	    showBrowse: true,
	    browseOnZoneClick: true,
	    initialPreviewShowDelete:false,//预览是否显示删除
	    removeLabel: '',
	    removeIcon: '<i class="glyphicon glyphicon-remove"></i>',
	    elErrorContainer: '#${id!}kv-avatar-errors',
	    msgErrorClass: 'alert alert-block alert-danger',
	    [#if uploadFile??&&uploadFile.id??]
		    	initialPreview: previewJson,
	    		initialPreviewConfig: preConfigList,
	    [/#if]
	    layoutTemplates: {main2: '{preview} {remove} {browse}'},
	    allowedPreviewTypes : [ 'image' ],
	    allowedFileExtensions: [${(allowedFileExtensions)!}]
	}).on("filebatchselected", function(event, files) {
            $(this).fileinput("upload");
        })
        .on("fileuploaded", function(event, res) {
        if(res.response)
        {
        	var data = res.response;
        	$("#${uuidId!}").val(data.uuid);
        }
    });
    
    $('#${id!}').on('filepreremove', function(event, id, index) {
    	 $("#${uuidId!}").val("");
    });
    $("#${id!}").on('fileclear', function(event) {
        $("#${uuidId!}").val("");
    });
</script>
[/#macro]

[#macro fileinputs id name value folder type accept allowedFileExtensions uploadFiles maxFileSize=5120 maxFileCount=1]
<script src="${base}/static/metronic/assets/global/plugins/bootstrap-fileinput/fileinput.js" type="text/javascript"></script>
<script src="${base}/static/metronic/assets/global/plugins/bootstrap-fileinput/locales/zh.js" type="text/javascript"></script>
<link href="${base}/static/metronic/assets/global/plugins/bootstrap-fileinput/css/fileinput.min.css" rel="stylesheet" type="text/css" />

<div class="fileinput fileinput-new" data-provides="fileinput">
 	<input type="file" id="${id!}" multiple name="${name!}" accept="${accept!}">
 	支持${(allowedFileExtensions?replace("'",""))!}格式，大小不超过${(maxFileSize/1024)!}M
</div>
<div id="${id!}kv-avatar-errors" class="center-block" style="display:none"></div>
<input type="hidden" id="uuid" name="uuid" value="${(value)!}" />
	    
<script>
	var preList = new Array();
	var preConfigList = new Array();
	[#assign pn=0]
	[#list uploadFiles as uploadFile]
		[#if uploadFile??&&uploadFile.id??]
		   //判断是否图片类型
		   [#assign extension=uploadFile.extension?string]
		   [#if extension=='jpg'||extension=='png']
		   		preList[${pn!}]= "<input type='hidden' name='uuids' value='${(uploadFile.uuid)!}'><img src='${base}/common/download.action?uuid=${(uploadFile.uuid)!}' class='file-preview-image' style='height:160px;width:auto'>"  
		   [#else]
		   		preList[${pn!}]= "<input type='hidden' name='uuids' value='${(uploadFile.uuid)!}'><div class='kv-preview-data file-preview-other-frame'><div class='file-preview-other'><span class='file-other-icon'><i class='glyphicon glyphicon-file'></i></span></div></div>"  
		   [/#if]
		   var tjson = {caption: "${(uploadFile.name)!}", // 展示的文件名  
			               width: '120px',   
			               url: '', // 删除url
			               key: "", // 删除是Ajax向后台传递的参数  
			               extra: {id: 100}
		               };  
		   preConfigList[${pn!}] = tjson;
		[/#if]
		[#assign pn=pn+1]
	[/#list]
	var previewJson = preList;
	
    $("#${id!}").fileinput({
    	language: 'zh', //设置语言
    	dropZoneEnabled:false,
    	uploadUrl: "${base}/common/file!upload.action?folder=${(folder)!}&type=${type}", //上传的地址
    	uploadAsync: true,
	    overwriteInitial: false,
	    maxFileSize: ${(maxFileSize)!},//单位kb
	    maxFileCount: ${(maxFileCount)!}, //表示允许同时上传的最大文件个数
	    validateInitialCount:false,
	    previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
	    showUpload: false, 
	    showClose: false,
	    showCaption: false,
	    browseOnZoneClick: true,
	    elErrorContainer: '#${id!}kv-avatar-errors',
	    msgErrorClass: 'alert alert-block alert-danger',
	    initialPreview: previewJson,
		initialPreviewConfig: preConfigList,
	    layoutTemplates: {main2: '{preview} {remove} {browse}'},
	    allowedPreviewTypes : [ 'image' ],
	    allowedFileExtensions: [${(allowedFileExtensions)!}]
	}).on("filebatchselected", function(event, files) {
			//fileinput.js3070行增加了数量验证
			$(this).fileinput("upload");
        })
        .on("fileuploaded", function(event, res, previewId, index) {
        if(res.response)
        {
        	var data = res.response;
        	var $uuidN=$("<input type='hidden' name='uuids' value='"+data.uuid+"'>");
        	$("#"+previewId).append($uuidN);
        	changeUUID();
        }
    });
    $('#${id!}').on('filepreremove', function(event, id, index) {
    	 setTimeout(function () { changeUUID();}, 1000);
    });
    $("#${id!}").on('fileclear', function(event) {
        $("#uuid").val("");
    });
    function changeUUID(){
    	var uuids = [];
    	$("input[name^='uuids']").each(function(i, o){
    		uuids[i] = $(o).val();
    	});
    	$("#uuid").val(uuids);
    }
    
    $(function () {
        $(".kv-file-remove").click(function () {
        	var div_ = $(this).parent().parent().parent().parent();
        	$("#zoom-"+div_.attr("id")).parent().remove();
            div_.remove();
        });
    });
</script>
[/#macro]