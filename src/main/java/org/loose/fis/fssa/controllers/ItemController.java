package org.loose.fis.fssa.controllers;

import org.loose.fis.fssa.Main;
import org.loose.fis.fssa.model.Shirt;
import org.loose.fis.fssa.model.ShirtListener;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ItemController {

	 @FXML
	 private Label teamLabel;

	 @FXML
	 private Label leagueLabel;

	 @FXML
	 private Label priceLabel;

	 @FXML
	 private Label currencyLabel;

	 @FXML
	 private ImageView imageLabel;
	 
	 private Shirt shirt;
	
	 private ShirtListener listener;
	 
	 @FXML
	 private void click(MouseEvent mouseEvent)
	 {
		 listener.onClickListener(shirt);
	 }
	 
	 public void setInfo(Shirt shirt,ShirtListener listener)
	 {
		 this.shirt=shirt;
		 this.listener=listener;
		 teamLabel.setText(shirt.getTeam());
		 leagueLabel.setText(shirt.getLeague());
		 currencyLabel.setText(Main.moneda);
		 priceLabel.setText(String.valueOf(shirt.getPrice()));
		 Image image=new Image(getClass().getResourceAsStream(shirt.getImage()));
		 imageLabel.setImage(image);
	 }
}
