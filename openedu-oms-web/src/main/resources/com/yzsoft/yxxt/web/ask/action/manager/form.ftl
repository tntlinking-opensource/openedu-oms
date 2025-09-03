[#ftl]
[#import "/template/form/utils.ftl" as fu/]
[@b.head/]
[@b.toolbar title="常见问题"]bar.addBack();[/@]
[@b.form action="!save" theme="form"]
    [@b.textfield label="排序" name="askCommon.sort" value="${askCommon.sort!}" required="true" maxlength="4" iclass="v_integer"/]
    [@b.textarea label="咨询内容" name="askCommon.content" value="${askCommon.content!}"
        required="true" maxlength="300"/]
    [@b.textarea label="回复内容" name="askCommon.replyContent" value="${askCommon.replyContent!}"
        required="true" maxlength="300"/]
    [@b.radios label="是否置顶"  name="askCommon.top" value=askCommon.top items="1:是,0:否"/]
    [@b.radios label="状态"  name="askCommon.enabled" value=askCommon.enabled items="1:启用,0:禁用"/]
    [@b.formfoot]
    <input type="hidden" name="askCommon.id" value="${askCommon.id!}"/>
        [@b.redirectParams/]
        [@b.submit value="action.submit"/]
    [/@]
[/@]
[@b.foot/]