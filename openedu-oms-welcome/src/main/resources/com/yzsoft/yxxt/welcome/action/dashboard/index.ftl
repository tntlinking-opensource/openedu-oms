[#ftl]
<!DOCTYPE html>
<!--[if IE 9]>
<html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<head>
	<meta charset="utf-8">
	<title>河南测绘职业学院迎新动态数据</title>
	<meta name="description" content="">
	<meta name="author" content="">
	<!-- Mobile Meta -->
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<!-- Favicon -->
	<link href="${base}/static/metronic/assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet"
	      type="text/css"/>
	<link href="${base}/static/yxxt/welcome/css/content1.0.css?v=20191120" rel="stylesheet">
	<script src="${base}/static/metronic/assets/global/plugins/jquery.min.js" type="text/javascript"></script>

</head>

<body ng-app="myApp" ng-controller="customersCtrl">
<!-- banner start -->
<div class="banner-caption clearfix">
	<div id="title">
		<div class="caption-title clearfix">
			<i class="title-left"><img src="${base}/static/yxxt/welcome/css/images/title-left.png"></i>
			<p class="title-left-title-font">河南测绘职业学院迎新动态数据</p>
			<i class="title-left"><img src="${base}/static/yxxt/welcome/css/images/title-right.png"></i>
		</div>

	</div>
	<div id="main">
		<div class="col-md-12">
			<div class="row">
				<div id="title01" class="col-xs-12 col-sm-12 col-md-3">
					<div class="data-box1-1 clearfix" id="box01">
						<i class="topL"></i>
						<i class="topR"></i>
						<i class="bottomL"></i>
						<i class="bottomR"></i>

						<div class="total-mn-1" id="total-mn1">
							<span>报到人数统计</span>
						</div>
						<div id="box1_top">
							<div id="box2" class="box-echart-1 num_chart_div"></div>
						</div>
						<div id="total-mn2"></div>
					</div>
					<div class="data-box1-1" id="box8-box" style="margin-top: 30px;">
						<i class="topL"></i>
						<i class="topR"></i>
						<i class="bottomL"></i>
						<i class="bottomR"></i>
						<div class="total-mn-1" id="total-mn1">
							<span>专业名称</span>
							<span style="float: right; margin-right: 10px;">报到人数 / 报到率</span>
						</div>
						<div id="box2" class="box-echart-2 major_chart_div"></div>
					</div>
				</div>
				<div id="title02" class="col-xs-12 col-sm-12 col-md-6 panel-bottom ">
					<div class="data-box1-1 box1-back" id="box02">
						<i class="topL"></i>
						<i class="topR"></i>
						<i class="bottomL"></i>
						<i class="bottomR"></i>

						<div class="total-mn-1">
							<span>各个省生源地统计</span>
						</div>
						<div id="box1" class="box-echart-3 china_chart_div"></div>
					</div>
					<div class="" id="box9-box" style="margin-top: 30px;">
						<div class="row">
							<div id="title04" class="col-xs-12 col-sm-12 col-md-6">
								<div class="data-box1-1 box1-back" id="box02">
									<i class="topL"></i>
									<i class="topR"></i>
									<i class="bottomL"></i>
									<i class="bottomR"></i>

									<div class="total-mn-1">
										<span>各院系统计</span>
									</div>
									<div id="box1" class="box-echart-4 department_chart_div">

									</div>
								</div>
							</div>
							<div id="title05" class="col-xs-12 col-sm-12 col-md-6">
								<div class="data-box1-1 box1-back" id="box02">
									<i class="topL"></i>
									<i class="topR"></i>
									<i class="bottomL"></i>
									<i class="bottomR"></i>

									<div class="total-mn-1">
										<span>新生民族分布TOP5</span>
									</div>
									<div id="box1" class="box-echart-4 nation_chart_div">

									</div>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div id="title03" class="col-xs-12 col-sm-12 col-md-3">
					<div class="data-box1-2" id="box03">
						<i class="topL"></i>
						<i class="topR"></i>
						<i class="bottomL"></i>
						<i class="bottomR"></i>
						<ul class="ym-menu clearfix" id="ym-menu">
[#--							<li class="ym-active">
								<a href="#" onclick="return false;">河南统计</a>
							</li>--]
							<li class="taba">
								<div>全国统计</div>
							</li>
						</ul>

						[#--<div class="box-echart-5 tabcontent city_chart_div"></div>--]
						<div class="box-echart-5 tabcontent province_chart_div" style="display: none;"></div>
					</div>
					<div class="data-box1-1" id="box04" style="margin-top: 22px;">
						<i class="topL"></i>
						<i class="topR"></i>
						<i class="bottomL"></i>
						<i class="bottomR"></i>
						<div class="total-mn-1">
							<span>各环节已办理百分比</span>
						</div>
						<div id="box5" class="box-echart-6">
							<div class="task-list item_chart_div"></div>
						</div>
					</div>
				</div>
			</div>
			<!--asad-->
		</div>
	</div>
</div>
</div>
<!-- JavaScript -->
<link href="${base}/static/metronic/assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet"
      type="text/css"/>
<script type="text/javascript" src="${base}/static/yxxt/welcome/scripts/echarts.min.js"></script>
<script type="text/javascript" src="${base}/static/yxxt/welcome/scripts/china.js"></script>
<script type="text/javascript" src="${base}/static/yxxt/welcome/scripts/mycharts.js?v=20191113"></script>
<script>
	var baseUrl = "${base}/welcome/dashboard.action", batchId = "${batchId}";
	$(function () {
		$('.ym-menu li').click(function () {
			var index = $(this).index();
			//$(this).attr('class', "ym-active").siblings('li').attr('class', 'taba');
			$('.tabcontent').eq(index).show(0).siblings('.tabcontent').hide();
		});
		var t = 0;
		var timer = setInterval(function () {
			if (t == $('.ym-menu li').length) t = 0;
			$('.ym-menu li:eq(' + t + ')').click();
			t++;
		}, 0);

		loadDashboard();
	})
</script>
</body>
</html>
