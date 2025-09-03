[#ftl]
[#import "/template/bootstrap.ftl" as bs/]
[@b.head/]
[@b.toolbar title="编学号"]bar.addBack();[/@]
[@b.form action="!generateCodeSave" validate="true" theme="form"]
    [@bs.panel title="分班规则"]
        [#macro rule label code]
            [@bs.formGroup label=label]
            <div class="mt-radio-list">
                [#list rules as rule]
                    [#if rule.type == code]
                    [@bs.checkbox name="rule.${code}" required="required" value=rule.id]${rule.name}[/@bs.checkbox]
                [/#if]
                [/#list]
            </div>
            [/@bs.formGroup]
        [/#macro]
        [@rule label="学号规则" code="STUDENT_CODE"/]
    [/@bs.panel]
    [@b.formfoot]
        [@b.redirectParams/]
        [@b.submit value="action.submit" onsubmit="submitForm"/]
    [/@]
[/@]
<script>
    $(function () {
        $(".mt-radio-list").each(function () {
            var radios = $("input", this);
            if (radios.length == 1) {
                radios.prop("checked", true);
            }
        });
    });

    function submitForm(form) {
        return $(form).valid();
    }
</script>
[@b.foot/]