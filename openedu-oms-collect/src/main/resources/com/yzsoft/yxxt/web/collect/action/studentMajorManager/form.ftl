[#ftl]
[#import "/template/form/utils.ftl" as fu/]
[@b.head/]
<style>
    .iform-inline .form-control { min-width: 200px; }
</style>
[@b.toolbar title="自选专业" entityId=studentMajor.id!0]bar.addBack();[/@]
[@b.form action="!save" theme="form"]
    [#if studentMajor.student?? && studentMajor.student.id??]
        [@fu.text label="学号"]${(studentMajor.student.code)!}[/@fu.text]
        [@fu.text label="姓名"]${(studentMajor.student.name)!}[/@fu.text]
    [#else]
        [@b.textfield label="学号" name="studentCode" value="" required="true" maxlength="30"/]
    [/#if]
    [#assign nums=["","一","二","三","四","五","六"]/]
    [#list 1..studentMajorNum as v]
        [#assign detail = studentMajor.getDetail(v)/]
        <input type="hidden" name="detail" value="${v}"/>
        <input type="hidden" name="detail${v}.sort" value="${v}"/>
        [@b.field label="第${v}志愿"]
        <div class="form-inline iform-inline">
            <select name="detail${v}.major.id" class="form-control f_major${v}"
                    data-val="${(studentMajor["major${v}"].id)!}" required>
                [#list majorInfos as majorInfo]
                    <option value="${(majorInfo.major.id)!}"
                            [#if majorInfo.major.id == (detail.major.id)!0]selected[/#if]>${(majorInfo.major.name)!}</option>
                [/#list]
            </select>
        </div>
        [/@b.field]
    [/#list]
    [@b.radios label="是否服从调剂" name="studentMajor.alterable" value=studentMajor.alterable!true items="1:是,0:否"/]
    [@b.formfoot]
    <input type="hidden" name="studentMajor.id" value="${studentMajor.id!}"/>
        [@b.redirectParams/]
        [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
[/@]
[@b.foot/]