[#ftl]
[@b.head/]
[@b.grid  items=adminClasss var="adminClass"]
    [@b.gridbar]
    window.stuentAction = action;
    bar.addItem("分班",action.method("allocSave"), "fa-edit", "blue");
    bar.addItem("生成学号",action.multi("createXh"), "fa-edit", "blue");
	bar.addItem("导出班级信息",action.exportData("班级代码,班级名称,培养类型代码,学生类别代码,年级,学制,院系部代码,专业代码,专业方向代码,计划人数,班主任,导师,专用教室",null, "fileName=班级信息"));
    bar.addItem("导出班级学生信息",action.method("exportStudentClass",null,"&format=xls&propertiesExport=adminClass.code:班级代码,student.code:学生学号&fileName=班级学生信息",true));
	bar.addItem("导入",action.method("importForm"));
    [/@]
    [@b.row align="center"]
    	[@b.boxcol/]
		[@b.col property="grade" title="年级"/]
       	[@b.col property="code" title="班级代码" /]
		[@b.col property="name" title="班级名称"/]
		[@b.col property="department.name" title="学院"/]
		[@b.col property="major.name" title="所属专业"/]
		[@b.col property="instructor.name" title="辅导员"]
			[#if adminClass.instructor??]${(adminClass.instructor.name)!}(${(adminClass.instructor.code)!})[/#if]
		[/@]
		[@b.col property="enrollType.name" title="招生类型" sortable="false"]
			[#list adminClass.enrolls as enroll]
				${(enroll.name)!}
			[/#list]
		[/@]
		[@b.col property="num" title="班级人数" /]
		[@b.col property="" title="学生数" sortable="false"]
			[@b.a href="!students?adminClass.id=${adminClass.id!}"]${(adminClass.stdNum)!0}[/@]
		[/@]
    [/@]
	<div class="modal fade create_student_modal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<p>学生名单生成中...</p>
				</div>
				<div class="modal-footer" style="display: none;">
					<button type="button" class="btn btn-primary" data-dismiss="modal">确定</button>
				</div>
			</div>
		</div>
	</div>
[/@]
[@b.foot/]