[#ftl]
[#include "../comm/macro.ftl"/]
[@index code="01"]
<div class="tab-content">
    <div class="tab-pane active" id="tab_6_1">
        <h3 class="caption">信息查看</h3>
        <div class="row">
            <div class="col-md-12">
                <h2 class="text-center"><span class="font-red-mint bold">欢迎您</span> ${(student.name)!} 同学</h2>
            <p class="font-grey-silver text-center font-example1">
                [#if (studentHome.province)?? ||  (studentHome.city)?? ||  (studentHome.county)??]
                    来自 ${(studentHome.province.name)!} ${(studentHome.city.name)!} ${(studentHome.county.name)!}</p>
                [/#if]
                [#if student.adminClass?? || student.major??]
                    <p class="font-dark text-center bold font-example1">
                        您被录取到
                        [#if systemConfig.hasDepartment && student.department??]${(student.department.name)!}（院系）[/#if]
                        [#if student.major??]${(student.major.name)!}（专业）[/#if]
                        [#if (student.direction)??]${(student.direction.name)!}（专业方向）[/#if]
                        [#if (student.adminClass)??]${(student.adminClass.name)!}（班级）[/#if]
                    </p>
                [/#if]
                <p class="margin-top-40">您的本届校友信息（点击同班级数字可查看详细信息）</p>
                <div class="portlet light bg-grey-cararra">
                    <div class="row">
                        <div class="col-md-4">
                            <img src="${base}/static/metronic/assets/yz_yxwz_css/xx-icon_03.png">
                            <span>
								同班级：
                                [#if (adminClassNum!0) gt 1]
                                    <a href="${b.url("!students")}&scope=adminClass" data-toggle="modal"
                                       data-target="#modal"
                                       class="font-red-mint bold stunumber">${(adminClassNum - 1)!0}</a>&nbsp;位
                                [#else]
                                    <span class="font-red-mint bold">0</span>位
                                [/#if]
							</span>
                        </div>
                        <div class="col-md-4">
                            <img src="${base}/static/metronic/assets/yz_yxwz_css/xx-icon_05.png">
                            <span>同专业：<span class="font-red-mint bold">${(majorNum - 1)!0}</span>&nbsp;位</span>
                        </div>
                        [#if systemConfig.hasDepartment]
                            <div class="col-md-4">
                                <img src="${base}/static/metronic/assets/yz_yxwz_css/xx-icon_07.png">
                                <span>同学院：<span class="font-red-mint bold">${(departmentNum - 1)!0}</span>&nbsp;位</span>
                            </div>
                        [/#if]
                    </div>
                    <div class="row margin-top-20">
                        <div class="col-md-4">
                            <img src="${base}/static/metronic/assets/yz_yxwz_css/xx-icon_12.png">
                            <span>同区（县）：
                                [#if (countyNum!0) gt 1]
                                    <a href="${b.url("!students")}&scope=county"
                                       data-toggle="modal" data-target="#modal"
                                       class="font-red-mint bold stunumber">${(countyNum - 1)!0}</a>&nbsp;位
                                [#else]
                                    <span class="font-red-mint bold">0</span>位
                                [/#if]
                        </div>
                        <div class="col-md-4">
                            <img src="${base}/static/metronic/assets/yz_yxwz_css/xx-icon_13.png">
                            <span>同市：<span class="font-red-mint bold">${(cityNum - 1)!0}</span>&nbsp;位</span>
                        </div>
                        <div class="col-md-4">
                            <img src="${base}/static/metronic/assets/yz_yxwz_css/xx-icon_14.png">
                            <span>同省：<span class="font-red-mint bold">${(provinceNum - 1)!0}</span>&nbsp;位</span>
                        </div>
                    </div>
                </div>
                [#if (student.adminClass.instructor)??]
                    [#assign teacher = student.adminClass.instructor/]
                    <p class="margin-bottom-10">您的辅导员是: ${(teacher.name)!}</p>
                    <p class="margin-top-10 margin-bottom-10">Email: ${(teacher.email)!}</p>
                    <p class="margin-top-10 margin-bottom-10">Tel: ${(teacher.phone)!}</p>
                    <div class="text-center margin-bottom-40">
                        <a href="${base}/web/ask/student.action" role="button" class="btn green">在线咨询</a>
                    </div>
                [/#if]
            </div>
        </div>
    </div>
</div>
<div id="modal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
[/@index]