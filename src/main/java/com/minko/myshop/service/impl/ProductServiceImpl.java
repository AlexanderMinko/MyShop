package com.minko.myshop.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.minko.myshop.entity.Category;
import com.minko.myshop.entity.Producer;
import com.minko.myshop.entity.Product;
import com.minko.myshop.exception.InternalServerErrorException;
import com.minko.myshop.form.SearchForm;
import com.minko.myshop.service.ProductService;

class ProductServiceImpl implements ProductService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);
	private static final String SELECT_ALL_PRODUCTS = "select p.*, c.name as category, pr.name as producer from product p, producer pr, category c "
			+ "where c.id=p.id_category and pr.id=p.id_producer";
	private static final String SELECT_ALL_PRODUCTS_BY_CATEGORY = "select p.*, c.name as category, pr.name as producer from product p, producer pr, category c "
			+ "where c.url=? and c.id=p.id_category and pr.id=p.id_producer";
	private static final String SELECT_ALL_PRODUCTS_BY_SEARCH_FORM = "select p.*, c.name as category, pr.name as producer from product p, producer pr, category c "
			+ "where p.name ilike ? and c.id=p.id_category and pr.id=p.id_producer";
	private static final String SELECT_ALL_CATEGORIES = "select c.* from category c order by c.name";
	private static final String SELECT_ALL_PRODUCERS = "select p.* from producer p order by p.name";
	private static final String SELECT_COUNT_PRODUCT = "select count(*) from product";
	private static final String SELECT_COUNT_PRODUCT_BY_SEARCH_FORM = "select count(*) from product p, producer pr, category c "
			+ "where p.name ilike ? and c.id=p.id_category and pr.id=p.id_producer";
	private static final String SELECT_COUNT_PRODUCT_BY_CATEGORY = "select count(*) from product p, category c "
			+ "where c.url=? and c.id=p.id_category";
	private static final String PRODUCTS_APPENDIX_QUERY = " order by p.id limit ? offset ?";

	private final DataSource dataSource;

	public ProductServiceImpl(DataSource dataSource) {
		super();
		this.dataSource = dataSource;

	}

	@Override
	public List<Product> listAllProducts(int page, int limit) {
		List<Product> products = new ArrayList<>();
		PreparedStatement st = null;
		ResultSet rs = null;
		try (Connection con = dataSource.getConnection()) {
			int offset = (page - 1) * limit;
			st = con.prepareStatement(SELECT_ALL_PRODUCTS + PRODUCTS_APPENDIX_QUERY);
			st.setInt(1, limit);
			st.setInt(2, offset);
			rs = st.executeQuery();
			while (rs.next()) {
				getProductsFromDb(products, rs);
			}
		} catch (SQLException e) {
			throw new InternalServerErrorException("Can't execute list of all products: " + e.getMessage(), e);
		}
		return products;
	}

	@Override
	public List<Product> listProductsByCategory(String categoryUrl, int page, int limit) {
		List<Product> products = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try (Connection con = dataSource.getConnection()) {
			int offset = (page - 1) * limit;
			ps = con.prepareStatement(SELECT_ALL_PRODUCTS_BY_CATEGORY + PRODUCTS_APPENDIX_QUERY);
			ps.setString(1, categoryUrl);
			ps.setInt(2, limit);
			ps.setInt(3, offset);
			rs = ps.executeQuery();
			while (rs.next()) {
				getProductsFromDb(products, rs);
			}
		} catch (SQLException e) {
			throw new InternalServerErrorException("Can't execute list of all products by category: " + e.getMessage(),
					e);
		}
		return products;
	}

	@Override
	public List<Category> listAllCategories() {
		List<Category> categories = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try (Connection con = dataSource.getConnection()) {
			ps = con.prepareStatement(SELECT_ALL_CATEGORIES);
			rs = ps.executeQuery();
			while (rs.next()) {
				Category c = new Category();
				c.setId(rs.getInt("id"));
				c.setName(rs.getString("name"));
				c.setProductCount(rs.getInt("product_count"));
				c.setUrl(rs.getString("url"));
				categories.add(c);
			}
		} catch (SQLException e) {
			throw new InternalServerErrorException("Can't execute list of all categories: " + e.getMessage(), e);
		}
		return categories;
	}

	@Override
	public List<Producer> listAllProducers() {
		List<Producer> producers = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try (Connection con = dataSource.getConnection()) {
			ps = con.prepareStatement(SELECT_ALL_PRODUCERS);
			System.out.println(SELECT_ALL_PRODUCERS + " 112page");
			rs = ps.executeQuery();
			while (rs.next()) {
				Producer p = new Producer();
				p.setId(rs.getInt("id"));
				p.setName(rs.getString("name"));
				p.setProductCount(rs.getInt("product_count"));
				producers.add(p);
			}
		} catch (SQLException e) {
			throw new InternalServerErrorException("Can't execute list of all producers: " + e.getMessage(), e);
		}
		return producers;
	}

	@Override
	public int countAllProducts() {
		int count;
		PreparedStatement st = null;
		ResultSet rs = null;
		try (Connection con = dataSource.getConnection()) {
			st = con.prepareStatement(SELECT_COUNT_PRODUCT);
			rs = st.executeQuery();
			if (rs.next()) {
				count = rs.getInt("count");
			} else {
				count = 0;
			}
		} catch (SQLException e) {
			throw new InternalServerErrorException("Can't execute count of all products: " + e.getMessage(), e);
		}
		return count;
	}

	@Override
	public int countProductsByCategory(String categoryUrl) {
		int count;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try (Connection con = dataSource.getConnection()) {
			ps = con.prepareStatement(SELECT_COUNT_PRODUCT_BY_CATEGORY);
			ps.setString(1, categoryUrl);
			rs = ps.executeQuery();
			if (rs.next()) {
				count = rs.getInt("count");
			} else {
				count = 0;
			}
		} catch (SQLException e) {
			throw new InternalServerErrorException("Can't execute count of products by category: " + e.getMessage(), e);
		}
		return count;
	}

	@Override
	public List<Product> listProductsBySearchForm(SearchForm searchForm, int page, int limit) {
		List<Product> products = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String categoriesQuery = getCategoriesQuery(searchForm);
		String producersQuery = getProducersQuery(searchForm);	
		StringBuilder finallyQuery = new StringBuilder();
		finallyQuery.append(SELECT_ALL_PRODUCTS_BY_SEARCH_FORM).append(categoriesQuery).append(producersQuery).append(PRODUCTS_APPENDIX_QUERY);
		try (Connection con = dataSource.getConnection()) {
			int offset = (page - 1) * limit;
			ps = con.prepareStatement(finallyQuery.toString());
			ps.setString(1, "%" + searchForm.getQuery() + "%");
			ps.setInt(2, limit);
			ps.setInt(3, offset);
			rs = ps.executeQuery();
			while (rs.next()) {
				getProductsFromDb(products, rs);
			}
		} catch (SQLException e) {
			throw new InternalServerErrorException(
					"Can't execute list of all products by search form : " + e.getMessage(), e);
		}
		return products;
	}

	@Override
	public int countProductsBySearchForm(SearchForm searchForm) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String categoriesQuery = getCategoriesQuery(searchForm);
		String producersQuery = getProducersQuery(searchForm);	
		int count;
		StringBuilder finallyQuery = new StringBuilder();
		finallyQuery.append(SELECT_COUNT_PRODUCT_BY_SEARCH_FORM).append(categoriesQuery).append(producersQuery);
		try (Connection con = dataSource.getConnection()) {
			ps = con.prepareStatement(finallyQuery.toString());
			ps.setString(1, "%" + searchForm.getQuery() + "%");
			rs = ps.executeQuery();
			if (rs.next()) {
				count = rs.getInt("count");
			} else {
				count = 0;
			}
		} catch (SQLException e) {
			throw new InternalServerErrorException(
					"Can't execute count of all products by serach form: " + e.getMessage(), e);
		}
		return count;
	}

	// Utility methods
	public void getProductsFromDb(List<Product> products, ResultSet rs) throws SQLException {
		Product p = new Product();
		p.setId(rs.getInt("id"));
		p.setName(rs.getString("name"));
		p.setDescription(rs.getString("description"));
		p.setImageLink(rs.getString("image_link"));
		p.setPrice(rs.getBigDecimal("price"));
		p.setCategory(rs.getString("category"));
		p.setProducer(rs.getString("producer"));
		products.add(p);
	}
	
	public String getCategoriesQuery(SearchForm searchForm) {
		List<Integer> categories = searchForm.getCategories();
		StringBuilder categoriesQuery = new StringBuilder();
		StringBuilder categoriesParams = new StringBuilder();
		if (!categories.isEmpty()) {
			for (Integer list : categories) {
				categoriesParams.append("c.id=").append(list).append(" or ");
			}
			categoriesQuery.append(" and (").append(categoriesParams.substring(0, categoriesParams.length() - 4)).append(")");
		}
		return categoriesQuery.toString();
	}
	
	public String getProducersQuery(SearchForm searchForm) {
		List<Integer> producers = searchForm.getProducers();
		StringBuilder producersQuery = new StringBuilder();
		StringBuilder producersParams = new StringBuilder();
		if (!producers.isEmpty()) {
			for (Integer list : producers) {
				producersParams.append("pr.id=").append(list).append(" or ");
			}
			producersQuery.append(" and (").append(producersParams.substring(0, producersParams.length() - 4)).append(")");
		}
		return producersQuery.toString();
	}
}
