[#ftl]
[#import "/template/mobile.ftl" as m/]

[@m.body title=(friend.fullname)!"" back=b.url("!index") cache="true"]
	<!--底部输入框和发送按钮-->
	<div style="max-height: calc(100vh - 53px); display: flex; flex-direction: column;">
		<div class="msg_div" style="overflow-y: scroll;">
			<ul data-role="listview" class="liuyan liuyan_ul">
				[#list messages?reverse as v]
					<li>
						<div class="zdblock-content-1 [#if v.from.id == userId]self[/#if]">
							<p class="zdblock-content-t msg_content">${(v.content)!}</p>
							<p class="zdblock-content-c msg_date">${(v.createTime?string('yyyy-MM-dd HH:mm'))!}</p>
						</div>
					</li>
				[/#list]
			</ul>
		</div>
		<div style="display: none;">
		<textarea class="msg_template">
			<li>
				<div class="zdblock-content-1 {{self}}">
					<p class="zdblock-content-t msg_content">{{content}}</p>
					<p class="zdblock-content-c msg_date">{{date}}</p>
				</div>
			</li>
		</textarea>
		</div>

		<div>
			<div class="input-text">
				<textarea type="text" class="input_area msg_content_ipt" placeholder="点击输入" maxlength="100"></textarea>
			</div>
			<div class="button_box">
				<button class="bluebtn ui-btn ui-shadow ui-corner-all msg_send_btn">发送留言</button>
			</div>
		</div>
	</div>
	<!--底部输入框和发送按钮结束-->
	<div class="timer_div"></div>

	<script>
		$(function () {
			var userId = ${userId}, friendId = ${friendId};

			function loadTemp() {
				$.getJSON("${base}/msg/message.action", {
					method: "messageTemp",
					friendId: ${friendId},
				}, function (res) {
					var items = res.data.items;
					for (var i = 0; i < items.length; i++) {
						var item = items[i];
						addLi(item);
					}
					afterAddMsg();
				});
			}

			function addLi(item) {
				var li = $(".msg_template").val();
				var li = li.replace('{{self}}', item.fromId == userId ? "self" : "");
				var li = li.replace('{{content}}', item.content);
				var li = li.replace('{{date}}', item.createTime);
				$(".liuyan_ul").append(li);
			}

			// loadTemp();

			var timeDiv = "t_" + new Date().getTime();
			$(".timer_div").addClass(timeDiv);

			function autoLoadTemp(){
				if ($("." + timeDiv).length == 0) {
					clearInterval(timer);
					return;
				}
				loadTemp();
				setTimeout(autoLoadTemp, 5000);
			}

			autoLoadTemp();


			$(".msg_send_btn").click(function () {
				var content = $(".msg_content_ipt").val();
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
						addLi(data.data);
						$(".msg_content_ipt").val("");
						afterAddMsg();
						$(".msg_send_btn").prop("disabled", false);
					}
				}, "json");
			});


			function afterAddMsg() {
				try {
					$(".liuyan_ul").listview('refresh');
				} catch (e) {
				}
				$(".msg_div").scrollTop($(".liuyan_ul").height());
			}

			afterAddMsg();
		});
	</script>
	</script>
[/@]