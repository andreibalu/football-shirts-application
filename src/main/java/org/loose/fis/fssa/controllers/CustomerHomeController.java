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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class CustomerHomeController implements Initializable {

	  @FXML
	    private Button Logout;
	  
	  @FXML
	  private ScrollPane scroll;

	  @FXML
	  private GridPane grid;
	  
	  private int contor;

	   @FXML
	   void handleLogoutAction(ActionEvent event) throws Exception{
		   Stage primaryStage=(Stage)Logout.getScene().getWindow();
			 Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("resources/login.fxml"));
		     primaryStage.setTitle("Login");
		     primaryStage.setScene(new Scene(root, 400, 300));
		     primaryStage.show();
	    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//toDo
	}
	   
}
