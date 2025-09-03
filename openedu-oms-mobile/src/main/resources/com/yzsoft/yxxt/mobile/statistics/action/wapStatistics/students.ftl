[#ftl]
{
	"data":{
		"items":[
			[#list students as v]
			    [#if v_index gt 0],[/#if]
				{
					"userId": "${(v.user.id)!}",
					"name": "${(v.name)!}",
					"gender": "${(v.gender.name)!}",
					"major": "${(v.major.name)!}",
					"adminClass": "${(v.adminClass.name)!}"
				}
			[/#list]
		],
		"total": ${students.total}
	}
}