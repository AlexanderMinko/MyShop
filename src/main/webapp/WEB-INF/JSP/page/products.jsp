<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"
isELIgnored="false"%>
<%@ taglib prefix="myshop" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div id="productList">
	<jsp:include page="../fragment/product-list.jsp"></jsp:include>
	<div class="text-center hidden-print">
		<img id="loadMoreIndicator" src="/static/img/loading.gif"
		class="hidden" alt="loading"> <a id="loadMore"
		class="btn btn-success">Load more products</a>
	</div>
</div>
<myshop:add-product-popup/>
<!--s-->