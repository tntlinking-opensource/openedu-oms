[#ftl]
[#include "macro.ftl"/]
[#if !studentInfo?? && student.info??]
    [#assign studentInfo=student.info/]
[/#if]
[#if !studentContact?? && student.contact??]
    [#assign studentContact=student.contact/]
[/#if]
[#if !studentHome?? && student.home??]
    [#assign studentHome=student.home/]
[/#if]
[#list  types as type]
	[#if type.name != '未命名']
	    [@hasConfig type]
	    <h3 class="caption">${type.name}[#if studentInfoStates?? && !typeSave(type)]<span style="color: red;">（未填写）</span>[/#if]</h3>
	    <div class="row">
	        [#list configs as config]
	                [#if config.type.id == type.id]
	            [@propertyInfo config/]
	        [/#if]
	            [/#list]
	    </div>
	        [#if switch?? && switch.editable]
	        <div class="text-center" style="margin: 15px;">
	            <a href="${b.url("!edit")}&typeId=${type.id}" class="btn btn-primary">修改</a>
	        </div>
	        [/#if]
	    [/@hasConfig]
    [/#if]
[/#list]
<style>
    .itable td { text-align: center; }
</style>
<div>
    <h3 class="caption">家庭成员信息[#if !(families?? && families?size gt 0)]<span style="color: red;">（未填写）</span>[/#if]</h3>
    <div style="margin: 15px;">
    [#if families?? && families?size gt 0]
        <table class="table table-bordered itable">
            <thead>
            <tr>
                <th>姓名</th>
                <th>称谓</th>
                <th>年龄</th>
                <th>工作单位及地址</th>
                [#--<th>职位</th>--]
                <th>政治面貌</th>
                <th>联系电话</th>
            </tr>
            </thead>
            <tbody>
                [#list families as f]
                    [#if f.name??]
                    <tr>
                        <td>${(f.name)!}</td>
                        <td>${(f.title)!}</td>
                        <td>${(f.age)!}</td>
                        <td>${(f.unit)!}</td>
                        [#--<td>${(f.post)!}</td>--]
                        <td>${(f.political.name)!}</td>
                        <td>${(f.phone)!}</td>
                    </tr>
                    [/#if]
                [/#list]
            </tbody>
        </table>
    [/#if]
    </div>
[#if switch?? && switch.editable]
    <div class="text-center" style="margin: 15px;">
        <a href="${b.url("!editFamily")}" class="btn btn-primary">修改</a>
    </div>
[/#if]
</div>
[#if educationEnabled]
<div>
    <h3 class="caption">教育经历信息[#if !(educations?? && educations?size gt 0)]<span style="color: red;">（未填写）</span>[/#if]</h3>
[#if educations?? && educations?size gt 0]
    <div style="margin: 15px;">
        <table class="table table-bordered itable">
            <thead>
            <tr>
                <th>何年至何月</th>
                <th>在何地何学校</th>
                <th>任何职</th>
                <th>证明人</th>
            </tr>
            </thead>
            <tbody>
                [#list educations as e]
                <tr>
                    <td>${(e.startDate?string("yyyy-MM"))!}<span
                            style="margin: 0 10px;">至</span>${(e.endDate?string("yyyy-MM"))!}</td>
                    <td>${(e.unit)!}</td>
                    <td>${(e.post)!}</td>
                    <td>${(e.reterence)!}</td>
                </tr>
                [/#list]
            </tbody>
        </table>
    </div>
[/#if]
[#if switch?? && switch.editable]
    <div class="text-center" style="margin: 15px;">
        <a href="${b.url("!editEducation")}" class="btn btn-primary">修改</a>
    </div>
[/#if]
</div>
[/#if]