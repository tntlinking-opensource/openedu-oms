$(document).ready(function(){ 
	$('#majorId').change(function(){ 
		var majorId=$('#majorId').children('option:selected').val();
		loadAdminClasses(majorId);
	})
	
	$('#majorFormId').change(function(){ 
		var departmentId = $('#departmentFormId').val();
		var majorId=$('#majorFormId').children('option:selected').val();
		loadFormAdminClasss(departmentId,majorId);
	})
	
   $('#gradeForm').change(function(){ 
	    var departmentId = $('#departmentFormId').val();
		var majorId=$('#majorFormId').val();
		var grade = $('#gradeForm').val();
		loadFormAdminClasss(departmentId,majorId,grade);
	})
	
	function loadAdminClasses(majorId) {
        $.ajax({
            url: base+'/core/admin-class-search.action?method=adminClassList&majorId='+ majorId,
            type: 'POST',
            timeout: 5000,
            error: function() { alert('Error loading data!'); },
            success: function(data) {
                $("#adminClassId").empty();
                $("<option value=''>请选择...</option>").appendTo($("#adminClassId"));
                var evalDate = eval(data);
                var evalLength =evalDate.length;
                $.each(evalDate, function(i, item) {
                	if(evalLength==1){
	                    $("<option selected='selected' value='" + item.id + "'>" + item.name + "</option>").appendTo($("#adminClassId"));
                	}else{
                		 $("<option value='" + item.id + "'>" + item.name + "</option>").appendTo($("#adminClassId"));
                	}
                });
            }
        });
    }
	
	function loadFormAdminClasss(departmentId,majorId) {
        $.ajax({
            url: base+'/core/admin-class-search.action?method=adminClassList&departmentId='+ departmentId+'&majorId='+ majorId,
            type: 'POST',
            timeout: 5000,
            error: function() { alert('Error loading data!'); },
            success: function(data) {
                $("#adminClassFormId").empty();
                $("<option value=''>请选择...</option>").appendTo($("#adminClassFormId"));
                $("#select2-adminClassFormId-container").html("<span class='select2-selection__placeholder'>请选择...</span>");
                var evalDate = eval(data);
                var evalLength =evalDate.length;
                $.each(evalDate, function(i, item) {
                	if(evalLength==1){
	                    $("<option selected='selected' value='" + item.id + "'>" + item.name + "</option>").appendTo($("#adminClassFormId"));
                	}else{
                		 $("<option value='" + item.id + "'>" + item.name + "</option>").appendTo($("#adminClassFormId"));
                	}
                });
            }
        });
    }
}) 