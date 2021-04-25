package org.loose.fis.fssa.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class CustomerHomeController {

	  @FXML
	    private Button Logout;

	   @FXML
	   void handleLogoutAction(ActionEvent event) throws Exception{
		   Stage primaryStage=(Stage)Logout.getScene().getWindow();
			 Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("resources/login.fxml"));
		     primaryStage.setTitle("Login");
		     primaryStage.setScene(new Scene(root, 400, 300));
		     primaryStage.show();
	    }
}
