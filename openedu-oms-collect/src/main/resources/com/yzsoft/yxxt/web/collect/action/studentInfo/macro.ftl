[#ftl]
[#import "/template/form/utils.ftl" as fu/]
[#assign text = "com.yzsoft.yxxt.web.collect.freemarker.Text"?new()/]

[#macro hasConfig type]
    [#assign result = false/]
    [#list configs as config]
        [#if config.type.id == type.id]
            [#assign result = true/]
            [#break /]
        [/#if]
    [/#list]
    [#if result]
        [#nested /]
    [/#if]
[/#macro]

[#macro propertyInfo config]
<div class="col-sm-6" style="line-height: 30px;">
    <div class="col-sm-6" style="text-align: right;">
        ${(config.name)!}
    </div>
    <div class="col-sm-6">
        [@propertyInfoValue config/]
    </div>
</div>
[/#macro]

[#macro propertyInfoValue config]
    [#if config.property.property == "student.photo"]
    <div style="height: 100px; overflow: hidden;">
        [#if student.photo??]
            <img style="max-height: 100px;" src="${base}/common/download.action?uuid=${(student.photo)!}"/>
        [/#if]
    </div>
    [#elseif  config.property.property == "studentEnroll.photo"]
    <div style="height: 100px; overflow: hidden;">
        [#if (studentEnroll.photo)??]
            <img style="max-height: 100px;" src="${base}/common/download.action?uuid=${(studentEnroll.photo)!}"/>
        [#else]
            无
        [/#if]
    </div>
    [#else]
        ${text(config.property.property)!}
    [/#if]
[/#macro]

[#macro propertyEdit config]
    [#assign property=config.property.property/]
    [#assign validateClass = "form-control " + (config.property.format)!""/]
    [#if config.required]
        [#assign validateClass = validateClass + " v_required"/]
    [/#if]
	[#if property == "studentEnroll.photo"]
		<script src="${base}/static/scripts/plupload-2.1.1/plupload.full.min.js"></script>
		<script src="${base}/static/scripts/plupload-2.1.1/i18n/zh_CN.js"></script>
		<div class="col-sm-6" style="line-height: 30px;min-height:34px;">
			<div class="col-sm-6" style="text-align:right">录取时照片</div>
			<div class="col-sm-6" >
				<div style="height: 160px; overflow: hidden;">
					<img id="albumImglq" src="${base}/common/download.action?uuid=${(studentEnroll.photo)!}" style="display: none; max-width: 300px; max-height: 200px;"/>
					<input id="valueiptlq" type="hidden" name="studentEnroll.photo" value="${(studentEnroll.photo)!}" />
				[#if config.editable]
					<div style="">
					    <a id="browselq" href="javascript:;">上传图片</a> 
					<p stype="color: #737373;">文件大小限制：3MB,3cm*4.5cm,蓝底或白底标准证件照，格式：jpg,png</p>
					</div>
				[/#if]
				</div>
			</div>
		</div>
				<script type="text/javascript">
				(function (){
					[#if "${(studentEnroll.photo)!}" != '']
						$("#albumImglq").show();
					[/#if]
					var uploader = new plupload.Uploader({
						multi_selection: false,
						file_data_name: "fileData",
						filters: {
						    mime_types : [
						        { title : "Image files", extensions : "jpg,gif,png" }
						    ]
						},
				        max_file_size :"3*1024kb",
					    browse_button: 'browselq',
					    url: "${base}/common/file!upload.action?dir=student/photo&type=uuid&width=300&zipImg=true"
					});
					
					uploader.init();
					
					uploader.bind('FilesAdded', function(up, files) {
						uploader.start();
					});
					uploader.bind('FileUploaded', function(up, file, res) {
						var data = eval("(" + res.response + ")");
						$("#albumImglq").attr("src", "${base}/common/download.action?uuid=" + data.uuid).show();
						$("#valueiptlq").val(data.uuid);
					});
					uploader.bind('Error', function(up, msg) {
						alert(msg.message);
					});
				})();
				</script>
	[#elseif property == "student.photo"]
	    <script src="${base}/static/scripts/plupload-2.1.1/plupload.full.min.js"></script>
		<script src="${base}/static/scripts/plupload-2.1.1/i18n/zh_CN.js"></script>
		<div class="col-sm-6" style="line-height: 30px;min-height:34px;">
			<div class="col-sm-6" style="text-align:right">
			[#if config.editable && config.required]<span class="requried">*</span>[/#if]
				最近照片
			</div>
			<style>
				hr, p {
			      margin: 0 0;
				}
			</style>
			<div class="col-sm-3" >
				<div style="height: 150px; overflow: hidden;">
				<img id="albumImg" src="${base}/common/download.action?uuid=${(student.photo)!}" style="display: none;max-height: 100px;"/>
				<input id="valueipt" type="hidden" name="student.photo" value="${(student.photo)!}" requried="true"/>
				<div style="padding-top: 7px;">
					<a id="browse" href="javascript:;">上传图片</a> 
				</div>
				</div>
			</div>
			<div class="col-sm-3" >
				[#if config.editable]
					<div style="padding-top: 7px;">
						<p style="color: #737373;">文件大小限制：3MB,3cm*4.5cm,蓝底或白底标准证件照，格式：jpg,png</p>
					</div>
				[/#if]
				
			</div>
		</div>
				<script type="text/javascript">
				(function (){
					[#if "${(student.photo)!}" != '']
					$("#albumImg").show();
					[/#if]
					var uploader = new plupload.Uploader({
						multi_selection: false,
						file_data_name: "fileData",
						filters: {
						    mime_types : [
						        { title : "Image files", extensions : "jpg,png" }
						    ]
						},
				        max_file_size :"3*1024kb",
					    browse_button: 'browse',
					    url: "${base}/common/file!upload.action?dir=student/photo&type=uuid&width=300&zipImg=true"
					});
					
					uploader.init();
					
					uploader.bind('FilesAdded', function(up, files) {
						uploader.start();
					});
					uploader.bind('FileUploaded', function(up, file, res) {
						var data = eval("(" + res.response + ")");
						$("#albumImg").attr("src", "${base}/common/download.action?uuid=" + data.uuid).show();
						$("#valueipt").val(data.uuid);
					});
					uploader.bind('Error', function(up, msg) {
						alert(msg.message);
					});
				})();
				</script>
	[#else]
		<div class="col-sm-6" style="line-height: 30px; min-height: 34px;">
		    <div class="col-sm-6" style="text-align: right;">
		        [#if config.editable && config.required]<span class="requried">*</span>[/#if]
		        ${(config.name)!}
		    </div>
		    <div class="col-sm-6">
		        [#if config.editable]
		            [#if "STD_PROPERTY_INPUT_TYPE_01" == (config.property.inputType.code)!""]
		                [#if property == "student.birthday"]
		                [#--生日--]
		                <input name="student.birthday" value="${(student.birthday?date)!}"
		                       class="datepicker ${validateClass} form-control"/>
		                [#elseif property == "studentInfo.politicalStatus"]
		                [#--政治面貌--]
		                    [@fu.select name="studentInfo.politicalStatus.id" value=(studentInfo.politicalStatus.id)!0 data=politicals class=validateClass/]
		                [#elseif property == "student.cardType"]
		                [#--证件类型--]
		                    [@fu.select name="student.cardType" value=(student.cardType)!"" data=[{"id":"身份证", "name":"身份证"}, {"id":"其它", "name":"其它"}] class=validateClass/]
		                [#elseif property == "studentInfo.maritalStatus"]
		                [#--婚姻状况--]
		                    [@fu.select name="studentInfo.maritalStatus.id" value=(studentInfo.maritalStatus.id)!0 data=hyzks class=validateClass/]
		                [#elseif property == "studentInfo.nation"]
		                [#--民族--]
		                    [@fu.select name="studentInfo.nation.id" value=(studentInfo.nation.id)!0 data=nations class=validateClass/]
		                [#elseif property == "studentInfo.health"]
		                [#--健康状况--]
		                    [@fu.select name="studentInfo.health.id" value=(studentInfo.health.id)!0 data=healths class=validateClass/]
			            [#elseif property == "student.faith"]
			            [#--宗教信仰--]
			                [@fu.select name="student.faith.id" value=(student.faith.id)!0 data=faiths class=validateClass /]
		                [#elseif property == "studentInfo.hmto"]
		                [#--是否澳台--]
		                    [@fu.radios name="studentInfo.hmto" value=(studentInfo.hmto?string("1", "0"))!"0" data=[{"id":"1", "name":"是"}, {"id":"0", "name":"否"}] class=validateClass/]
		                [#elseif property == "studentInfo.accommodationed"]
		                [#--是否澳台--]
		                    [@fu.radios name="studentInfo.accommodationed" value=(studentInfo.accommodationed?string("1", "0"))!"0" data=[{"id":"1", "name":"是"}, {"id":"0", "name":"否"}] class=validateClass/]
		                [#elseif property == "student.enrollSourceType"]
		                [#--生源性质--]
		                    [@fu.select name="student.enrollSourceType.id" value=(student.enrollSourceType.id)!0 data=enrollSourceTypes class=validateClass/]
		                [#elseif property == "studentInfo.awards"]
		                [#--获奖情况--]
		                <textarea name="${config.property.property}" class="${validateClass}" rows="3"
		                          maxlength="${config.property.length!30}"
		                          [#if config.required]required[/#if]>${text(config.property.property)!}</textarea>
		                [#else]
			                <input name="${config.property.property}" value="${text(config.property.property)!}"
			                       class="${validateClass}"
			                       maxlength="${config.property.length!30}" [#if config.required]required[/#if]>
		                [/#if]
		            [#elseif "STD_PROPERTY_INPUT_TYPE_02" == (config.property.inputType.code)!""]
		                [#assign property=config.property.property + ".id"/]
		                [@fu.select name=property datavalue=(text(property))!0 class=validateClass/]
		            [#elseif "STD_PROPERTY_INPUT_TYPE_03" == (config.property.inputType.code)!""]
		                [@fu.radios name=config.property.property value=(studentInfo.hmto?string("1", "0"))!"0" data=[{"id":"1", "name":"是"}, {"id":"0", "name":"否"}] class=validateClass/]
		            [#elseif "STD_PROPERTY_INPUT_TYPE_90" == (config.property.inputType.code)!""]
		                [@propertyInfoValue config/]
		            [#else]
		                <input name="${config.property.property}" value="${text(config.property.property)!}"
		                       class="${validateClass}"
		                       maxlength="${config.property.length!30}" [#if config.required]required[/#if]>
		            [/#if]
		        [#else]
		            [@propertyInfoValue config/]
		        [/#if]
		    </div>
		</div>
	[/#if]
[/#macro]

[#function typeSave type]
    [#assign  code = "TYPE_" + type.id/]
    [#list studentInfoStates as state]
        [#if state.code == code]
            [#return true/]
        [/#if]
    [/#list]
    [#return false/]
[/#function]