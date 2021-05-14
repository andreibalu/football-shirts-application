package org.loose.fis.fssa;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.assertions.api.Assertions.assertThat;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loose.fis.fssa.exceptions.UsernameAlreadyExistsException;
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

class AcceptOrDenyAnOrderAsShopOwnerTest {
	
	
	
	public static final String USERNAMEOWNER = "shopowner";
	public static final String PASSWORD = "password";
	public static final String CUSTOMERNAME = "Radu";
	public static final String CUSTOMERCOUNTRY= "Romania";

	@AfterEach
	   void tearDown() {
	      ShirtService.getDatabase().close();
	      UserService.getDatabase().close();
	      OrderService.getDatabase().close();
	      ShirtCartService.getDatabase().close();
	    }
	
	@BeforeEach
	void setUp() throws Exception{
		FileSystemService.APPLICATION_FOLDER=".test-shirt-application";
		FileSystemService.initDirectory();
 FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
 ShirtService.initDatabase();
 UserService.initDatabase();    
 OrderService.initDatabase();
 ShirtCartService.initDatabase();
 UserService.addUser(USERNAMEOWNER, PASSWORD,"Shop Owner");
	}
	
	@Start
	void start(Stage primaryStage) throws Exception {	    
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
	       primaryStage.setTitle("Login");
	       primaryStage.setScene(new Scene(root, 900, 600));
	       primaryStage.show();
	}
	
	@Test
	void testGoToLogoutButton(FxRobot robot) throws UsernameAlreadyExistsException {
		ShirtService.addShirt("Otelul Galati","Liga 4","16","20","/images/otelul.jpg");
		OrderService.addOrdertoDatabase("Liverpool-6,Arsenadl-10,", "20", CUSTOMERNAME, CUSTOMERCOUNTRY);
		robot.clickOn("#username");
		robot.write(USERNAMEOWNER);
		robot.clickOn("#password");
		robot.write(PASSWORD);
		robot.clickOn("#role");
		robot.type(KeyCode.DOWN); //selects Shop Owner role
		robot.type(KeyCode.ENTER);
		robot.clickOn("#loginButton");
		robot.clickOn("#ordersButton");
		robot.clickOn("#Logout");
	}
	
	@Test
	void testGoBackToShopOwnerPage(FxRobot robot) throws UsernameAlreadyExistsException{
		ShirtService.addShirt("Liverpools","Premier league","10","10","/images/lvp.jpg");
		OrderService.addOrdertoDatabase("Liverpool-6,Arsenadl-10,", "20", CUSTOMERNAME, CUSTOMERCOUNTRY);
		robot.clickOn("#username");
		robot.write(USERNAMEOWNER);
		robot.clickOn("#password");
		robot.write(PASSWORD);
		robot.clickOn("#role");
		robot.type(KeyCode.DOWN); //selects Shop Owner role
		robot.type(KeyCode.ENTER);
		robot.clickOn("#loginButton");
		robot.clickOn("#ordersButton");
		robot.clickOn("#Homepage");
	}
	
	@Test
	void TestAcceptOrder(FxRobot robot) throws UsernameAlreadyExistsException{
		ShirtService.addShirt("Otelul Galati","Liga 4","16","20","/images/otelul.jpg");
		OrderService.addOrdertoDatabase("Liverpool-6,Arsenadl-10,", "20", CUSTOMERNAME, CUSTOMERCOUNTRY);
		robot.clickOn("#username");
		robot.write(USERNAMEOWNER);
		robot.clickOn("#password");
		robot.write(PASSWORD);
		robot.clickOn("#role");
		robot.type(KeyCode.DOWN); //selects Shop Owner role
		robot.type(KeyCode.ENTER);
		robot.clickOn("#loginButton");
		robot.clickOn("#ordersButton");
		
		robot.clickOn("#selectedName");
		robot.write(CUSTOMERNAME);
		robot.clickOn("#selectedCountry");
		robot.write(CUSTOMERCOUNTRY);
		robot.clickOn("#acceptButton");
		robot.clickOn("#refreshOrders");
		
		assertThat(robot.lookup("#afterOrderMessage").queryText()).hasText("Order was accepted successfully !");
	}
	
	
	
}