[#ftl]
[@yx.head title=topColumn.name/]
<div class="row">
    <div class="col-md-10 col-md-offset-1" style="padding-left:0; padding-right:0;">
        <div class="portlet light clearcss" style="padding: 15px;">
            <div class="row">
                <div class="col-md-3 col-sm-3 col-xs-3" style="padding-left:0; padding-right:0;">
                [@yx.columnLeft columnId=topColumn.id/]
                </div>
                <div class="col-md-9 col-sm-9 col-xs-9">
                    <div style="min-height: 600px;">
                        <div style="position: relative;">
                            <h4 style="text-align: center">${(content.title)!}</h4>
                            <span style="position:absolute; right:0; bottom: 0;">${(content.publishTime?date)!}</span>
                        </div>
                        <hr>
                        <style>
                            .content_detail p { margin: 10px 0; }
                            .content_detail table p { margin: 0; }
                            .selectTdClass { background-color: #edf5fa !important }
                            .content_detail table.noBorderTable td, table.noBorderTable th, table.noBorderTable caption { border: 1px dashed #ddd !important }
                            .content_detail table { margin-bottom: 10px; border-collapse: collapse; display: table; }
                            .content_detail td, th { padding: 5px 10px; border: 1px solid #DDD; }
                            .content_detail caption { border: 1px dashed #DDD; border-bottom: 0; padding: 3px; text-align: center; }
                            .content_detail th { border-top: 1px solid #BBB; background-color: #F7F7F7; }
                            .content_detail table tr.firstRow th { border-top-width: 2px; }
                            .ue-table-interlace-color-single { background-color: #fcfcfc; }
                            .ue-table-interlace-color-double { background-color: #f7faff; }
                            td p { margin: 0; padding: 0; }
                        </style>
                        <div class="content_detail" style="padding: 0 15px;">${(content.content)!}</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
[@yx.foot/]