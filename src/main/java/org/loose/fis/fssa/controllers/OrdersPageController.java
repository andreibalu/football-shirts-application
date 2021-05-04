package org.loose.fis.fssa.controllers;

import org.loose.fis.fssa.model.Order;
import org.loose.fis.fssa.model.Shirt;
import org.loose.fis.fssa.services.OrderService;
import org.loose.fis.fssa.services.ShirtService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class OrdersPageController {
	
	
	@FXML
    private Button Logout;

    @FXML
    private TableView<Order> tableOrders;

    @FXML
    private TableColumn<Order, String> col_teamquantity;

    @FXML
    private TableColumn<Order, Integer> col_totalPrice;

    @FXML
    private TableColumn<Order, String> col_name;

    @FXML
    private TableColumn<Order, String> col_country;

    
    private int contor;

    @FXML
    private Button HomePage;
    
   
    

    

    @FXML
    void handleGoToHomePage(ActionEvent event) {

    }

    @FXML
    void handleLogout(ActionEvent event) throws Exception {
		Stage primaryStage=(Stage)Logout.getScene().getWindow();
		 Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("resources/login.fxml"));
	     primaryStage.setTitle("Login");
	     primaryStage.setScene(new Scene(root, 400, 300));
	     primaryStage.show();
    }
	

}
