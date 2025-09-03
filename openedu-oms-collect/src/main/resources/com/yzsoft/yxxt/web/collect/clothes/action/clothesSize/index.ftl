[#ftl]
[@b.head/]
[@b.form name="clothesTypeSearchForm"  action="!search?orderBy=type.code,code" target="clothesTypelist" title="ui.searchForm" theme="search"]
    [@b.textfield label="编号" name="clothesSize.code"/]
    [@b.select name="clothesSize.type.id" label="服装类型" items=types option="id,name"/]
<input type="hidden" name="orderBy" value="code"/>
[/@]
[@b.div id="clothesTypelist" href="!search?orderBy=type.code,code" /]
[@b.foot/]