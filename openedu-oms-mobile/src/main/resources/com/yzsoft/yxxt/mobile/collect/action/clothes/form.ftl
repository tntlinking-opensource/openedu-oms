[#ftl]
[#import "/template/comm.ftl" as c/]
[#import "/template/mobile.ftl" as m/]
[#include "../comm/macro.ftl"/]
[@m.body title=switch.name back=formBack((suppliesStd.id)??)]
<style>
    .ui-header .ui-title, .ui-footer .ui-title { margin: 0; overflow: auto; }
</style>
<form action="${b.url("!save")}" method="post" class="clothesStudentForm">
    <div class="yx-xxtx">
        <ul data-role="listview" class="yx-xxtx-info">
            <li class="yx-bg-grey">
                <div class="yx-model-zx">
                    <a href="#popupDialog" data-rel="popup" data-position-to="window" data-transition="pop">
                        <button type="button" class="yx-pop-btn">服装尺码对照表</button>
                    </a>
                    <div id="popupDialog" style="max-width: 400px;" data-role="popup" data-theme="a"
                         data-overlay-theme="b"
                         data-dismissible="false">
                        <div data-role="header" data-theme="a" class="yx-popup-head">
                            <h3 class="yx-popup-title">服装尺码对照表</h3>
                        </div>
                        <div class="ui-content yx-ui-content-nopad" role="main">
                            <div class="yx-popup-tb">
                                <table cellpadding="0" cellspacing="0" border="0" class="yx-popup-table">
                                    <thead>
                                    <tr>
                                        <th>尺码</th>
                                        <th>身高（cm）</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                        [#list clothesSizes as v]
                                        <tr>
                                            <td>${(v.name)!}</td>
                                            <td>${(v.height)!}</td>
                                        </tr>
                                        [/#list]
                                    </tbody>
                                </table>
                            </div>
                            <div class="yx-center">
                                <a class="ui-btn ui-corner-all ui-btn-inline yx-back-btn" href="#"
                                   data-rel="back">关闭</a>
                            </div>
                        </div>
                    </div>
                </div>
            </li>
            <li>
                <div class="yx-xxtx-info-a">
                    <span class="yx-xxtx-info-a-left margin-top-em">服装尺码</span>
                    <div class="form-right">
                        [@c.select name="clothesStudent.clothesSize" required="required" empty="请选择服装尺码"
                        data=clothesSizes noOption=true; clothesSize]
                            <option value="${clothesSize.name}"
                                    [#if clothesSize.name == (clothesStudent.clothesSize)!""]selected[/#if]>${(clothesSize.name)!}
                                (身高：${(clothesSize.height)!}cm)
                            </option>
                        [/@]
                    </div>
                </div>
            </li>
            <li class="yx-bg-grey">
                <div class="yx-model-zx">
                    <a href="#popupDialog2" data-rel="popup" data-position-to="window" data-transition="pop">
                        <button type="button" class="yx-pop-btn">鞋码尺码对照表</button>
                    </a>
                    <div id="popupDialog2" style="max-width: 400px;" data-role="popup" data-theme="a"
                         data-overlay-theme="b"
                         data-dismissible="false">
                        <div data-role="header" data-theme="a" class="yx-popup-head">
                            <h3 class="yx-popup-title">鞋码尺码对照表</h3>
                        </div>
                        <div class="ui-content yx-ui-content-nopad" role="main">
                            <div class="yx-popup-tb">
                                <table cellpadding="0" cellspacing="0" border="0" class="yx-popup-table">
                                    <thead>
                                    <tr>
                                        <th>尺码</th>
                                        <th>脚长（cm）</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                        [#list shoesSizes as v]
                                        <tr>
                                            <td>${(v.name)!}</td>
                                            <td>${(v.footLength)!}</td>
                                        </tr>
                                        [/#list]
                                    </tbody>
                                </table>
                            </div>
                            <div class="yx-center">
                                <a class="ui-btn ui-corner-all ui-btn-inline yx-back-btn" href="#"
                                   data-rel="back">关闭</a>
                            </div>
                        </div>
                    </div>
                </div>
            </li>
            <li>
                <div class="yx-xxtx-info-a">
                    <span class="yx-xxtx-info-a-left margin-top-em">鞋码尺码</span>
                    <div class="form-right">
                        [@c.select name="clothesStudent.shoesSize" value="${(clothesStudent.shoesSize)!}"
                        data=shoesSizes required="required" empty="请选择鞋码尺码" noOption=true; shoesSize]
                            <option value="${shoesSize.name}"
                                    [#if shoesSize.name == (clothesStudent.shoesSize)!""]selected[/#if]>${(shoesSize.name)!}(脚长：${(shoesSize.footLength)!}cm)
                            </option>
                        [/@]
                    </div>
                </div>
            </li>
        </ul>
    </div>
    <div class="yx-jiange-1em"></div>
    <div class="yx-model-btnmodel">
        <button type="button" class="yx-model-bluebtn submit">提交</button>
    </div>
    <div class="yx-jiange-1em"></div>
</form>
    [@m.validation/]
<script src="${base}/static/yxxt/mobile/scripts/form.js"></script>
<script>
    $(function () {
        $(".clothesStudentForm").initForm();
    });
</script>
[/@]