[#ftl]
[#include "../comm/macro.ftl"/]
[#include "macro.ftl"/]
[@form code="01"]
    [@c.bootstrapDatepickerJsAndCss/]
<style>
    .ifrom input { line-height: 25px; margin: 2px 0; }
    .ifrom input, .ifrom select { width: 200px; height: 30px; padding: 2px 3px; }
    .ifrom textarea { width: 200px; height: 71px; padding: 2px 3px; margin: 2px 0;}
    .ifrom .radio-inline input { width: auto; }
    span.requried { color: red; font-family: "宋体"; }
    input.error { border: 2px solid red; }
    label.error { color: red; margin: 5px 0; position: absolute; z-index: 99; background-color: #fff; border: 1px solid red; padding: 0px 5px; }
    .tab-content .tab-pane .caption { margin: 10px 15px; }
</style>
<input type="hidden" name="student.id" value="${(student.id)!}"/>
<input type="hidden" name="studentInfo.id" value="${(studentInfo.id)!}"/>
<input type="hidden" name="studentContact.id" value="${(studentContact.id)!}"/>
<input type="hidden" name="studentHome.id" value="${(studentHome.id)!}"/>
<input type="hidden" name="studentEnroll.id" value="${(studentEnroll.id)!}"/>
<input type="hidden" name="studentOther.id" value="${(studentOther.id)!}"/>
<input type="hidden" name="code" value="TYPE_${typeId}"/>
    [#list  types as type]
        [#if type.id == typeId]
        <h3 class="caption">${type.name}</h3>
        <div class="row ifrom">
            [#list configs as config]
                [#if config.type.id == type.id]
                    [@propertyEdit config/]
                [/#if]
            [/#list]
        </div>
        [/#if]
    [/#list]
<script src="${base}/static/yxxt/scripts/district.js"></script>
<script>
    $(function () {
        $('.datepicker').datepicker({
            autoclose: true,
            language: "zh-CN",
            format: 'yyyy-mm-dd',
            startView: 2,
            clearBtn: true,
        });
        $(".province").loadProvince().cascadeCity(".city").cascadeCounty(".county");
        $(".v_enroll_province").loadProvince().cascadeCity(".v_enroll_city").cascadeCounty(".v_enroll_county");
        $(".v_contact_province").loadProvince().cascadeCity(".v_contact_city").cascadeCounty(".v_contact_county");
        $(".v_home_province").loadProvince().cascadeCity(".v_home_city").cascadeCounty(".v_home_county");
    });
</script>
[/@form]
