<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<!-- 登录 注册 购物车... -->
<div class="container-fluid" style="width: 85%;margin-top: 30px">
	<div class="col-md-6">
		<img src="img/mylogo.png" height="40px"/>
	</div>
	<div class="col-md-2">
		<!-- <img src="img/header.png" /> -->
	</div>
	<div class="col-md-4" style="padding-top:15px;">
		
		<c:if test="${empty user }">
			<ol class="list-inline" style="float: right">
				<li><a href="login.jsp" style="color: red">登录</a></li>
				<li><a href="register.jsp" style="color: red">注册</a></li>
				<li><a href="cart.jsp" style="color: #666">购物车</a></li>
				<li><a href="${pageContext.request.contextPath }/product?method=myOrders" style="color: #666">我的订单</a></li>
			</ol>
		</c:if>
		<c:if test="${!empty user }">
			<ol class="list-inline" style="float: right">
				<li><a href="javascript:;" style="color: red">欢迎你，${user.username }</a></li>
				<li><a href="${pageContext.request.contextPath}/user?method=logot" style="color: red">退出</a></li>
				<li><a href="cart.jsp" style="color: #666">购物车</a></li>
				<li><a href="${pageContext.request.contextPath }/product?method=myOrders" style="color: #666">我的订单</a></li>
			</ol>
		</c:if>
		
	</div>
</div>

<!-- 导航条 -->
<div class="container-fluid" style="width: 85%">
	<nav class="navbar navbar-inverse" style="border-color: white;margin-bottom: 5px;">
		<div class="container-fluid" style="background-color: #D3D7FA;">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="${pageContext.request.contextPath }/" style="font-size: 22px;color: black">首页</a>
			</div>

			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul id="categoryUl" class="nav navbar-nav">
				
				</ul>
				
				<form class="navbar-form navbar-right" role="search" action="javascript:;">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="Search">
						
					</div>
					<button type="submit" class="btn btn-default">Submit</button>
				</form>
			</div>
		</div>
		
		<script type="text/javascript">
			$(function(){
				$.post(
					"${pageContext.request.contextPath}/product?method=categoryList",
					function (data) {
						var content = "";
						for(var i = 0; i<data.length; i++){
							content += "<li><a style='color: black;font-size: 14px' href='${pageContext.request.contextPath}/product?method=productListByCid&cid="+data[i].cid+"'>"+ data[i].cname +"</a></li>"
						}
						//content+="<li><a style='color: black;font-size: 14px' href='myinfo.jsp'>关于我们</a></li>"
						$("#categoryUl").html(content);
					},
					"json"
				);
			})		
		</script>
		
	</nav>
</div>