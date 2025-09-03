[#ftl]
[#import "/template/mobile.ftl" as m/]
[@m.body title="新生信息" back=b.url("index?t=1")]
<div class="yx-xxtx">
    <h3 class="yx-xxtx-title yx-xxtx-title-center">${(student.name)!} 同学, 欢迎您！</h3>
    <ul data-role="listview" class="yx-xxtx-info">
        <li>
            <div class="yx-xxtx-info-a">
                <span class="yx-xxtx-info-a-left">学院</span>
                <span class="yx-xxtx-info-a-right">${(student.department.name)!}</span>
            </div>
        </li>
        <li>
            <div class="yx-xxtx-info-a">
                <span class="yx-xxtx-info-a-left">专业</span>
                <span class="yx-xxtx-info-a-right">${(student.major.name)!}</span>
            </div>
        </li>
        <li>
            <div class="yx-xxtx-info-a">
                <span class="yx-xxtx-info-a-left">班级</span>
                <span class="yx-xxtx-info-a-right">${(student.adminClass.name)!}</span>
            </div>
        </li>
        [#assign instructor = ((student.adminClass.instructors[0])!)/]
        <li>
            <div class="yx-xxtx-info-a">
                <span class="yx-xxtx-info-a-left">辅导员</span>
                <span class="yx-xxtx-info-a-right">${(instructor.name)!}</span>
            </div>
        </li>
        <li>
            <div class="yx-xxtx-info-a">
                <span class="yx-xxtx-info-a-left">辅导员手机</span>
                <span class="yx-xxtx-info-a-right">${(instructor.mobile)!}</span>
            </div>
        </li>
        <li>
            <div class="yx-xxtx-info-a">
                <span class="yx-xxtx-info-a-left">辅导员邮箱</span>
                <span class="yx-xxtx-info-a-right">${(instructor.email)!}</span>
            </div>
        </li>
    </ul>
    <h3 class="yx-xxtx-title">校友信息</h3>
    <ul data-role="listview" class="yx-xxtx-info">
        <li>
            [#if (adminClassNum!0) gt 1]
                <a href="${b.url("!students")}&scope=adminClass" data-transition="slide"
                   class="yx-xxtx-info-a yx-xxtx-info-b yx-xxtx-info-b-grey">
                    <span class="yx-xxtx-info-a-left">同班级</span>
                    <span class="yx-xxtx-info-a-right"><b class="yx-xxtx-info-b-data">${adminClassNum - 1}</b> 位</span>
                </a>
            [#else]
                <div class="yx-xxtx-info-a">
                    <span class="yx-xxtx-info-a-left">同班级</span>
                    <span class="yx-xxtx-info-a-right"><b class="yx-xxtx-info-b-data">0</b> 位</span>
                </div>
            [/#if]
        </li>
        <li>
            <div class="yx-xxtx-info-a">
                <span class="yx-xxtx-info-a-left">同专业</span>
                <span class="yx-xxtx-info-a-right"><b class="yx-xxtx-info-b-data">${(majorNum - 1)!0}</b> 位</span>
            </div>
        </li>
        <li>
            <div class="yx-xxtx-info-a">
                <span class="yx-xxtx-info-a-left">同学院</span>
                <span class="yx-xxtx-info-a-right"><b class="yx-xxtx-info-b-data">${(departmentNum - 1)!0}</b> 位</span>
            </div>
        </li>
        <li>
            [#if (countyNum!0) gt 1]
                <a href="${b.url("!students")}&scope=county" data-transition="slide"
                   class="yx-xxtx-info-a yx-xxtx-info-b yx-xxtx-info-b-grey">
                    <span class="yx-xxtx-info-a-left">同(区)县</span>
                    <span class="yx-xxtx-info-a-right"><b class="yx-xxtx-info-b-data">${countyNum - 1}</b> 位</span>
                </a>
            [#else]
                <div class="yx-xxtx-info-a">
                    <span class="yx-xxtx-info-a-left">同(区)县</span>
                    <span class="yx-xxtx-info-a-right"><b class="yx-xxtx-info-b-data">0</b> 位</span>
                </div>
            [/#if]
        </li>
        <li>
            <div class="yx-xxtx-info-a">
                <span class="yx-xxtx-info-a-left">同市</span>
                <span class="yx-xxtx-info-a-right"><b class="yx-xxtx-info-b-data">${(cityNum - 1)!0}</b> 位</span>
            </div>
        </li>
        <li>
            <div class="yx-xxtx-info-a">
                <span class="yx-xxtx-info-a-left">同省</span>
                <span class="yx-xxtx-info-a-right"><b class="yx-xxtx-info-b-data">${(provinceNum - 1)!0}</b> 位</span>
            </div>
        </li>
    </ul>
</div>
[/@]