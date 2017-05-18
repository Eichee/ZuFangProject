<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include  file="/WEB-INF/head.jsp" %>
<title>登陆</title>
<script type="text/javascript">
		$(window).load(function(){
			$(".loading").addClass("loader-chanage")
			$(".loading").fadeOut(300)
		})
	</script>
<script type="text/javascript">
	$(function(){
		$("#login").click(function(){
			var phoneNum=$("#txtPhone").val();
			var password=$("#txtPassword").val();
			$.ajax({
				type:"post",
				url:"<%=ctxPath%>/User",
				data:{action:'loginSubmit',phoneNum:phoneNum,password:password},
				success:function(result){
					if(result.status=="ok"){
						location.href="<%=ctxPath%>/User?action=index";
					}
					else{
						alert(result.msg);
					}
				},
				error:function(){
					alert("通讯错误!");
				}
			})
			
		})
	})
</script>
</head>
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
<body>
		<div class="headertwo clearfloat" id="header">
			<a href="javascript:history.go(-1)" class="fl box-s"><i class="iconfont icon-arrow-l fl"></i></a>
			<p class="fl">登录</p>
		</div>
		
		<div class="sign clearfloat" id="main">
			<ul>
				<li class="clearfloat">
					<i class="iconfont icon-phone fl"></i>
					<input type="text" id="txtPhone" value="" placeholder="请输入手机号" class="fl phone" />
				</li>
				<li class="clearfloat">
					<i class="iconfont icon-lock fl"></i>
					<input type="text" id="txtPassword" value="" placeholder="请输入密码" class="fl phone" />
				</li>
			</ul>
			<a href="#" id="login" class="pay-btn clearfloat">
				登录
			</a>
			<div class="bottom clearfloat">
				<p class="fl">
					还不是会员？
					<a href="<%=ctxPath%>/User?action=register">立即注册</a>
				</p>
				<a href="<%=ctxPath %>/User?action=findPassword" class="fr">忘记密码？</a>
			</div>
		</div>
	</body>
</html>