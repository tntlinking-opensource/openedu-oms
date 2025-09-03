[#ftl]
[#include "../comm/lib.ftl"/]

[#function hasFinanceGreen fg]
    [#list financeGreenStudent.items as fgs]
        [#if fgs.green?? && fgs.green.id == fg.id]
            [#return true/]
        [/#if]
    [/#list]
    [#return false/]
[/#function]
[#function money fg]
    [#list financeGreenStudent.items as fgs]
        [#if fgs.green?? && fgs.green.id == fg.id]
            [#return fgs.money/]
        [/#if]
    [/#list]
    [#if fg.money??]
        [#return fg.money/]
    [/#if]
    [#return ""/]
[/#function]

[@edit]
    [@panel title="绿色通道信息"]
    <table class="table table-bordered fjsh table-striped">
            [#if financeGreen??]
            <table class="table table-bordered fjsh table-striped">
            <thead>
            <tr>
                <th>折扣项目</th>
                <th>抵扣金额（元）</th>
            </tr>
            </thead>
            <tbody>
            
	            [#list financeGreen as green]
	                <tr align="center">
	                    <td>${(green.name)!}</td>
	                    <td>${(green.money)!}</td>
	                </tr>
	            [/#list]
           
            </tbody>
            </table>
            <table class="table table-bordered table-striped">
            [#import "/template/form/utils.ftl" as lu/]
			[@b.form title="业务附件" action="!saveFj" name ="dataForm" theme="form"]
				[#if studentId??]
				   <input type="hidden" name="studentId" value="${studentId}"/>
					 <div class="form-group">
							[#list student.stdInfoFile as photo]
								<img src="${base}/common/download.action?file=${(photo.path)!}" style="max-height: 200px;">
								<span style="margin:10px;"></span>
							[/#list]
					 </div>
				[#else]	 
					 <span style="color: green;">该学生还没有上传附件</span>	
				[/#if]
			[/@]
        </table>
        
        [#else]
            
            <div class="col-sm-9">
                [#list financeGreens as fg]
                    <div class="finance_green_div">
                        <label class="checkbox-inline">
                            <input type="radio" name="fgs" value="${fg.id}" class="fgs_checkbox"
                                   [#if hasFinanceGreen(fg)]checked[/#if]/>
                            <input type="hidden" name="fgs${fg.id}.green.id" value="${fg.id}"/>
                        ${fg.name}
                        </label>
                        <span class="money_div"  [#if !hasFinanceGreen(fg)]style="display: none;"[/#if]>
                            [#if fg.manual]
                                <input style="width: 100px;" name="fgs${fg.id}.money" value="${money(fg)!}" class="v_integer" required/>（元）
                            [#else]
                                <span>${money(fg)!}（元）</span>
                                <input type="hidden" name="fgs${fg.id}.money" value="${money(fg)!}" class="v_integer" required/>
                            [/#if]
				        </span>
                    </div>
                [/#list]
            </div>
            
        <div class="form-group">
            <label class="col-sm-3 control-label">备注：</label>
            <div class="col-sm-9">
                <textarea name="remark" class="form-control" placeholder="备注" maxlength="100">${(financeGreenStudent.remark?replace("<br/>", "\n"))!}</textarea>
            </div>
        </div>
        [/#if]
    </table>
    <script>
        $(function () {
            $(".fgs_checkbox").click(function () {
                var moneyDiv = $(this).closest(".checkbox-inline").next();
                if (this.checked) {
                    moneyDiv.fadeIn();
                } else {
                    moneyDiv.fadeOut();
                }
            });
        });
    </script>
    
    [/@panel]
   
[/@edit]