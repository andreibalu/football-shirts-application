package org.loose.fis.fssa.services;


import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectFilter;
import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;
import org.loose.fis.fssa.exceptions.BlankFieldsException;
import org.loose.fis.fssa.exceptions.InvalidCredentialsException;
import org.loose.fis.fssa.exceptions.NotEnoughStockException;
import org.loose.fis.fssa.exceptions.UsernameAlreadyExistsException;
import org.loose.fis.fssa.exceptions.WrongFieldsException;
import org.loose.fis.fssa.model.Shirt;
import org.loose.fis.fssa.model.User;

import javafx.scene.image.Image;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import static org.loose.fis.fssa.services.FileSystemService.getPathToFile;

public class ShirtService {

	private static ObjectRepository<Shirt> shirtRepository;
	
	
	private static int contorshirt=0;
	
	
	public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("shirts-list.db").toFile())
                .openOrCreate("test", "test");

        shirtRepository = database.getRepository(Shirt.class);           
    }
	
	public static void addShirt(String team, String league, String price, String quantity, String image)  { 
		int pr=Integer.parseInt(price);
		int qu=Integer.parseInt(quantity);
        shirtRepository.insert(new Shirt(team, league,pr, qu, image));
    }
	
	public static void editShirt(String team, String league, String price, String quantity, String image) {
		int pr=Integer.parseInt(price);
		int qu=Integer.parseInt(quantity);
		for (Shirt shirt : shirtRepository.find()) {
        	if(Objects.equals(team, shirt.getTeam())) {
        		shirt.setLeague(league);
        		shirt.setPrice(pr);
        		shirt.setQuantity(qu);
        		shirt.setImage(image);
        		}
        	shirtRepository.update(shirt);
		}
	}

	public static void removeShirt(String team, String league, String price, String quantity, String image) {
		for (Shirt shirt : shirtRepository.find()) {
			if(Objects.equals(team, shirt.getTeam()))
				shirtRepository.remove(shirt);
		}
	}
	
	public static void checkBlankFieldsException(String team, String league, String price, String quantity, String image) throws BlankFieldsException{
		if(team.isBlank() || league.isBlank() || price.isBlank() || quantity.isBlank() || image.isBlank())
			throw new BlankFieldsException();
	}
	
	public static void VerifyBlanks(String team, String league, String price, String quantity, String image) throws BlankFieldsException {
		checkBlankFieldsException(team,league,price,quantity,image);
	}
	
	public static void checkWrongFieldsException(String team, String league, String price, String quantity, String image) throws WrongFieldsException{
		int contor=0;
    	for(Shirt shirt : shirtRepository.find()) {
    		if(Objects.equals(team,shirt.getTeam()))
    		{
    				contor++;
    		}
    	}
    	if(contor==0)
    	{
    		throw new WrongFieldsException();
    	}
	}
	
	public static void VerifyWrongs(String team, String league, String price, String quantity, String image) throws WrongFieldsException{
		checkWrongFieldsException(team,league,price,quantity,image);
	}
	public static void checkNotEnoughStockException(String team, int quantity) throws NotEnoughStockException
	{
		int qu=0;
		for(Shirt shirt : shirtRepository.find())
		{
			if(Objects.equals(team,shirt.getTeam()))
			{
				qu=shirt.getQuantity();
			}
		}
		if(quantity>qu)
		{
			throw new NotEnoughStockException(qu);
		}
	}
	public static void VerifyStock(String team,int quantity) throws NotEnoughStockException{
		checkNotEnoughStockException(team,quantity);
	}
	public static int getShirtNumber() {
		contorshirt=0;
        for (Shirt shirt : shirtRepository.find()) {
            	contorshirt++;
        }
        return contorshirt;
    }
	
	private static int y=0;
	public static Shirt returnShirt(int i)  {
		y=0;
        for (Shirt shirt : shirtRepository.find()) {
        	y++;
            if (i==y)
                return shirt;
            
        }
        return null;
    }
	
}
