[#ftl]
[#if isBirthday?? && isBirthday && !homeHelp.birthdayClicked]
	<style>
		.mask_div {position: fixed; width: 100vw; height: 100vh; top: 0; left: 0; background-color: rgba(0, 0, 0, 0.3); z-index: 9999; display: flex;}
		.mask_div img {width: 90vw; margin: auto;}
	</style>
	<div class="mask_div">
		<img src="${base}/static/yxxt/mobile/css/images/bd_03.png"/>
	</div>
	<script>
		$(function () {
			$(".mask_div").off().click(function () {
				$(this).fadeOut();
				$.post('${b.url("!birthdayClicked")}');
			});
		});
	</script>
[/#if]