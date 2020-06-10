package com.minko.myshop.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.minko.myshop.entity.Account;
import com.minko.myshop.entity.Order;
import com.minko.myshop.entity.OrderItem;
import com.minko.myshop.entity.Product;
import com.minko.myshop.exception.AccessDeniedException;
import com.minko.myshop.exception.InternalServerErrorException;
import com.minko.myshop.exception.ResourceNotFoundException;
import com.minko.myshop.form.ProductForm;
import com.minko.myshop.model.CurrentAccount;
import com.minko.myshop.model.ShoppingCart;
import com.minko.myshop.model.ShoppingCartItem;
import com.minko.myshop.model.SocialAccount;
import com.minko.myshop.service.OrderService;

class OrderServiceImpl implements OrderService {
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);
	private static final String SELECT_PRODUCT_TO_SHOPPING_CART = "select p.*, c.name as category, pr.name as producer from product p, producer pr, category c"
			+ " where c.id=p.id_category and pr.id=p.id_producer and p.id=?";
	private static final String SELECT_FB_USER = "select * from account where email=?";
	private static final String ADD_FB_USER = "Insert into account (name, email) values(?,?)";
	private static final String INSERT_ORDER = "insert into \"order\" (id_account, created) values(?, ?)";
	private static final String INSERT_ORDER_ITEMS = "insert into order_item (id_order, id_product, count) values (?, ?, ?)";
	private static final String SELECT_ID_ORDER = "select * from \"order\" where id_account=? and created=?";
	private static final String SELECT_ORDER_ITEMS_BY_ID = "SELECT oi.id as oiid, oi.id_order as id_order, oi.id_product, oi.count, p.*, c.name as category, pr.name as producer \r\n"
			+ " FROM order_item oi, product p, category c, producer pr where p.id = oi.id_product and c.id = p.id_category and pr.id = p.id_producer\r\n"
			+ "  and id_order = ?";
	private static final String SELECT_ORDER_BY_ID = "SELECT * from \"order\" where id = ?";
	private static final String SELECT_ORDERS_BY_ID_ACCOUNT = " SELECT * FROM \"order\" where id_account = ? limit ? offset ?";
	private static final String SELECT_COUNT_ORDERS_BY_ID_ACCOUNT = "SELECT COUNT(*) FROM \"order\" where id_account = ?";

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

	@Override
	public String serializeShoppingCart(ShoppingCart shoppingCart) {
		StringBuilder cook = new StringBuilder();
		for (ShoppingCartItem item : shoppingCart.getItems()) {
			cook.append(item.getProduct().getId()).append("-").append(item.getCount()).append("|");
		}
		if (cook.length() > 0)
			cook.deleteCharAt(cook.length() - 1);
		return cook.toString();
	}

	@Override
	public ShoppingCart deserializeShoppingCart(String string) {
		ShoppingCart shoppingCart = new ShoppingCart();
		String[] cookieValues = string.split("\\|");
		for (String str : cookieValues) {
			try {
				String[] data = str.split("-");
				int idProduct = Integer.parseInt(data[0]);
				int count = Integer.parseInt(data[1]);
				ProductForm productForm = new ProductForm(idProduct, count);
				addProductToShoppingCart(productForm, shoppingCart);
			} catch (RuntimeException e) {
				LOGGER.error("Cant deserialize shipping cart item: " + str, e);
			}
		}
		return shoppingCart.getItems().isEmpty() ? null : shoppingCart;
	}

	@Override
	public CurrentAccount authentificate(SocialAccount socialAccount) {
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		Account account = null;
		try (Connection con = dataSource.getConnection()) {
			ps1 = con.prepareStatement(SELECT_FB_USER);
			ps1.setString(1, socialAccount.getEmail());
			rs1 = ps1.executeQuery();
			if (rs1.next()) {
				account = getAccountFromDb(rs1);
			} else if (account == null) {
				account = new Account(socialAccount.getName(), socialAccount.getEmail());
				ps1 = con.prepareStatement(ADD_FB_USER);
				ps1.setString(1, socialAccount.getName());
				ps1.setString(2, socialAccount.getEmail());
				ps1.execute();
				con.commit();
			}
		} catch (SQLException e) {
			throw new InternalServerErrorException("Can't authentificate user: " + e.getMessage(), e);
		}
		return account;
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

	public Order getOrderFromDb(ResultSet rs) throws SQLException {
		Order o = new Order();
		o.setId(rs.getLong("id"));
		o.setCreated(rs.getTimestamp("created"));
		o.setIdAccount(rs.getInt("id_account"));
		return o;
	}

	public Account getAccountFromDb(ResultSet rs) throws SQLException {
		Account a = new Account();
		a.setId(rs.getInt("id"));
		a.setName(rs.getString("name"));
		a.setEmail(rs.getString("email"));
		return a;
	}

	@Override
	public long makeOrder(ShoppingCart shoppingCart, CurrentAccount currentAccount) {
		if (shoppingCart == null || shoppingCart.getItems().isEmpty()) {
			throw new InternalServerErrorException("Shopping cart is empty");
		}
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		Order order = new Order();
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps3 = null;
		try (Connection con = dataSource.getConnection()) {
			ps = con.prepareStatement(INSERT_ORDER);
			ps.setInt(1, currentAccount.getId());
			ps.setTimestamp(2, ts);
			ps.execute();
			con.commit();
			System.out.println("Order created");
		} catch (SQLException e) {
			throw new InternalServerErrorException("Can't create order: " + e.getMessage(), e);
		}
		ResultSet rs = null;
		try (Connection con = dataSource.getConnection()) {
			ps1 = con.prepareStatement(SELECT_ID_ORDER);
			ps1.setInt(1, currentAccount.getId());
			System.out.println(ts);
			ps1.setTimestamp(2, ts);
			rs = ps1.executeQuery();
			if (rs.next()) {
				order = getOrderFromDb(rs);
			} else {
				throw new InternalServerErrorException("RS IS EMPTY ");
			}
			ps3 = con.prepareStatement(INSERT_ORDER_ITEMS);
			List<Object[]> items = toOrderItemsList(order.getId(), shoppingCart.getItems());
			for (Object[] item : items) {
				System.out.println(item);
				ps3.setLong(1, (long) item[0]);
				ps3.setInt(2, (int) item[1]);
				ps3.setInt(3, (int) item[2]);
				ps3.addBatch();
			}
			ps3.executeBatch();
			con.commit();
			return order.getId();
		} catch (SQLException e) {
			throw new InternalServerErrorException("Can't make order: " + e.getMessage(), e);
		}
	}

	private List<Object[]> toOrderItemsList(long idOrder, Collection<ShoppingCartItem> items) {
		List<Object[]> orderItemsList = new ArrayList<>();
		for (ShoppingCartItem item : items) {
			orderItemsList.add(new Object[] { idOrder, item.getProduct().getId(), item.getCount() });
		}
		return orderItemsList;
	}

	@Override
	public Order findOrderItemsById(long idOrder, CurrentAccount currentAccount) {
		List<OrderItem> orderItems = new ArrayList<>();
		Order order = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try (Connection con = dataSource.getConnection()) {
			ps = con.prepareStatement(SELECT_ORDER_BY_ID);
			ps.setLong(1, idOrder);
			rs = ps.executeQuery();
			while (rs.next()) {
				order = getOrderFromDb(rs);
			}
			if(order == null) {
				throw new ResourceNotFoundException("Order not fount. ID order: " + idOrder);
			}
			if(!order.getIdAccount().equals(currentAccount.getId())) {
				throw new AccessDeniedException("Account with id=" + currentAccount.getId() + " is not owner for order with id=" + idOrder);
			}
		} catch (SQLException e) {
			throw new InternalServerErrorException("Can't find order: " + e.getMessage(), e);
		}
		try (Connection con = dataSource.getConnection()) {
			ps = con.prepareStatement(SELECT_ORDER_ITEMS_BY_ID);
			ps.setLong(1, idOrder);
			rs = ps.executeQuery();
			while (rs.next()) {
				OrderItem orderItem = new OrderItem();
				orderItem.setId(rs.getLong("oiid"));
				orderItem.setIdOrder(rs.getLong("id_order"));
				orderItem.setCount(rs.getInt("count"));
				Product p = new Product();
				p.setId(rs.getInt("id"));
				p.setName(rs.getString("name"));
				p.setDescription(rs.getString("description"));
				p.setImageLink(rs.getString("image_link"));
				p.setPrice(rs.getBigDecimal("price"));
				p.setCategory(rs.getString("category"));
				p.setProducer(rs.getString("producer"));
				orderItem.setProduct(p);
				orderItems.add(orderItem);
			}
			order.setItems(orderItems);
			return order;
		} catch (SQLException e) {
			throw new InternalServerErrorException("Can't find order: " + e.getMessage(), e);
		}	
	}
	
	@Override
	public List<Order> listOrders(CurrentAccount currentAccount, int page, int limit) {
		int offset = (page - 1) * limit;
		List<Order> listOrder = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try (Connection con = dataSource.getConnection()){
			ps = con.prepareStatement(SELECT_ORDERS_BY_ID_ACCOUNT);
			ps.setInt(1, currentAccount.getId());
			ps.setInt(2, limit);
			ps.setInt(3, offset);
			rs = ps.executeQuery();
			while(rs.next()) {
				Order o = new Order();
				o.setId(rs.getLong("id"));
				o.setIdAccount(rs.getInt("id_account"));
				o.setCreated(rs.getTimestamp("created"));
				listOrder.add(o);
			}
			return listOrder;
		} catch (SQLException e) {
			throw new InternalServerErrorException("Can't get list of order: " + e.getMessage(), e);
		}
		
	}
	
	@Override
	public int countMyOrders(CurrentAccount currentAccount) {
		int count = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try (Connection con = dataSource.getConnection()){
			ps = con.prepareStatement(SELECT_COUNT_ORDERS_BY_ID_ACCOUNT);
			ps.setInt(1, currentAccount.getId());
			rs = ps.executeQuery();
			if(rs.next()) {
				count = rs.getInt("count");
			}
			return count;
		} catch (SQLException e) {
			throw new InternalServerErrorException("Can't get count of orders: " + e.getMessage(), e);
		}	
	}
}
