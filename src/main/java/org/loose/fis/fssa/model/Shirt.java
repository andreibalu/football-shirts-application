package org.loose.fis.fssa.model;

import org.dizitart.no2.objects.Id;

import javafx.scene.image.Image;

public class Shirt {
	
	@Id
    private String team;
    private String league;
    private int price;
    private int quantity;
    private String image;
    
	public Shirt(String team, String league, int price, int quantity, String image) {
		this.team = team;
		this.league = league;
		this.price = price;
		this.quantity = quantity;
		this.image = image;
	}
	
	public Shirt() {
		// TODO Auto-generated constructor stub
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getLeague() {
		return league;
	}

	public void setLeague(String league) {
		this.league = league;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		result = prime * result + ((league == null) ? 0 : league.hashCode());
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
		Shirt other = (Shirt) obj;
		if (image == null) {
			if (other.image != null)
				return false;
		} else if (!image.equals(other.image))
			return false;
		if (league == null) {
			if (other.league != null)
				return false;
		} else if (!league.equals(other.league))
			return false;
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
}
