[#ftl]
[#import "/template/form/utils.ftl" as fu/]
[@b.head/]
[@b.toolbar title="学生院系修改"]bar.addBack();[/@]
[@b.form action="!save" theme="list"]
    [@b.field label="学号"][@fu.text]${(studentMajor.student.code)!}[/@][/@]
    [@b.field label="姓名"][@fu.text]${(studentMajor.student.name)!}[/@][/@]
    [@b.field label="当前院系专业"][@fu.text]${(studentMajor.major.department.name)!} ${(studentMajor.major.name)!}[/@][/@]
    [@b.field label="修改后院系专业" required="true"]
    <style>
        .i_div select { min-width: 150px; }
    </style>
    <div class="i_div form-inline">
        <select class="form-control e_department">
            <option>请选择...</option>
        </select>
        <select name="studentMajor.major.id" class="form-control e_major" required>
            <option>请选择...</option>
        </select>
    </div>
    [/@]
    [@b.formfoot]
    <input type="hidden" name="studentMajor.id" value="${(studentMajor.id)!}"/>
        [@b.redirectParams/]
        [@b.submit value="action.submit" onsubmit="submitForm"/]
    [/@]
[/@]
<script src="${base}/static/yxxt/scripts/major.js"></script>
<script>
    $(function () {
        $(".e_department").loadDepartment().cascadeMajor(".e_major");
    });

    function submitForm(form) {
        form = $(form);
        if (form.valid()) {
            return true;
        }
        return false;
    }
</script>
[@b.foot/]