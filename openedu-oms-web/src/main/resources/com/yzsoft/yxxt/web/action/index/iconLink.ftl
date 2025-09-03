[#ftl]
<div class="portlet-title border_style_01">
    <div class="caption">
        <span class="caption-subject font-dark  uppercase new_style">快速通道</span>
    [#--<a href="#"><span class="more_style">更多</span></a>--]
    </div>
</div>
<div class="portlet light" style="margin-top:10px; min-height:302px;">
    <!-- 快捷功能 -->
    <div class="portlet-body">
        <div class="tab-content">
        [#assign maxPage = (iconLinks?size / 9.0)?ceiling/]
        [#list 1..maxPage as row]
            <div class="tab-pane [#if row_index == 0]active[/#if] " id="portlet_tab${row}" style="min-height: 195px;">
                <div class="row" style="margin-bottom:15px;">
                    [#list iconLinks as iconLink]
                        [#if ((iconLink_index / 9)?floor + 1) == row]
                            <div class="col-md-4 col-sm-6 col-xs-3 text-center">
                                <div class="pie-chart">
                                    <a href="[@href (iconLink.url)!"#"/]">
                                        <img src="${base}/${(iconLink.img)!}"/>
                                        <p style=" margin:0 -15px; word-break: keep-all; white-space: nowrap;text-overflow: ellipsis;overflow: hidden;">${iconLink.name!}</p>
                                    </a>
                                </div>
                            </div>
                        [/#if]
                    [/#list]
                </div>
            </div>
        [/#list]
        </div>
    </div>
    <div class="tabbable-line">
        <ul class="nav nav-tabs">
        [#list 1..maxPage as row]
            <li [#if row_index == 0]class="active"[/#if]>
                <a href="#portlet_tab${row}" data-toggle="tab">${row}</a>
            </li>
        [/#list]
        </ul>
    </div>
    <!-- 快捷功能结束 -->
</div>