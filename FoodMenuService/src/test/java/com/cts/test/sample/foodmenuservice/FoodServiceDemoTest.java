package com.cts.test.sample.foodmenuservice;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cts.test.sample.foodmenuservice.rest.ItemSerivce;

/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = ItemSerivce.class, secure = false)
public class FoodServiceDemoTest {
    public static final String REST_SERVICE_URI = "http://localhost:8080/FoodMenuService";

    @Autowired
	private MockMvc mockMvc;
    
    @Test
    public void testGetMenus() throws Exception {
    	String expected = "[{\"menuName\":\"Indian\"},{\"menuName\""
    			+ ":\"Chinese\"},{\"menuName\":\"Italian\"}]";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/menus").accept(
				MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		Assert.assertEquals("Correct Response Status", HttpStatus.OK.value(), result.getResponse().getStatus());
		
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
    }
    
    @Test
    public void testGetCategories() throws Exception {
		String expected = "[{\"categoryName\":\"Starters\"},{\"categoryName\""
				+ ":\"Entrees\"},{\"categoryName\":\"Maincourse\"},{\"categoryName\":\"Desserts\"}]";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/categories").accept(
				MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		Assert.assertEquals("Correct Response Status", HttpStatus.OK.value(), result.getResponse().getStatus());
		
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
    }
    
    @Test
    public void testGetMenuByMenuName() throws Exception {
    	String expected = "{\"menuName\":\"Indian\"}";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/menu/Indian").accept(
				MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		Assert.assertEquals("Correct Response Status", HttpStatus.OK.value(), 
				result.getResponse().getStatus());
		
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
    }
    
    @Test
    public void testGetMenuByMenuNameAndCategory() throws Exception {
    	String expected = "[{\"id\":1,\"menu\":{\"menuName\":\"Indian\"},\"category\":"
    			+ "{\"categoryName\":\"Starters\"},\"itemName\":\"Panneer Tikka\",\"description\""
    			+ ":\"Panneer Tikka\",\"price\":120.0,\"discount\":10.0},{\"id\":2,\"menu\""
    			+ ":{\"menuName\":\"Indian\"},\"category\":{\"categoryName\":\"Starters\"}"
    			+ ",\"itemName\":\"Chicken Kabab\",\"description\":\"Chicken Kabab\","
    			+ "\"price\":140.0,\"discount\":10.0}]";
    	
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/menuitems/Indian/Starters").accept(
				MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		Assert.assertEquals("Correct Response Status", HttpStatus.OK.value(), 
				result.getResponse().getStatus());
		
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
    }
    
    
    @Test
    public void testGetMenuByMenuNameAndCategoryAndItemId() throws Exception {
    	String expected = "[{\"id\":1,\"menu\":{\"menuName\":\"Indian\"},\"category\""
    			+ ":{\"categoryName\":\"Starters\"},\"itemName\":\"Panneer Tikka\","
    					+ "\"description\":\"Panneer Tikka\",\"price\":120.0,"
    							+ "\"discount\":10.0}]";
    	
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/menuItem/Indian/Starters/1").accept(
				MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		Assert.assertEquals("Correct Response Status", HttpStatus.OK.value(), 
				result.getResponse().getStatus());
		
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
    }
    
    @Test
    public void testGetItemById() throws Exception {
    	String expected = "{\"id\":4,\"menu\":{\"menuName\":\"Italian\"},\"category\""
    			+ ":{\"categoryName\":\"Starters\"},\"itemName\":\"Bread\",\"description\""
    					+ ":\"Bread\",\"price\":100.0,\"discount\":15.0}";
    	
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/item/4").accept(
				MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		Assert.assertEquals("Correct Response Status", HttpStatus.OK.value(), 
				result.getResponse().getStatus());
		
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
    }
    
    @Test
    public void testGetItemsByIds() throws Exception {
    	String expected = "[{\"id\":1,\"menu\":{\"menuName\":\"Indian\"},\"category\":{\"categoryName\":\"Starters\"},"
    			+ "\"itemName\":\"Panneer Tikka\",\"description\":\"Panneer Tikka\",\"price\":120.0,\"discount\":10.0},"
    					+ "{\"id\":2,\"menu\":{\"menuName\":\"Indian\"},\"category\":{\"categoryName\":\"Starters\"},"
    					+ "\"itemName\":\"Chicken Kabab\",\"description\":\"Chicken Kabab\",\"price\":140.0,\"discount\""
    					+ ":10.0},{\"id\":3,\"menu\":{\"menuName\":\"Chinese\"},\"category\":{\"categoryName\":\"Starters\"},"
    					+ "\"itemName\":\"Chicken Clear Soup\",\"description\":\"Chicken Clear Soup\",\"price\":100.0,\"discount\""
    					+ ":15.0},{\"id\":4,\"menu\":{\"menuName\":\"Italian\"},\"category\":{\"categoryName\":\"Starters\"},"
    			+ "\"itemName\":\"Bread\",\"description\":\"Bread\",\"price\":100.0,\"discount\":15.0}]";
    	
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/items/1,2,3,4").accept(
				MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		Assert.assertEquals("Correct Response Status", HttpStatus.OK.value(), 
				result.getResponse().getStatus());
		
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
    }

}