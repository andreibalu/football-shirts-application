package org.loose.fis.fssa.controllers;

import org.loose.fis.fssa.model.Shirt;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ItemController {

	 @FXML
	 private Label teamLabel;

	 @FXML
	 private Label leagueLabel;

	 @FXML
	 private Label priceLabel;

	 @FXML
	 private ImageView imageLabel;
	 
	 private Shirt shirt;
	 
	 public void setInfo(Shirt shirt)
	 {
		 this.shirt=shirt;
		 teamLabel.setText(shirt.getTeam());
		 leagueLabel.setText(shirt.getLeague());
		 priceLabel.setText(String.valueOf(shirt.getPrice()));
		 Image image=new Image(getClass().getResourceAsStream(shirt.getImage()));
		 imageLabel.setImage(image);
	 }
	 
}
