<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"
	isELIgnored="false" trimDirectiveWhitespaces="true"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form class="search" action="/search">
	<div id="find" class="panel panel-success collapse">
		<div class="panel-heading">Find</div>
		<div class="panel-body">
			<div class="input-group">
				<input type="text" name="query" class="form-control" placeholder="Search query..." value="${searchForm.query }"> <span class="input-group-btn">
					 <a id="goSearch" class="btn btn-default">Go!</a>
				</span>
			</div>
			<div class="more-options">
				<a data-toggle="collapse" href="#serchOptions">More filters<span class="caret"></span></a>
			</div>
		</div>
		<div id="serchOptions" class="collapse ${!searchForm.categoriesEmpty or !searchForm.producersEmpty ? 'in' : ''}">
			<!--pullout menu filters-->
			<div class="panel-heading">Category filters</div>
			<div class="panel-body categories">
				<!--CATEGORIES-->
				<label><input type="checkbox" id="allCategories">All</label>
				<div class="form-group">
				<c:forEach var="c" items="${categories}">
					<div class="checkbox">
						<label><input type="checkbox" name="category" value="${c.id}"
						${searchForm.categories.contains(c.id) ? 'checked' : ''} class="search-option">${c.name}(${c.productCount})</label>
					</div>
					</c:forEach>
				</div>
			</div>
			<!--/CATEGORIES-->
			<div class="panel-heading">Producers filters</div>
			<div class="panel-body producers">
				<label><input type="checkbox" id="allProducers">All</label>
				<div class="form-group">
				<c:forEach var="p" items="${producers}">
					<div class="checkbox">
						<label><input type="checkbox" name="producer" value="${p.id}" 
						${searchForm.producers.contains(p.id) ? 'checked' : ''} class="search-option"> ${p.name}(${p.productCount})</label>
					</div>
					</c:forEach>
				</div>
				
			</div>
		</div>
		<!--/pullout menu filters-->
	</div>
	</form>
<div id="productCatalog" class="panel panel-success collapse">
	<div class="panel-heading">Product catalog</div>
	<div class="list-group">
	<c:forEach var="c" items="${categories}">
		<a href="/products${c.url}" class="list-group-item ${selectedCategoryUrl == c.url ? 'active' : '' }"><span class="badge">${c.productCount}</span>${c.name}</a>
		</c:forEach>
	</div>
</div>

