[#ftl]
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>Page title</title>
	<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, user-scalable=no">
</head>
<body>
<style>
	html, body {padding: 0; margin: 0;}
	.edit_div {width: 800px; margin: auto; position: relative; min-height: 500px;}
	.edit_div .btn {position: absolute;}
	.btn { display: inline-block; padding: 6px 12px; margin-bottom: 0; font-size: 14px; font-weight: 400; line-height: 1.42857143; text-align: center; white-space: nowrap; vertical-align: middle; -ms-touch-action: manipulation; touch-action: manipulation; cursor: pointer; -webkit-user-select: none; -moz-user-select: none; -ms-user-select: none; user-select: none; background-image: none; border: 1px solid transparent; border-radius: 4px;}
	.btn-primary { color: #fff; background-color: #337ab7; border-color: #2e6da4;}
</style>
<div class="edit_div">
	<img src="${base}/${dormRoomBedType.backgroundImg}" width="100%"/>
</div>

<script src="${base}/static/scripts/comm/jquery-latest.min.js" type="text/javascript"></script>
<script>
	$(function () {

		var editDiv = $(".edit_div");

		var bedPositions = ${dormRoomBedType.bedPosition};

		[#list dormPlanBeds as v]
		addBed({
			id:${v.id},
			num:${v.bed.name},
		});
		[/#list]

		function addBed(bed) {
			var bedPosition = getBedPosition(bed.num);
			var btn = $('<button type="button" class="btn btn-primary btn_select_bed"></button>');
			btn.data("id", bed.id).text("选择" + bedPosition.num + "号床");
			btn.css({
				top: bedPosition.y,
				left: bedPosition.x,
			});
			editDiv.append(btn);
		}

		function getBedPosition(num) {
			for (var i = 0; i < bedPositions.length; i++) {
				var bedPosition = bedPositions[i];
				if (bedPosition.num == num) {
					return bedPosition;
				}
			}
			return {num: num, x: 0, y: 0};
		}

		$(".btn_select_bed").click(function () {
			var dormPlanBedId = $(this).data("id");
			$.post("${b.url("!bedSave")}", {
				"dormPlanBedId": dormPlanBedId,
			}, function (data) {
				if (data.error) {
					alert(data.error);
					top.location.reload();
				} else {
					// alert("操作成功");
					top.location.href = "${b.url("!index")}";
				}
			}, "json");
		});
	});
</script>
</body>
</html>