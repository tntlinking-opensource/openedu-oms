[#ftl]
[@b.head/]
[#import "/com/yzsoft/dorm/comm.ftl" as bc]
[@b.grid  items=dormBeds var="dormBed"]
    [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove());
    bar.addItem("床位详情",action.info());
    bar.addItem("床位历史",action.single("historyInfo"),"fa-info","blue");
    bar.addItem("导入",action.method("importForm"));
    //床位编号、床位号、寝室编号、寝室、楼栋编号、面向层次、面向性别、租金、学号、姓名、学院、班级、是否可用
    bar.addItem("${b.text("action.export")}",action.exportData("code:床位编号,name:床位号,room.floor.building.name:宿舍楼,room.code:寝室编号,"
    			+ "room.name:寝室,room.gender:面向性别,education.name:面向层次,room.rent:租金,state:床位状态,student.code:学号,student.name:姓名,"
    			+ "student.department.name:学院,student.adminClass.name:班级,student.adminClass.classTeacher.name:辅导员,student.phone:手机,enabledStr:是否可用",null,"&fileName=床位信息"));
    bar.addItem("退宿",action.multi("checkOutInterim", "确认要对所选床位进行退宿处理吗?"),'fa-remove','red');
    [/@]
    [@b.row]
        [@b.boxcol/]
        [@b.col title="床位编号" property="code" align="center"]
        	[@b.a href="!info?dormBed.id=${dormBed.id}"]${dormBed.code}[/@]
        [/@]
        [@b.col title="床位号" property="name" align="center"/]
        [@b.col title="宿舍楼" property="room.floor.building.name" align="center"/]
        [@b.col title="寝室" property="room.name" align="center"/]
        [@b.col title="面向性别" property="room.gender" align="center"/]
        [@b.col title="面向层次" property="education.name" align="center"/]
        [@b.col title="租金" property="room.rent" align="center"/]
        [@b.col title="床位状态" property="state" align="center"]
        	[@bc.bedState dormBed.state/]
        [/@]
        [@b.col title="学号" property="student.code" align="center"/]
        [@b.col title="姓名" property="student.name" align="center"/]
        [@b.col title="学院" property="student.department.name" align="center"/]
        [@b.col title="班级" property="student.adminClass.name" align="center"/]
        [@b.col title="辅导员" property="student.adminClass.instructor.name" align="center"/]
        [@b.col title="手机" property="student.phone" align="center"/]
        [@b.col title="是否可用" property="enabled" align="center"][@c.sfyx enabled=dormBed.enabled yes="是" no="否"/][/@]
    [/@]
[/@]
[@b.foot/]