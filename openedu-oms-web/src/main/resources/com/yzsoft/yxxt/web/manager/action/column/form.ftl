[#ftl]
[@b.head/]
[@b.toolbar title="栏目信息" entityId=column.id!0]bar.addBack();[/@]
<link rel="stylesheet" type="text/css" href="${base}/static/scripts/webuploader/webuploader.css">
<script type="text/javascript" src="${base}/static/scripts/webuploader/webuploader.html5only.min.js"></script>
<script type="text/javascript" src="${base}/static/scripts/webuploader/webuploader.custom.js"></script>
[@b.form action="!save" title="友情链接" theme="list"]
    [@b.textfield label="排序" name="column.code" value="${column.code!}" required="true" maxlength="4" class="v_integer"/]
    [@b.textfield label="名称" name="column.name" value="${column.name!}" required="true" maxlength="20"/]
    [@b.select label="栏目级别" name="column.level.id" value=(column.level.id)! items=levels required="true" class="column_level_select"/]
    [@b.select label="上级栏目" name="column.parent.id" value=(column.parent.id)! items=topColumns class="top_column_select"/]
    [@b.select label="栏目类型" name="column.type.id" value=(column.type.id)! items=types required="true" class="column_type_select"/]
    [@b.textfield label="链接" name="column.url" value="${column.url!}" maxlength="200" class="column_url_ipt" required="true"/]
    [@b.radios label="状态"  name="column.enabled" value=column.enabled items="1:启用,0:禁用"/]
    [@b.formfoot]
    <input type="hidden" name="column.id" value="${column.id!}"/>
        [@b.redirectParams/]
        [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
[/@]
<script>
    $(function () {
        $(".column_level_select").change(function () {
            levelSelectChange();
        });

        function levelSelectChange() {
            var tpColumnTr = $(".column_level_select").closest(".form-group");
            if ($(".column_level_select option:selected").text() == "二级") {
                tpColumnTr.show();
            } else {
                tpColumnTr.hide();
            }
        }

        levelSelectChange();
        $(".column_type_select").change(function () {
            typeSelectChange();
        });

        function typeSelectChange() {
            var urlTr = $(".column_url_ipt").closest(".form-group");
            console.log(urlTr);
            if ($(".column_type_select option:selected").text() == "链接") {
                urlTr.show();
            } else {
                urlTr.hide();
            }
        }

        typeSelectChange();
    });
</script>
[@b.foot/]