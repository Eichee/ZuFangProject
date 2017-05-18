<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
		String ctxPath=request.getContextPath();
	%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
   
    <meta name="keywords" content="">
    <meta name="description" content="">
        <script type="text/javascript" src="<%=ctxPath %>/js/jquery-1.8.3.min.js" ></script>
    <script src="js/rem.js"></script> 
    <script src="<%=ctxPath %>/js/jquery.min.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="<%=ctxPath %>/css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=ctxPath %>/css/page.css"/>
    <link rel="stylesheet" type="text/css" href="<%=ctxPath %>/css/all.css"/>
    <link rel="stylesheet" type="text/css" href="<%=ctxPath %>/css/mui.min.css"/>
    <link rel="stylesheet" type="text/css" href="<%=ctxPath %>/css/loaders.min.css"/>
    <link rel="stylesheet" type="text/css" href="<%=ctxPath %>/css/loading.css"/>
    <link rel="stylesheet" type="text/css" href="<%=ctxPath %>/slick/slick.css"/>
    
    <script type="text/javascript" src="<%= ctxPath%>/js/layer.js"></script>

	<script src="<%=ctxPath %>/js/fastclick.js"></script>
	<script src="<%=ctxPath %>/js/mui.min.js"></script>
	
	<script type="text/javascript" src="<%=ctxPath %>/js/jquery.SuperSlide.2.1.js" ></script>
	<script type="text/javascript" src="<%=ctxPath %>/js/hmt.js" ></script>
	 
	<script type="text/javascript" src="<%=ctxPath %>/slick/slick.min.js" ></script>
	<script type="text/javascript" src="<%=ctxPath %>/js/jquery.leanModal.min.js"></script>
	<script type="text/javascript" src="<%=ctxPath %>/js/tchuang.js" ></script>
</html>
	<!--插件-->
	<link rel="stylesheet" href="<%=ctxPath %>/css/swiper.min.css">
	<script src="<%=ctxPath %>/js/swiper.jquery.min.js"></script>
	
	<script type="text/javascript">
		$(window).load(function(){
			$(".loading").addClass("loader-chanage")
			$(".loading").fadeOut(300)
		})
	</script>
	
	<!--搜索点击效果-->
	<script >
	    $(function () {
	        var banner = new Swiper('.banner',{
	            autoplay: 5000,
	            pagination : '.swiper-pagination',
	            paginationClickable: true,
	            lazyLoading : true,
	            loop:true
	        });
	
	         mui('.pop-schwrap .sch-input').input();
	        var deceleration = mui.os.ios?0.003:0.0009;
	         mui('.pop-schwrap .scroll-wrap').scroll({
	                bounce: true,
	                indicators: true,
	                deceleration:deceleration
	        });
	        $('.top-sch-box .fdj,.top-sch-box .sch-txt,.pop-schwrap .btn-back').on('click',function () {
	            $('html,body').toggleClass('holding');
	            $('.pop-schwrap').toggleClass('on');
	            if($('.pop-schwrap').hasClass('on')) {;
	                $('.pop-schwrap .sch-input').focus();
	            }
	        });
	
	    });
	</script>
	