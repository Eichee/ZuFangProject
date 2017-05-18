<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/head.jsp" %>
<title>找回密码</title>
<script type="text/javascript">
	
	$(function(){
		$("#submit").click(function(){
			var password=$("#password").val();
			var password2=$("#password2").val();
			if(password!=password2){
				alert("两次输入不一致");
				return false;
			}
			$.ajax({
				type:"post",
				url:"<%=ctxPath%>/User",
				data:{action:'findPassword3Submit',password:password},
				success:function(result){
					if(result.status=="ok"){
						location.href="<%=ctxPath%>/User?action=findPasswordComplete";
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
			<p class="fl">输入新密码</p>
		</div>
		
		<div class="modify clearfloat" id="main">
			<ul>
				<li class="clearfloat">
					<input type="text" name="" id="password" value="" placeholder="请输入新密码" class="sname snametwo" />
				</li>
				<li class="clearfloat">
					<input type="text" name="" id="password2" value="" placeholder="请再次输入新密码" class="sname snametwo" />
				</li>	
			</ul>
			<a href="#" id="submit" class="pay-btn clearfloat">
				确认修改
			</a>
		</div>
	</body>
</html>