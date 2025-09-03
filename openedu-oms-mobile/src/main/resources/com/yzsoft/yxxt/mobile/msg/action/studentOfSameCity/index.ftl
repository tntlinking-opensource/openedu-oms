[#ftl]
[#import "/template/mobile.ftl" as m/]

[@m.body title="同城学生" back=b.url("/mobile/home?t=1") cache="true"]
	<div class="zdblock">
		<h3 class="zdblock_title-2">总人数统计：${students?size}人</h3>
	</div>
	<div class="ui-grid-a qddata">
		<div class="ui-block-a qddata-cell" style="border-right:1px solid #f0f0f0;">
			<p class="qddata-cell-title">男生统计</p>
			<p class="qddata-cell-data fontblue">${genderNum("男")}</p>
		</div>
		<div class="ui-block-b qddata-cell">
			<p class="qddata-cell-title">女生统计</p>
			<p class="qddata-cell-data fontred">${genderNum("女")}</p>
		</div>
	</div>
	[#if students?size gt 0]
		<div class="zdblock">
			<h3 class="zdblock_title-2">成员详情</h3>
		</div>
		<ul data-role="listview" style="margin-bottom:0.5em;" class="ui-listview">
			[#list students as v]
				<li class="ui-field-contain">
					<a href="${b.url('!edit')}&friendId=${(v.user.id)!}" class="zbblockcell00 ui-btn ui-btn-icon-right ui-icon-carat-r">
						<table cellpadding="0" cellspacing="0" border="0" width="100%" class="tj-tb">
							<tbody>
							<tr>
								<td class="name_font fontbold">${(v.name)!}</td>
								<td class="text-center">${(v.gender.name)!}</td>
								<td class="text-center">${(v.major.name)!}</td>
								<td class="text-center">${(v.adminClass.name)!}</td>
							</tr>
							</tbody>
						</table>
					</a>
				</li>
			[/#list]
		</ul>
	[/#if]
[/@]

[#function genderNum name]
	[#assign num = 0/]
	[#list students as v]
		[#if v.gender.name == name]
			[#assign num = num + 1/]
		[/#if]
	[/#list]
	[#return num/]
[/#function]