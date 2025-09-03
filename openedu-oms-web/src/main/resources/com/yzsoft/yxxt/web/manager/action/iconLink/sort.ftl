[#ftl]
[@b.head/]
[@b.toolbar title="图标链接排序"]bar.addBack();[/@]
[#include "../comm/sort_comm.ftl"/]
[@b.form action="!sortSave" title="应用排序" theme="list"]
    [@b.field]
    <ol class="isortable isortable_all">
        [#list iconLinks?sort_by("sort") as b]
            <li data-group-id="${(b.group.id)!}">
                <img src="${base}/common/download.action?uuid=${(b.img)!}" style="height: 30px;"/>
                <span>${b.name!}</span>
                <input type="hidden" name="iconLink" value="${b_index}"/>
                <input type="hidden" name="iconLink${b_index}.id" value="${b.id}"/>
                <input type="hidden" class="sort_ipt" name="iconLink${b_index}.sort" value=""/>
                <button class="up">上移</button>
                <button class="down">下移</button>
            </li>
        [/#list]
    </ol>
    [/@]
    [@b.formfoot]
	    [@b.redirectParams/]
        [@b.submit value="action.submit"/]
    [/@]
[/@]
[@b.foot/]