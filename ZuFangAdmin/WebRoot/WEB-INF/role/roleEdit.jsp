<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="com.zufang.dto.RoleDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/header.jsp" %>   
<%@taglib prefix="fns" uri="http://java.zufang.com/jsp/jstl/functions"%>

<script type="text/javascript">
	$(function(){
				
		$("#btnSave").click(function(){
			var data=$("#form-user-character-add").serializeArray();
			$.ajax({
				type:"post",
				url:"<%=ctxPath%>/role",
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
 
<title>新建网站角色</title>
</head>
<body>
<div class="pd-20">

	<form action="" method="post" class="form form-horizontal" id="form-user-character-add">
		<input type="hidden" name="action" value="editSubmit"/>
		<input type="hidden" name="roleId" value="${role.id }"/>
		<div class="row cl">
			<label class="form-label col-2"><span class="c-red">*</span>角色名称：</label>
			<div class="formControls col-10">
				<input type="text" class="input-text" value="${role.name }" placeholder="" id="user-name" name="roleName" datatype="*4-16" nullmsg="用户账户不能为空">
			</div>
		</div>
		
		<div class="row cl">
			<label class="form-label col-2">权限名称：</label>
			<div class="formControls col-10">
				<dl class="permission-list">					
						<dl class="cl permission-list2">
								<c:forEach items="${permissions }" var="perm">
									<label class="">
									<input type="checkbox" value="${perm.id }" name="permId" id="permId${perm.id }" 
										<c:if test="${fns:contains(rolePermIds,perm.id) }">checked</c:if>
									/>
									${perm.description }</label>
								</c:forEach>
						</dl>
				</dl>
			</div>
		</div>
		<div class="row cl">
			<div class="col-10 col-offset-2">
				<button type="button" class="btn btn-success radius" id="btnSave" name="admin-role-save"><i class="icon-ok"></i> 确定</button>
			</div>
		</div>
	</form>
</div>
<script type="text/javascript" src="lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="lib/layer/1.9.3/layer.js"></script> 
<script type="text/javascript" src="lib/laypage/1.2/laypage.js"></script> 
<script type="text/javascript" src="lib/My97DatePicker/WdatePicker.js"></script> 
<script type="text/javascript" src="js/H-ui.js"></script> 
<script type="text/javascript" src="js/H-ui.admin.js"></script> 
<script>
$(function(){
	$(".permission-list dt input:checkbox").click(function(){
		$(this).closest("dl").find("dd input:checkbox").prop("checked",$(this).prop("checked"));
	});
	$(".permission-list2 dd input:checkbox").click(function(){
		var l =$(this).parent().parent().find("input:checked").length;
		var l2=$(this).parents(".permission-list").find(".permission-list2 dd").find("input:checked").length;
		if($(this).prop("checked")){
			$(this).closest("dl").find("dt input:checkbox").prop("checked",true);
			$(this).parents(".permission-list").find("dt").first().find("input:checkbox").prop("checked",true);
		}
		else{
			if(l==0){
				$(this).closest("dl").find("dt input:checkbox").prop("checked",false);
			}
			if(l2==0){
				$(this).parents(".permission-list").find("dt").first().find("input:checkbox").prop("checked",false);
			}
		}
		
	});
});
</script>
</body>
</html>