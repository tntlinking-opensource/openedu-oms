[#ftl]
[#include "../comm/lib.ftl"/]
[#if financeStudent??]
    [@edit]
        [@panel title="缴费情况"]
	        <table class="table table-bordered">
	            <thead>
		            <tr>
		         	    <th>费用名称</th>
		                <th>应缴金额（元）</th>
		                <th>已缴金额（元）</th>
		                [#---
		               		<th>减免金额（元）</th>
		                --]
		                <th>未缴金额（元）</th>
		            </tr>
	            </thead>
	            <tbody>
		            [#if financeStudent.items??]
			            [#list financeStudent.items as item]
				            <tr align="center">
				      	        <td>${(item.item.name)!}</td>
				                <td>${(item.feeTotal)!}</td>
				                <td>[#if financeStudent.feePaid?? && financeStudent.feePaid > 0]${(item.feeTotal)!}[#else]${(item.feePaid)!}[/#if]</td>
				                [#---
				                	<td>${(item.greenMoney)!}</td>
				                --]
				                <td>[#if financeStudent.feePaid?? && financeStudent.feePaid > 0]0[#else]${(item.feeOdd)!}[/#if]</td>
				            </tr>
			            [/#list]
		            [#else]
		            	<span style="color: green;">信息缺失</span>	
		            [/#if]
	            </tbody>
	        </table>
            [#if financeStudent.remark??]
            	<div style="margin: 10px 0;">备注：${financeStudent.remark}</div>
            [/#if]
        [/@panel]
        
        [@panel title="绿色通道信息"]
        	[#if !financeGreen??]
          		<span style="color: green;">未办理</span>
            [#elseif iftongguo??]
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
		            <table class="table table-bordered table-striped">
		            	[#import "/template/form/utils.ftl" as lu/]
					</table>
		        </table>
	        [#else]
          		<span style="color: green;">未通过</span>	
            [/#if]
        [/@panel]
        
        [@panelEdit]
        <div class="form-horizontal">
            <div class="form-group">
                <label class="col-sm-3 control-label">实缴金额：</label>
                <div class="col-sm-9">
                    <input class="form-control v_money" name="feePaid" value="${financeStudent.feeTotal - financeStudent.greenMoney}"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">备注：</label>
                <div class="col-sm-9">
                    <textarea name="remark" class="form-control" placeholder="备注" maxlength="100">${(financeStudent.remark?replace("<br/>", "\n"))!}</textarea>
                </div>
            </div>
        </div>
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
        [/@panelEdit]
    [/@edit]
[#else]
<div class="alert alert-danger">没有找到该学生的学费信息，无法操作。</div>
[/#if]