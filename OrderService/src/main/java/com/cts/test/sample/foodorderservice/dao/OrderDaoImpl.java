package com.cts.test.sample.foodorderservice.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.cts.test.sample.foodorderservice.rest.Item;
import com.cts.test.sample.foodorderservice.rest.Order;

@Component
public class OrderDaoImpl implements OrderDao {
	private static Map<Long, Order> orders = new HashMap<Long, Order>();
	private static long id = 1;
	
	public Order createOrder(List<Item> orderedItems) {
		Order order = new Order();
		double amount = 0;
		order.setId(id);
		Iterator<Item> itemIterator = orderedItems.iterator();
		Item item;
		while(itemIterator.hasNext()) {
			item = itemIterator.next();
			amount = amount +(item.getPrice()*item.getDiscount()/100);
		}
		order.setAmount(amount);
		order.setItemsOrdered(orderedItems);
		order.setOrderDate(new Date());
		orders.put(id, order);
		id++;
		return order;
	}
	
	public Order updateOrder(Long orderId, List<Item> orderedItems) {
		Order order = new Order();
		double amount = 0;
		long curId;
		if(orderId > 0) {
			order.setId(orderId);
			curId = orderId;
		} else {
			order.setId(id);
			curId = id;
			id++;
		}
		Iterator<Item> itemIterator = orderedItems.iterator();
		Item item;
		while(itemIterator.hasNext()) {
			item = itemIterator.next();
			amount = amount +(item.getPrice()*item.getDiscount()/100);
		}
		order.setAmount(amount);
		order.setItemsOrdered(orderedItems);
		order.setOrderDate(new Date());
		orders.put(curId, order);

		return order;
	}
	
	public void deleteOrder(Long orderId) {
		if (orderId > 0 && orders.containsKey(orderId)) {
			orders.remove(orderId);
		} 
	}
	
	public Order getOrder(Long orderId) {
		Order order = new Order();

		if (orderId > 0 && orders.containsKey(orderId)) {
			order = orders.get(orderId);
		} 
		return order;
	}
	
	public List<Order> getOrders() {
		List<Order> returnOrders = new ArrayList<Order>();
		Set<Long> objKeySet = orders.keySet();
		for ( Long orderKey: objKeySet) {
			returnOrders.add(orders.get(orderKey));
		}
		return returnOrders;
	}
}
