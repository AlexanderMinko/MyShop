package com.minko.myshop.entity;

public class OrderItem extends AbstractEntity<Long> {

	private static final long serialVersionUID = 7427078946367726756L;
	private Long idOrder;
	private Product product;
	private int count;

	public OrderItem(Product product, int count) {
		super();
		this.product = product;
		this.count = count;
	}

	public Long getIdOrder() {
		return idOrder;
	}

	public void setIdOrder(Long idOrder) {
		this.idOrder = idOrder;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return String.format("OrderItem [id=%s, idOrder=%s, product=%s, count=%s]", getId(), idOrder, product, count);
	}

}
