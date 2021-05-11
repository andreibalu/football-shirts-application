package org.loose.fis.fssa.services;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.AbstractLabeledAssert;
import org.testfx.assertions.api.ButtonAssert;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.testfx.assertions.api.Assertions.assertThat;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)

class RegistrationTest {

	 public static final String USERNAME = "owner boss";
	 public static final String PASSWORD = "password";
	 
	@AfterEach
	   void tearDown() {
	      UserService.getDatabase().close();
	    }
	
	@BeforeEach
	void setUp() throws Exception{
		FileSystemService.APPLICATION_FOLDER=".test-shirt-application";
		FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        UserService.initDatabase();
	}
	
	@Start
	void start(Stage primaryStage) throws Exception {
		 Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("resources/register.fxml"));
	     primaryStage.setTitle("Register");
	     primaryStage.setScene(new Scene(root, 400, 300));
	     primaryStage.show();
	}
	
	@Test
	void testRegistration(FxRobot robot) {
		robot.clickOn("#username");
		robot.write(USERNAME);
		robot.clickOn("#password");
		robot.write(PASSWORD);
		robot.clickOn("#role");
		robot.type(KeyCode.DOWN); //selects Shop Owner role
		robot.type(KeyCode.ENTER);
		robot.clickOn("#registerButton");
		
		assertThat(robot.lookup("#registrationMessage").queryText()).hasText("Account created successfully!");
		assertThat(UserService.getAllUsers()).size().isEqualTo(1);
		
		robot.clickOn("#registerButton");
		assertThat(robot.lookup("#registrationMessage").queryText()).hasText(
				String.format("An account with the username %s already exists!", USERNAME)); //you can't create a account with the same username
		
		robot.clickOn("#username");
		robot.write("1");
		robot.clickOn("#registerButton");
		
		assertThat(robot.lookup("#registrationMessage").queryText()).hasText("Account created successfully!");
		assertThat(UserService.getAllUsers()).size().isEqualTo(2); //we add one more user, and verify that there are 2 users in the database
		
	}
	
	@Test
	void testGoToLoginButton(FxRobot robot) {
		robot.clickOn("#gotologinButton");
		assertThat(robot.lookup("#title-text").queryText()).hasText("Football Shirts Shop Application Login");
		//verifies that when clicking the go to login button, it opens the login window
	}
}
