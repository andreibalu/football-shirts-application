package org.loose.fis.fssa.services;


import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.loose.fis.fssa.exceptions.UsernameAlreadyExistsException;
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

	
	public static int getShirtNumber() {
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
