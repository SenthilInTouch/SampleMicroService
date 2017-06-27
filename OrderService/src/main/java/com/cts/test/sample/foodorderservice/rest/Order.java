package com.cts.test.sample.foodorderservice.rest;

import java.util.Date;
import java.util.List;

public class Order {

	private long id;

	private double amount;

	private Date orderDate;

	private List<Item> itemsOrdered;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Item> getItemsOrdered() {
		return itemsOrdered;
	}

	public void setItemsOrdered(List<Item> itemsOrdered) {
		this.itemsOrdered = itemsOrdered;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

}
