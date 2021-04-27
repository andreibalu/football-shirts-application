package org.loose.fis.fssa.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.loose.fis.fssa.model.Shirt;
import org.loose.fis.fssa.model.ShirtListener;
import org.loose.fis.fssa.services.OrderService;
import org.loose.fis.fssa.services.ShirtCartService;
import org.loose.fis.fssa.services.ShirtService;
import org.loose.fis.fssa.Main;
import org.loose.fis.fssa.exceptions.NotEnoughStockException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CustomerHomeController  {

	 @FXML
	 private VBox selectedShirt;
	 
	 @FXML
	 private Label SelectedShirtLabel;

	 @FXML
	 private Label SelectedPriceLabel;
	 
	 @FXML
	 private Label currencyLabel;

	 @FXML
	 private ImageView SelectedShirtImage;

	 @FXML
	 private TextField selectedQuantity;
	 
	 @FXML
	 private Text addtocartmessage;
	 
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
	 
	 @FXML
	 private Text ordermessage;
	  
	 private int contor;
	 
	 private Image image;
	 
	 private ShirtListener listener;

	   @FXML
	   void handleLogoutAction(ActionEvent event) throws Exception{
		   Stage primaryStage=(Stage)Logout.getScene().getWindow();
			 Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("resources/login.fxml"));
		     primaryStage.setTitle("Login");
		     primaryStage.setScene(new Scene(root, 400, 300));
		     primaryStage.show();
	    }
	   
	   @FXML
	    void handleAddToCart(ActionEvent event) throws Exception {
		   try {
		   int qu=0;
		   qu=Integer.parseInt(selectedQuantity.getText());
		   ShirtService.VerifyStock(SelectedShirtLabel.getText(), qu);
          ShirtCartService.addShirtToCart(SelectedShirtLabel.getText(),SelectedPriceLabel.getText(),selectedQuantity.getText());
          addtocartmessage.setText("Added shirt succesfully to cart");
	    }catch(NotEnoughStockException e)
		   {
	    	addtocartmessage.setText(e.getMessage());
		   }
	   }
	   
	   @FXML
	    void handlePlaceOrder(ActionEvent event) {
         String team_quantity;
         int total_price;
        team_quantity=ShirtCartService.getTeamQuantityForOrder();
        total_price=ShirtCartService.getTotalPriceForOrder();
        OrderService.addOrdertoDatabase(team_quantity, String.valueOf(total_price),enteredName.getText(),enteredCountry.getText());
        ShirtCartService.removeShirtsFromCart();
        ordermessage.setText("Order placed succesfully!");
	    }

	private void setSelectedShirt(Shirt shirt)
	{
		SelectedShirtLabel.setText(shirt.getTeam());
		currencyLabel.setText(Main.moneda);
		SelectedPriceLabel.setText(String.valueOf(shirt.getPrice()));
		image=new Image(getClass().getResourceAsStream(shirt.getImage()));
		SelectedShirtImage.setImage(image);		
	}

	@FXML
	public void initialize() {
		contor=ShirtService.getShirtNumber();
		if(contor>0)
		{
			listener=new ShirtListener() {
				@Override
				public void onClickListener(Shirt shirt)
				{
					setSelectedShirt(shirt);
				}
			};
			
		}
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
			itemcontroller.setInfo(shirt,listener);
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
