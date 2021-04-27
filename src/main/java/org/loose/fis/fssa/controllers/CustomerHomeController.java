package org.loose.fis.fssa.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.loose.fis.fssa.model.Shirt;
import org.loose.fis.fssa.services.ShirtService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CustomerHomeController  {

	 @FXML
	 private VBox selectedShirt;

	 @FXML
	 private TextField selectedQuantity;
	 
	  @FXML
	  private Button Logout;
	  
	  @FXML
	  private ScrollPane scroll;

	  @FXML
	  private GridPane grid;
	  
	  @FXML
	  private TextField enteredName;

	  @FXML
	  private TextField enteredCountry;
	  
	  private int contor;

	   @FXML
	   void handleLogoutAction(ActionEvent event) throws Exception{
		   Stage primaryStage=(Stage)Logout.getScene().getWindow();
			 Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("resources/login.fxml"));
		     primaryStage.setTitle("Login");
		     primaryStage.setScene(new Scene(root, 400, 300));
		     primaryStage.show();
	    }
	   
	   @FXML
	    void handleAddToCart(ActionEvent event) {
          //todo
	    }
	   
	   @FXML
	    void handlePlaceOrder(ActionEvent event) {
         //todo
	    }

	   

	@FXML
	public void initialize() {
		contor=ShirtService.getShirtNumber();
		int coloana=0;
		int linie=0;
		try {
		for(int i=1;i<=contor;i++)
		{
			FXMLLoader fxmlloader=new FXMLLoader();
			fxmlloader.setLocation(getClass().getClassLoader().getResource("resources/item.fxml"));
			AnchorPane anchorpane=fxmlloader.load();
			
			ItemController itemcontroller=fxmlloader.getController();
			Shirt shirt=new Shirt();
			shirt=ShirtService.returnShirt(i);
			itemcontroller.setInfo(shirt);
			if(coloana==2)
			{
				coloana=0;
				linie++;
			}
			coloana++;
			grid.add(anchorpane, coloana,linie);
			GridPane.setMargin(anchorpane, new Insets(15,5,15,5));
			grid.setMinWidth(Region.USE_COMPUTED_SIZE);
			grid.setMaxWidth(Region.USE_PREF_SIZE);
			grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
			grid.setMinHeight(Region.USE_COMPUTED_SIZE);
			grid.setMaxHeight(Region.USE_PREF_SIZE);
			grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
		}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	   
}
