[#ftl]
[@b.head/]
[@b.grid  items=suppliesStds var="suppliesStd"]
    [@b.gridbar]
    //bar.addItem("新建",action.add());
    //bar.addItem("修改",action.edit());
    //bar.addItem("删除",action.remove());
    bar.addItem("导出",action.exportData("student.code:学号,student.name:姓名,student.adminClass.name:班级,num:数量,total:金额,agree:是否购买",null,"&fileName=生活用品"));
    bar.addItem("统计",action.method("count"));
    [/@]
    [@b.row align="center"]
        [@b.boxcol/]
        [@b.col title="学号" property="student.code"]
            [@b.a href="!info?id=${suppliesStd.id}"]${(suppliesStd.student.code)!}[/@]
        [/@]
        [@b.col title="姓名" property="student.name" /]
        [#--[@b.col title="学院" property="student.department.name"/]--]
        [#--[@b.col title="专业" property="student.major.name"/]--]
        [@b.col title="班级" property="student.adminClass.name"/]
        [@b.col title="数量" property="num"/]
        [@b.col title="金额" property="total"/]
        [@b.col title="是否购买" property="agree"][@c.boolean suppliesStd.agree/][/@]
        [@b.col title="是否领取" property="agree"][@c.boolean suppliesStd.used/][/@]
    [/@]
[/@]
[@b.foot/]