<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"
	isELIgnored="false"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

  <div id="shoppingCart">
      <div class="alert alert-warning hidden-print" role="alert">to make order, pls sign in</div>
      <table class="table table-bordered">
        <thead>
          <tr>
            <th>Product</th>
            <th>Price</th>
            <th>Count</th>
            <th class="hidden-print">Action</th>
          </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${CURRENT_SHOPPING_CART.items}">
          <tr id="product${item.product.id}" class="item">
            <td class="text-center"><img src="${item.product.imageLink}" alt="${item.product.name}"><br>${item.product.name}</td>
            <td class="price">${item.product.price}</td>
            <td class="count">${item.count}</td>
            <td class="hidden-print">
            <c:choose>
            <c:when test="${item.count > 1}">
              <a class="btn btn-danger remove-product" data-id-product="${item.product.id}" data-count="1">Remove one</a><br><br>
              <a id="remAll" class="btn btn-danger remove-product all" data-id-product="${item.product.id}" data-count="${item.count}">Remove all</a> 
              </c:when>
              <c:otherwise>
              <a class="btn btn-danger remove-product" data-id-product="${item.product.id}" data-count="1">Remove one</a><br><br>
              </c:otherwise>  
              </c:choose>  
            </td>
          </tr>
          </c:forEach>
          <tr>
            <td colspan="2" class="text-right"><strong>Total: </strong></td>
            <td colspan="2" class="total" >${CURRENT_SHOPPING_CART.totalCost}</td>
          </tr>
        </tbody>
      </table>
      <div class="row hidden-print">
        <div class="col-md-4 col-md-offset-4 col-lg-2 col-lg-offset-5">
          <a class="btn btn-primary btn-block"><i class="fa fa-facebook-official"></i> Sign in</a><br>
        </div>
      </div>
    </div>
	