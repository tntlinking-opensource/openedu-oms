[#ftl]
[#include "../comm/macro.ftl"/]
[#import "/template/form/utils.ftl" as fu/]
[@index code="03"]
<style>.help-block.valid { display: none; }</style>
<h3 class="caption">军训服装</h3>
<div class="row">
	<div class="col-sm-4">
		<h4>我的尺码</h4>
		<div style="padding: 30px 0;">
			<form action="${b.url("!edit")}" class="form-horizontal clothes_form" role="form">
                [#macro text label name addon="cm"]
					<div class="form-group">
						<label class="col-sm-5 control-label">${label}</label>
						<div class="col-sm-7">
							<p class="form-control-static">${(clothesStudent[name])!"未填写"}[#if addon != ""]（${addon}
								）[/#if]</p>
						</div>
					</div>
                [/#macro]
            [#--   [@text "身高" "height"/]
               [@text "体重" "weight" "kg"/]
               [@text "胸围" "bust"/]
               [@text "腰围" "waist"/]
               [@text "臀围" "hip"/]--]
                [@text "服装尺码" "clothesSize" ""/]
                [@text "鞋子尺码" "shoesSize" ""/]
                [#if switch?? && switch.editable]
					<div class="form-group">
						<div class="col-sm-offset-5 col-sm-7">
							<a href="${b.url("!edit")}" class="btn btn-primary">修改</a>
						</div>
					</div>
                [/#if]
			</form>
		</div>
	</div>
	<div class="col-sm-8">
        [#include "size.ftl"/]
	</div>
</div>
[/@index]
