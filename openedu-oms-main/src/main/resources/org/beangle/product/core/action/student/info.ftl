[#ftl]
[@b.head/]
[@b.toolbar title="学生信息"]bar.addBack();[/@]
<style>
    .basictable .basictable_left{text-align: right;}
</style>
[@b.form theme="sinfo"]
<table cellpadding="0" cellspacing="0" border="0" class="basictable table table-bordered table-striped" width="100%">
    <tbody>
    <tr>
        <td width="20%" class="basictable_left">学号：</td>
        <td width="20%" class="basictable_right">${(student.code)!}</td>
        <td width="20%" class="basictable_left">姓名：</td>
        <td width="20%" class="basictable_right">${(student.name)!}</td>
        <td width="20%" rowspan="5">
            [#if student.photo??]
                <img src="${base}/common/download.action?uuid=${student.photo}"
                     style="max-height: 160px; max-width: 180px; min-height: 100px;"/>
            [#else ]
                <div style="padding: 50px 0;font-size: 20px; text-align: center;">
                    还没有上传照片
                </div>
            [/#if]
        </td>
    </tr>
    <tr class="basictable_jiange">
        <td class="basictable_left">性别：</td>
        <td class="basictable_right">${(student.gender.name)!}</td>
        <td class="basictable_left">年级：</td>
        <td class="basictable_right">${(student.grade)!}</td>
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
    <tr>
        <td class="basictable_left">入学方式：</td>
        <td class="basictable_right">${(student.enrollType.name)!}</td>
        <td class="basictable_left">入校时间：</td>
        <td class="basictable_right">${(student.enrollmentDate?date)!}</td>
    </tr>
    <tr class="basictable_jiange">
        <td class="basictable_left">培养方式：</td>
        <td class="basictable_right">${(student.trainType.name)!}</td>
        <td class="basictable_left">学籍状态：</td>
        <td class="basictable_right">${(student.status)!}</td>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td class="basictable_left">学籍是否有效：</td>
        <td class="basictable_right">${(student.registed?string("是","否"))!}</td>
        <td class="basictable_left">是否在校：</td>
        <td class="basictable_right">${(student.inSchooled?string("是","否"))!}</td>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td class="basictable_left">证件类型：</td>
        <td class="basictable_right">${(student.cardType)!}</td>
        <td class="basictable_left">证件号：</td>
        <td class="basictable_right">${(student.cardcode)!}</td>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td class="basictable_left">生源地：</td>
        <td class="basictable_right">${(student.enrollSource)!}</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
    </tr>
    </tbody>
</table>
[/@]
[@b.foot/]