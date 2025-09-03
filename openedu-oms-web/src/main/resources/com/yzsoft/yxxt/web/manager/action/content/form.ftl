[#ftl]
[@b.head/]
[@b.toolbar title="信息" entityId=content.id!0]bar.addBack();[/@]
<link rel="stylesheet" type="text/css" href="${base}/static/scripts/webuploader/webuploader.css">
<script type="text/javascript" src="${base}/static/scripts/webuploader/webuploader.html5only.min.js"></script>
<script type="text/javascript" src="${base}/static/scripts/webuploader/webuploader.custom.js"></script>
<style>
    .column_div select { margin-right: 10px; }
    .datetimepicker { z-index: 2000 !important; }
</style>
[@b.form action="!save" title="友情链接" theme="form"]
    [@b.field label="所属栏目"]
    <div class="column_div form-inline group">
    </div>
    [/@]
    [@b.textfield label="标题" name="content.title" value="${content.title!}" required="true" maxlength="100"/]
    [@b.datepicker label="发布日期" name="content.publishTime" value=content.publishTime required="true" format="datetime"/]
    [@b.ueditor label="内容" name="content.content" value=content.content required="true"/]
    [@b.radios label="是否置顶"  name="content.stick" value=content.stick items="1:是,0:否"/]
    [@b.radios label="是否发布"  name="content.enabled" value=content.enabled items="1:是,0:否"/]
    [@b.formfoot]
    <input type="hidden" name="content.id" value="${(content.id)!}"/>
    <input type="hidden" name="content.column.id" value="${(content.column.id)!}" class="column_ipt"/>
        [@b.redirectParams/]
        [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
[/@]
<script src="${base}/static/yxxt/scripts/column.js"></script>
<script>
    $(function () {
        $(".column_div").columnSelect(".column_ipt");
    });
</script>
[@b.foot/]