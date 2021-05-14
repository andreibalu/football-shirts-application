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

class ShopOwnerTest {
	
	public static final String TEAM = "TEAM";
	public static final String QUANTITY= "10";
	public static final String PRICE= "10";
	public static final String LEAGUE = "LEAGUE";
	public static final String IMAGE= "/images/lvp.jpg";
	
	
	public static final String USERNAMEOWNER = "owner boss";
	public static final String PASSWORD = "password";
	
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
    ShirtService.initDatabase();
    UserService.initDatabase();    
    OrderService.initDatabase();
    UserService.addUser(USERNAMEOWNER, PASSWORD,"Shop Owner");
	ShirtService.addShirt("Liverpool","Premier league","10","10","/images/lvp.jpg");
	}
	
	@Start
	void start(Stage primaryStage) throws Exception {	    
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
	       primaryStage.setTitle("Login");
	       primaryStage.setScene(new Scene(root, 400, 300));
	       primaryStage.show();
	}
	
	

	@Test
	void testGoToLogoutButton(FxRobot robot) throws UsernameAlreadyExistsException {
		robot.clickOn("#username");
		robot.write(USERNAMEOWNER);
		robot.clickOn("#password");
		robot.write(PASSWORD);
		robot.clickOn("#role");
		robot.type(KeyCode.DOWN); //selects Shop Owner role
		robot.type(KeyCode.ENTER);
		robot.clickOn("#loginButton");
		robot.clickOn("#Logout");
	}
	
	@Test
	void testAddShirtsToShop(FxRobot robot) {
		robot.clickOn("#username");
		robot.write(USERNAMEOWNER);
		robot.clickOn("#password");
		robot.write(PASSWORD);
		robot.clickOn("#role");
		robot.type(KeyCode.DOWN); //selects Shop Owner role
		robot.type(KeyCode.ENTER);
		robot.clickOn("#loginButton");
		
		robot.clickOn("#teamField");
		robot.write("Arsenal");
		robot.clickOn("#leagueField");
		robot.write("Premier League");
		robot.clickOn("#priceField");
		robot.write("10");
		robot.clickOn("#quantityField");
		robot.write("10");
		robot.clickOn("#imageField");
		robot.write("/images/ars.jpg");
		
		robot.clickOn("#addButton");
		robot.clickOn("#refreshButton");
		
		assertThat(robot.lookup("#afterMessage").queryText()).hasText("Added shirt successfully");			
	}
	
	@Test
	void testBlankFieldsCannotBeEnteredWhenAddingAShirt(FxRobot robot) {
		robot.clickOn("#username");
		robot.write(USERNAMEOWNER);
		robot.clickOn("#password");
		robot.write(PASSWORD);
		robot.clickOn("#role");
		robot.type(KeyCode.DOWN); //selects Shop Owner role
		robot.type(KeyCode.ENTER);
		robot.clickOn("#loginButton");
		
		robot.clickOn("#teamField");
		robot.write("");
		robot.clickOn("#leagueField");
		robot.write("Premier League");
		robot.clickOn("#priceField");
		robot.write("10");
		robot.clickOn("#quantityField");
		robot.write("10");
		robot.clickOn("#imageField");
		robot.write("/images/ars.jpg");
		
		robot.clickOn("#addButton");
		
		assertThat(robot.lookup("#afterMessage").queryText()).hasText(String.format("You forgot to complete a field, try again"));			
	}
	
	@Test
	void testEditShirtsToShop(FxRobot robot) {
		robot.clickOn("#username");
		robot.write(USERNAMEOWNER);
		robot.clickOn("#password");
		robot.write(PASSWORD);
		robot.clickOn("#role");
		robot.type(KeyCode.DOWN); //selects Shop Owner role
		robot.type(KeyCode.ENTER);
		robot.clickOn("#loginButton");
		
		
		robot.clickOn("#teamField");
		robot.write("Liverpool");
		robot.clickOn("#leagueField");
		robot.write("Premier league");
		robot.clickOn("#priceField");
		robot.write("15");
		robot.clickOn("#quantityField");
		robot.write("15");
		robot.clickOn("#imageField");
		robot.write("/images/lvp.jpg");
		
		robot.clickOn("#editButton");
		robot.clickOn("#refreshButton");
		
		assertThat(robot.lookup("#afterMessage").queryText()).hasText("Edited shirt successfully");			
	}
	
	@Test
	void testWrongFieldsWhenEditing(FxRobot robot) {
		robot.clickOn("#username");
		robot.write(USERNAMEOWNER);
		robot.clickOn("#password");
		robot.write(PASSWORD);
		robot.clickOn("#role");
		robot.type(KeyCode.DOWN); //selects Shop Owner role
		robot.type(KeyCode.ENTER);
		robot.clickOn("#loginButton");
		
		
		robot.clickOn("#teamField");
		robot.write("Liverpooool");
		robot.clickOn("#leagueField");
		robot.write("Premier league");
		robot.clickOn("#priceField");
		robot.write("15");
		robot.clickOn("#quantityField");
		robot.write("15");
		robot.clickOn("#imageField");
		robot.write("/images/lvp.jpg");
		
		robot.clickOn("#editButton");
		robot.clickOn("#refreshButton");
		
		assertThat(robot.lookup("#afterMessage").queryText()).hasText(String.format("You entered a shirt that does not exist, enter again"));			
	}
	
	@Test
	void testBlankFieldsWhenEditing(FxRobot robot) {
		robot.clickOn("#username");
		robot.write(USERNAMEOWNER);
		robot.clickOn("#password");
		robot.write(PASSWORD);
		robot.clickOn("#role");
		robot.type(KeyCode.DOWN); //selects Shop Owner role
		robot.type(KeyCode.ENTER);
		robot.clickOn("#loginButton");
		
		
		robot.clickOn("#teamField");
		robot.write("");
		robot.clickOn("#leagueField");
		robot.write("Premier league");
		robot.clickOn("#priceField");
		robot.write("15");
		robot.clickOn("#quantityField");
		robot.write("15");
		robot.clickOn("#imageField");
		robot.write("/images/lvp.jpg");
		
		robot.clickOn("#editButton");
		robot.clickOn("#refreshButton");
		
		assertThat(robot.lookup("#afterMessage").queryText()).hasText(String.format("You forgot to complete a field, try again"));
	}
	
	
	
	@Test
	void testRemoveShirt(FxRobot robot) {
		robot.clickOn("#username");
		robot.write(USERNAMEOWNER);
		robot.clickOn("#password");
		robot.write(PASSWORD);
		robot.clickOn("#role");
		robot.type(KeyCode.DOWN); //selects Shop Owner role
		robot.type(KeyCode.ENTER);
		robot.clickOn("#loginButton");
		
		ShirtService.addShirt("Arsenal","Premier league","10","10","/images/ars.jpg");
		robot.clickOn("#teamField");
		robot.write("Liverpool");
		robot.clickOn("#leagueField");
		robot.write("Premier league");
		robot.clickOn("#priceField");
		robot.write("15");
		robot.clickOn("#quantityField");
		robot.write("15");
		robot.clickOn("#imageField");
		robot.write("/images/lvp.jpg");
		
		robot.clickOn("#removeButton");
		robot.clickOn("#refreshButton");	
		assertThat(robot.lookup("#afterMessage").queryText()).hasText("Removed shirt successfully");	
	}
	
	@Test
	void testWrongFieldsWhenRemovingShirt(FxRobot robot) {
		robot.clickOn("#username");
		robot.write(USERNAMEOWNER);
		robot.clickOn("#password");
		robot.write(PASSWORD);
		robot.clickOn("#role");
		robot.type(KeyCode.DOWN); //selects Shop Owner role
		robot.type(KeyCode.ENTER);
		robot.clickOn("#loginButton");
		
		robot.clickOn("#teamField");
		robot.write("Liver");
		robot.clickOn("#leagueField");
		robot.write("Premier league");
		robot.clickOn("#priceField");
		robot.write("15");
		robot.clickOn("#quantityField");
		robot.write("15");
		robot.clickOn("#imageField");
		robot.write("/images/lvp.jpg");
		
		robot.clickOn("#removeButton");
		robot.clickOn("#refreshButton");	
		assertThat(robot.lookup("#afterMessage").queryText()).hasText(String.format("You entered a shirt that does not exist, enter again"));	
	}
	
	
	void testBlankFieldsWhenRemoving(FxRobot robot) {
		robot.clickOn("#username");
		robot.write(USERNAMEOWNER);
		robot.clickOn("#password");
		robot.write(PASSWORD);
		robot.clickOn("#role");
		robot.type(KeyCode.DOWN); //selects Shop Owner role
		robot.type(KeyCode.ENTER);
		robot.clickOn("#loginButton");
		
		
		robot.clickOn("#teamField");
		robot.write("");
		robot.clickOn("#leagueField");
		robot.write("Premier league");
		robot.clickOn("#priceField");
		robot.write("15");
		robot.clickOn("#quantityField");
		robot.write("15");
		robot.clickOn("#imageField");
		robot.write("/images/lvp.jpg");
		
		robot.clickOn("#removeButton");
		robot.clickOn("#refreshButton");
		
		assertThat(robot.lookup("#afterMessage").queryText()).hasText(String.format("You forgot to complete a field, try again"));
	}
	
	@Test
	void testGoToOrdersPage(FxRobot robot) throws UsernameAlreadyExistsException {
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
