package org.loose.fis.fssa.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.loose.fis.fssa.model.CartShirt;
import org.loose.fis.fssa.model.Order;

class ShirtCartServiceTest {

	private static final String TEAM = "TEAM";
	private static final String PRICE = "10";
	private static final String QUANTITY = "5";
	
	@AfterEach
	   void tearDown() {
	      System.out.println("After each");
	      ShirtCartService.getDatabase().close();
	    }
	 
	@BeforeAll
	static void beforeAll() {
		System.out.println("Before Class");
	}
	
	@AfterAll
	static void afterAll() {
		System.out.println("After Class");
	}
	
	@BeforeEach
	void setUp() throws Exception{
		FileSystemService.APPLICATION_FOLDER=".test-shirt-application";
		FileSystemService.initDirectory();
  FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
  ShirtCartService.initDatabase();
	}
	
	@Test
	@DisplayName("Database is initialized, and there are no orders")
	void testDatabaseIsInitializedAndNoOrderIsThere() {
        assertThat(ShirtCartService.getAllShirtsInCart()).isNotNull();
        assertThat(ShirtCartService.getAllShirtsInCart()).isEmpty();
    }
	
	@Test
	@DisplayName("The shirtcart is successfully persisted to the database")
	void testShirtIsAddedToCartDatabase() {
		ShirtCartService.addShirtToCart(TEAM, PRICE, QUANTITY);
		assertThat(ShirtCartService.getAllShirtsInCart()).isNotEmpty();
        assertThat(ShirtCartService.getAllShirtsInCart()).size().isEqualTo(1);
        CartShirt cart = ShirtCartService.getAllShirtsInCart().get(0);
        assertThat(cart).isNotNull();
        assertThat(cart.getPrice()).isEqualTo(Integer.parseInt(PRICE));
        assertThat(cart.getQuantity()).isEqualTo(Integer.parseInt(QUANTITY));
        assertThat(cart.getTeam()).isEqualTo(TEAM);
	}
	
	
	@Test
	@DisplayName("The shirtcart database is empty after removing all its' elements")
	void testShirtCartIsEmptied() {
		ShirtCartService.addShirtToCart(TEAM, PRICE, QUANTITY);
		ShirtCartService.addShirtToCart(TEAM+1, PRICE+1, QUANTITY+1);
		ShirtCartService.addShirtToCart(TEAM+2, PRICE+2, QUANTITY+2);
		ShirtCartService.removeShirtsFromCart();
		assertThat(ShirtCartService.getAllShirtsInCart()).size().isEqualTo(0);	
	}
	
	@Test
	@DisplayName("The correct name combination of team and quantity is returned")
	void testShirtCartTeamQuantityIsReturned() {
		ShirtCartService.addShirtToCart(TEAM, PRICE, QUANTITY);
		ShirtCartService.addShirtToCart(TEAM+1, PRICE+1, "6");
		assertThat(ShirtCartService.getTeamQuantityForOrder()).isEqualTo("TEAM-5,TEAM1-6,");
	}
	
	@Test
	@DisplayName("The corect price is returned ")
	void testShirtCartTotalPriceIsReturned() {
		ShirtCartService.addShirtToCart(TEAM, "1", QUANTITY);
		ShirtCartService.addShirtToCart(TEAM, "2", QUANTITY);
		ShirtCartService.addShirtToCart(TEAM, "3", QUANTITY);
		assertThat(ShirtCartService.getTotalPriceForOrder()).isEqualTo(6);
	}
	
	

}
