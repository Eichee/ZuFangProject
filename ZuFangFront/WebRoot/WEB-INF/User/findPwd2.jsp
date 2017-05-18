<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/head.jsp" %>
<title>找回密码</title>
<script type="text/javascript">
	$(function(){
		$("#verify").click(function(){
			var smsCode=$("#smsCode").val();
			$.ajax({
				type:"post",
				url:"<%=ctxPath%>/User",
				data:{action:'findPassword2Submit',smsCode:smsCode},
				success:function(result){
					if (result.status=="ok") {
						location.href="<%=ctxPath%>/User?action=findPassword3";
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
<body>
		<div class="headertwo clearfloat" id="header">
			<a href="javascript:history.go(-1)" class="fl box-s"><i class="iconfont icon-arrow-l fl"></i></a>
			<p class="fl">短信验证</p>
		</div>
		
		<div class="modify clearfloat" id="main">
			<ul>
				<li class="clearfloat">
					<input type="text" name="" id="" value="" placeholder="${FindPwdPhoneNum }" class="syzma fl" />
					<a href="#" class="fr">发送验证码</a>
				</li>
				<li class="clearfloat">
					<input type="text" name="" id="smsCode" value="" placeholder="请输入手机短信中的验证码" class="sname" />
				</li>				
			</ul>
			<a href="#" id="verify" class="pay-btn clearfloat">
				验证
			</a>
		</div>		
	</body>

</html>