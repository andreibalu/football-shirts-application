package org.loose.fis.fssa.services;

import static org.loose.fis.fssa.services.FileSystemService.getPathToFile;

import java.util.Objects;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectFilter;
import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;
import org.loose.fis.fssa.model.CartShirt;
import org.loose.fis.fssa.model.Order;
import org.loose.fis.fssa.model.Shirt;

public class OrderService {
	private static int contororder=0;
      
	private static ObjectRepository<Order> orderRepository;
	public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("orders.db").toFile())
                .openOrCreate("test", "test");

        orderRepository = database.getRepository(Order.class);         
    }
	public static void addOrdertoDatabase(String team_quantity,String total_price,String customer_name,String customer_country)
	{
		int pr=Integer.parseInt(total_price);
		orderRepository.insert(new Order(team_quantity,pr,customer_name,customer_country));
	}
	
	public static int getOrderNumber() {
		contororder=0;
        for (Order order : orderRepository.find()) {
            	contororder++;
        }
        return contororder;
    }
	
	public static void acceptOrder(String name, String country) {
		for (Order order : orderRepository.find()) {
			if(Objects.equals(name, order.getCustomer_name()) && Objects.equals(country,order.getCustomer_Country()))
				orderRepository.remove(order);
		}
	}
	
	public static void denyOrder(String name, String country) {
		for (Order order : orderRepository.find()) {
			if(Objects.equals(name, order.getCustomer_name()) && Objects.equals(country,order.getCustomer_Country()))
				orderRepository.remove(order);
		}
	}
	
	private static int y=0;
	public static Order returnOrder(int i)  {
		y=0;
        for (Order order : orderRepository.find()) {
        	y++;
            if (i==y)
                return order;
            
        }
        return null;
    }
	
	
	
}
