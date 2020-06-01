;$(function(){
	var init = function(){
		initBuyBtn();
		$('#addToCart').click(addProductToCart);
		$('#addProductPopup .count').change(calculateCost);
		$('#loadMore').click(loadMoreProducts);
		selectAllCheck();
		$('#goSearch').click(goSearch);
		$('.remove-product').click(removeProductFromCart);
	};

	var showAddProductPopup = function() {
		var idProduct = $(this).attr('data-id-product');
		var product = $('#product' + idProduct);
		$('#addProductPopup').attr('data-id-product', idProduct);
		$('#addProductPopup .product-image').attr('src', product.find('img').attr('src'));
		$('#addProductPopup .name').text(product.find('.name').text());
		$('#addProductPopup .price').text(product.find('.price').text());
		$('#addProductPopup .category').text(product.find('.category').text());
		$('#addProductPopup .producer').text(product.find('.producer').text());
		$('#addProductPopup .count').val(1);
		$('#addProductPopup .cost').text(product.find('.price').text());
		$('#addToCart').removeClass('hidden');
		$('#addToCartIndicator').addClass('hidden');
		$('#addProductPopup').modal('show');
	};

	var initBuyBtn = function(){
		$('.buy-btn').on('click', showAddProductPopup);
	};



	var calculateCost = function(){
		var priceStr = $('#addProductPopup .price').text();
		var price = parseFloat(priceStr.replace('$', ' '));
		var count = parseInt($('#addProductPopup .count').val());
		var cost = price * count;
		$('#addProductPopup .cost').text('$ ' + cost.toFixed(2));

	};

	var loadMoreProducts = function(){
		$('#loadMore').addClass('hidden');
		$('#loadMoreIndicator').removeClass('hidden');
		var pageCurrent = parseInt($('#productList').attr('data-page-current'));
		var url = '/ajax/html/more' + location.pathname + '?page=' + (pageCurrent + 1) + '&' + location.search.substring(1);
		$.ajax({
			url : url ,
			success : function(html) {
				$('#productList .row').append(html);
				pageCurrent = pageCurrent + 1;
				var pageCount = parseInt($('#productList').attr('data-page-count'));
				$('#productList').attr('data-page-current', pageCurrent);
				if(pageCurrent < pageCount){	
					$('#loadMoreIndicator').addClass('hidden');
					$('#loadMore').removeClass('hidden');
				} else {
					$('#loadMore').remove();
					$('#loadMoreIndicator').remove();
				}
			},
			error : function(data){
				$('#loadMoreIndicator').addClass('hidden');
				$('#loadMore').removeClass('hidden');
				alert('Error');
			}
		});
	};

	var selectAllCheck = function(){
		$('#allCategories').on('click', function(){
			$('.categories .search-option').prop('checked', $(this).is(':checked'))
		});
		$('.categories .search-option').on('click', function(){
			$('#allCategories').prop('checked', false);
		});

		$('#allProducers').on('click', function(){
			$('.producers .search-option').prop('checked', $(this).is(':checked'))
		});
		$('.producers .search-option').on('click', function(){
			$('#allProducers').prop('checked', false);
		});
		
	};

	var goSearch = function(){
		var isAllSelected = function(selector){
			var unchecked = 0;
			$(selector).each(function(index, value){
				if(!$(value).is(':checked')){
					unchecked++;
				}
			});
			return unchecked === 0;
		};
		if(isAllSelected('.categories .search-option')){
			$('.categories .search-option').prop('checked', false);
		}

		if(isAllSelected('.producers .search-option')){
			$('.producers .search-option').prop('checked', false);
		}

		$('form.search').submit();
	};

	var confirm = function(msg, okFunction){
		if(window.confirm(msg)){
			okFunction();
		}	
	};

	var removeProductFromCart = function(){
		var btn = $(this);
		confirm('Are you sure?', function(){
			executeRemoveProduct(btn);
		});
	};

	var refreshTotalCost = function(){
		var total = 0;
		$('#shoppingCart .item').each(function(index, value){
			var count = parseInt($(value).find('.count').text());
			var price = parseFloat($(value).find('.price').text().replace('$', ' '));
			var val = price * count;
			total = total + val;
		});
		$('#shoppingCart .total').text('$ ' + total)
	};

	var executeRemoveProduct = function(btn){
		var idProductFromJS = btn.attr('data-id-product');
		var countFromJS = btn.attr('data-count');
		btn.removeClass('btn-danger');
		btn.removeClass('btn');
		btn.addClass('load-indicator');
		var str = btn.text();
		btn.text('');
		var currentCount = parseInt($('#product' + idProductFromJS + ' .count').text());

		$.ajax({
			url : '/ajax/json/product/remove' ,
			method : 'POST',
			data : {
				idProduct : idProductFromJS,
				count : countFromJS
			},
			success : function(data) {
				$('#currentShoppingCart .total-count').text(data.totalCount);
				$('#currentShoppingCart .total-cost').text(data.totalCost);
					var removeCount = parseInt(countFromJS);
					currentCount = currentCount - removeCount;
					if(currentCount < 2){
						$('#product' + idProductFromJS + ' .all').addClass('hidden');
					}
					btn.removeClass('load-indicator');
					btn.addClass('btn-danger');
					btn.addClass('btn');
					btn.text(str);
					if(currentCount <= 0){
						$('#product' + idProductFromJS).remove();
					} else {
						$('#product' + idProductFromJS + ' .count').text(currentCount);
					}
				refreshTotalCost();
				if($('#shoppingCart .item').length === 0)
					window.location.href = '/products';
			},
			error : function(data){
				$('#loadMoreIndicator').addClass('hidden');
				$('#loadMore').removeClass('hidden');
				alert('Error');
			}
		});
	}
	
	var addProductToCart = function(){
		var idProductFromJS = $('#addProductPopup').attr('data-id-product');
		var countFromJS = $('#addProductPopup .count').val();
		$('#addToCart').addClass('hidden');
		$('#addToCartIndicator').removeClass('hidden');
		$.ajax({
			url : '/ajax/json/product/add' ,
			method : 'POST',
			data : {
				idProduct : idProductFromJS,
				count : countFromJS
			},
			success : function(data) {
				$('#currentShoppingCart .total-count').text(data.totalCount);
				$('#currentShoppingCart .total-cost').text(data.totalCost);
				$('#currentShoppingCart').removeClass('hidden');
				$('#addProductPopup').modal('hide');
			},
			error : function(data){
				$('#loadMoreIndicator').addClass('hidden');
				$('#loadMore').removeClass('hidden');
				alert('Error');
			}
		});
	};
		
//		setTimeout(function(){
//			var data = {
//				totalCount : count,
//				totalCost : cost
//			};
//			if($('#shoppingCart .item').length === 1){
//				window.location.href = '/products';
//			}else{
//				var removeCount = parseInt(count);
//				var result = currentCount - removeCount;
//				btn.removeClass('load-indicator');
//				btn.addClass('btn-danger');
//				btn.addClass('btn');
//				btn.text(str);
//				if(result <= 0){
//					$('#product' + idProduct).remove();
//				} else {
//					$('#product' + idProduct + ' .count').text(result);
//				}
//
//			}refreshTotalCost();
//				
//		}, 800);

	
	init();
});
