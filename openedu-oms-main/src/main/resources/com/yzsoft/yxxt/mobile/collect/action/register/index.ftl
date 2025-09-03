[#ftl]
[#import "/template/mobile.ftl" as m/]
[#if student.id??]
	[#include "macro.ftl"/]
	[@m.body title=switch.name back=b.url("index?t=1")]
	<div class="ui-listview">
	    <div class="yx-xxtx-btnarea">
	        [#list types as v]
		        [#if v.name != '未命名']
		            [#if hasConfig(v)]
		            <a href="${b.url("!info")}&id=${v.id}&cardcode=${(student.cardcode)!}" data-role="button" class="yx-xxtx-btnarea-a" data-transition="slide">
		                ${v.name!}
		            </a>
		            [/#if]
	            [/#if]
	        [/#list]
	        <a href="${b.url("!family")}&cardcode=${(student.cardcode)!}" data-role="button" class="yx-xxtx-btnarea-a" data-transition="slide">
	            家庭成员
	        </a>
	        [#if educationEnabled]
	        <a href="${b.url("!education")}&cardcode=${(student.cardcode)!}" data-role="button" class="yx-xxtx-btnarea-a" data-transition="slide">
	            教育经历
	        </a>
	        [/#if]
	    </div>
	</div>
	[/@]
[#else]
	[@m.body title="身份证号"  back=b.url("!index?t=1")]
	<form action="${b.url("!edit?id=" + type.id)}" class="studentInfoForm" method="post">
	    <div class="yx-xxtx">
	        <ul data-role="listview" class="yx-xxtx-info">
	            <li>
	                <div class="yx-xxtx-info-a">
	                    <span class="yx-xxtx-info-a-left margin-top-em">身份证号</span>
	                    <div class="form-right yx-xxtx-info-a-right">
	                        <input name="cardcode" value="${(student.cardcode)!}" maxlength="18"
	                               placeholder="请输入身份证号" class="v_cardcode" required>
	                    </div>
	                </div>
	            </li>
	        </ul>
	    </div>
	    <div class="yx-jiange-1em"></div>
	    <div class="yx-model-btnmodel">
	        <button type="button" class="yx-model-greenbtn submit">下一步</button>
	    </div>
	    <div class="yx-jiange-1em"></div>
	</form>
	<script src="${base}/static/scripts/jquery-validation/jquery.validate.min.js"></script>
	<script src="${base}/static/scripts/jquery-validation/additional-methods.min.js"></script>
	<script src="${base}/static/scripts/jquery-validation/additional-methods.js"></script>
	<script src="${base}/static/yxxt/mobile/scripts/validate.js"></script>
	<script src="${base}/static/yxxt/mobile/scripts/form.js"></script>
	<script>
	    $(function () {
	    	jQuery.validator.addMethod("v_cardcode", function(value, element) {   
			    var tel = /^[\d]{15}$|^[\d]{18}$|^[\d]{17}[X]$/;
			    return this.optional(element) || (tel.test(value));
			}, "身份证号码应为15、18位或者17位+X！");
			
	        $(".studentInfoForm").initForm();
	    });
	</script>
	[/@]
[/#if]