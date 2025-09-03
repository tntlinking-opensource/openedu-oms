[#ftl]
[@b.head/]
[@b.toolbar title="绿色通道类型" entityId=financeGreen.id!0]bar.addBack();[/@]
[@b.form action="!save" theme="form"]
    [@b.textfield label="代码"name="financeGreen.code" value="${financeGreen.code!}" required="true" maxlength="50"/]
    [@b.textfield label="困难类型"name="financeGreen.name" value="${financeGreen.name!}" required="true" maxlength="50"/]
    [@b.checkboxes label="学历层次" name="educationIds" items=educations value=financeGreen.educations required="true" /]
    [@b.textfield label="说明"name="financeGreen.remark" value="${financeGreen.remark!}" required="true" maxlength="100"/]
    [@b.radios label="是否可以修改金额"  name="financeGreen.manual" value=financeGreen.manual items="1:是,0:否"/]
[#--<div class="items_div" style="display: none;">--]
    [@b.field label="默认减免金额"]
    <table class="table table-striped table-bordered table-advance table-hover">
        <thead>
        <tr>
            <td>项目</td>
            <td>减免金额</td>
        </tr>
        </thead>
        <tbody>
            [#function getMoney item]
                [#list financeGreen.items as v]
                    [#if v.item.id == item.id]
                        [#return v.money/]
                    [/#if]
                [/#list]
                [#return 0/]
            [/#function]
            [#list items as item]
            <tr>
                <td>${item.name}</td>
                <td>
                    <input type="hidden" name="item" value="${item_index}"/>
                    <input type="hidden" name="item${item_index}.item.id" value="${item.id}"/>
                    <input name="item${item_index}.money" value="${financeGreen.getMoney(item)}"/>
                </td>
            </tr>
            [/#list]
        </tbody>
    </table>
    [/@]
[#--</div>--]
    [@b.radios label="状态"  name="financeGreen.enabled" value=financeGreen.enabled items="1:启用,0:禁用"/]
    [@b.formfoot]
    <input type="hidden" name="financeGreen.id" value="${financeGreen.id!}"/>
        [@b.redirectParams/]
        [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
[/@]
<script>
    $(function () {
        function showOrHiddenItems() {
            var val = $('[name="financeGreen.manual"]:checked').val();
            var itemsDiv = $(".items_div");
            if (val == 0) {
                itemsDiv.slideDown();
            } else {
                itemsDiv.slideUp();
            }
        }

        $('[name="financeGreen.manual"]').click(function () {
            showOrHiddenItems();
        });
        if ($('[name="financeGreen.manual"]:checked').val() == 0) {
            $(".items_div").show();
        }
    });
</script>
[@b.foot/]