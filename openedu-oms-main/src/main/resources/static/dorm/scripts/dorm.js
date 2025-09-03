function bootboxUrl(url,title){
	$.get(url, function(data){
		var modal=bootbox.dialog({
			title:title,
	        message:data
	    });
	});
};

function dormArrange(deptAccomPlanId){
	var url = base+"/dorm/plan/dorm-plan-arrange!index.action?deptAccomPlan.id="+deptAccomPlanId;
	$.get(url, function(data){
		var modal=bootbox.dialog({
	        message:data
	    });
	});
};

function majorAccomPlanDormArrange(majorAccomPlanId){
	var url = base+"/dorm/plan/dorm-plan-arrange!index.action?majorAccomPlan.id="+majorAccomPlanId;
	$.get(url, function(data){
		var modal=bootbox.dialog({
	        message:data
	    });
	});
};

function classAccomPlanDormArrange(classAccomPlanId){
	var url = base+"/dorm/plan/dorm-plan-arrange!index.action?classAccomPlan.id="+classAccomPlanId;
	$.get(url, function(data){
		var modal=bootbox.dialog({
	        message:data
	    });
	});
};

bootbox.hideAll();