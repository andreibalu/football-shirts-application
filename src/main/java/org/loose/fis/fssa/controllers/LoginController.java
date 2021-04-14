package org.loose.fis.fssa.controllers;

import org.loose.fis.fssa.exceptions.UsernameAlreadyExistsException;
import org.loose.fis.fssa.exceptions.InvalidCredentialsException;
import org.loose.fis.fssa.services.UserService;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginController {
	 @FXML
	    private Text loginMessage;
	    @FXML
	    private PasswordField passwordField;
	    @FXML
	    private TextField usernameField;
	    @FXML
	    private ChoiceBox role;
@FXML
public void initialize() {
    role.getItems().addAll("Customer", "Shop Owner");
}

@FXML
public void handleLoginAction() throws Exception {
	Stage primaryStage=(Stage)loginMessage.getScene().getWindow();
	try {
  UserService.verifyLogin(usernameField.getText(),passwordField.getText()); //verificaparola
   String rol=(String)role.getValue();
   if(rol.equals("Customer"))
   {
	   Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("resources/customerHome.fxml"));
       primaryStage.setTitle("Customer Home");
       primaryStage.setScene(new Scene(root, 400, 300));
       primaryStage.show();
   }
   if(rol.equals("Shop Owner"))
   {
	   Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("resources/shopownerHome.fxml"));
       primaryStage.setTitle("Shop Owner");
       primaryStage.setScene(new Scene(root, 400, 300));
       primaryStage.show();
   }
	}catch(InvalidCredentialsException e) {
		loginMessage.setText(e.getMessage());
	}
}
@FXML
public void handleRedirectRegisterAction() throws Exception{
	Stage primaryStage=(Stage)loginMessage.getScene().getWindow();
	 Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("resources/register.fxml"));
     primaryStage.setTitle("Register");
     primaryStage.setScene(new Scene(root, 400, 300));
     primaryStage.show();
}
}
