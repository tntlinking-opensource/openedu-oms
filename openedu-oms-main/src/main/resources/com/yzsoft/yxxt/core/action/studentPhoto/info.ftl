[#ftl]
[@b.head/]
[@b.toolbar title="学生信息"]bar.addBack();[/@]
[@b.form theme="sinfo"]
<style>
    .basictable_left{text-align: right;}
</style>
<table cellpadding="0" cellspacing="0" border="0" class="basictable">
    <tbody>
    <tr>
        <td width="20%" class="basictable_left">学号：</td>
        <td width="20%" class="basictable_right">${(student.code)!}</td>
        <td width="20%" class="basictable_left">姓名：</td>
        <td width="20%" class="basictable_right">${(student.name)!}</td>
        <td width="20%" rowspan="5">
            [#if student?? && student.photo??]
                <img src="${base}/common/download.action?uuid=${student.photo}"
                     style="max-height: 500px; max-width: 300px;"/>
            [#else ]
                <div style="padding: 50px 0;font-size: 20px; text-align: center;">
                    还没有上传照片
                </div>
            [/#if]
        </td>
    </tr>
    <tr class="basictable_jiange">
        <td class="basictable_left">年级：</td>
        <td class="basictable_right">${(student.grade)!}</td>
        <td class="basictable_left">性别：</td>
        <td class="basictable_right">${(student.gender.name)!}</td>
    </tr>
    <tr>
        <td class="basictable_left">学院：</td>
        <td class="basictable_right">${(student.department.name)!}</td>
        <td class="basictable_left">专业：</td>
        <td class="basictable_right">${(student.major.name)!}</td>
    </tr>
    <tr class="basictable_jiange">
        <td class="basictable_left">班级：</td>
        <td class="basictable_right">${(student.adminClass.name)!}</td>
        <td class="basictable_left">学制：</td>
        <td class="basictable_right">${(student.duration)!}</td>
    </tr>
    </tbody>
</table>
[/@]
[@b.foot/]