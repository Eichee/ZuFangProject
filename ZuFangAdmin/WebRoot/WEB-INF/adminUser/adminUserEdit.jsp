<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/header.jsp" %>   
<%@ taglib prefix="fns" uri="http://java.zufang.com/jsp/jstl/functions" %>

<script type="text/javascript">
	$(function(){
		var validForm = $("#form-admin-add").Validform({tiptype:2});//初始化校验器
		$("#btnSubmit").click(function(){
			if(validForm.check(false)==false)//表单校验不通过
			{
				return;
			}
			
			var data=$("#form-admin-add").serializeArray();
			$.ajax({
				type:"post",
				url:"<%=ctxPath%>/adminuser",
				data:data,
				success:function(result){
					if(result.status=="ok"){
						parent.location.reload();
					}
					else{
						layer.msg("Error:"+result.msg,{icon:6,time:2000});
					}
				},
				error:function(){
					layer.msg("Error:通讯错误",{icon:6,time:2000});
				}	
			})
		})
	})

</script>
 
<title>添加管理员</title>
</head>
<body>
<div class="pd-20">
	<form action="" method="post" class="form form-horizontal" id="form-admin-add">
		<div class="row cl">
			<label class="form-label col-3"><span class="c-red">*</span>管理员：</label>
			<div class="formControls col-5">
				<input type="text" class="input-text" value="${admin.name }" placeholder="" id="username" name="username" datatype="*2-16" nullmsg="用户名不能为空">
			</div>
			<div class="col-4"> </div>
		</div>
		<div class="row cl">
			<label class="form-label col-3"><span class="c-red">*</span>初始密码：</label>
			<div class="formControls col-5">
				<input type="password" placeholder="密码" autocomplete="off" value="" class="input-text" datatype="*6-20" nullmsg="密码不能为空" id="newpassword">
			</div>
			<div class="col-4"> </div>
		</div>
		<div class="row cl">
			<label class="form-label col-3"><span class="c-red">*</span>确认密码：</label>
			<div class="formControls col-5">
				<input type="password" placeholder="确认新密码" autocomplete="off" class="input-text Validform_error" errormsg="您两次输入的新密码不一致！" datatype="*" nullmsg="请再输入一次新密码！" id="newpassword2" name="newpassword2">
			</div>
			<div class="col-4"> </div>
		</div>
		<div class="row cl">
			<label class="form-label col-3"><span class="c-red">*</span>手机：</label>
			<div class="formControls col-5">
				<input type="text" class="input-text" value="${admin.phoneNum }" placeholder="" id="usertel" name="usertel"  datatype="m" nullmsg="手机不能为空">
			</div>
			<div class="col-4"> </div>
		</div>
		<div class="row cl">
			<label class="form-label col-3"><span class="c-red">*</span>邮箱：</label>
			<div class="formControls col-5">
				<input type="text" class="input-text" placeholder="@" name="email" id="email" datatype="e" nullmsg="请输入邮箱！" value="${admin.email }">
			</div>
			<div class="col-4"> </div>
		</div>
		<div class="row cl">
			<label class="form-label col-3"><span class="c-red">*</span>城市</label>
			<div class="formControls col-5">
				<select name="cityid" id="city">
					<option value="0"></option>
					<c:forEach items="${cities }" var="city">
						<option value="${city.id }" <c:if test="${city.id==admin.cityId }">selected</c:if> >${city.name }</option>
					</c:forEach>
					
				</select>
			</div>
		</div>
		<input type="hidden" name="action" value="editSubmit"/>
		<input type="hidden" name="adminUserId" value="${admin.id }"/>
		<div class="row cl">
			<div class="col-9 col-offset-3">
				<input class="btn btn-primary radius" type="button" id="btnSubmit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
			</div>
		</div>
	</form>
</div>
<script type="text/javascript" src="lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="lib/icheck/jquery.icheck.min.js"></script> 
<script type="text/javascript" src="lib/Validform/5.3.2/Validform.min.js"></script> 
<script type="text/javascript" src="lib/layer/1.9.3/layer.js"></script> 
<script type="text/javascript" src="js/H-ui.js"></script> 
<script type="text/javascript" src="js/H-ui.admin.js"></script> 
<script type="text/javascript">
$(function(){
	$('.skin-minimal input').iCheck({
		checkboxClass: 'icheckbox-blue',
		radioClass: 'iradio-blue',
		increaseArea: '20%'
	});
	
	$("#form-admin-add").Validform({
		tiptype:2,
		callback:function(form){
			form[0].submit();
			var index = parent.layer.getFrameIndex(window.name);
			parent.$('.btn-refresh').click();
			parent.layer.close(index);
		}
	});
});
</script>
</body>
</html>