<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:forEach var="order" items="${orders}">
	<tr class="item">
		<td><a href="/order?id=${order.id }">Order # ${order.id } </a></td>
		<td><fmt:formatDate value="${order.created }"
				pattern="dd.MM.yyyy HH:mm" /></td>
	</tr>
</c:forEach>