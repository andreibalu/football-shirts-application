package org.loose.fis.fssa.controllers;

import java.io.IOException;

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

    
    

    @FXML
    private Button HomePage;
    
    private int contor;
    
    ObservableList<Order> list=  FXCollections.observableArrayList();
    
    
    public void initialize() {   
        
        col_teamquantity.setCellValueFactory(new PropertyValueFactory<Order,String>("team_quantity"));
        col_totalPrice.setCellValueFactory(new PropertyValueFactory<Order,Integer>("total_price"));
        col_name.setCellValueFactory(new PropertyValueFactory<Order,String>("customer_name"));
        col_country.setCellValueFactory(new PropertyValueFactory<Order,String>("customer_Country"));
        
        contor=OrderService.getOrderNumber();
        for(int i=1;i<=contor;i++) {
            Order order=new Order();
            order= OrderService.returnOrder(i);
            list.add(order);
        }
        
        tableOrders.setItems(list);
    }
    

    @FXML
    void handleGoToHomePage(ActionEvent event) throws Exception {
    	Stage primaryStage=(Stage)HomePage.getScene().getWindow();
    	Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("resources/shopownerHome.fxml"));
        primaryStage.setTitle("Shop Owner Home");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
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