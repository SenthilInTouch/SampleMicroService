package com.cts.test.sample.foodmenuservice.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ItemSerivce {
	private static List<Menu> menus = new ArrayList<Menu>();
	private static List<Category> categories = new ArrayList<Category>();
	private static List<Item> items = new ArrayList<Item>();
	static {
		Menu indianMenu = new Menu();
		indianMenu.setMenuName("Indian");
		
		Menu chineseMenu = new Menu();
		chineseMenu.setMenuName("Chinese");
		
		Menu italianMenu = new Menu();
		italianMenu.setMenuName("Italian");
		
		Category starters = new Category();
		starters.setCategoryName("Starters");
		
		Category entrees = new Category();
		entrees.setCategoryName("Entrees");
		
		Category maincourse = new Category();
		maincourse.setCategoryName("Maincourse");
		
		Category desserts = new Category();
		desserts.setCategoryName("Desserts");

		Item item1 = new Item();
		item1.setId(1);
		item1.setMenu(indianMenu);
		item1.setCategory(starters);
		item1.setDiscount(10);
		item1.setPrice(120);
		item1.setItemName("Panneer Tikka");
		item1.setDescription("Panneer Tikka");

		Item item2 = new Item();
		item2.setId(2);
		item2.setMenu(indianMenu);
		item2.setCategory(starters);
		item2.setDiscount(10);
		item2.setPrice(140);
		item2.setItemName("Chicken Kabab");
		item2.setDescription("Chicken Kabab");

		Item item3 = new Item();
		item3.setId(3);
		item3.setMenu(chineseMenu);
		item3.setCategory(starters);
		item3.setDiscount(15);
		item3.setPrice(100);
		item3.setItemName("Chicken Clear Soup");
		item3.setDescription("Chicken Clear Soup");

		Item item4 = new Item();
		item4.setId(4);
		item4.setMenu(italianMenu);
		item4.setCategory(starters);
		item4.setDiscount(15);
		item4.setPrice(100);
		item4.setItemName("Bread");
		item4.setDescription("Bread");

		Item item5 = new Item();
		item5.setId(5);
		item5.setMenu(indianMenu);
		item5.setCategory(maincourse);
		item5.setDiscount(10);
		item5.setPrice(120);
		item5.setItemName("Briyani");
		item5.setDescription("Briyani");

		menus.add(indianMenu);
		menus.add(chineseMenu);
		menus.add(italianMenu);
		
		categories.add(starters);
		categories.add(entrees);
		categories.add(maincourse);
		categories.add(desserts);
		
		items.add(item1);
		items.add(item2);
		items.add(item3);
		items.add(item4);
		items.add(item5);
	}

	public List<Item> getItems() {
		return items;
	}

	@GetMapping("/menus")
	public List<Menu> getMenus() {
		return menus;
	}
	
	@GetMapping("/categories")
	public List<Category> getCategories() {
		return categories;
	}
	
	@GetMapping("/menu/{menuName}")
	public Menu getItem(@PathVariable("menuName") String menuName) {

		Menu menu = null;

		for (Menu menuObj : menus) {

			if (menuObj.getMenuName().equalsIgnoreCase(menuName)) {
				menu = menuObj;
			}
		}

		return menu;
	}
	
	@GetMapping("/category/{categoryName}")
	public Category getCategory(@PathVariable("categoryName") String categoryName) {

		Category category = null;

		for (Category catgeroyObj : categories) {

			if (catgeroyObj.getCategoryName().equalsIgnoreCase(categoryName)) {
				category = catgeroyObj;
			}
		}
		return category;
	}
	
	@GetMapping("/menuitems/{menuName}/{categoryName}")
	public List<Item> getItems(@PathVariable("menuName") String menuName, 
			@PathVariable("categoryName") String categoryName) {

		List<Item> returnItems = null;
		System.out.println(this.getItems().toString());
		System.out.println("Menu Name "+menuName);
		this.getItems().stream().forEach(item-> System.out.println(item.getMenu().getMenuName().toString()));
		System.out.println("cate Name "+categoryName);
		this.getItems().stream().forEach(item-> System.out.println(item.getCategory().getCategoryName().toString()));
		returnItems = this.getItems().stream()
				.filter(item -> (item.getMenu().getMenuName().equalsIgnoreCase(menuName)
						&& item.getCategory().getCategoryName().equalsIgnoreCase(categoryName)))
				.collect(Collectors.toCollection(() -> new ArrayList<Item>()));
		return returnItems;
	}
	
	@GetMapping("/menuItem/{menuName}/{categoryName}/{itemId}")
	public List<Item> getItems(@PathVariable("menuName") String menuName, 
			@PathVariable("categoryName") String categoryName,
			@PathVariable("itemId") int itemId) {

		List<Item> returnItems = null;

		returnItems = this.getItems().stream()
				.filter(item -> item.getMenu().getMenuName().equalsIgnoreCase(menuName)
						&& item.getCategory().getCategoryName().equalsIgnoreCase(categoryName)
						&& item.getId() == itemId)
				.collect(Collectors.toCollection(() -> new ArrayList<Item>()));
		return returnItems;
	}

	@GetMapping("/item/{itemId}")
	public Item getItems(@PathVariable("itemId") long itemId) {

		Item returnItem = null;
		for(Item item: this.getItems()) {
			if(item.getId() == itemId) {
				returnItem = item;
				break;
			}
		}
		return returnItem;
	}

	@GetMapping("/items/{itemIds}")
	public List<Item> getItems(@PathVariable("itemIds") List<Long> itemIds) {

		List<Item> returnItems = new ArrayList<Item>();

		for(Long itemId: itemIds) {
			returnItems.add(this.getItems().stream()
					.filter(item -> item.getId() == itemId)
					.collect(Collectors.toCollection(() -> new ArrayList<Item>())).get(0));
		}
		return returnItems;
	}
}
