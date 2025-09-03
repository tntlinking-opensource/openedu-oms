[#ftl]
[#import "/template/form/utils.ftl" as fu/]
[@b.head/]
[@b.toolbar title="学生照片修改"]bar.addBack();[/@]
[@b.form action="!save" title="学生管理信息" theme="list"]
    [@fu.text label="学号"]${(student.code)!}[/@]
    [@fu.text label="姓名"]${(student.name)!}[/@]
    [@fu.text label="性别"]${(student.gender.name)!}[/@]
    [@fu.text label="学院"]${(student.department.name)!}[/@]
    [@fu.text label="所属专业"]${(student.major.name)!}[/@]
    [#if student.direction??]
        [@fu.text label="所属专业方向"]${(student.direction.name)!}[/@]
    [/#if]
    [@fu.text label="行政班"]${(student.adminClass.name)!}[/@]
    [@b.plupload label="上传照片" name="uuid" value=(student.photo)!"" dir="student/photo" width="300" type="uuid" limit="100"/]
    [@b.formfoot]
        [@b.reset/]&nbsp;&nbsp;
        [@b.submit value="action.submit" /]
        [@b.redirectParams/]
    <input type="hidden" name="oldUuid" value="${student.photo!}"/>
    <input type="hidden" name="student.id" value="${student.id!}"/>
    [/@]
<div class="alert alert-info" style="margin-top: 20px;">
    <h4>说明</h4>
    <ol>
        <li>单张上传照片大小不要超过100KB。</li>
        <li>如果学生的照片已存在，系统会将该学生的照片覆盖。</li>
    </ol>
</div>
[/@]
[@b.foot/]