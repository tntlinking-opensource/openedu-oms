[#ftl]
[#if !homeHelp??]
	<link rel="stylesheet" href="${base}/static/yxxt/mobile/css/introjs.css"/>
	<script src="${base}/static/yxxt/mobile/scripts/intro.js"></script>
	<script>
		var hasIntroJS = false;

		function startIntro() {
			if (hasIntroJS) {
				return;
			}
			hasIntroJS = true;

			$(document).on("click", ".introjs-donebutton", function () {
				$.post("${b.url("!homeHelp")}");
			});

			var intro = introJs();
			intro.setOptions({
				nextLabel: "查看下一步 &rarr;",
				doneLabel: "我知道了",
				hideNext: true,
				exitOnOverlayClick: false,
				showStepNumbers: false,
				showBullets: false,
				showProgress: false,
				disableInteraction: true,
				steps: [
					{
						element: '#step1',
						intro: "点击这里可以查看${(noticeColumn.name)!}相关的新闻",
					},
					{
						element: '#step2',
						intro: '点击这里可以查看学校迎新相关的公告',
					},
					{
						element: '#step3',
						intro: "点击这里可以查看学校迎新相关的常见问题",
					},
					{
						element: '#step4',
						intro: "点击这里可以填写自己的个人信息",
					},
					{
						element: '#step5',
						intro: "点击这里可以查看个人信息及完善个人信息",
					},
					{
						element: '#step6',
						intro: "点击这里可以选择自己的宿舍",
						position: "top-middle-aligned",
					},
					{
						element: '#step10',
						intro: "点击这里可以查看现在报道流程及目前所在流程",
					},
					{
						element: '#step11',
						intro: "点击这里可以查看迎新现场需要使用的报道二维码",
						position: "top",
					},
					{
						element: '#step12',
						intro: "点击这里可以退出登录",
						position: "top",
					},
				]
			});

			intro.start();
		}

		[#if !(student?? && !student.noticed)]
		$(function () {
			setTimeout(startIntro, 300);
		});
		[/#if]
	</script>
[/#if]