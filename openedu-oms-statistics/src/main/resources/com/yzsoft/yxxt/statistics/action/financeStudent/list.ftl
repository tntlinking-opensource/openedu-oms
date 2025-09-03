[#ftl]
[@b.head/]
[@b.grid  items=financeStudentViews var="financeStudentView"]
	[@b.gridbar]
		bar.addItem("${b.text("action.export")}",action.exportData("student.code:学号,student.name:姓名,financeStudent.feeTotal:应缴金额,financeStudent.feePaid:已缴金额,financeStudent.feeOdd:未缴金额",null,"&fileName=财务缴费明细"));
	[/@]
	[@b.row align="center"]
	[#--[@b.boxcol/]--]
		[@b.col property="student.department.name" title="院系" /]
		[@b.col property="student.major.name" title="专业" /]
		[@b.col property="student.adminClass.name" title="班级" /]
		[@b.col property="student.code" title="学号" /]
		[@b.col property="student.name" title="姓名" /]
		[@b.col property="student.gender.name" title="性别" /]
		[@b.col property="student.education.name" title="学历层次" /]
		[@b.col property="financeStudent.feeTotal" title="应缴金额" /]
		[@b.col property="financeStudent.feePaid" title="已缴金额" /]
		[@b.col property="financeStudent.feeOdd" title="未缴金额" /]
	[/@]
[/@]
<div style="background-color: #fff; padding: 10px; margin-top: -50px;">
	<table class="table table-bordered table-striped" style="text-align: center;">
		<thead>
		<tr>
			<th></th>
			<th>应缴金额</th>
			<th>已缴金额</th>
			<th>未缴金额</th>
		</tr>
		</thead>
		<tbody>
		<tr>
			<td>合计</td>
			<td>${feeTotal()}</td>
			<td>${feePaid()}</td>
			<td>${feeOdd()}</td>
		</tr>
		</tbody>
	</table>
</div>

[#function feeTotal]
	[#assign num = 0/]
	[#list financeStudentViews as v]
		[#if v.financeStudent?? && v.financeStudent.feeTotal??]
			[#assign num = num + v.financeStudent.feeTotal/]
		[/#if]
	[/#list]
	[#return num/]
[/#function]
[#function feePaid]
	[#assign num = 0/]
	[#list financeStudentViews as v]
		[#if v.financeStudent?? && v.financeStudent.feePaid??]
			[#assign num = num + v.financeStudent.feePaid/]
		[/#if]
	[/#list]
	[#return num/]
[/#function]
[#function feeOdd]
	[#assign num = 0/]
	[#list financeStudentViews as v]
		[#if v.financeStudent?? && v.financeStudent.feeOdd??]
			[#assign num = num+ v.financeStudent.feeOdd/]
		[/#if]
	[/#list]
	[#return num/]
[/#function]
[@b.foot/]