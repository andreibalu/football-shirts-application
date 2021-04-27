package org.loose.fis.fssa.model;

public class Order {
  public String team_quantity;
  public int total_price;
  public String customer_name;
  public String customer_Country;
  
public Order(String team_quantity, int total_price, String customer_name, String customer_Country) {
	this.team_quantity = team_quantity;
	this.total_price = total_price;
	this.customer_name = customer_name;
	this.customer_Country = customer_Country;
}
public Order()
{
	
}
public String getTeam_quantity() {
	return team_quantity;
}
public void setTeam_quantity(String team_quantity) {
	this.team_quantity = team_quantity;
}
public int getTotal_price() {
	return total_price;
}
public void setTotal_price(int total_price) {
	this.total_price = total_price;
}
public String getCustomer_name() {
	return customer_name;
}
public void setCustomer_name(String customer_name) {
	this.customer_name = customer_name;
}
public String getCustomer_Country() {
	return customer_Country;
}
public void setCustomer_Country(String customer_Country) {
	this.customer_Country = customer_Country;
}
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((customer_Country == null) ? 0 : customer_Country.hashCode());
	result = prime * result + ((customer_name == null) ? 0 : customer_name.hashCode());
	result = prime * result + ((team_quantity == null) ? 0 : team_quantity.hashCode());
	result = prime * result + total_price;
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
	Order other = (Order) obj;
	if (customer_Country == null) {
		if (other.customer_Country != null)
			return false;
	} else if (!customer_Country.equals(other.customer_Country))
		return false;
	if (customer_name == null) {
		if (other.customer_name != null)
			return false;
	} else if (!customer_name.equals(other.customer_name))
		return false;
	if (team_quantity == null) {
		if (other.team_quantity != null)
			return false;
	} else if (!team_quantity.equals(other.team_quantity))
		return false;
	if (total_price != other.total_price)
		return false;
	return true;
}
@Override
public String toString() {
	return "Order [team_quantity=" + team_quantity + ", total_price=" + total_price + ", customer_name=" + customer_name
			+ ", customer_Country=" + customer_Country + "]";
}


  
  
  
}
