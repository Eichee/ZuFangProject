<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/head.jsp" %>
<title>修改密码</title>
<script type="text/javascript">
	$(function(){
	
		$("#imgCaptcha,#aCaptcha").click(function(){
			$("#imgCaptcha").attr("src","<%=ctxPath%>/User?action=captcha&rd="+Math.random());
		})
		
		
		$("#next").click(function(){
			var phoneNum=$("#phoneNum").val();
			var captcha=$("#captcha").val();
			$.ajax({
				type:"post",
				url:"<%=ctxPath%>/User",
				data:{action:'findPasswordSubmit',phoneNum:phoneNum,captcha:captcha},
				success:function(result){
					if(result.status=="ok"){
						location.href="<%=ctxPath%>/User?action=findPassword2";
					}
					else{
						alert(result.msg);
					}
				},
				error:function(){
					alert("通讯错误");
				}
			})
			
		})
		
	})
	

</script>
</head>
<body>
		<div class="headertwo clearfloat" id="header">
			<a href="javascript:history.go(-1)" class="fl box-s"><i class="iconfont icon-arrow-l fl"></i></a>
			<p class="fl">修改密码</p>
		</div>
		
		<div class="modify clearfloat" id="main">
			<ul>
				<li class="clearfloat">
					<input type="text" name="" id="phoneNum" value="" placeholder="手机" class="sname" />
				</li>
				<li class="clearfloat">
					<input type="text" name="" id="captcha" value="" placeholder="请输入右图验证码" class="syzma fl" />
					<span class="fl"><img id="imgCaptcha" src="<%=ctxPath%>/User?action=captcha&rd=<%= Math.random()%>"></span>
					<a href="#" class="fr"  id="aCaptcha">换一张</a>
				</li>
			</ul>
			<a href="#" id="next" class="pay-btn clearfloat">
				下一步
			</a>
		</div>		
	</body>
	<script type="text/javascript" src="js/jquery-1.8.3.min.js" ></script>
	<script type="text/javascript" src="js/jquery.SuperSlide.2.1.js" ></script>
	<script type="text/javascript" src="slick/slick.min.js" ></script>
	<script type="text/javascript" src="js/jquery.leanModal.min.js"></script>
	<script type="text/javascript" src="js/tchuang.js" ></script>
	<script type="text/javascript" src="js/hmt.js" ></script>
</html>