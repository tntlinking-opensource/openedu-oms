[#ftl]

[#function money item]
    [#list dormSettings as v]
        [#if v.item.id == item.id]
            [#return v.money/]
        [/#if]
    [/#list]
    [#return 0/]
[/#function]

[@b.head/]
[@b.toolbar title="住宿费用设置"]bar.addBack("${b.url("!index")}");[/@]
[@b.form action="!save" theme="list"]
    [@b.field label="收费标准"]
    <table class="table table-bordered table-striped" style="max-width: 600px;">
        <thead>
        <tr>
            <th>收费项目</th>
            <th>金额（元）</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        	[#if dormSettings?? && dormSettings?size > 1]
	            [#list dormSettings?sort_by("id") as v]
		            <tr align="center">
		                <td>${v.item.name}</td>
		                <td><input name="dormSetting${(v_index)}.money" value="${(v.money)!}" maxlength="6" class="v_money" style="width: 80px; text-align: center;"/></td>
		            	<input type="hidden" name="dormSetting" value="${(v_index)}" />
			            <td>
							<input type="button" value="添加" onclick="add(this,${(v_index)!})" class="btn blue"/>&nbsp;
							<input type="button" value="删除" onclick="del(this,${(v.id)!})" class="btn red"/>
						</td>
		            	<input type="hidden" name="dormSetting${(v_index)}.id" value="${(v.id)!}" />
		                <input type="hidden" name="dormSetting${(v_index)}.item.id" value="${(v.item.id)!}">
		            </tr>
	            [/#list]
            [#else]
            	<tr align='center'>
	                <td>${(financeItem.name)!}</td>
	                <td><input name="dormSetting#id.money" value="" maxlength="6" class="v_money" style="width: 80px; text-align: center;"/></td>
	                <td>
						<input type="button" value="添加" onclick="add(this,0)" class="btn blue"/>&nbsp;
						<input type="button" value="删除" onclick="del(this,"")" class="btn red"/>
					</td>
					<input type="hidden" name="dormSetting" value="#id" />
	                <input type="hidden" name="dormSetting#id.item.id" value="${(financeItem.id)!}">
	            </tr>
            [/#if]
        </tbody>
    </table>
    <div style="display:none">
    	<textarea id="templateId">
    		<tr align='center'>
                <td>${(financeItem.name)!}</td>
                <td><input name="dormSetting#id.money" value="" maxlength="6" class="v_money" style="width: 80px; text-align: center;"/></td>
                <td>
					<input type="button" value="添加" onclick="add(this,${(v_index)!})" class="btn blue"/>&nbsp;
					<input type="button" value="删除" onclick="del(this,${(v.id)!})" class="btn red"/>
				</td>
				<input type="hidden" name="dormSetting" value="#id" />
                <input type="hidden" name="dormSetting#id.item.id" value="${(financeItem.id)!}">
            </tr>
    	</textarea>
    </div>
    [/@]
    [@b.formfoot]
        [@b.submit value="action.submit" /]
        <input type="hidden" id="removeIds" name="removeIds" value="" />
    [/@]
[/@]
[@b.foot/]
<script>
	var remIds = $("#removeIds").val();
	function add(this_,index_){
		index_++;
		var tem = $("#templateId").val();
		tem = tem.replace(/#id/g,index_);
		$("tbody").append(tem).closest(".table");
	}
	
	function del(this_,id_){
		if(id_ != ""){
			if( remIds == ''){
				remIds = id_;
			}else{
				remIds += ","+id_;
			}
		}
		$("#removeIds").val(remIds);
		$(this_).parent().parent().remove();
	}
</script>
