package com.minko.myshop.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.minko.myshop.entity.Product;
import com.minko.myshop.exception.InternalServerErrorException;
import com.minko.myshop.form.ProductForm;
import com.minko.myshop.model.ShoppingCart;
import com.minko.myshop.service.OrderService;

class OrderServiceImpl implements OrderService {
	private static final String SELECT_PRODUCT_TO_SHOPPING_CART = "select p.*, c.name as category, pr.name as producer from product p, producer pr, category c"
			+ " where c.id=p.id_category and pr.id=p.id_producer and p.id=?";

	private final DataSource dataSource;

	public OrderServiceImpl(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	@Override
	public void addProductToShoppingCart(ProductForm productForm, ShoppingCart shoppingCart) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try (Connection con = dataSource.getConnection()) {
			ps = con.prepareStatement(SELECT_PRODUCT_TO_SHOPPING_CART);
			ps.setInt(1, productForm.getIdProduct());
			rs = ps.executeQuery();
			if (rs.next()) {
				Product product = getProductFromDb(rs);
				if (product == null) {
					throw new InternalServerErrorException("Product not found by id=" + productForm.getIdProduct());
				}
				shoppingCart.addProduct(product, productForm.getCount());
			}
		} catch (SQLException e) {
			throw new InternalServerErrorException("Can't execute addProductToShoppingCart: " + e.getMessage(), e);
		}
	}

	@Override
	public void removeProductFromShoppingCart(ProductForm productForm, ShoppingCart shoppingCart) {
		shoppingCart.removeProduct(productForm.getIdProduct(), productForm.getCount());
	}
	
	// Utility
	public Product getProductFromDb(ResultSet rs) throws SQLException {
		Product p = new Product();
		p.setId(rs.getInt("id"));
		p.setName(rs.getString("name"));
		p.setDescription(rs.getString("description"));
		p.setImageLink(rs.getString("image_link"));
		p.setPrice(rs.getBigDecimal("price"));
		p.setCategory(rs.getString("category"));
		p.setProducer(rs.getString("producer"));
		return p;
	}
}
