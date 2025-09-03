[#ftl]
[#import "/template/comm.ftl" as c/]
[#import "/template/mobile.ftl" as m/]
[@m.body title=switch.name back=b.url("!index")]
<style>
    .ui-header .ui-title, .ui-footer .ui-title { margin: 0; overflow: auto; }
</style>
<form action="${b.url("!save")}" method="post" class="clothesStudentForm">
    <div class="yx-xxtx">
        <ul data-role="listview" class="yx-xxtx-info">
            <li>
                <div class="yx-xxtx-info-a">
                    <span class="yx-xxtx-info-a-left">物品名称</span>
                    <span class="yx-xxtx-info-a-right">${supplies.name!}</span>
                </div>
            </li>
            <li>
                <div class="yx-xxtx-info-a">
                    <span class="yx-xxtx-info-a-left">物品规格</span>
                    <span class="yx-xxtx-info-a-right">${supplies.spec!}</span>
                </div>
            </li>
            <li>
                <div class="yx-xxtx-info-a">
                    <span class="yx-xxtx-info-a-left">单价</span>
                    <span class="yx-xxtx-info-a-right">${supplies.price!}（元）</span>
                </div>
            </li>
            <li>
                <div class="yx-xxtx-info-a">
                    <span class="yx-xxtx-info-a-left">限购数量</span>
                    <span class="yx-xxtx-info-a-right">${supplies.maxNum!}</span>
                </div>
            </li>
            <li>
                <div class="yx-xxtx-info-a">
                    <span class="yx-xxtx-info-a-left">订购数量</span>
                    <div class="form-right">
                        <input name="num" value="${(suppliesStdItem.num)!}" placeholder="请输入订购数量" class="v_supplies_num" required/>
                    </div>
                </div>
            </li>
        </ul>
    </div>
    <div class="yx-jiange-2em"></div>
    <div class="yx-model-btnmodel">
        <input type="hidden" name="supplies.id" value="${supplies.id}"/>
        <button type="submit" class="yx-model-bluebtn">提交</button>
        [#if suppliesStdItem?? && suppliesStdItem.id??]
            <a href="${b.url("!remove")}&id=${suppliesStdItem.id!}" data-role="button" data-shadow="false"
               style="background-color:  #FF8080; color: #fff; letter-spacing: 1em; text-indent: 1em;">删除</a>
        [/#if]
    </div>
</form>
    [@m.validation/]
<script src="${base}/static/yxxt/mobile/scripts/form.js"></script>
<script>
    $(function () {
        jQuery.validator.addClassRules("v_supplies_num", {
            digits: true,
            min: 1,
            max:${supplies.maxNum!},
        });
        $(".clothesStudentForm").initForm();
    });
</script>
[/@]