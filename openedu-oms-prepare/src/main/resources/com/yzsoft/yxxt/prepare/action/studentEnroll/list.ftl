[#ftl]
[@b.head/]
[@b.grid  items=studentEnrolls var="studentEnroll"]
    [@b.gridbar]
    window.stuentAction = action;
    [#--bar.addItem("打印通知单", 'printNotice("printNotice")');--]
    [#--bar.addItem("打印封面", 'printNotice("printFace")');--]
    [#--var menu = bar.addMenu("通知标记", null, "fa fa-plus");--]
    bar.addItem("标记为“已通知”", notice(1), "fa-check",  "green-haze");
    bar.addItem("标记为“未通知”", notice(0), "fa-close",  "red");
    [#--bar.addItem("导入通知名单", 'studentNoticeImport()', "fa-share", "green-haze");--]
    [#--var menu = bar.addMenu("录取标记", null, "fa fa-plus");--]
    bar.addItem("标记为“已录取”", enrolled(1), "fa-check",  "blue");
    bar.addItem("标记为“未录取”", enrolled(0), "fa-close",  "red");
    [#--bar.addItem("导入录取名单", 'studentEnrollImport()', "fa-share",  "blue");--]
    bar.addItem("导入",action.method("importForm"));
    bar.addItem("导出", action.exportDataProperty("",null, "fileName=学生信息"));
    [/@]
<script>
    function notice(noticed) {
        return stuentAction.multi("notice", null, "noticed=" + noticed);
    }

    function enrolled(enrolled) {
        return stuentAction.multi("enroll", null, "enrolled=" + enrolled);
    }

    function studentNoticeImport() {
        var form = $(".studentNoticeImportForm");
        bg.form.submit(form[0]);
    }

    function studentEnrollImport() {
        var form = $(".studentEnrollImportForm");
        bg.form.submit(form[0]);
    }
</script>
    [@b.row align="center"]
        [@b.boxcol width="1%"/]
        [@b.col property="batch.name" title="招生批次" /]
        [@b.col title="学院" property="student.department.name"/]
        [@b.col title="专业" property="student.major.name"/]
        [@b.col title="班级" property="student.adminClass.name"/]
    [#--[@b.col property="grade" title="年级" /]--]
        [#--[@b.col property="student.code" title="学号" /]--]
        [@b.col property="code" title="学号" /]
        [@b.col property="student.name" title="姓名" /]
        [@b.col property="student.gender.name" title="性别" /]
        [@b.col property="student.info.nation.name" title="民族" /]
        [@b.col property="student.enrollType.name" title="招生类别" /]
        [@b.col property="student.education.name" title="学历层次" /]
        [@b.col property="student.phone" title="学生电话" /]
        [@b.col property="student.cardcode" title="身份证号" /]
    [#--[@b.col property="birthday" title="出生年月"]${(student.birthday?string("yyyy-M"))!}[/@]--]
    [#--[@b.col property="schoolName" title="毕业学校" /]--]
    [#--[@b.col property="enrollType.name" title="招生类别" /]--]
    [#--[@b.col property="province.name" title="省" /]--]
    [#--[@b.col property="city.name" title="市" /]--]
    [#--[@b.col property="county.name" title="区县" /]--]
    [#--[@b.col property="fatherPhone" title="父亲电话" /]--]
    [#--[@b.col property="motherPhone" title="母亲电话" /]--]
        [@b.col property="noticed" title="是否通知" align="center"][@c.boolean studentEnroll.noticed/][/@]
        [@b.col property="enrolled" title="是否录取" align="center"][@c.boolean studentEnroll.enrolled/][/@]
    [/@]
[/@]
[@b.form action="student-notice-import!importForm" class="studentNoticeImportForm"]
[/@]
[@b.form action="student-enroll-import!importForm" class="studentEnrollImportForm"]
[/@]
[@b.foot/]