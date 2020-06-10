<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"
	isELIgnored="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<h2 class="text-center">My orders</h2>

<table id="myOrders" class="table table-bordered"
	data-page-count="${pageCount}" data-page-current="1">
	<thead>
		<tr>
			<th>Order ID</th>
			<th>Date</th>
		</tr>
	</thead>
	<tbody>
		<c:if test="${empty orders }">
			<tr>
				<td>No Orders Found</td>
			</tr>
		</c:if>
		<jsp:include page="../fragment/my-orders-tbody.jsp" />
	</tbody>
</table>
<div class="text-center hidden-print">
	<img id="loadMoreIndicator" src="/static/img/loading.gif"
		class="hidden" alt="loading">
	<c:if test="${pageCount > 1 }">
		<a id="loadMoreOrders" class="btn btn-success">Load more products</a>
	</c:if>
</div>
