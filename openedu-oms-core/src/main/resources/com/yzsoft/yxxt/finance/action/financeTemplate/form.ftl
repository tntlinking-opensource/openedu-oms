[#ftl]
[#import "/template/form/utils.ftl" as fu/]
[@b.head/]
[@b.toolbar title="收费模板" entityId=financeTemplate.id!0]bar.addBack();[/@]
[@b.form action="!save" theme="form"]
    [@fu.text label="年份"]
    ${financeTemplate.year}
    <input type="hidden" name="financeTemplate.year" value="${financeTemplate.year}"/>
    [/@fu.text]
    [@b.textfield label="名称"name="financeTemplate.name" value="${financeTemplate.name!}" required="true" maxlength="50"/]
    [@b.radios label="学历层次"  name="financeTemplate.education.id" value=(financeTemplate.education.id)!0 items=educations  required="true"/]
    [@b.radios label="专业类别"  name="financeTemplate.majorType.id" value=(financeTemplate.majorType.id)!0 items=majorTypes/]
    [@b.radios label="是否限制专业"  name="financeTemplate.limitMajor" value=financeTemplate.limitMajor items="1:是,0:否"/]
<div class="majors_div" style="display: none;">
    [@b.field label="适用专业"]
        <style>
            .majors > div { margin: 10px 0; }
        </style>
        <div class="majors">
            [#list departments?sort_by("code") as department]
                <div>
                ${department.name}：
                    [#assign majorNum = 0/]
                    [#list financeTemplate.majors as major]
                        [#if major.department.id = department.id]
                            [#assign majorNum = majorNum + 1/]
                            [#if majorNum gt 1]、[/#if]
                        ${major.name}
                        [/#if]
                    [/#list]
                </div>
            [/#list]
        </div>
        <button type="button" class="add_major_btn btn">添加专业</button>
        <input type="hidden" name="majorIds" class="major_ids"
               value="[#list financeTemplate.majors as major][#if major_index gt 0],[/#if]${major.id}[/#list]"/>
    [/@]
</div>
    [@b.field]
    <table class="table table-striped table-bordered table-advance table-hover">
        <thead>
        <tr>
            <td>项目</td>
            <td>收费金额</td>
        </tr>
        </thead>
        <tbody>
            [#function getMoney item]
                [#list financeTemplate.items as v]
                    [#if v.item.id == item.id]
                        [#return v.money/]
                    [/#if]
                [/#list]
                [#return 0/]
            [/#function]
            [#list items as item]
	            [#if item.name != '住宿费']
		            <tr>
		                <td>${item.name}</td>
		                <td>
		                    <input type="hidden" name="item" value="${item_index}"/>
		                    <input type="hidden" name="item${item_index}.item.id" value="${item.id}"/>
		                    <input name="item${item_index}.money" value="${financeTemplate.getMoney(item)}"/>
		                </td>
		            </tr>
	            [/#if]
            [/#list]
        </tbody>
    </table>
    [/@]
    [@b.radios label="状态"  name="financeTemplate.enabled" value=financeTemplate.enabled items="1:启用,0:禁用"/]
    [@b.formfoot]
    <input type="hidden" name="financeTemplate.id" value="${financeTemplate.id!}"/>
        [@b.redirectParams/]
        [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
[/@]
<script>
    $(function () {
        function showOrHiddenMajors() {
            var val = $('[name="financeTemplate.limitMajor"]:checked').val();
            var majorsDiv = $(".majors_div");
            if (val == 1) {
                majorsDiv.slideDown();
            } else {
                majorsDiv.slideUp();
            }
        }

        $('[name="financeTemplate.limitMajor"]').click(function () {
            showOrHiddenMajors();
        });
        if ($('[name="financeTemplate.limitMajor"]:checked').val() == 1) {
            $(".majors_div").show();
        }

        $(".add_major_btn").click(function () {
            var educationId = $name("financeTemplate.education.id").val();
            var majorIds = $(".major_ids").val();
            $.colorbox({
                title: "添加专业",
                href: "${base}/core/major-select.action?educationId=" + educationId + "&majorIds=" + majorIds,
                iframe: true,
                width: "50%",
                height: "80%",
            });
        });
    });

    function selectedMajor(names, ids) {
        var majors = $(".majors").empty();
        names.forEach(function (name) {
            majors.append("<div>" + name + "</div>");
        });
        $(".major_ids").val(ids);
        $.colorbox.close();
    }
</script>
[@b.foot/]