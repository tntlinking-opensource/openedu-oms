[#ftl]
[#import "/template/form/utils.ftl" as fu/]
[@b.head/]
[@b.toolbar title="学生班级修改"]bar.addBack();[/@]
[@b.form action="!save" theme="list"]
    [@b.field label="学号"][@fu.text]${(studentClass.student.enrollCode)!}[/@][/@]
    [@b.field label="姓名"][@fu.text]${(studentClass.student.name)!}[/@][/@]
    [@b.field label="院系"][@fu.text]${(studentClass.student.major.department.name)!}[/@][/@]
    [@b.field label="专业"][@fu.text]${(studentClass.student.major.name)!}[/@][/@]
    [@b.field label="当前班级"][@fu.text]${(studentClass.adminClass.name)!}[/@][/@]
    [@b.select label="修改后班级"  name="studentClass.adminClass.id" items=adminClasss/]
    [@b.formfoot]
        <input type="hidden" name="studentClass.id" value="${(studentClass.id2)!}"/>
        [@b.redirectParams/]
        [@b.submit value="action.submit" onsubmit="submitForm"/]
    [/@]
[/@]
<script>
    function submitForm(form) {
        form = $(form);
        if(form.valid()){
            return true;
        }
        return false;
    }
</script>
[@b.foot/]