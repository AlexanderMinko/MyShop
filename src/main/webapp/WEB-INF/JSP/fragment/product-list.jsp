<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"
	isELIgnored="false" trimDirectiveWhitespaces="true"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:forEach var="p" items="${products}">
	<div class="col-xs-12 col-sm-6 col-md-4 col-lg-3 col-xlg-2">	
		<div id="product${p.id}" class="panel panel-default product">
			<div class="panel-body">
				<div class="thumbnail">
					<img src="${p.imageLink}" alt="${p.name}">
					<div class="desc">
						<div class="cell">
							<p>
								<span class="title">Details</span> ${p.description}
							</p>
						</div>
					</div>
				</div>
				<div class="name">${p.name}</div>
				<div class="code">Code: ${p.id}</div>
				<div class="price">$ ${p.price}</div>
				<a class="btn btn-primary pull-right buy-btn" data-id-product="${p.id}">Buy</a>
				<ul class="list-group">
					<li class="list-group-item"><small>Category:</small> <span class="category">${p.category}</span></li>
					<li class="list-group-item"><small>Producer:</small> <span class="producer">${p.producer}</span></li>
				</ul>
			</div>
		</div>
	</div> 
	</c:forEach>

