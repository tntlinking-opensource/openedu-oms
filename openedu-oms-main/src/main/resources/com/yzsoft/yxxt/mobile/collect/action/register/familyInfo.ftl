[#ftl]
[#import "/template/mobile.ftl" as m/]
[@m.body title="家庭成员" back="back" rightBtnName="编辑" rightBtnUrl=b.url("!familyEdit?id=${(studentFamily.id)!}&cardcode=${(student.cardcode)!}")]
<div class="yx-xxtx">
    <ul data-role="listview" class="yx-xxtx-info">
        <li>
            <div class="yx-xxtx-info-a">
                <span class="yx-xxtx-info-a-left">姓名</span>
                <span class="yx-xxtx-info-a-right">${(studentFamily.name)!}</span>
            </div>
        </li>
        <li>
            <div class="yx-xxtx-info-a">
                <span class="yx-xxtx-info-a-left">称谓</span>
                <span class="yx-xxtx-info-a-right">${(studentFamily.title)!}</span>
            </div>
        </li>
        <li>
            <div class="yx-xxtx-info-a">
                <span class="yx-xxtx-info-a-left">年龄</span>
                <span class="yx-xxtx-info-a-right">${(studentFamily.age)!}</span>
            </div>
        </li>
        <li>
            <div class="yx-xxtx-info-a">
                <span class="yx-xxtx-info-a-left">工作单位及地址</span>
                <span class="yx-xxtx-info-a-right">${(studentFamily.unit)!}</span>
            </div>
        </li>
        <li>
            <div class="yx-xxtx-info-a">
                <span class="yx-xxtx-info-a-left">职位</span>
                <span class="yx-xxtx-info-a-right">${(studentFamily.post)!}</span>
            </div>
        </li>
        <li>
            <div class="yx-xxtx-info-a">
                <span class="yx-xxtx-info-a-left">政治面貌</span>
                <span class="yx-xxtx-info-a-right">${(studentFamily.political.name)!}</span>
            </div>
        </li>
        <li>
            <div class="yx-xxtx-info-a">
                <span class="yx-xxtx-info-a-left">联系电话</span>
                <span class="yx-xxtx-info-a-right">${(studentFamily.phone)!}</span>
            </div>
        </li>

    </ul>
</div>
<div class="yx-jiange-1em"></div>
<div class="yx-model-btnmodel">
    <button type="button" class="yx-model-redbtn student_family_remove">删除</button>
</div>
<form id="studentFamilyForm" action="${b.url("!familyRemove")}&id=${(studentFamily.id)!}&cardcode=${(student.cardcode)!}" method="post">
</form>

<script>
    $(".student_family_remove").click(function (){
        if(confirm("是否确定删除“${(studentFamily.name)!}”？")){
           $("#studentFamilyForm").submit();
        }
    });
</script>
[/@]