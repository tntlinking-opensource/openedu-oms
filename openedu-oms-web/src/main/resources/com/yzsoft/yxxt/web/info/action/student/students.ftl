[#ftl]
[#if errormsg??]
<div style="padding: 50px 20px;">
	<div class="alert alert-info" role="alert">${errormsg}</div>
</div>
[#else]
<div style="min-height: 500px; padding: 15px;">
	<table class="table table-bordered">
		<thead>
		<tr>
			<th>姓名</th>
			<th>性别</th>
			<th>省份</th>
			<th>区县</th>
			<th>学院</th>
			<th>专业</th>
			<th>班级</th>
			<th>毕业中学</th>
			<th>手机号码</th>
			<th>QQ号码</th>
		</tr>
		</thead>
		<tbody>
            [#list students as student]
			<tr align="center">
				<td>${(student.name)!}</td>
				<td>${(student.gender.name)!}</td>
				<td>${(student.home.province.name)!}</td>
				<td>${(student.home.county.name)!}</td>
				<td>${(student.department.name)!}</td>
				<td>${(student.major.name)!}</td>
				<td>${(student.adminClass.name)!}</td>
				[#--<td>${(student.info.schoole)!}</td>--]
				<td>${(student.graduateSchool)!}</td>
				<td>${(student.phone)!}</td>
				<td>${(student.qq)!}</td>
			</tr>
            [/#list]
		</tbody>
	</table>
</div>
[/#if]