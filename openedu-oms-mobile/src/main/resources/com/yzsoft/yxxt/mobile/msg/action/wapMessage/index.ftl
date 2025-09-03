[#ftl]
[#import "/template/mobile.ftl" as m/]

[@m.body title="消息" back=b.url("/mobile/home?t=1") cache="true"]
	<ul data-role="listview" style="margin-bottom:0.5em;" class="ui-listview friend_ul">

	</ul>
	<div style="display: none;">
		<textarea class="friend_template">
		<li class="ui-field-contain ui-last-child">
			<a href="${base}/mobile/msg/wap-message.action?method=edit&friendId={{friendId}}" class="zbblockcell00 ui-btn ui-btn-icon-right ui-icon-carat-r">
				<table cellpadding="0" cellspacing="0" border="0" width="100%" class="tj-tb">
					<tbody>
					<tr>
						<td class="fontbold">{{name}}</td>
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
	<div class="button_box friend_box">
		<button class="bluebtn ui-btn ui-shadow ui-corner-all load_friends">加载更多</button>
	</div>

	<script>
		$(function () {

			var pageNo = 1;
			var pageSize = 10;

			function loadFriend() {
				$.getJSON("${base}/msg/message.action", {
					method: "friends",
					pageNo: pageNo++,
					pageSize: pageSize,
				}, function (res) {
					console.log(res)
					var items = res.data.items;
					var template = $(".friend_template").val();
					for (var i = 0; i < items.length; i++) {
						var item = items[i];
						console.log(item)
						var li = template.replace('{{friendId}}', item.userId);
						var li = li.replace('{{name}}', item.name);
						var li = li.replace('{{gender}}', item.gender);
						var li = li.replace('{{major}}', item.major);
						var li = li.replace('{{adminClass}}', item.adminClass);
						$(".friend_ul").append(li);
					}
					var total = res.data.total;
					if (total < (pageNo - 1) * pageSize) {
						$(".friend_box").hide();
					}
				});
			}

			loadFriend();

			$(".load_friends").click(function () {
				loadFriend();
			});
		});
	</script>
[/@]