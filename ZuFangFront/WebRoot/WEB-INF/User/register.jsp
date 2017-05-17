<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/head.jsp"%>
<script type="text/javascript" src="<%=ctxPath%>/js/hmt.js"></script>
<script type="text/javascript"	src="<%=ctxPath%>/js/jquery.SuperSlide.2.1.js"></script>
<script type="text/javascript"	src="<%=ctxPath%>/js/jquery.leanModal.min.js"></script>
<script type="text/javascript" src="<%=ctxPath%>/js/tchuang.js"></script>
<script type="text/javascript">
	
	$(function(){
		
		$("#captchaRefresh").click(function(){
			$("#imgCaptcha").attr("src","<%=ctxPath%>/User?action=captcha&rd="+Math.random());
		});
		
		$("#btnCaptchaSubmit").click(function(){	
			var captcha=$("#captcha").val();
			var phoneNum=$("#phoneNum").val();
			
			$.ajax({
				type:"post",
				url:"<%=ctxPath%>/User",
				data : {action:'registerSendSmsCode',captcha:captcha, phoneNum:phoneNum},
				success : function(result) {
					if (result.status == "ok") {
						$("#loginmodalt").attr("style", "display:none");
						$("#lean_overlay").attr("style","display:none");
					} else {
						alert("Error:" + result.msg);
						//layer_show(600, 80, "Error:"+result.msg, 2000);
					}
				},
				error : function() {
					//layer_show(600, 80, "Error:通讯错误!", 2000);
				}
			})
		})

		
		$("#register").click(function(){
			var phoneNum=$("#phoneNum").val();
			var smsCode=$("#smsCode").val();
			var password=$("#password").val();
			var password2=$("#password2").val();
			if(password!=password2){
				alert("密码不一致");
				return false;
			}
			$.ajax({
				type:"post",
				url:"<%= ctxPath%>/User",
				data:{action:'registerSubmit',phoneNum:phoneNum, smsCode:smsCode, password:password},
				success:function(result){
					if(result.status=="ok"){
						location.href="<%=ctxPath%>/User?action=login";
					}
					else{
						alert("错误:"+result.msg);
					}
				},
				error:function(){
					alert("通讯错误!");
				}
			})
		})
		
	})
</script>
<title>Register</title>
</head>
<body>
	<div class="headertwo clearfloat" id="header">
		<a href="javascript:history.go(-1)" class="fl box-s"><i
			class="iconfont icon-arrow-l fl"></i></a>
		<p class="fl">用户注册</p>
	</div>

	<div class="register clearfloat" id="main">
		<ul>
			<li class="clearfloat">
				<p class="tit fl">手机号</p> <input type="text" id="phoneNum" value=""
				class="shuru fl" placeholder="请输入手机号码" />
			</li>
			<li class="clearfloat">
				<p class="tit fl">验证码</p> <input type="text" id="" value=""
				class="shuru shurutwo fl" placeholder="请输入短信验证码" /> <a
				href="#loginmodalt" id="modaltrigger"> <input type="button"
					id="smsCode" value="获取短信验证码" class="btn fr" />
			</a>
			</li>
			<li class="clearfloat">
				<p class="tit fl">密码</p> <input type="text" id="password" value=""
				class="shuru fl" placeholder="请设置密码" />
			</li>
			<li class="clearfloat">
				<p class="tit fl">确认密码</p> <input type="text" id="password2" value=""
				class="shuru fl" placeholder="请再次输入密码" />
			</li>
		</ul>
		<a href="javascript:void(0);" id="register" class="pay-btn clearfloat"> 注册 </a>
		<div class="bottom clearfloat">
			<p class="fl">
				已有账号？ <a href="register.html">立即登录</a>
			</p>
		</div>
	</div>

	<!--弹窗内容 star-->
	<div id="loginmodalt" class="box-s loginmodaltwo" style="display:none;">
		<div class="top clearfloat box-s">
			<p class="tit">请输入图片验证码</p>
			<div class="xia clearfloat">
				<input type="text" id="captcha" value="" class="yzm fl"
					placeholder="填写图片验证码" /> <span class="fl"><img
					id="imgCaptcha"
					src="<%=ctxPath%>/User?action=captcha&rd=<%=Math.random()%>"></img></span>
				<i class="iconfont icon-shuaxin fr" id="captchaRefresh"></i>
			</div>
		</div>
		<form id="loginform" name="loginform" method="post" action="">
			<div class="center fl">
				<input type="button"  id="btnCaptchaCancel"
					class="hidemodal" value="取消" tabindex="3">
			</div>
			<div class="center fl">
				<input type="button"  id="btnCaptchaSubmit"
					class="" value="确定" tabindex="3">
			</div>
		</form>
	</div>
	<!--弹窗内容 end-->
</body>

</html>