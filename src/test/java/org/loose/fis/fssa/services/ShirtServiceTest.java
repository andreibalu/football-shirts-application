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
import org.loose.fis.fssa.exceptions.BlankFieldsException;
import org.loose.fis.fssa.exceptions.InvalidCredentialsException;
import org.loose.fis.fssa.exceptions.NotEnoughStockException;
import org.loose.fis.fssa.exceptions.WrongFieldsException;
import org.loose.fis.fssa.model.Shirt;
import org.loose.fis.fssa.model.User;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ShirtServiceTest {

	public static final String TEAM = "team";
	public static final String LEAGUE = "league";
	public static final String QUANTITY = "5";
	public static final String PRICE = "10";
	public static final String IMAGE = "/images/lvp.jpg";
	@AfterEach
	   void tearDown() {
	      System.out.println("After each");
	      ShirtService.getDatabase().close();
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
        ShirtService.initDatabase();
	}
	
	@Test
    @DisplayName("Database is initialized, and there are no shirts")
    void testDatabaseIsInitializedAndNoShirtIsThere() {
        assertThat(ShirtService.getAllShirts()).isNotNull();
        assertThat(ShirtService.getAllShirts()).isEmpty();
    }
	
	@Test
	@DisplayName("Shirt is successfully persisted to the database")
	void testShirtIsAddedToDatabase() {
		ShirtService.addShirt(TEAM, LEAGUE, PRICE, QUANTITY, IMAGE);
		assertThat(ShirtService.getAllShirts()).isNotEmpty();
        assertThat(ShirtService.getAllShirts()).size().isEqualTo(1);
        Shirt shirt = ShirtService.getAllShirts().get(0);
        assertThat(shirt).isNotNull();
        assertThat(shirt.getTeam()).isEqualTo(TEAM);
        assertThat(shirt.getLeague()).isEqualTo(LEAGUE);
        assertThat(shirt.getQuantity()).isEqualTo(Integer.parseInt(QUANTITY));
        assertThat(shirt.getPrice()).isEqualTo(Integer.parseInt(PRICE));
        assertThat(shirt.getImage()).isEqualTo(IMAGE);
	}
	
	@Test
	 @DisplayName("Blank team field cannot be entered")
	 void testBlankTeamFieldCannotBeEntered()
	 {
		 assertThrows(BlankFieldsException.class, () -> {
			 ShirtService.VerifyBlanks("", LEAGUE, PRICE, QUANTITY, IMAGE);	 
		 });
	 }
	
	@Test
	 @DisplayName("Blank league field cannot be entered")
	 void testBlankLeagueFieldCannotBeEntered()
	 {
		 assertThrows(BlankFieldsException.class, () -> {
			 ShirtService.VerifyBlanks(TEAM, "", PRICE, QUANTITY, IMAGE);	 
		 });
	 }
	
	@Test
	 @DisplayName("Blank price field cannot be entered")
	 void testBlankPriceFieldCannotBeEntered()
	 {
		 assertThrows(BlankFieldsException.class, () -> {
			 ShirtService.VerifyBlanks(TEAM, LEAGUE, "", QUANTITY, IMAGE);		 
		 });
	 }
	
	@Test
	 @DisplayName("Blank quantity field cannot be entered")
	 void testBlankQuantityFieldCannotBeEntered()
	 {
		 assertThrows(BlankFieldsException.class, () -> {
			 ShirtService.VerifyBlanks(TEAM, LEAGUE, PRICE, "", IMAGE);	 
		 });
	 }
	
	@Test
	 @DisplayName("Blank image field cannot be entered")
	 void testBlankImageFieldCannotBeEntered()
	 {
		 assertThrows(BlankFieldsException.class, () -> {
			 ShirtService.VerifyBlanks(TEAM, LEAGUE, PRICE, QUANTITY, "");		 
		 });
	 }
	
	@Test
	 @DisplayName("All blank fields cannot be entered")
	 void testAllBlankFieldsCannotBeEntered()
	 {
		 assertThrows(BlankFieldsException.class, () -> {
			 ShirtService.VerifyBlanks("", "", "", "", "");		 
		 });
	 }
	
	@Test
	 @DisplayName("Shirt does not exist in database when editing/removing existing shirt")
	 void testCanNotEnterShirtThatDoesNotExistWhenEditingOrRemoving()
	 {
		 assertThrows(WrongFieldsException.class, () -> {
			ShirtService.addShirt(TEAM, LEAGUE, PRICE, QUANTITY, IMAGE);
			ShirtService.VerifyWrongs("team1", LEAGUE, PRICE, QUANTITY, IMAGE);
		 });
	 }
	
	
	@Test
	@DisplayName("Shirt is succesfuly edited in database")
	void testShirtIsSuccesfullyEditedInDatabase()
	{
		ShirtService.addShirt(TEAM, LEAGUE, PRICE, QUANTITY, IMAGE);
		ShirtService.editShirt(TEAM,"otherleague", PRICE, QUANTITY, IMAGE);
		Shirt shirt = ShirtService.getAllShirts().get(0);
        assertThat(shirt.getLeague()).isEqualTo("otherleague");
	}
	
	@Test
	@DisplayName("Shirt is succesfully removed from database")
	void testShirtIsSuccesfullyRemovedFromDatabase() {
		ShirtService.addShirt(TEAM, LEAGUE, PRICE, QUANTITY, IMAGE);
		ShirtService.addShirt(TEAM+1, LEAGUE+1, PRICE+1, QUANTITY+1, IMAGE+1);
		ShirtService.removeShirt(TEAM, LEAGUE, PRICE, QUANTITY, IMAGE);
		assertThat(ShirtService.getAllShirts()).size().isEqualTo(1);
	}
	
	@Test
	@DisplayName("Quantity is succesfully updated in the database after substracting")
	void testQuantityIsSuccesfullyUpdated() {
		ShirtService.addShirt(TEAM, LEAGUE, PRICE, QUANTITY, IMAGE);
		ShirtService.removeQuantity(TEAM,2);
		Shirt shirt = ShirtService.getAllShirts().get(0);
        assertThat(shirt.getQuantity()).isEqualTo(3);
	}
	
	@Test
	@DisplayName("Quantity is succesfully restored in the database")
	void testQuantityIsSuccesfullyRestored() {
		ShirtService.addShirt(TEAM, LEAGUE, PRICE, QUANTITY, IMAGE);
		ShirtService.restoreQuantity(TEAM,2);
		Shirt shirt = ShirtService.getAllShirts().get(0);
        assertThat(shirt.getQuantity()).isEqualTo(7);
	}
	
	@Test
	@DisplayName("Quantity higher than the existing one in the database for a shirt can not be entered")
	 void testCanNotEnterQuantityHigherThanTheStock()
	 {
		 assertThrows(NotEnoughStockException.class, () -> {
			ShirtService.addShirt(TEAM, LEAGUE, PRICE, QUANTITY, IMAGE);
			ShirtService.VerifyStock(TEAM,10);
		 });
	 }
	
	@Test
	@DisplayName("Correct shirt is retrieved from database")
	void testCorrectShirtIsRetrievedFromDatabase() {
		ShirtService.addShirt(TEAM, LEAGUE, PRICE, QUANTITY, IMAGE);
		ShirtService.addShirt(TEAM+1, LEAGUE+1, PRICE+1, QUANTITY+1, IMAGE+1);
		Shirt shirt = ShirtService.returnShirt(2);
		assertThat(shirt).isNotNull();
        assertThat(shirt.getTeam()).isEqualTo(TEAM+1);
        assertThat(shirt.getLeague()).isEqualTo(LEAGUE+1);
        assertThat(shirt.getQuantity()).isEqualTo(Integer.parseInt(QUANTITY+1));
        assertThat(shirt.getPrice()).isEqualTo(Integer.parseInt(PRICE+1));
        assertThat(shirt.getImage()).isEqualTo(IMAGE+1);
	}
	
	@Test
	@DisplayName("Shirt number is succesfully returned")
	void testShirtNumberIsReturnedCorrectly() {
		ShirtService.addShirt(TEAM, LEAGUE, PRICE, QUANTITY, IMAGE);
		ShirtService.addShirt(TEAM+1, LEAGUE+1, PRICE+1, QUANTITY+1, IMAGE+1);
		ShirtService.addShirt(TEAM+2, LEAGUE+2, PRICE+2, QUANTITY+2, IMAGE+2);
		ShirtService.addShirt(TEAM+3, LEAGUE+3, PRICE+3, QUANTITY+3, IMAGE+3);
		assertThat(ShirtService.getShirtNumber()).isEqualTo(4);
	}
}
