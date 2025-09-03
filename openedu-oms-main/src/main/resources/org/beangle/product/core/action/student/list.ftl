[#ftl]
[@b.head/]
[@b.grid  id="studentTable" items=students var="student" configs=listConfig]
	    [#if isDepartmentAdmin??&&isDepartmentAdmin?string('yes', 'no')=='yes']
			[@b.gridbar title="菜单列表"]
				bar.addItem("${b.text("action.new")}",action.add());
				bar.addItem("${b.text("action.edit")}",action.edit());
				//bar.addItem("${b.text("action.delete")}",action.remove());
				bar.addItem("${b.text("action.info")}",action.info());
				bar.addItem("导入",action.method("importForm"));
				bar.addItem("${b.text("action.export")}",action.exportData("code:学号,name:姓名,gender.name:性别,grade:年级,adminClass.name:班级,department.name:学院,major.name:专业,inSchooled:是否在校",null,"&fileName=学生信息"));
				bar.addItem("同步", "syncData()","fa-random","blue");

				bar.addItem("过滤字段", function (){$("#studentTable").filterTd("config");},"fa-gear","green");
				$(function (){ $("#studentTable").filterTd();});

				function syncData(){
				jQuery.colorbox({
				overlayClose:false,
				iframe : true,
				width : "500px",
				height : "170px",
				href : "${b.url('!sync')}"
				});
				}
			[/@]
	    	[#else ]
				[@b.gridbar title="菜单列表"]
					bar.addItem("${b.text("action.new")}",action.add());
					bar.addItem("${b.text("action.edit")}",action.edit());
					bar.addItem("${b.text("action.delete")}",action.remove());
					bar.addItem("${b.text("action.info")}",action.info());
					bar.addItem("导入",action.method("importForm"));
					bar.addItem("${b.text("action.export")}",action.exportData("code:学号,name:姓名,gender.name:性别,grade:年级,adminClass.name:班级,department.name:学院,major.name:专业,inSchooled:是否在校",null,"&fileName=学生信息"));
					bar.addItem("同步", "syncData()","fa-random","blue");

					bar.addItem("过滤字段", function (){$("#studentTable").filterTd("config");},"fa-gear","green");
					$(function (){ $("#studentTable").filterTd();});

					function syncData(){
					jQuery.colorbox({
					overlayClose:false,
					iframe : true,
					width : "500px",
					height : "170px",
					href : "${b.url('!sync')}"
					});
					}
				[/@]
	    [/#if]

	[@b.row]
		[@b.boxcol/]
		[@b.col property="code" title="学号"]
			[@b.a href="!info?student.id=${student.id!}"]${(student.code)!}[/@]
		[/@]
		[@b.col property="name" title="姓名"/]
		[@b.col property="gender.name" align="center" title="性别"/]
		[@b.col property="grade" title="年级"/]
		[@b.col property="adminClass.name" title="班级"/]
		[@b.col property="department.name" title="学院"/]
		[@b.col property="major.name" title="专业"/]
		[@b.col property="campus.name" title="校区"/]
		[@b.col property="graduateYear" title="毕业年份"/]
		[@b.col property="inSchooled" title="是否在校" align="center"]
			[@c.sfyx enabled=student.inSchooled yes="在校" no="离校" /]
		[/@]
	[/@]
[/@]
[@b.foot/]