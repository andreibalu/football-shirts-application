package org.loose.fis.fssa.services;


import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.loose.fis.fssa.model.Shirt;
import org.loose.fis.fssa.model.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import static org.loose.fis.fssa.services.FileSystemService.getPathToFile;

public class ShirtService {

	private static ObjectRepository<Shirt> shirtRepository;
	
	public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("shirts-list.db").toFile())
                .openOrCreate("test", "test");

        shirtRepository = database.getRepository(Shirt.class);
    }
	
	
}
