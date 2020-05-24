<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"
isELIgnored="false" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Insert title here</title>
</head>
<body>
	<nav class="navbar navbar-default">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#myshopNav" aria-expanded="false">
				<span class="sr-only">Toggle navigation</span>
				 <span class="icon-bar"></span>
				 <span class="icon-bar"></span>
				 <span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="/products">MyShop</a><span>
			<a class="navbar-brand" href="shopping-cart.html">cart</a></span>
			</div>
			<div class="collapse navbar-collapse" id="myshopNav">
				<ul id="currentShoppingCart" class="nav navbar-nav navbar-right hidden">
				<li class="dropdown">
				<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
				 <i class="fa fa-shopping-cart"
					aria-hidden="true"></i> Shopping cart (<span class="total-count">0</span>)
					<span class="caret"></span></a>
					<div class="dropdown-menu shopping-cart-desc">
						Total count: <span class="total-count">0</span><br>
						Total cost: <span class="total-cost">0</span><br>
					<a href="shopping-cart.html" class="btn btn-primary btn-block">Cart</a>
					</div></li>
				</ul>
				<a href="#" class="btn btn-primary navbar-btn navbar-right sign-in">
				<i class="fa fa-facebook-official" aria-hidden="true"></i> Sign in</a>
				</div>
				<!-- /.navbar-collapse -->
			</div>
			<!-- /.container-fluid -->
		</nav>
	</body>
	</html>