<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>窝窝团商城首页</title>
		<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
		<script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="js/bootstrap.min.js" type="text/javascript"></script>
	</head>
	<body>
		<div class="container-fluid">

			<!-- 引入header.jsp -->
			<jsp:include page="/header.jsp"></jsp:include>

			<!-- 轮播图 -->
			<div class="container-fluid" style="margin-top: 0;width: 85%">
				<div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
					<!-- 轮播图的中的小点 -->
					<ol class="carousel-indicators">
						<li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
						<li data-target="#carousel-example-generic" data-slide-to="1"></li>
						<li data-target="#carousel-example-generic" data-slide-to="2"></li>
						<li data-target="#carousel-example-generic" data-slide-to="3"></li>
					</ol>
					<!-- 轮播图的轮播图片 -->
					<div class="carousel-inner" role="listbox">
						<div class="item active">
							<a href="${pageContext.request.contextPath }/product?method=productInfo&pid=c7b8a4b11d704cd992c81557f4cad102">
								<img src="img/44.jpg" width="100%" style="height: 450px">
							</a>
						</div>
						<div class="item">
							<a href="${pageContext.request.contextPath }/product?method=productInfo&pid=06e3fc6b2d3646d3a67bc53aa6a750b6">
								<img src="img/55.jpg" width="100%" style="height: 450px">
							</a>
						</div>
						<div class="item">
							<img src="img/66.jpg" width="100%" style="height: 450px">
						</div>
						<div class="item">
							<img src="img/33.jpg" width="100%" style="height: 450px">
						</div>
					</div>

					<!-- 上一张 下一张按钮 -->
					<a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
						<span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
						<span class="sr-only">Previous</span>
					</a>
					<a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
						<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
						<span class="sr-only">Next</span>
					</a>
				</div>
			</div>
			
			<!-- 热门商品 -->
			<div class="container-fluid" style="width: 85%">
				<div class="col-md-12">
					<h2>热门商品&nbsp;&nbsp;<img src="img/title2.jpg"/></h2>
				</div>
				<div class="col-md-2" style="border:1px solid #E7E7E7;border-right:0;padding:0;">
					<a href="${pageContext.request.contextPath }/product?method=productInfo&pid=d7fd0bcfed544fa6b8a4a5b85dff1a76">
						<img src="products/big01.jpg" width="205" height="404" style="display: inline-block;"/>
					</a>
				</div>
				<div class="col-md-10">
					<div class="col-md-6" style="text-align:center;height:200px;padding:0px;">
						<a href="${pageContext.request.contextPath }/product?method=productInfo&pid=dd54ba80641a46d4b0b08b96caabf30d">
							<img src="images/middle01.png" width="450px" height="200px" style="display: inline-block;">
						</a>
					</div>
					
					<s:iterator value="#request.hotProducts">
						<div class="col-md-2" style="text-align:center;height:200px;padding:10px 0px;">
						<a href="${pageContext.request.contextPath }/product?method=productInfo&pid=${pid}">
							<img src="${pageContext.request.contextPath }/${pimage }" width="130" height="130" style="display: inline-block;">
						</a>
						<p style="margin: 5px 1px -2px"><a href="${pageContext.request.contextPath }/product?method=productInfo&pid=${pid}" style='color:black;font-size: 13px'>${pname }</a></p>
						<p><font color="#E4393C" style="font-size:16px">&yen;${marketPrice }</font></p>
					</div>
					</s:iterator>
	
				</div>
			</div>
			
			<!-- 最新商品 -->
			<div class="container-fluid" style="width: 85%">
				<div class="col-md-12">
					<h2>最新商品&nbsp;&nbsp;<img src="img/title2.jpg"/></h2>
				</div>
				<div class="col-md-2" style="border:1px solid #E7E7E7;border-right:0;padding:0;">
					<a href="${pageContext.request.contextPath }/product?method=productInfo&pid=0f6791cc45be4d9dbc1515c138dca2d0">
						<img src="images/big6.jpg" width="205" height="404" style="display: inline-block;"/>
					</a>
				</div>
				<div class="col-md-10">
					<div class="col-md-6" style="text-align:center;height:200px;padding:0px;">
						<a href="${pageContext.request.contextPath }/product?method=productInfo&pid=dd54ba80641a46d4b0b08b96caabf30d">
							<img src="images/middle01.png" width="450px" height="200px" style="display: inline-block;">
						</a>
					</div>
					
					<s:iterator value="#request.newProducts">
						<div class="col-md-2" style="text-align:center;height:200px;padding:10px 0px;">
							<a href="${pageContext.request.contextPath }/product?method=productInfo&pid=${pid}">
								<img src="${pageContext.request.contextPath }/${pimage }" width="130" height="130" style="display: inline-block;">
							</a>
							<p style="margin: 5px 1px -2px"><a href="${pageContext.request.contextPath }/product?method=productInfo&pid=${pid}" style='color:black;font-size: 13px'>${pname }</a></p>
							<p><font color="#E4393C" style="font-size:16px">&yen;${marketPrice }</font></p>
						</div>
					</s:iterator>
<%-- 					<c:forEach items="${newProductList }" var="newProduct">
						<div class="col-md-2" style="text-align:center;height:200px;padding:10px 0px;">
							<a href="${pageContext.request.contextPath }/product?method=productInfo&pid=${newProduct.pid}">
								<img src="${pageContext.request.contextPath }/${newProduct.pimage }" width="130" height="130" style="display: inline-block;">
							</a>
							<p style="margin: 5px 1px -2px"><a href="${pageContext.request.contextPath }/product?method=productInfo&pid=${newProduct.pid}" style='color:black;font-size: 13px'>${newProduct.pname }</a></p>
							<p><font color="#E4393C" style="font-size:16px">&yen;${newProduct.market_price }</font></p>
						</div>
					</c:forEach> --%>
				
				</div>
			</div>			
			
			<!-- 引入footer.jsp -->
			<jsp:include page="/footer.jsp"></jsp:include>
			
		</div>
	</body>

</html>