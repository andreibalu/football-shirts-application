package org.loose.fis.fssa.services;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.loose.fis.fssa.exceptions.InvalidCredentialsException;
import org.loose.fis.fssa.exceptions.UsernameAlreadyExistsException;
import org.loose.fis.fssa.model.User;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.testfx.assertions.api.Assertions.assertThat;

class UserServiceTest {
    
	 public static final String ADMIN = "admin";
	 
	 @AfterEach
	   void tearDown() {
	      System.out.println("After each");
	      UserService.getDatabase().close();
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
        UserService.initDatabase();
	}
	
	@Test
    @DisplayName("Database is initialized, and there are no users")
    void testDatabaseIsInitializedAndNoUserIsPersisted() {
        assertThat(UserService.getAllUsers()).isNotNull();
        assertThat(UserService.getAllUsers()).isEmpty();
    }
	
	 @Test
	    @DisplayName("User is successfully persisted to Database")
	    void testUserIsAddedToDatabase() throws UsernameAlreadyExistsException {
	        UserService.addUser(ADMIN, ADMIN, ADMIN);
	        assertThat(UserService.getAllUsers()).isNotEmpty();
	        assertThat(UserService.getAllUsers()).size().isEqualTo(1);
	        User user = UserService.getAllUsers().get(0);
	        assertThat(user).isNotNull();
	        assertThat(user.getUsername()).isEqualTo(ADMIN);
	        assertThat(user.getPassword()).isEqualTo(UserService.encodePassword(ADMIN, ADMIN));
	        assertThat(user.getRole()).isEqualTo(ADMIN);
	    }
	 
	 @Test
	    @DisplayName("User can not be added twice")
	    void testUserCanNotBeAddedTwice() {
	        assertThrows(UsernameAlreadyExistsException.class, () -> {
	            UserService.addUser(ADMIN, ADMIN, ADMIN);
	            UserService.addUser(ADMIN, ADMIN, ADMIN);
	        });
	    }
	
	 
}
