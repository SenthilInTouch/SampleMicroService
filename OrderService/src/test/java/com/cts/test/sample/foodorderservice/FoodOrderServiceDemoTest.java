package com.cts.test.sample.foodorderservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import com.cts.test.sample.foodorderservice.dao.OrderDaoImpl;
import com.cts.test.sample.foodorderservice.rest.Category;
import com.cts.test.sample.foodorderservice.rest.Item;
import com.cts.test.sample.foodorderservice.rest.Menu;
import com.cts.test.sample.foodorderservice.rest.Order;
import com.cts.test.sample.foodorderservice.rest.OrderService;

/**
 * Unit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class FoodOrderServiceDemoTest {

	Item item = new Item();
	Menu menu = new Menu();
	Category category = new Category();
	List<Long> itemIds = new ArrayList<Long>();
	List<Item> lstItems = new ArrayList<Item>();
	Order objOrder = new Order();
	Date value = new Date();
	
	private MockMvc mockMvc;
    
    @Mock
	private RestTemplate objRestTemplate;
    
    @Mock
    private OrderDaoImpl orderDao;
    
    @InjectMocks
    private OrderService objOrderService;  
    
    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(objOrderService)
                .build();
    }
    
    @Before
    public void setup() {
    	menu.setMenuName("Indian");
    	category.setCategoryName("Starter");
    	item.setCategory(category);
    	item.setDescription("Panneer Sattey");
    	item.setDiscount(15);
    	item.setId(1);
    	item.setItemName("Panneer Sattey");
    	item.setMenu(menu);
    	item.setPrice(150);
    	
    	itemIds = new ArrayList<Long>();
    	itemIds.add(1l);
    	
    	objOrder.setId(1);
    	objOrder.setAmount(128.50);
    	lstItems.add(item);
    	objOrder.setItemsOrdered(lstItems);
    	objOrder.setOrderDate(value);
    	
    }
    
    @Test
    public void testCreateOrder() throws Exception {
    	Mockito.when(
				objRestTemplate.getForObject(
						Mockito.anyString(),
						Mockito.eq(Item.class), 
						Mockito.anyLong()))
    		.thenReturn(item);

    	String expected = "{\"id\":1,\"amount\":128.5,\"orderDate\":"+ value.getTime() + ","
    			+ "\"itemsOrdered\":[{\"id\":1,\"menu\":{\"menuName\":\"Indian\"},"
    					+ "\"category\":{\"categoryName\":\"Starter\"},\"itemName\""
    							+ ":\"Panneer Sattey\",\"description\""
    									+ ":\"Panneer Sattey\",\"price\":150.0,\"discount\":15.0}]}";
    	
    	Mockito.when(
    			orderDao.createOrder(Mockito.anyList())).thenReturn(objOrder);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				"/order").accept(
				MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(itemIds.toString());

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		Assert.assertEquals("Correct Response Status", HttpStatus.OK.value(), result.getResponse().getStatus());
		
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
    }
    

    @Test
    public void testUpdateOrder() throws Exception {
    	Mockito.when(
				objRestTemplate.getForObject(
						Mockito.anyString(),
						Mockito.eq(Item.class), 
						Mockito.anyLong()))
    		.thenReturn(item);
    	String expected = "{\"id\":1,\"amount\":128.5,\"orderDate\":"+ value.getTime() + ","
    			+ "\"itemsOrdered\":[{\"id\":1,\"menu\":{\"menuName\":\"Indian\"},"
    					+ "\"category\":{\"categoryName\":\"Starter\"},\"itemName\""
    							+ ":\"Panneer Sattey\",\"description\""
    									+ ":\"Panneer Sattey\",\"price\":150.0,\"discount\":15.0}]}";
    	
    	Mockito.when(
    			orderDao.updateOrder(Mockito.anyLong(), Mockito.anyList())).thenReturn(objOrder);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(
				"/order/1").accept(
				MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(itemIds.toString());

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		Assert.assertEquals("Correct Response Status", HttpStatus.OK.value(), result.getResponse().getStatus());
		
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
    }

    @Test
    public void testGetOrder() throws Exception {
    	String expected = "[{\"id\":1,\"amount\":128.5,"
    			+ "\"orderDate\":"+ value.getTime() + ","
    				+ "\"itemsOrdered\":[{\"id\":1,\"menu\""
    					+ ":{\"menuName\":\"Indian\"},\"category\":{\"categoryName\""
    							+ ":\"Starter\"},\"itemName\":\"Panneer Sattey\",\"description\""
    									+ ":\"Panneer Sattey\",\"price\":150.0,\"discount\":15.0}]}]";
    	Mockito.when(
    			orderDao.getOrder(Mockito.anyLong())).thenReturn(objOrder);
    	
    	RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/order/1").accept(
				MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		Assert.assertEquals("Correct Response Status", HttpStatus.OK.value(), result.getResponse().getStatus());
		
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
		
    }

    @Test
    public void testGetOrders() throws Exception {
    	String expected = "[{\"id\":1,\"amount\":128.5,"
    			+ "\"orderDate\":"+ value.getTime() + ","
    				+ "\"itemsOrdered\":[{\"id\":1,\"menu\""
    					+ ":{\"menuName\":\"Indian\"},\"category\":{\"categoryName\""
    							+ ":\"Starter\"},\"itemName\":\"Panneer Sattey\",\"description\""
    									+ ":\"Panneer Sattey\",\"price\":150.0,\"discount\":15.0}]}]";
    	List<Order> orders = new ArrayList<Order>();
    	orders.add(objOrder);
    	Mockito.when(
    			orderDao.getOrders()).thenReturn(orders);
    	
    	RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/orders").accept(
				MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		Assert.assertEquals("Correct Response Status", HttpStatus.OK.value(), result.getResponse().getStatus());
		
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

    }

}