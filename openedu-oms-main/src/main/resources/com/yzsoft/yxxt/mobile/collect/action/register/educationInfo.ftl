[#ftl]
[#import "/template/mobile.ftl" as m/]
[@m.body title="教育经历" back="back" rightBtnName="编辑" rightBtnUrl=b.url("!educationEdit?id=${(studentEducation.id)!}")]
<div class="yx-xxtx">
    <ul data-role="listview" class="yx-xxtx-info">
        <li>
            <div class="yx-xxtx-info-a">
                <span class="yx-xxtx-info-a-left">开始日期</span>
                <span class="yx-xxtx-info-a-right">${(studentEducation.startDate?string("yyyy-MM-dd"))!}</span>
            </div>
        </li>
        <li>
            <div class="yx-xxtx-info-a">
                <span class="yx-xxtx-info-a-left">结束日期</span>
                <span class="yx-xxtx-info-a-right">${(studentEducation.endDate?string("yyyy-MM-dd"))!}</span>
            </div>
        </li>
        <li>
            <div class="yx-xxtx-info-a">
                <span class="yx-xxtx-info-a-left">学校及地址</span>
                <span class="yx-xxtx-info-a-right">${(studentEducation.unit)!}</span>
            </div>
        </li>
        <li>
            <div class="yx-xxtx-info-a">
                <span class="yx-xxtx-info-a-left">职位</span>
                <span class="yx-xxtx-info-a-right">${(studentEducation.post)!}</span>
            </div>
        </li>
        <li>
            <div class="yx-xxtx-info-a">
                <span class="yx-xxtx-info-a-left">证明人</span>
                <span class="yx-xxtx-info-a-right">${(studentEducation.reterence)!}</span>
            </div>
        </li>
    </ul>
</div>
<div class="yx-jiange-1em"></div>
<div class="yx-model-btnmodel">
    <button type="button" class="yx-model-redbtn student_family_remove">删除</button>
</div>
<form id="studentEducationForm" action="${b.url("!educationRemove")}&id=${(studentEducation.id)!}" method="post">
</form>

<script>
    $(".student_family_remove").click(function (){
        if(confirm("是否确定删除“${(studentEducation.name)!}”？")){
           $("#studentEducationForm").submit();
        }
    });
</script>
[/@]