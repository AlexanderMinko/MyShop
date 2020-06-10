<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"
	isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myshop" tagdir="/WEB-INF/tags"%>

<div id="order">
	<c:if test="${CURRENT_MESSAGE != null}">
		<div class="alert alert-warning hidden-print" role="alert">${CURRENT_MESSAGE }</div>
	</c:if>
	<h4 class="text-center">Order # ${order.id }</h4>
	<table class="table table-bordered">
		<thead>
			<tr>
				<th>Product</th>
				<th>Price</th>
				<th>Count</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${order.items}">
				<tr id="product${item.product.id}" class="item">
					<td class="text-center"><img src="${item.product.imageLink}"
						alt="${item.product.name}"><br>${item.product.name}</td>
					<td class="price">$ ${item.product.price}</td>
					<td class="count">${item.count}</td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="2" class="text-right"><strong>Total: </strong></td>
				<td colspan="1" class="total">$ ${order.totalCost}</td>
			</tr>
		</tbody>
	</table>
	<div class="row hidden-print">
		<div class="col-md-4 col-md-offset-4 col-lg-2 col-lg-offset-5">
			<a href="/my-orders" class="btn btn-primary btn-block" >My orders</a>
		</div>
	</div>
</div>
