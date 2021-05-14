package org.loose.fis.fssa.services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;
import org.loose.fis.fssa.exceptions.UsernameAlreadyExistsException;
import org.loose.fis.fssa.exceptions.InvalidCredentialsException;
import org.loose.fis.fssa.model.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

import static org.loose.fis.fssa.services.FileSystemService.getPathToFile;

public class UserService {

    private static ObjectRepository<User> userRepository;
    private static Nitrite database;
    public static void initDatabase() {
    	FileSystemService.initDirectory();
         database = Nitrite.builder()
                .filePath(getPathToFile("shirt-application.db").toFile())
                .openOrCreate("test", "test");

        userRepository = database.getRepository(User.class);
    }
    
    public static void addUser(String username, String password, String role) throws UsernameAlreadyExistsException {
        checkUserDoesNotAlreadyExist(username);
        userRepository.insert(new User(username, encodePassword(username, password), role));
    }

    private static void checkUserDoesNotAlreadyExist(String username) throws UsernameAlreadyExistsException {
        for (User user : userRepository.find()) {
            if (Objects.equals(username, user.getUsername()))
                throw new UsernameAlreadyExistsException(username);
        }
    }
    public static List<User> getAllUsers() {
        return userRepository.find().toList();
    }
    public static void verifyLogin(String username,String password) throws InvalidCredentialsException{
    	checkUserCredentialsInLogin(username,password);
    }
    private static void checkUserCredentialsInLogin(String username, String password) throws InvalidCredentialsException {
    	int contor=0;
    	for(User user : userRepository.find()) {
    		if(Objects.equals(username,user.getUsername()))
    		{
    			if(Objects.equals(user.getPassword(),encodePassword(username,password)))
    			{
    				contor++;
    			}
    		}
    	}
    	if(contor==0)
    	{
    		throw new InvalidCredentialsException();
    	}
    }

    public static String encodePassword(String salt, String password) {
        MessageDigest md = getMessageDigest();
        md.update(salt.getBytes(StandardCharsets.UTF_8));

        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

        // This is the way a password should be encoded when checking the credentials
        return new String(hashedPassword, StandardCharsets.UTF_8)
                .replace("\"", ""); //to be able to save in JSON format
    }

    private static MessageDigest getMessageDigest() {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-512 does not exist!");
        }
        return md;
    }
    
    public static Nitrite getDatabase() {
        return database;
    }


}
