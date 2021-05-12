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
import org.loose.fis.fssa.model.Order;
import org.loose.fis.fssa.model.Shirt;

class OrderServiceTest {

	public static final String TEAM_QUANTITY = "team";
	public static final String TOTAL_PRICE = "10";
	public static final String CUSTOMER_NAME = "Aurel";
	public static final String CUSTOMER_COUNTRY = "Valcea";
	
	@AfterEach
	   void tearDown() {
	      System.out.println("After each");
	      OrderService.getDatabase().close();
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
     OrderService.initDatabase();
	}
	
	
	@Test
	@DisplayName("Database is initialized, and there are no orders")
	void testDatabaseIsInitializedAndNoOrderIsThere() {
        assertThat(OrderService.getAllOrders()).isNotNull();
        assertThat(OrderService.getAllOrders()).isEmpty();
    }
	
	@Test
	@DisplayName("Order is successfully persisted to the database")
	void testOrderIsAddedToDatabase() {
		OrderService.addOrdertoDatabase(TEAM_QUANTITY, TOTAL_PRICE, CUSTOMER_NAME , CUSTOMER_COUNTRY);
		assertThat(OrderService.getAllOrders()).isNotEmpty();
        assertThat(OrderService.getAllOrders()).size().isEqualTo(1);
        Order order = OrderService.getAllOrders().get(0);
        assertThat(order).isNotNull();
        assertThat(order.getTeam_quantity()).isEqualTo(TEAM_QUANTITY);
        assertThat(order.getCustomer_Country()).isEqualTo(CUSTOMER_COUNTRY);
        assertThat(order.getCustomer_name()).isEqualTo(CUSTOMER_NAME);
        assertThat(order.getTotal_price()).isEqualTo(Integer.parseInt(TOTAL_PRICE));
	}
	
	@Test
	@DisplayName("Correct order is retrieved from database")
	void testCorrectOrderIsRetrievedFromDatabase() {
		OrderService.addOrdertoDatabase(TEAM_QUANTITY, TOTAL_PRICE, CUSTOMER_NAME , CUSTOMER_COUNTRY);
		OrderService.addOrdertoDatabase(TEAM_QUANTITY+1, TOTAL_PRICE+1, CUSTOMER_NAME+1 , CUSTOMER_COUNTRY+1);
		Order order=OrderService.returnOrder(2);
		assertThat(order).isNotNull();
		assertThat(order.getTeam_quantity()).isEqualTo(TEAM_QUANTITY+1);
        assertThat(order.getCustomer_Country()).isEqualTo(CUSTOMER_COUNTRY+1);
        assertThat(order.getCustomer_name()).isEqualTo(CUSTOMER_NAME+1);
        assertThat(order.getTotal_price()).isEqualTo(Integer.parseInt(TOTAL_PRICE+1));
	}
	
		
	@Test
	@DisplayName("Order is succesfully accepted and so, removed from the database")
	void testOrderIsAcceptedAndRemovedFromDatabase() {
		OrderService.addOrdertoDatabase(TEAM_QUANTITY, TOTAL_PRICE, CUSTOMER_NAME , CUSTOMER_COUNTRY);
		OrderService.addOrdertoDatabase(TEAM_QUANTITY+1, TOTAL_PRICE+1, CUSTOMER_NAME+1 , CUSTOMER_COUNTRY+1);
		OrderService.acceptOrder(CUSTOMER_NAME, CUSTOMER_COUNTRY);
		assertThat(OrderService.getAllOrders()).size().isEqualTo(1);
	}
		
	@Test
	@DisplayName("Order is succesfully denied and so, removed from the database")
	void testOrderIsDeniedAndRemovedFromDatabase() {
		OrderService.addOrdertoDatabase(TEAM_QUANTITY, TOTAL_PRICE, CUSTOMER_NAME , CUSTOMER_COUNTRY);
		OrderService.addOrdertoDatabase(TEAM_QUANTITY+1, TOTAL_PRICE+1, CUSTOMER_NAME+1 , CUSTOMER_COUNTRY+1);
		OrderService.denyOrder(CUSTOMER_NAME, CUSTOMER_COUNTRY);
		assertThat(OrderService.getAllOrders()).size().isEqualTo(1);
	}
		
	@Test
	@DisplayName("Order number is succesfully returned")
	void testOrderNumberIsReturned() {
		OrderService.addOrdertoDatabase(TEAM_QUANTITY, TOTAL_PRICE, CUSTOMER_NAME , CUSTOMER_COUNTRY);
		OrderService.addOrdertoDatabase(TEAM_QUANTITY+1, TOTAL_PRICE+1, CUSTOMER_NAME+1 , CUSTOMER_COUNTRY+1);
		OrderService.addOrdertoDatabase(TEAM_QUANTITY+2, TOTAL_PRICE+2, CUSTOMER_NAME+2, CUSTOMER_COUNTRY+2);
		assertThat(OrderService.getOrderNumber()).isEqualTo(3);
	}
}
	
	
	
	

