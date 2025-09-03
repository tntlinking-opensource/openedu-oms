$(document).ready(function(){ 
	$('#departmentId').change(function(){ 
		var departmentId=$('#departmentId').children('option:selected').val();
		loadMajors(departmentId);
	})
	
	$('#departmentFormId').change(function(){ 
		var departmentId=$('#departmentFormId').children('option:selected').val();
		loadFormMajors(departmentId);
	})
	
	function loadMajors(departmentId) {
        $.ajax({
            url: base+'/core/major-search.action?method=majorList&departmentId='+ departmentId,
            type: 'POST',
            timeout: 5000,
            error: function() { alert('Error loading data!'); },
            success: function(data) {
                $("#majorId").empty();
                $("<option value=''>请选择...</option>").appendTo($("#majorId"));
                var evalDate = eval(data);
                var evalLength =evalDate.length;
                $.each(evalDate, function(i, item) {
                	if(evalLength==1){
	                    $("<option selected='selected' value='" + item.id + "'>" + item.name + "</option>").appendTo($("#majorId"));
	                    $('#majorId').select2({  placeholder: "所属专业", allowClear: true});
                	}else{
                		 $("<option value='" + item.id + "'>" + item.name + "</option>").appendTo($("#majorId"));
                	}
                });
            }
        });
    }
	
	function loadFormMajors(departmentId) {
        $.ajax({
            url: base+'/core/major-search.action?method=majorList&departmentId='+ departmentId,
            type: 'POST',
            timeout: 5000,
            error: function() { alert('Error loading data!'); },
            success: function(data) {
                $("#majorFormId").empty();
                $("<option value=''>请选择...</option>").appendTo($("#majorFormId"));
                $("#select2-majorFormId-container").html("<span class='select2-selection__placeholder'>请选择...</span>");
                var evalDate = eval(data);
                var evalLength =evalDate.length;
                $.each(evalDate, function(i, item) {
                	if(evalLength==1){
	                    $("<option value='" + item.id + "'>" + item.name + "</option>").appendTo($("#majorFormId"));
	                    $('#majorId').select2({  placeholder: "请选择...", allowClear: true});
                	}else{
                		 $("<option value='" + item.id + "'>" + item.name + "</option>").appendTo($("#majorFormId"));
                	}
                });
            }
        });
    }
}) 