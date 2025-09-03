[#ftl]
[#if errormsg??]
<div style="padding: 50px 20px;">
	<div class="alert alert-info" role="alert">${errormsg}</div>
</div>
[#else]
<div style="min-height: 500px;">
	<table class="table table-bordered">
		<thead>
		<tr>
			<th>姓名</th>
			<th>性别</th>
			<th>省份</th>
			<th>区县</th>
			<th>毕业中学</th>
			<th>手机号码</th>
		</tr>
		</thead>
		<tbody>
            [#list studentMajors as v]
			<tr align="center">
				<td>${(v.student.name)!}</td>
				<td>${(v.student.gender.name)!}</td>
				<td>${(v.student.province.name)!}</td>
				<td>${(v.student.county.name)!}</td>
				<td>${(v.student.schoolName)!}</td>
				<td>${(v.student.contact.phone)!}</td>
			</tr>
            [/#list]
		</tbody>
	</table>
</div>
[/#if]