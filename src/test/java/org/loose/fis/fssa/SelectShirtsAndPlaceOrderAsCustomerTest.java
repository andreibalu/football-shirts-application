package org.loose.fis.fssa;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.assertions.api.Assertions.assertThat;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loose.fis.fssa.controllers.CustomerHomeController;
import org.loose.fis.fssa.exceptions.UsernameAlreadyExistsException;
import org.loose.fis.fssa.model.Shirt;
import org.loose.fis.fssa.services.FileSystemService;
import org.loose.fis.fssa.services.OrderService;
import org.loose.fis.fssa.services.ShirtCartService;
import org.loose.fis.fssa.services.ShirtService;
import org.loose.fis.fssa.services.UserService;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)

class SelectShirtsAndPlaceOrderAsCustomerTest {


	public static final String USERNAMECUSTOMER = "customer";
	public static final String PASSWORD = "password";
	 

	@AfterEach
	   void tearDown() {
	     ShirtService.getDatabase().close();
	     UserService.getDatabase().close();
	     ShirtCartService.getDatabase().close();
	     OrderService.getDatabase().close();
	    }
	
	@BeforeEach
	void setUp() throws Exception{
		FileSystemService.APPLICATION_FOLDER=".test-shirt-application";
		FileSystemService.initDirectory();
      FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
      UserService.initDatabase();
      ShirtService.initDatabase();
      ShirtCartService.initDatabase();
      OrderService.initDatabase();
      UserService.addUser(USERNAMECUSTOMER, PASSWORD,"Customer");
	}
	
	@Start
	void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
       primaryStage.setTitle("Login");
       primaryStage.setScene(new Scene(root, 400, 300));
       primaryStage.show();
	}
	
	@Test
	void testGoToLogoutButton(FxRobot robot) throws Exception {
		ShirtService.addShirt("Otelul Galati","Liga 4","16","20","/images/otelul.jpg");
		robot.clickOn("#username");
		robot.write(USERNAMECUSTOMER);
		robot.clickOn("#password");
		robot.write(PASSWORD);
		robot.clickOn("#role");
		robot.type(KeyCode.DOWN);
		robot.type(KeyCode.DOWN); //selects Customer role
		robot.type(KeyCode.ENTER);
		robot.clickOn("#loginButton");
		robot.clickOn("#logoutfromCustomerButton");
	}
	
	@Test
	void testAddToCart(FxRobot robot) {
		ShirtService.addShirt("Otelul Galati","Liga 4","16","20","/images/otelul.jpg");
		ShirtService.addShirt("Poli Timisoara","Liga 2","6","20","/images/politm.jpg");
		ShirtService.addShirt("Eintracht Frankfurt","Bundesliga","5","40","/images/frankfurt.jpg");
		ShirtService.addShirt("Liverpool","Premier League","10","60","/images/lvp.jpg");
		
		robot.clickOn("#username");
		robot.write(USERNAMECUSTOMER);
		robot.clickOn("#password");
		robot.write(PASSWORD);
		robot.clickOn("#role");
		robot.type(KeyCode.DOWN);
		robot.type(KeyCode.DOWN); //selects Customer role
		robot.type(KeyCode.ENTER);
		robot.clickOn("#loginButton");
		
		robot.clickOn("#selectShirt");
		robot.clickOn("#quantity");
		robot.write("10");
		robot.clickOn("#addtocartButton");
		assertThat(robot.lookup("#addtocartMessage").queryText()).hasText("Added shirt succesfully to cart");	
		
	}
	
	@Test
	void testNotEnoughStock(FxRobot robot)  {
		ShirtService.addShirt("Eintracht Frankfurt","Bundesliga","5","10","/images/frankfurt.jpg");
		ShirtService.addShirt("Otelul Galati","Liga 4","16","15","/images/otelul.jpg");
		ShirtService.addShirt("Poli Timisoara","Liga 2","6","20","/images/politm.jpg");
		ShirtService.addShirt("Liverpool","Premier League","10","60","/images/lvp.jpg");
		
		robot.clickOn("#username");
		robot.write(USERNAMECUSTOMER);
		robot.clickOn("#password");
		robot.write(PASSWORD);
		robot.clickOn("#role");
		robot.type(KeyCode.DOWN);
		robot.type(KeyCode.DOWN); //selects Customer role
		robot.type(KeyCode.ENTER);
		robot.clickOn("#loginButton");
		
		robot.clickOn("#selectShirt");
		robot.clickOn("#quantity");
		robot.write("20");
		robot.clickOn("#addtocartButton");
		assertThat(robot.lookup("#addtocartMessage").queryText()).hasText(
				String.format("Not enough shirts on stock, please select a quantity lower or equal to "+String.valueOf("10")));	
	}
	
	@Test
	void testAddNewOrder(FxRobot robot) {
		ShirtService.addShirt("Otelul Galati","Liga 4","16","20","/images/otelul.jpg");
		ShirtService.addShirt("Poli Timisoara","Liga 2","6","20","/images/politm.jpg");
		ShirtService.addShirt("Eintracht Frankfurt","Bundesliga","5","40","/images/frankfurt.jpg");
		ShirtService.addShirt("Liverpool","Premier League","10","60","/images/lvp.jpg");
		
		robot.clickOn("#username");
		robot.write(USERNAMECUSTOMER);
		robot.clickOn("#password");
		robot.write(PASSWORD);
		robot.clickOn("#role");
		robot.type(KeyCode.DOWN);
		robot.type(KeyCode.DOWN); //selects Customer role
		robot.type(KeyCode.ENTER);
		robot.clickOn("#loginButton");
		
	    robot.clickOn("#selectShirt");
		robot.clickOn("#quantity");
		robot.write("2");
		robot.clickOn("#addtocartButton");
		robot.clickOn("#entername");
		robot.write("Adrian Ciatlosi");
		robot.clickOn("#entercountry");
		robot.write("Romania");
		robot.clickOn("#placeorderButton");
		assertThat(robot.lookup("#orderplacedMessage").queryText()).hasText("Order placed succesfully!");	
		
	}
}
