[#ftl]
[#import "/template/mobile.ftl" as m/]

[@m.body title=(friend.fullname)!"" back=b.url("/mobile/home?t=1") cache="true"]
	<!--底部输入框和发送按钮-->
	<div>
		<div class="input-text">
			<textarea type="text" class="input_area msg_content" placeholder="点击输入" maxlength="100"></textarea>
		</div>
		<div class="button_box">
			<button type="button" class="bluebtn ui-btn ui-shadow ui-corner-all msg_send_btn">发送留言</button>
		</div>
	</div>
	<!--底部输入框和发送按钮结束-->
	<script>
		$(function () {
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
					"toUserId": ${friend.id},
					"content": content,
				}, function (data) {
					if (data.error) {
						alert(data.error);
						$(".msg_send_btn").prop("disabled", false);
					} else {
						alert("发送成功");
						location.href = '${b.url("!index")}';
					}
				}, "json");
			});
		});
	</script>
[/@]