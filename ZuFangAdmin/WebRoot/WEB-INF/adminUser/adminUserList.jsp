<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="/WEB-INF/header.jsp" %>
   
    <title>角色管理</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 管理员管理 <span class="c-gray en">&gt;</span> 角色管理 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="pd-20">
	<div class="cl pd-5 bg-1 bk-gray"> 
	<span class="l"> 
		<a href="javascript:;" onclick="datadel()" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a>
	 	<a class="btn btn-primary radius" href="javascript:;" onclick="admin_role_add('添加管理员','<%=ctxPath%>/adminuser?action=add','800')">
	 	<i class="Hui-iconfont">&#xe600;</i> 添加管理员</a> </span> <span class="r">共有数据：<strong>54</strong> 条</span> </div>
	<table class="table table-border table-bordered table-hover table-bg">
		<thead>
			<tr>
				<th scope="col" colspan="6">管理员管理</th>
			</tr>
			<tr class="text-c">
				<th width="25"><input type="checkbox" value="" name=""></th>
				<th width="40">ID</th>
				<th width="200">名称</th>
				<th width="200">电话</th>
				<th width="200">邮箱</th>
				<th width="200">城市</th>				
				<th width="70">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${adminusers }" var="admin">
			<tr class="text-c">
				<td><input type="checkbox" value="${admin.id }" name="roleId"></td>
				<td>${admin.id }</td>
				<td>${admin.name }</td>	
				<td>${admin.phoneNum }</td>	
				<td>${admin.email }</td>	
				<td>${admin.cityName }</td>			
				<td class="f-14"><a title="编辑" href="javascript:;" onclick="admin_role_edit('角色编辑','<%=ctxPath %>/adminuser?action=edit&id=${admin.id }','1')" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i></a> 
				<a title="删除" href="javascript:;" onclick="admin_role_del(this,'${admin.id}')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6e2;</i></a></td>
			</tr>	
		</c:forEach>
					
		</tbody>
	</table>
</div>
<script type="text/javascript" src="lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="lib/layer/1.9.3/layer.js"></script> 
<script type="text/javascript" src="lib/My97DatePicker/WdatePicker.js"></script> 
<script type="text/javascript" src="js/H-ui.js"></script> 
<script type="text/javascript" src="js/H-ui.admin.js"></script> 
<script type="text/javascript">
/*管理员-角色-添加*/
function admin_role_add(title,url,w,h){
	layer_show(title,url,w,h);
}
/*管理员-角色-编辑*/
function admin_role_edit(title,url,id,w,h){
	layer_show(title,url,w,h);
}
/*管理员-角色-删除*/
function admin_role_del(obj,id){
	layer.confirm('角色删除须谨慎，确认要删除吗？',function(index){
		//此处请求后台程序，下方是成功后的前台处理……
		$.ajax({
			type:'post',
			url:'<%=ctxPath%>/adminuser',
			data:{action:'delete', id:id},
			success:function(result){
				if(result.status=='ok'){
					$(obj).parents('tr').remove();
					layer.msg('已删除!',{icon:1,time:1000});
				}
				else{
					layer.msg('删除失败：'+result.msg,{icon:1,time:1000});
				}
			},
			error:function(){
				layer.msg('通讯错误！',{icon:1,time:1000});
			}
		})
	});
}
</script>
</body>
</html>