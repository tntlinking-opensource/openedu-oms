[#ftl]
[#import "/template/mobile.ftl" as m/]

[@m.body title="统计查询" back="${base}/mobile/statistics/wap-statistics.action" cache="true"]

	<ul data-role="listview" style="margin-bottom:0.5em;" class="ui-listview student_ul">

	</ul>
	<div class="button_box load_more_box">
		<button class="bluebtn ui-btn ui-shadow ui-corner-all load_student">加载更多</button>
	</div>
	<div style="display: none;">
		<textarea class="li_template">
			<li class="ui-field-contain">
				<a href="#" data-userid="{{userId}}" class="zbblockcell00 ui-btn ui-btn-icon-right ui-icon-carat-r">
					<table cellpadding="0" cellspacing="0" border="0" width="100%" class="tj-tb">
						<tbody>
						<tr>
							<td class="name_font fontbold">{{name}}</td>
							<td class="text-center">{{gender}}</td>
							<td class="text-center">{{major}}</td>
							<td class="text-center">{{adminClass}}</td>
						</tr>
						</tbody>
					</table>
				</a>
			</li>
		</textarea>
	</div>
	<style>
		.send_msg_mask_div {position: fixed; width: 100vw; height: 100vh; top: 0; left: 0; background-color: rgba(0, 0, 0, 0.3); z-index: 9999; display: flex;}
		.send_msg_mask_div > div {width: 90vw; margin: auto;}
	</style>
	<div class="send_msg_mask_div" style="display: none;">
		<div class="send_msg_box" style="background-color: rgba(0,0,0, 0.3);">
			<div class="input-text">
				<textarea type="text" class="input_area msg_content" placeholder="点击输入" maxlength="100"></textarea>
			</div>
			<div class="button_box">
				<button type="button" class="bluebtn ui-btn ui-shadow ui-corner-all msg_send_btn">发送留言</button>
				<button type="button" class="redbtn ui-btn ui-shadow ui-corner-all msg_cancle_btn">取消</button>
			</div>
		</div>
	</div>
[#--	<script src="${base}/static/scripts/colorbox/jquery.colorbox-min.js" type="text/javascript"></script>--]
[#--	<link href="${base}/static/scripts/colorbox/colorbox.css" rel="stylesheet" type="text/css"/>--]
	<script>
		$(function () {
			var method = "findStudentByName", pageNo = 1, pageSize = 10, toUserId = null;
			[#if department?? && department != ""]
			method = "findStudentByDepartment";
			[#elseif major?? && major != ""]
			method = "findStudentByMajor";
			[#elseif birthday?? && birthday != ""]
			method = "findStudentByBirthday";
			[/#if]

			function loadStudent() {
				$.getJSON("${base}/mobile/statistics/wap-statistics.action", {
					method: method,
					gender: "${gender!}",
					name: "${name!}",
					department: "${department!}",
					major: "${major!}",
					birthday: "${birthday!}",
					pageNo: pageNo++,
					pageSize: pageSize,
				}, function (res) {
					var items = res.data.items;
					var template = $(".li_template").val();
					for (var i = 0; i < items.length; i++) {
						var item = items[i];
						var li = template.replace('{{userId}}', item.userId);
						var li = li.replace('{{name}}', item.name);
						var li = li.replace('{{gender}}', item.gender);
						var li = li.replace('{{major}}', item.major);
						var li = li.replace('{{adminClass}}', item.adminClass);
						$(".student_ul").append(li);
					}
					var total = res.data.total;
					if (total < (pageNo - 1) * pageSize) {
						$(".load_more_box").hide();
					}
				});
			}

			loadStudent();

			$(".load_student").click(function () {
				loadStudent();
			});

			$(".student_ul").on("click", "a", function () {
				toUserId = $(this).data("userid");
				$(".msg_content").val("");
				$(".send_msg_mask_div").fadeIn();
				return false;
			});

			$(".msg_cancle_btn").click(function (e) {
				$(".send_msg_mask_div").fadeOut();
			});

			$(".msg_send_btn").click(function () {
				var content = $(".msg_content").val();
				if (content.length == 0) {
					alert("请输入要发送的内容");
					return;
				}
				if (content.length > 100) {
					alert("最多只能发送100个字符");
					return;
				}
				$(this).prop("disabled", true);

				$.post("${base}/msg/message.action", {
					"method": "send",
					"toUserId": toUserId,
					"content": content,
				}, function (data) {
					if (data.error) {
						alert(data.error);
						$(".msg_send_btn").prop("disabled", false);
					} else {
						alert("发送成功");
						$(".send_msg_mask_div").fadeOut();
					}
				}, "json");
			});
		});
	</script>
[/@]