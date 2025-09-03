[#ftl]
[#import "/template/mobile.ftl" as m/]
[@m.body title="校友信息" back=b.url("!index")]
    [#if errormsg??]
    <div style="padding: 50px 20px;">
        <div class="alert alert-info" role="alert">${errormsg}</div>
    </div>
    [#else]
    <div class="yx-xxtx">
        <ul>
            [#list students as student]
                <li>
                    <div class="yx-model-xx">
                        <p>${(student.name)!} ${(student.gender.name)!} ${(student.home.province.name)!} ${(student.home.city.name)!} ${(student.home.county.name)!}</p>
                        <p class="margin-top-em">微信：${(student.wechat)!} QQ：${(student.qq)!}</p>
                    </div>
                </li>
            [/#list]
        </ul>
    </div>
    [/#if]
[/@]