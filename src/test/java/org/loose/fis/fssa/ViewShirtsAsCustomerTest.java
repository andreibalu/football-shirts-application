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

class ViewShirtsAsCustomerTest {

	public static final String USERNAMECUSTOMER = "customer";
	public static final String PASSWORD = "password";
	 

	@AfterEach
	   void tearDown() {
	      ShirtService.getDatabase().close();
	      UserService.getDatabase().close();
	    }
	
	@BeforeEach
	void setUp() throws Exception{
		FileSystemService.APPLICATION_FOLDER=".test-shirt-application";
		FileSystemService.initDirectory();
      FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
      UserService.initDatabase();
      ShirtService.initDatabase();
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
	void testViewShirts(FxRobot robot) throws UsernameAlreadyExistsException {
		ShirtService.addShirt("Liverpool","Premier League","10","60","/images/lvp.jpg");
		ShirtService.addShirt("Poli Timisoara","Liga 2","6","20","/images/politm.jpg");
		ShirtService.addShirt("Eintracht Frankfurt","Bundesliga","5","40","/images/frankfurt.jpg");
		ShirtService.addShirt("Otelul Galati","Liga 4","16","10","/images/otelul.jpg");
		robot.clickOn("#username");
		robot.write(USERNAMECUSTOMER);
		robot.clickOn("#password");
		robot.write(PASSWORD);
		robot.clickOn("#role");
		robot.type(KeyCode.DOWN);
		robot.type(KeyCode.DOWN); //selects Customer role
		robot.type(KeyCode.ENTER);
		robot.clickOn("#loginButton");
	}
}
