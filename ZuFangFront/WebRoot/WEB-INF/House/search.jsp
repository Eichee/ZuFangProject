<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/head.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="zf" uri="http://java.zufang.com/jsp/jstl/functions" %>
<title>Insert title here</title>
	<script type="text/javascript" src="<%=ctxPath %>/js/menu.js" ></script>
	<script type="text/javascript">
		$(window).load(function(){
			$(".loading").addClass("loader-chanage")
			$(".loading").fadeOut(300)
		})
	</script>
</head>
<body>
		<div class="headertwo clearfloat" id="header">
			<a href="javascript:history.go(-1)" class="fl box-s"><i class="iconfont icon-arrow-l fl"></i></a>
			<p class="fl">${searchDispaly }</p>
			<a href="javascript:history.go(-1)" class="fr"><i class="iconfont icon-sousuo fl"></i></a>
		</div>		
		<div class="clearfloat" id="main">
			<div class="menu-list clearfloat am-sharetwo">
				<ul class="yiji" id="oe_menu">
					<li>
						<a href="#" class="inactive">区域<i></i></a>
						<ul style="display: none">
						<c:forEach items="${regions }" var="region">
							<li><a href='<%=ctxPath %>/House?${zf:addQueryStringPart(queryString,"regionId",region.id)}'>${region.name }</a></li> 							
						</c:forEach>
						</ul>
					</li>
					<li>
						<a href="#" class="inactive">租金<i></i></a>
						<ul style="display: none">
							<li><a href='<%=ctxPath %>/House?${zf:addQueryStringPart(queryString,"monthRent","*-500")}'>500元以下</a></li> 
							<li><a href='<%=ctxPath %>/House?${zf:addQueryStringPart(queryString,"monthRent","500-1500")}'>500-1500元</a></li> 
							<li><a href='<%=ctxPath %>/House?${zf:addQueryStringPart(queryString,"monthRent","1500-2500")}'>1500-2500元</a></li> 
							<li><a href='<%=ctxPath %>/House?${zf:addQueryStringPart(queryString,"monthRent","2500-*")}'>2500元以上</a></li> 
						</ul>
					</li>
					<li> 
						<a href="#" class="inactive">排序<i></i></a>
						<ul style="display: none">
							<li><a href='<%=ctxPath %>/House?${zf:addQueryStringPart(queryString,"orderBy","monthRent")}'>价格</a></li> 
							<li><a href='<%=ctxPath %>/House?${zf:addQueryStringPart(queryString,"orderBy","area")}'>面积</a></li> 
						</ul>
					</li>
				</ul>
			</div>
			
			<div id="oe_overlay" class="oe_overlay"></div>
			
			<div class="recom clearfloat recomtwo">
		    	<div class="content clearfloat box-s">
		    	<c:forEach items="${houses }" var="house">
			    	<div class="list clearfloat fl box-s">
			    			<!--  <a href="house-details.html"> -->
			    			<a href="<%=ctxPath%>/House?action=view&id=${house.id}">
				    			<div class="tu clearfloat">
				    				<span></span>
				    				<img src="upload/list-tu.jpg"/>
				    			</div>
				    			<div class="right clearfloat">
				    				<div class="tit clearfloat">
				    					<p class="fl">${house.communityName }</p>
				    					<span class="fr">${house.monthRent }<samp>元/月</samp></span>
				    				</div>
				    				<p class="recom-jianjie">${house.roomTypeName }   |  ${house.area }m²  |  ${house.decorateStatusName }</p>
				    				<div class="recom-bottom clearfloat">
				    					<span><i class="iconfont icon-duihao"></i>随时住</span>
				    					<span><i class="iconfont icon-duihao"></i>家电齐全</span>
				    				</div>
				    			</div>
			    			</a>
			    		</div>
		    	</c:forEach>
		    		
		    	</div>
		    </div>
	    </div>
	</body>
	<script type="text/javascript" src="js/psong.js" ></script>
	<script type="text/javascript" src="js/hmt.js" ></script>
</html>