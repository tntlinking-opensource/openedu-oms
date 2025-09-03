[#ftl]
[@b.head/]
[@b.toolbar title="招生批次" entityId=batch.id!0]bar.addBack();[/@]
[@b.form action="!save" theme="form"]
    [@b.textfield label="学年" name="batch.year" value="${batch.year!}" required="true" maxlength="10"/]
    [@b.select label="学期" required="true" name="batch.term.id" empty="请选择..." value=(batch.term.id)!  items=terms option="id,name"/]
    [@b.startend label="起止日期" name="batch.startTime,batch.endTime" start=batch.startTime end=batch.endTime
    format="date" required="true,true"/]
    [@b.select2 label="开放专业" name1st="major" name2nd="majorIds"
        items1st=majors items2nd=batch.majors option="id,name"/]
    [@b.textarea label="备注" name="batch.remark" value=(batch.remark)! cols="50" rows="2" maxlength="100"/]
    [@b.formfoot]
    <input type="hidden" name="batch.id" value="${batch.id!}"/>
        [@b.redirectParams/]
        [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
[/@]
[@b.foot/]