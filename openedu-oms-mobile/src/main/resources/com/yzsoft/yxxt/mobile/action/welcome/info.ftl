[#ftl]
[#import "/template/mobile.ftl" as m/]

[@m.body title="我的二维码" back="back"]
<div class="ui-content" style="text-align: center; background-color: #fff; height: 100vh;">
    [#if qrcode??]
        <div>
            <img src="data:image/jpeg;base64,${qrcode!}" style="width: 70%; margin-bottom: 50px;"/>
        </div>
    [/#if]
    [#if barcode??]
        <div>
            <img src="data:image/jpeg;base64,${barcode!}" style="width: 80%;"/>
        </div>
    [/#if]
</div>
[/@]
