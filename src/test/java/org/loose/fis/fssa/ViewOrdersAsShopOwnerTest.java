package org.loose.fis.fssa;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loose.fis.fssa.exceptions.UsernameAlreadyExistsException;
import org.loose.fis.fssa.services.FileSystemService;
import org.loose.fis.fssa.services.OrderService;
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

class ViewOrdersAsShopOwnerTest {

	public static final String USERNAMEOWNER = "shopowner";
	public static final String PASSWORD = "password";
	public static final String CUSTOMERNAME = "Radu";
	public static final String CUSTOMERCOUNTRY= "Romania";
	 

	@AfterEach
	   void tearDown() {
	      ShirtService.getDatabase().close();
	      UserService.getDatabase().close();
	      OrderService.getDatabase().close();
	    }
	
	@BeforeEach
	void setUp() throws Exception{
		FileSystemService.APPLICATION_FOLDER=".test-shirt-application";
		FileSystemService.initDirectory();
      FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
      UserService.initDatabase();
      ShirtService.initDatabase();
      OrderService.initDatabase();
      UserService.addUser(USERNAMEOWNER, PASSWORD,"Shop Owner");
	}
	
	@Start
	void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
       primaryStage.setTitle("Login");
       primaryStage.setScene(new Scene(root, 400, 300));
       primaryStage.show();
	}
	
	@Test
	void testViewOrders(FxRobot robot) throws UsernameAlreadyExistsException{
		OrderService.addOrdertoDatabase("Liverpool-5,", "11", CUSTOMERNAME, CUSTOMERCOUNTRY);
		OrderService.addOrdertoDatabase("Liverpool-6,Arsenal-10,", "20", CUSTOMERNAME+1, CUSTOMERCOUNTRY+1);
		OrderService.addOrdertoDatabase("Frankfurt-5,", "13", CUSTOMERNAME+2, CUSTOMERCOUNTRY+2);
		OrderService.addOrdertoDatabase("Real Madrid-5,Frankfurt-5,Arsenal-2,", "17", CUSTOMERNAME+3, CUSTOMERCOUNTRY+3);
		robot.clickOn("#username");
		robot.write(USERNAMEOWNER);
		robot.clickOn("#password");
		robot.write(PASSWORD);
		robot.clickOn("#role");
		robot.type(KeyCode.DOWN); //selects Shop Owner role
		robot.type(KeyCode.ENTER);
		robot.clickOn("#loginButton");
		robot.clickOn("#ordersButton");
		
	}

}
