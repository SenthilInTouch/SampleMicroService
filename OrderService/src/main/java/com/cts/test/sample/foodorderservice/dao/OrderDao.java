package com.cts.test.sample.foodorderservice.dao;

import java.util.List;

import com.cts.test.sample.foodorderservice.rest.Item;
import com.cts.test.sample.foodorderservice.rest.Order;

public interface OrderDao {

	public Order getOrder(Long orderId);
	public List<Order> getOrders();
	public Order createOrder(List<Item> orderedItems);
	public Order updateOrder(Long orderId, List<Item> orderedItems);
	public void deleteOrder(Long orderId);

}
