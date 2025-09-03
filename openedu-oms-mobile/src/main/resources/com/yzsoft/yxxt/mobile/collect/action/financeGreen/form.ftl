[#ftl]
[#import "/template/comm.ftl" as c/]
[#import "/template/mobile.ftl" as m/]
[#include "../comm/macro.ftl"/]
[@m.body title=switch.name back=formBack((financeGreenStd.id)??)]
<style>
	.div_img{width: 50%;float: left;padding: .7em;}
	.div_img div{margin-top: -21px;margin-left: 105px;}
</style>
<form action="${b.url("!save")}" method="post" class="clothesStudentForm">
    <div class="yx-xxtx">
        <ul data-role="listview" class="yx-xxtx-info">
            <li>
                <div class="yx-xxtx-info-a">
                    <span class="yx-xxtx-info-a-left margin-top-em">是否办理</span>
                    <div class="yx-xxtx-info-a-right">
                        <select name="financeGreenStd.handle" data-role="flipswitch" class="handle_select">
                            <option value="0">否</option>
                            <option value="1" [#if financeGreenStd.handle]selected[/#if]>是</option>
                        </select>
                    </div>
                </div>
            </li>
        </ul>
        <ul data-role="listview" class="yx-xxtx-info finance_greens_ul"
            [#if !financeGreenStd.handle]style="display: none;" [/#if]>
            [#function hasFinanceGreen financeGreen]
                [#list financeGreenStd.financeGreens as v]
                    [#if v.id == financeGreen.id]
                        [#return true/]
                    [/#if]
                [/#list]
                [#return false/]
            [/#function]
            [#list financeGreens as v]
                <li>
                    <div class="yx-xxtx-info-a">
                        <span class="yx-xxtx-info-a-left margin-top-em">${(v.name)!}</span>
                        <div class="yx-xxtx-info-a-right">
                            <input name="financeGreenId" type="radio" data-role="flipswitch" value="${v.id}"
                                   data-on-text="是" data-off-text="否" [#if hasFinanceGreen(v)]checked[/#if]/>
                        </div>
                    </div>
                </li>
            [/#list]
            <li>
                <div class="yx-xxtx-info-a">
                    <span class="yx-xxtx-info-a-left margin-top-em">上传附件</span>
                    <div class="yx-xxtx-info-a">
                    	<input type="hidden" name="studentId" value="${studentId}"/>
						<input type="file" id="choose" multiple class="v_ignore" accept="image/*;">
						<div id="${id!}kv-avatar-errors" class="center-block" style="display:none"></div>
						<input type="hidden" id="uuid" name="uuid" value="" />
                    </div>
                    <p style="    float: left;">支持jpg,png,gif格式，大小不超过1024M</p>
                </div>
            </li>
            <li>
	        	<div class="img-list yx-xxtx-info-a" style="text-align: center;">
					[#list student.stdInfoFile as v]
						<div class="div_img">
							<input type="hidden" name="uuids" value="${(v.uuid)!}" />
			                <img style="height:auto;width:100%;" src="${base}/common/download.action?uuid=${(v.uuid)!}"/>
			                <div class="kv-file-remove btn btn-xs btn-default" title="删除文件">
			                	<i class="glyphicon glyphicon-trash text-danger" style="color: #f06ba1;"></i>
			                </div>
		                </div>
		        	[/#list]
	            </div>
	        </li>
        </ul>
    </div>
    <div style="display:none">
    	<textarea id="temp">
    		<div class="div_img">
				<input type="hidden" name="uuids" value="#uuid" />
                <img style="height:auto;width:100%;" src="${base}/common/download.action?uuid=#uuid"/>
                <div class="kv-file-remove btn btn-xs btn-default" title="删除文件">
                	<i class="glyphicon glyphicon-trash text-danger" style="color: #f06ba1;"></i>
                </div>
            </div>
    	</textarea>
    </div>
    <div class="yx-jiange-2em"></div>
    <div class="yx-model-btnmodel">
        <button type="button" class="yx-model-bluebtn submit">提交</button>
    </div>
    <div class="yx-jiange-2em"></div>
</form>
    [@m.validation/]
    
<script src="${base}/static/yxxt/mobile/scripts/form.js"></script>
<script>
    $(function () {
    	$.validator.setDefaults({
		  ignore: ".v_ignore"
		})
    
        $(".clothesStudentForm").initForm({
            submitHandler:function(form) {
                if($(".handle_select").val() == "1"){
                    if($("[name='financeGreenId']:checked").length == 0){
                        alert("请选择一个申请项目");
                        return;
                    }
                }
                form.submit();
            }
        });
        $(".handle_select").change(function () {
            if (this.value == "1") {
                $(".finance_greens_ul").slideDown();
            } else {
                $(".finance_greens_ul").slideUp();
            }
        });
        
        
        $(".img-list").on("click", ".kv-file-remove", function () {
        	$(this).parent().remove();
        	changeUUID();
        });
    });
    
    function changeUUID(){
    	var uuids = [];
    	$("input[name^='uuids']").each(function(i, o){
    		uuids[i] = $(o).val();
    	});
    	$("#uuid").val(uuids);
    }
</script>

<script>
	var filechooser = document.getElementById("choose");
	//    用于压缩图片的canvas
	var canvas = document.createElement("canvas");
	var ctx = canvas.getContext('2d');
	//    瓦片canvas
	var tCanvas = document.createElement("canvas");
	var tctx = tCanvas.getContext("2d");
	var maxsize = 100 * 1024;
	filechooser.onchange = function() {
		if (!this.files.length) return;
		var files = Array.prototype.slice.call(this.files);
		if (files.length > 9) {
			alert("最多同时只可上传9张图片");
			return;
		}
		files.forEach(function(file, i) {
			if (!/\/(?:jpeg|png|gif|jpg)/i.test(file.type)) return;
			var reader = new FileReader();
			var li = document.createElement("li");
			//获取图片大小
			var size = file.size / 1024 > 1024 ? (~~(10 * file.size / 1024 / 1024)) / 10 + "MB" : ~~(file.size / 1024) + "KB";
			//li.innerHTML = '<div class="progress"><span></span></div><div class="size">' + size + '</div>';
			//$(".img-list").append($(li));
			reader.onload = function() {
				var result = this.result;
				var img = new Image();
				img.src = result;
				//$(li).css("background-image", "url(" + result + ")");
				upload(result, file.type, $(li));
				function callback() {
					var data = compress(img);
				  	upload(data, file.type, $(li));
				  	img = null;
				}
			};
			reader.readAsDataURL(file);
		})
	};
	
	//使用canvas对大图片进行压缩
	function compress(img) {
		var initSize = img.src.length;
		var width = img.width;
		var height = img.height;
		//如果图片大于四百万像素，计算压缩比并将大小压至400万以下
		var ratio;
		if ((ratio = width * height / 4000000) > 1) {
			ratio = Math.sqrt(ratio);
			width /= ratio;
			height /= ratio;
		} else {
			ratio = 1;
		}
		canvas.width = width;
		canvas.height = height;
		//铺底色
		ctx.fillStyle = "#fff";
		ctx.fillRect(0, 0, canvas.width, canvas.height);
		//如果图片像素大于100万则使用瓦片绘制
		var count;
		if ((count = width * height / 1000000) > 1) {
			count = ~~(Math.sqrt(count) + 1); //计算要分成多少块瓦片
			//计算每块瓦片的宽和高
			var nw = ~~(width / count);
			var nh = ~~(height / count);
			tCanvas.width = nw;
			tCanvas.height = nh;
			for (var i = 0; i < count; i++) {
				for (var j = 0; j < count; j++) {
					tctx.drawImage(img, i * nw * ratio, j * nh * ratio, nw * ratio, nh * ratio, 0, 0, nw, nh);
				    ctx.drawImage(tCanvas, i * nw, j * nh, nw, nh);
				}
			}
		} else {
			ctx.drawImage(img, 0, 0, width, height);
		}
		//进行最小压缩
		var ndata = canvas.toDataURL('image/jpeg', 0.1);
		console.log('压缩前：' + initSize);
		console.log('压缩后：' + ndata.length);
		console.log('压缩率：' + ~~(100 * (initSize - ndata.length) / initSize) + "%");
		tCanvas.width = tCanvas.height = canvas.width = canvas.height = 0;
		return ndata;
	}
	
	//图片上传，将base64的图片转成二进制对象，塞进formdata上传
	function upload(basestr, type, $li) {
		var text = window.atob(basestr.split(",")[1]);
		var buffer = new Uint8Array(text.length);
		var pecent = 0, loop = null;
		for (var i = 0; i < text.length; i++) {
			buffer[i] = text.charCodeAt(i);
		}
		var blob = getBlob([buffer], type);
		var xhr = new XMLHttpRequest();
		var formdata = getFormData();
		formdata.append('fileData', blob);
		xhr.open('post', '${base}/common/file!upload.action?folder=repair/assetRepair&type=uuid');
		//xhr.open('post', '${base}/index.action?method=saveImgScene&activity.id=${(activity.id)!}');
		xhr.onreadystatechange = function() {
			if (xhr.readyState == 4 && xhr.status == 200) {
				var data = eval('(' + xhr.response + ')');
				var temp = $("#temp").val();
				temp = temp.replace(/#uuid/g,data.uuid);
				//debugger;
				$(".img-list").append(temp);
				changeUUID();
			}
		};
		xhr.send(formdata);
	}
	
		  /**
		   * 获取blob对象的兼容性写法
		   * @param buffer
		   * @param format
		   * @returns {*}
		   */
	function getBlob(buffer, format) {
		try {
			return new Blob(buffer, {type: format});
		} catch (e) {
			var bb = new (window.BlobBuilder || window.WebKitBlobBuilder || window.MSBlobBuilder);
			buffer.forEach(function(buf) {
				bb.append(buf);
			});
			return bb.getBlob(format);
		}
	}
	
	/**
	* 获取formdata
	*/
	function getFormData() {
		var isNeedShim = ~navigator.userAgent.indexOf('Android')
			&& ~navigator.vendor.indexOf('Google')
			&& !~navigator.userAgent.indexOf('Chrome')
			&& navigator.userAgent.match(/AppleWebKit\/(\d+)/).pop() <= 534;
		return isNeedShim ? new FormDataShim() : new FormData()
	}
	
	/**
	* formdata 补丁, 给不支持formdata上传blob的android机打补丁
	* @constructor
	*/
	function FormDataShim() {
		console.warn('using formdata shim');
		var o = this,
		parts = [],
		boundary = Array(21).join('-') + (+new Date() * (1e16 * Math.random())).toString(36),
		oldSend = XMLHttpRequest.prototype.send;
		this.append = function(name, value, filename) {
			parts.push('--' + boundary + '\r\nContent-Disposition: form-data; name="' + name + '"');
			if (value instanceof Blob) {
				parts.push('; filename="' + (filename || 'blob') + '"\r\nContent-Type: ' + value.type + '\r\n\r\n');
				parts.push(value);
			} else {
				parts.push('\r\n\r\n' + value);
			}
			parts.push('\r\n');
		};
		
		// Override XHR send()
		XMLHttpRequest.prototype.send = function(val) {
			var fr,data,oXHR = this;
			if (val === o) {
				// Append the final boundary string
				parts.push('--' + boundary + '--\r\n');
				// Create the blob
				data = getBlob(parts);
				// Set up and read the blob into an array to be sent
				fr = new FileReader();
				fr.onload = function() {
					oldSend.call(oXHR, fr.result);
				};
				fr.onerror = function(err) {
				 	throw err;
				};
				fr.readAsArrayBuffer(data);
				// Set the multipart content type and boudary
				this.setRequestHeader('Content-Type', 'multipart/form-data; boundary=' + boundary);
				XMLHttpRequest.prototype.send = oldSend;
			} else {
				oldSend.call(this, val);
			}
		};
	}
	
</script>
[/@]