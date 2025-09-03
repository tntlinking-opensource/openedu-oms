[#ftl]
[@yx.head title=topColumn.name/]
<link href="${base}/static/yxxt/css/todo.css" rel="stylesheet" type="text/css"/>
<div class="row">
    <div class="col-md-10 col-md-offset-1" style="padding-left:0; padding-right:0;">
        <div class="portlet light clearcss" style="padding: 15px;">
            <div class="row">
                <div class="col-md-3 col-sm-3 col-xs-3" style="padding-left:0; padding-right:0;">
                [@yx.columnLeft columnId=topColumn.id/]
                </div>
                <div class="col-md-9 col-sm-9 col-xs-9">

                [#if contents?size gt 0]
                    <div class="todo-container">
                        <div class="todo-tasks-container">
                            <ul class="todo-tasks-content">
                                [#list  contents as content]
                                    <li class="todo-tasks-item">
                                        <h4 class="todo-inline">
                                            <a data-toggle="modal"
                                               href="${base}/web/content-detail.action?contentId=${(content.id)!}">${(content.title)!}</a>
                                        </h4>
                                        <p class="todo-inline todo-float-r">
                                            <span class="todo-red">${(content.publishTime)!}</span>
                                        </p>
                                    </li>
                                [/#list]
                            </ul>
                        </div>
                    </div>
                    <div>
                        <ul class="pagination pagination-lg pull-right">
                            [#assign page = contents/]
                            [#assign href]${base}/web/content-list.action?columnId=${(column.id)!}[/#assign]
                            [#if page.pageNo gt 1]
                                <li>
                                    <a href="${href}&pageNo=1">
                                        <i class="fa fa-angle-left"></i>
                                    </a>
                                </li>
                            [#else]
                                <li class="disabled">
                                    <a>
                                        <i class="fa fa-angle-left"></i>
                                    </a>
                                </li>
                            [/#if]
                            [#list (page.pageNo-3)..(page.pageNo+3) as v]
                                [#if v gt 0 && v lte page.maxPageNo]
                                    [#if v == page.pageNo]
                                        <li class="active">
                                            <a> ${v} </a>
                                        </li>
                                    [#else]
                                        <li>
                                            <a href="${href}&pageNo=${v}"> ${v} </a>
                                        </li>
                                    [/#if]
                                [/#if]
                            [/#list]
                            [#if page.pageNo < page.maxPageNo]
                                <li>
                                    <a href="${href}&pageNo=${page.pageNo + 1}">
                                        <i class="fa fa-angle-right"></i>
                                    </a>
                                </li>
                            [#else]
                                <li class="disabled">
                                    <a>
                                        <i class="fa fa-angle-right"></i>
                                    </a>
                                </li>
                            [/#if]
                        </ul>
                    </div>
                [#else]
                    <div class="note note-info">
                        <h4 class="block">该栏目下还没有发布信息</h4>
                    </div>
                [/#if]
                </div>
            </div>
        </div>
    </div>
</div>
[@yx.foot/]