package com.cts.test.sample.foodorderservice.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cts.test.sample.foodorderservice.dao.OrderDao;

@RestController
@RequestMapping("/")
public class OrderService {

	@Autowired
	private OrderDao orderDao;
	
	private RestTemplate objRestTemplate;

	@PostMapping("/order")
	public Order submitOrder(@RequestBody List<Long> itemIds) {

		Item orderedItem;
		List<Item> orderedItems = new ArrayList<Item>();
		for(Long itemId : itemIds) {
			orderedItem = new Item();
			objRestTemplate = new RestTemplate();
			orderedItem = objRestTemplate.getForObject(
					"http://localhost:8080/FoodMenuService/item/{itemId}", Item.class,
					itemId);
			orderedItems.add(orderedItem);
		}
		return orderDao.createOrder(orderedItems);
	}

	@PutMapping("/order/{id}")
	public Order submitOrder(@PathVariable("id") long orderId, @RequestBody List<Long> itemIds) {

		Item orderedItem;
		List<Item> orderedItems = new ArrayList<Item>();

		for(Long itemId : itemIds) {
			orderedItem = new Item();
			objRestTemplate = new RestTemplate();
			orderedItem = objRestTemplate.getForObject(
					"http://localhost:8080/FoodMenuService/item/{itemId}", Item.class,
					itemId);
			orderedItems.add(orderedItem);
		}
		System.out.println(orderedItems.toString());
		return orderDao.updateOrder(orderId, orderedItems);
	}

	@GetMapping("/order/{orderIds}")
	public List<Order> getOrders(@PathVariable("orderIds") List<Long> orderIds) {

		List<Order> returnOrders = new ArrayList<Order>();
		for(Long orderId : orderIds) {
			returnOrders.add(orderDao.getOrder(orderId));
		}
		return returnOrders;
	}

	@GetMapping("/orders")
	public List<Order> getOrders() {
		return orderDao.getOrders();
	}

}
