[#ftl]

[#macro sizeTable type]
    [#if type.sizeName??]
    <div class="type_div type_div_${type.id}" data-id="${type.id}">
        <h4>${type.name}尺码对照表（单位：公分）</h4>
        <input type="hidden" name="sizeid" value="" class="hide_ipt" data-name="${type.name}"/>
        [#assign colTitles = type.getColTitle()/]
        <table data-role="table" class="table table-bordered table-hover table-striped">
            <thead>
            <tr>
                [#list colTitles as ctitle]
                    <th>${ctitle}</th>
                [/#list]
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
                [#list type.sizes as size]
                <tr align="center">
                    <td>${size.name}</td>
                    [#list size.value?split(" ") as v]
                        <td>${v}</td>
                    [/#list]
                    <td>
                        <a href="#" data-role="button" data-inline="true" data-rel="back"
                           class="btn btn-default size_btn"
                           data-id="${size.id}" data-name="${size.name}"
                           onclick="sizeBtnClick(this);">
                            <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                            选择
                        </a>
                    </td>
                </tr>
                [/#list]
            </tbody>
        </table>
    </div>
    [/#if]
[/#macro]

[#function hasSize size]
    [#list clothesStudent.sizes as s]
        [#if s.size.id == size.id]
            [#return true/]
        [/#if]
    [/#list]
    [#return false/]
[/#function]