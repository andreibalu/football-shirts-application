package org.loose.fis.fssa.model;

public class CartShirt {
   private String team;
   private int price;
   private int quantity;
   
   public CartShirt(String team, int price, int quantity) {
	this.team = team;
	this.price = price;
	this.quantity = quantity;
	}
   
   public CartShirt() {
	   
   }

public String getTeam() {
	return team;
}

public void setTeam(String team) {
	this.team = team;
}

public int getPrice() {
	return price;
}

public void setPrice(int price) {
	this.price = price;
}

public int getQuantity() {
	return quantity;
}

public void setQuantity(int quantity) {
	this.quantity = quantity;
}

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + price;
	result = prime * result + quantity;
	result = prime * result + ((team == null) ? 0 : team.hashCode());
	return result;
}

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	CartShirt other = (CartShirt) obj;
	if (price != other.price)
		return false;
	if (quantity != other.quantity)
		return false;
	if (team == null) {
		if (other.team != null)
			return false;
	} else if (!team.equals(other.team))
		return false;
	return true;
}

@Override
public String toString() {
	return "CartShirt [team=" + team + ", price=" + price + ", quantity=" + quantity + "]";
}

   
   
   
}
