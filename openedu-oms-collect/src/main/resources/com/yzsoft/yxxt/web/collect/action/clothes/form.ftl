[#ftl]
[#include "../comm/macro.ftl"/]
[#import "/template/form/utils.ftl" as fu/]
[@index code="03"]
<style>.help-block.valid {
    display: none;
}</style>
<h3 class="caption">军训服装填写</h3>
<div class="row">
    <div class="col-sm-4">
        <h4>我的尺码</h4>

        <div style="padding: 30px 0;">
            <form action="${b.url("!save")}" class="form-horizontal clothes_form" role="form">
                [#macro input label name addon="cm"]
                    <div class="form-group">
                        <label class="col-sm-5 control-label">${label}</label>
                        <div class="col-sm-7">
                            <div class="input-group">
                                <input name="clothesStudent.${name}" value="${(clothesStudent[name])!}"
                                       class="form-control v_integer">
                                <span class="input-group-addon">${addon}</span>
                            </div>
                        </div>
                    </div>
                [/#macro]
            [#--[@input "身高" "height"/]
            [@input "体重" "weight" "kg"/]
            [@input "胸围" "bust"/]
            [@input "腰围" "waist"/]
            [@input "臀围" "hip"/]--]
                <div class="form-group">
                    <label class="col-sm-5 control-label"><span class="required">*</span>服装尺码</label>
                    <div class="col-sm-7">
                        <select name="clothesStudent.clothesSize" class="form-control" required>
                            <option value="">请选择...</option>
                            [#list clothesSizes as v]
                                <option [#if v.name == (clothesStudent.clothesSize)!""]selected[/#if]>${v.name}</option>
                            [/#list]
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-5 control-label"><span class="required">*</span>鞋子尺码</label>
                    <div class="col-sm-7">
                        <select name="clothesStudent.shoesSize" class="form-control" required>
                            <option value="">请选择...</option>
                            [#list shoesSizes as v]
                                <option [#if v.name == (clothesStudent.shoesSize)!""]selected[/#if]>${v.name}</option>
                            [/#list]
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-5 col-sm-7">
                        <input type="hidden" name="clothesStudent.id" value="${(clothesStudent.id)!}"/>
                        <button type="submit" class="btn btn-primary">
                            提交 <i class="fa fa-check "></i>
                        </button>
                    </div>
                </div>
            </form>
        </div>
        [@c.validateJsAndCss/]
        <script>
            $(function () {
                ValidateForm($(".clothes_form"));
            });
        </script>
    </div>
    <div class="col-sm-8">
        [#include "size.ftl"/]
    </div>
</div>
    [@editRemark/]
[/@index]
