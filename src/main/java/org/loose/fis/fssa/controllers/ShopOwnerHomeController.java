package org.loose.fis.fssa.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ShopOwnerHomeController {

	@FXML
    private Button Logout;
	
	@FXML
	private Button addButton;

	@FXML
	private Button editButton;

	@FXML
	private Button removeButton;

    @FXML
    private TextField teamField;

    @FXML
    private TextField leagueField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField quantityField;

    @FXML
    private TextField imageField;

    @FXML
    private TableView<?> tableShirts;

    @FXML
    private TableColumn<?, ?> col_team;

    @FXML
    private TableColumn<?, ?> col_league;

    @FXML
    private TableColumn<?, ?> col_price;

    @FXML
    private TableColumn<?, ?> col_quantity;

    @FXML
    private TableColumn<?, ?> col_image;
	
	@FXML
    void handleLogout(ActionEvent event) throws Exception {
		Stage primaryStage=(Stage)Logout.getScene().getWindow();
		 Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("resources/login.fxml"));
	     primaryStage.setTitle("Login");
	     primaryStage.setScene(new Scene(root, 400, 300));
	     primaryStage.show();
    }
	
	
	@FXML
    void handleAddShirts(ActionEvent event) {

    }

    @FXML
    void handleEditShirts(ActionEvent event) {

    }
	
	
    @FXML
    void handleRemoveShirts(ActionEvent event) {

    }
	
	
	
	
	
	
	
	
	
	
	
	
	
}
