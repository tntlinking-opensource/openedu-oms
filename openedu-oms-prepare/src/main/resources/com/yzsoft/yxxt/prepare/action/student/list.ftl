[#ftl]
[@b.head/]
[@b.grid  items=studentEnrolls var="studentEnroll"]
    [#if isDepartmentAdmin??&&isDepartmentAdmin?string('yes', 'no')=='yes']
        [@b.gridbar]
            window.stuentAction = action;
            bar.addItem("导出", action.exportDataProperty("",null, "fileName=学生信息"));
        [/@]
    [#else ]
        [@b.gridbar]
            window.stuentAction = action;
            bar.addItem("${b.text("action.new")}",action.add());
            bar.addItem("${b.text("action.modify")}",action.edit());
            bar.addItem("${b.text("action.delete")}",action.remove());
            bar.addItem("导入",action.method("importForm"));
            bar.addItem("导出", action.exportDataProperty("",null, "fileName=学生信息"));
        [/@]
    [/#if]
    [@b.row align="center"]
        [@b.boxcol width="1%"/]
        [@b.col property="batch.name" title="招生批次" /]
        [@b.col property="student.department.name" title="学院" /]
        [@b.col property="student.major.name" title="专业" /]
        [@b.col property="student.adminClass.name" title="班级" /]
    [#--[@b.col property="grade" title="年级" /]--]
        [@b.col property="code" title="学号" /]
        [@b.col property="student.name" title="姓名" /]
        [@b.col property="student.gender.name" title="性别" /]
        [@b.col property="student.cardcode" title="身份证号" /]
        [@b.col property="student.info.nation.name" title="民族" sortable="false"/]
        [@b.col property="student.enrollType.name" title="招生类别" /]
        [@b.col property="student.enrollSourceType.name" title="生源性质" /]
        [@b.col property="student.education.name" title="学历层次" /]
        [@b.col property="student.phone" title="学生电话" /]
        [@b.col property="student.noticed" title="是否同意" align="center"][@c.boolean studentEnroll.student.noticed/][/@]
    [#--[@b.col property="birthday" title="出生日期"]${(student.birthday?date)!}[/@]--]
    [#--[@b.col property="schoolName" title="毕业学校" /]--]
    [#--[@b.col property="enrollType.name" title="招生类别" /]--]
    [#--[@b.col property="major.name" title="专业" /]--]
    [#--[@b.col property="province.name" title="省" /]--]
    [#--[@b.col property="county.name" title="市" /]--]
    [#--[@b.col property="county.name" title="区县" /]--]
    [#--[@b.col property="fatherPhone" title="父亲电话" /]--]
    [#--[@b.col property="motherPhone" title="母亲电话" /]--]
    [#--[@b.col property="noticed" title="是否通知" align="center"][@c.boolean student.noticed/][/@]--]
    [#--[@b.col property="enrolled" title="是否录取" align="center"][@c.boolean student.enrolled/][/@]--]
    [/@]
[/@]
[@b.form action="student-print" class="studentPrintForm" target="_blank"]
    <input type="hidden" name="method" value="" class="method"/>
[/@]
[@b.form action="student-notice-import!importForm" class="studentNoticeImportForm"]
[/@]
[@b.form action="student-enroll-import!importForm" class="studentEnrollImportForm"]
[/@]
<div class="modal fade create_student_modal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body">
                <p>学生名单生成中...</p>
            </div>
            <div class="modal-footer" style="display: none;">
                <button type="button" class="btn btn-primary" data-dismiss="modal">确定</button>
            </div>
        </div>
    </div>
</div>
[#--<div>
    <p>说明：生成录取学生帐号时，密码默认为身份证后六位。</p>
</div>--]
[@b.foot/]