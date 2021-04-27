package org.loose.fis.fssa.services;

import static org.loose.fis.fssa.services.FileSystemService.getPathToFile;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectFilter;
import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;
import org.loose.fis.fssa.model.CartShirt;
import org.loose.fis.fssa.model.Shirt;

public class ShirtCartService {
	
	private static ObjectRepository<CartShirt> shirtcartRepository;
	
	public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("shirts-cart.db").toFile())
                .openOrCreate("test", "test");

        shirtcartRepository = database.getRepository(CartShirt.class);         
    }
	
	public static void addShirtToCart(String team,String price,String quantity)
	{
		int pr=Integer.parseInt(price);
		int qu=Integer.parseInt(quantity);
		shirtcartRepository.insert(new CartShirt(team,pr,qu));
	}
	public static void removeShirtsFromCart()
	{
		
			shirtcartRepository.remove(ObjectFilters.ALL);
	}
	public static String getTeamQuantityForOrder()
	{
		String st="";
		for(CartShirt cartshirt : shirtcartRepository.find()) {
			st=st+cartshirt.getTeam()+"-"+String.valueOf(cartshirt.getQuantity())+",";
		}
		return st;
	}
	public static int getTotalPriceForOrder()
	{
		int pr=0;
		for(CartShirt cartshirt : shirtcartRepository.find()) {
			pr=pr+cartshirt.getPrice();
		}
		return pr;
	}

}
