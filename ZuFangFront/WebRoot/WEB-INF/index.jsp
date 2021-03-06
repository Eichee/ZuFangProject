<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="head.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<title>首页</title>
<script type="text/javascript">
	$(function(){
		$.ajax({
			type:"post",
			url:"<%=ctxPath%>/Index",
			data:{action:"getCurrentCity"},
			success:function(result){
				if(result.status=="ok"){
					$("#cityName").text(result.data);
				}
			},
			error:function(){}
		})
		
		
		$("#currentCity").click(function(){
			var listDisplay=$("#cityList").attr("style");
			if (listDisplay.indexOf("none")>0) {
				$("#cityList").attr("style","display:block");
			}
			else{
				$("#cityList").attr("style","display:none");
			}
		 	
		})
		
		
		$("#txtSearch").keydown(function(e){
			if(e.keyCode==13)//回车键
			{
				location.href="<%=ctxPath%>/House?action=search&keywords="+encodeURI($(this).val());
			}
		})
		
		
		
	})

</script>
</head>
<body>
	<div class="loading">
		<div class="loader">
			<div class="loader-inner pacman">
				<div></div>
				<div></div>
				<div></div>
				<div></div>
				<div></div>
			</div>
		</div>
	</div>
	<!--loading页结束-->
<body>
	<!--header star-->
	<div class="header clearfloat">
		<div class="tu clearfloat">
			<img src="img/index-banner.jpg" />
		</div>
		<header class="mui-bar mui-bar-nav">
			<a class="btn" href="#" id="currentCity">
				<p id="">
					<b id='cityName'></b><i class="iconfont icon-iconfontarrowdown-copy"></i>
				</p>
			</a>
			<ul style="display: none" id="cityList">
				<c:forEach items="${cities }" var="city">
				<li><a	href='#'>${city.name }</a></li>
				</c:forEach>
			</ul>
			<div class="top-sch-box flex-col">
				<div class="centerflex">
					<i class="fdj iconfont icon-sousuo"></i>
					<div class="sch-txt">请输入您要搜索的内容</div>
				</div>
			</div>
		</header>
		<div class="header-tit clearfloat">
			<p class="header-num">
				掌上租已为<span>${houseAppTotalCount }</span>用户成功租房！
			</p>
			<p class="header-da">轻松租房 乐享生活</p>
		</div>
	</div>
	<!--header end-->
	<div id="main" class="mui-clearfix">
		<!-- 搜索层 -->
		<div class="pop-schwrap">
			<div class="ui-scrollview">
				<div class="poo-mui clearfloat box-s">
					<div class="mui-bar mui-bar-nav clone poo-muitwo">
						<div class="top-sch-box flex-col">
							<div class="centerflex">
								<i class="fdj iconfont icon-sousuo"></i> <input
									class="sch-input mui-input-clear" type="text" name="" id="txtSearch"
									placeholder="请输入您要搜索的内容" />
							</div>
						</div>
					</div>
					<a href="javascript:;" class="mui-btn mui-btn-primary btn-back"
						href="#">取消</a>
				</div>
				<div class="scroll-wrap">
					<div class="mui-scroll">
						<div class="sch-cont">
							<div class="section ui-border-b">
								<div class="tit">热门搜索</div>
								<div class="tags">
									<span class="tag">大溪地</span><span class="tag">大溪地</span><span
										class="tag">大溪地</span> <span class="tag">大溪地</span><span
										class="tag">大溪地</span><span class="tag">大溪地</span> <span
										class="tag">大溪地</span><span class="tag">大溪地</span><span
										class="tag">大溪地</span>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="cation clearfloat">
			<ul class="clearfloat">
				<li><a href="entire.html"> <img src="img/fang.png" />
						<p>整租</p>
				</a></li>
				<li><a href="entire.html"> <img src="img/chuang.png" />
						<p>合租</p>
				</a></li>
				<li><a href="entire.html"> <img src="img/bao.png" />
						<p>短租</p>
				</a></li>
				<li><a href="entire.html"> <img src="img/lou.png" />
						<p>写字楼</p>
				</a></li>
				<li><a href="map.html"> <img src="img/map.png" />
						<p>地图找房</p>
				</a></li>
				<li><a href="landlord.html"> <img src="img/people.png" />
						<p>我是房东</p>
				</a></li>
				<li><a href="join.html"> <img src="img/woshou.png" />
						<p>加盟</p>
				</a></li>
				<li><a href="life-service.html"> <img src="img/self.png" />
						<p>生活服务</p>
				</a></li>
			</ul>
		</div>

		<div class="recom clearfloat">
			<div class="recom-tit clearfloat box-s">
				<p>热门房源推荐</p>
			</div>
			<div class="content clearfloat box-s">
				<div class="list clearfloat fl box-s">
					<a href="house-details.html">
						<div class="tu clearfloat">
							<span></span> <img src="upload/list-tu.jpg" />
						</div>
						<div class="right clearfloat">
							<div class="tit clearfloat">
								<p class="fl">华府骏苑</p>
								<span class="fr">2500<samp>元/月</samp></span>
							</div>
							<p class="recom-jianjie">三室一厅一卫 | 125m² | 普装</p>
							<div class="recom-bottom clearfloat">
								<span><i class="iconfont icon-duihao"></i>随时住</span> <span><i
									class="iconfont icon-duihao"></i>家电齐全</span>
							</div>
						</div>
					</a>
				</div>
				<div class="list clearfloat fl box-s">
					<a href="house-details.html">
						<div class="tu clearfloat">
							<span></span> <img src="upload/list-tu.jpg" />
						</div>
						<div class="right clearfloat">
							<div class="tit clearfloat">
								<p class="fl">华府骏苑</p>
								<span class="fr">2500<samp>元/月</samp></span>
							</div>
							<p class="recom-jianjie">三室一厅一卫 | 125m² | 普装</p>
							<div class="recom-bottom clearfloat">
								<span><i class="iconfont icon-duihao"></i>随时住</span> <span><i
									class="iconfont icon-duihao"></i>家电齐全</span>
							</div>
						</div>
					</a>
				</div>
				<div class="list clearfloat fl box-s">
					<a href="house-details.html">
						<div class="tu clearfloat">
							<span></span> <img src="upload/list-tu.jpg" />
						</div>
						<div class="right clearfloat">
							<div class="tit clearfloat">
								<p class="fl">华府骏苑</p>
								<span class="fr">2500<samp>元/月</samp></span>
							</div>
							<p class="recom-jianjie">三室一厅一卫 | 125m² | 普装</p>
							<div class="recom-bottom clearfloat">
								<span><i class="iconfont icon-duihao"></i>随时住</span> <span><i
									class="iconfont icon-duihao"></i>家电齐全</span>
							</div>
						</div>
					</a>
				</div>
			</div>
		</div>
	</div>


	<%@ include file="footer.jsp"%>