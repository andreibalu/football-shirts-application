package org.loose.fis.fssa;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.assertions.api.Assertions.assertThat;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loose.fis.fssa.exceptions.InvalidCredentialsException;
import org.loose.fis.fssa.exceptions.UsernameAlreadyExistsException;
import org.loose.fis.fssa.services.FileSystemService;
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

class LoginTest {

	public static final String USERNAMEOWNER = "owner boss";
	public static final String USERNAMECUSTOMER = "customer";
	public static final String PASSWORD = "password";
	 
	@AfterEach
	   void tearDown() {
	      UserService.getDatabase().close();
	      ShirtService.getDatabase().close();
	    }
	
	@BeforeEach
	void setUp() throws Exception{
		FileSystemService.APPLICATION_FOLDER=".test-shirt-application";
		FileSystemService.initDirectory();
      FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
      UserService.initDatabase();
      ShirtService.initDatabase();
	}
	
	@Start
	void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
       primaryStage.setTitle("Login");
       primaryStage.setScene(new Scene(root, 400, 300));
       primaryStage.show();
	}
	
	@Test
	void testLogin(FxRobot robot) throws UsernameAlreadyExistsException  {
		UserService.addUser(USERNAMEOWNER, PASSWORD,"Shop Owner");
		ShirtService.addShirt("team","league","10","10","/images/lvp.jpg");
		robot.clickOn("#username");
		robot.write(USERNAMEOWNER);
		robot.clickOn("#password");
		robot.write(PASSWORD);
		robot.clickOn("#role");
		robot.type(KeyCode.DOWN);//selects Shop Owner role
		robot.type(KeyCode.ENTER);
		robot.clickOn("#loginButton");
	}
	
	@Test
	void testCustomerLogin(FxRobot robot) throws UsernameAlreadyExistsException {
		UserService.addUser(USERNAMECUSTOMER, PASSWORD,"Customer");
		ShirtService.addShirt("team","league","10","10","/images/lvp.jpg");
		robot.clickOn("#username");
		robot.write(USERNAMECUSTOMER);
		robot.clickOn("#password");
		robot.write(PASSWORD);
		robot.clickOn("#role");
		robot.type(KeyCode.DOWN);
		robot.type(KeyCode.DOWN);//selects Customer role
		robot.type(KeyCode.ENTER);
		robot.clickOn("#loginButton");
	}
	
	@Test
	void testCustomerCanNotEnterInvalidCredentials(FxRobot robot) throws UsernameAlreadyExistsException,InvalidCredentialsException{
		UserService.addUser(USERNAMECUSTOMER, PASSWORD,"Customer");
		robot.clickOn("#username");
		robot.write("customerWRONG");
		robot.clickOn("#password");
		robot.write(PASSWORD);
		robot.clickOn("#role");
		robot.type(KeyCode.DOWN);
		robot.type(KeyCode.DOWN);//selects Customer role
		robot.type(KeyCode.ENTER);
		
		robot.clickOn("#loginButton");
		assertThat(robot.lookup("#loginMessage").queryText()).hasText(String.format("Invalid credentials, please try again!"));
	}
	
	@Test
	void testRedirectToRegister(FxRobot robot) {
		robot.clickOn("#redirectToRegisterButton");
	}

}
