[#ftl]
[#include "../comm/lib.ftl"/]

[@body title="物品发放"]
    [@b.form id="studentSearchForm" name="searchForm"  action="!search" target="datalist" title="ui.searchForm" theme="search"]
        [@b.textfield label="学号" name="student.code"/]
        [@b.textfield label="姓名" name="student.name"/]
		[@b.textfield label="编号" name="student.serial"/]
		[@b.textfield label="身份证号" name="student.cardcode"/]
		[@b.select label="是否办理" name="sfbd" items={"1":"已办理","0":"未办理"} /]
        [@b.select label="是否需要领用" name="agree" items={'1':'是','0':'否'}/]
        <input type="hidden" name="batchId" value="${batchId}"/>
        <input type="hidden" name="itemId" value="${itemId}"/>
    [/@]
    <div style="margin-top: 10px;"></div>
    [@b.div id="datalist" href="!search?batchId=${batchId}&itemId=${itemId}" /]
[/@body]