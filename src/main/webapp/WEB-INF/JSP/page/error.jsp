<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"
isELIgnored="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="alert alert-danger hidden-print" role="alert"><h2>Code: ${statusCode }</h2>
<c:choose>
<c:when test="${statusCode == 403}"> You have not enough rights</c:when>
<c:when test="${statusCode == 404}"> Resource not found</c:when>
<c:otherwise>Undefined error</c:otherwise>
</c:choose>
</div>