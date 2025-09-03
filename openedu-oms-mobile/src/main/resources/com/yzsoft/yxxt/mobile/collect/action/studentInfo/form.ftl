[#ftl]
[#import "/template/mobile.ftl" as m/]
[#include "macro.ftl"/]
[@m.body title=type.name back=b.url("!info?id=${(type.id)!}")]
<div class="yx-xxtx">
    <form action="${b.url("!save")}" class="studentInfoForm" method="post">
        <ul data-role="listview" class="yx-xxtx-info">
            [#list configs as config]
                [#if config.type.id == type.id]
                    [@propertyEdit config/]
                [/#if]
            [/#list]
        </ul>
        <div class="yx-model-btnmodel">
            <input type="hidden" name="student.id" value="${(student.id)!}"/>
            <input type="hidden" name="studentInfo.id" value="${(studentInfo.id)!}"/>
            <input type="hidden" name="studentContact.id" value="${(studentContact.id)!}"/>
            <input type="hidden" name="studentHome.id" value="${(studentHome.id)!}"/>
            <input type="hidden" name="studentEnroll.id" value="${(studentEnroll.id)!}"/>
            <input type="hidden" name="studentOther.id" value="${(studentOther.id)!}"/>
            <input type="hidden" name="typeId" value="${(type.id)!}"/>
            <input type="hidden" name="code" value="TYPE_${(type.id)!}"/>
            <button type="button" class="yx-model-greenbtn submit">提交</button>
        </div>
    </form>
</div>
<link rel="stylesheet" type="text/css" href="${base}/static/mobile/mobiscroll/css/mobiscroll.custom-2.17.0.min.css"/>
<script src="${base}/static/mobile/mobiscroll/js/mobiscroll.custom-2.17.0.min.js"></script>
<script src="${base}/static/scripts/jquery-validation/jquery.validate.min.js"></script>
<script src="${base}/static/scripts/jquery-validation/additional-methods.min.js"></script>
<script src="${base}/static/scripts/jquery-validation/additional-methods.js"></script>
<script src="${base}/static/bpcg/student/scripts/validate.js"></script>
<script src="${base}/static/yxxt/mobile/scripts/form.js"></script>
<script src="${base}/static/yxxt/scripts/district.js"></script>
<script>
    $(function () {
        $("select.province").loadProvince().cascadeCity("select.city").cascadeCounty("select.county");
        $("select.v_enroll_province").loadProvince().cascadeCity("select.v_enroll_city").cascadeCounty("select.v_enroll_county");
        $("select.v_contact_province").loadProvince().cascadeCity(".v_contact_city").cascadeCounty("select.v_contact_county");
        $("select.v_home_province").loadProvince().cascadeCity("select.v_home_city").cascadeCounty("select.v_home_county");
        $(".studentInfoForm").initForm({
            rules: studentInfoRules,
        });
        $(".datepicker").mobiscroll().date({
            dateFormat: "yy-mm-dd",
            lang: 'zh',
            showNow: true,
            buttons: [
                'set', 'clear', 'cancel'
            ],
        });
    });
</script>
[/@]