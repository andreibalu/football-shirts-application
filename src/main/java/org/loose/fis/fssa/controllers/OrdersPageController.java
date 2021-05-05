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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class OrdersPageController {
	
	
	@FXML
    private Button Accept;

    @FXML
    private Button Deny;
	
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
    private TextField selectedName;

    @FXML
    private TextField selectedCountry;
    
    @FXML
    private Text messageAfter;

    @FXML
    private Button refresh;

    

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
	
    
    @FXML
    void handleAcceptOrder(ActionEvent event) {
    	
    }

    @FXML
    void handleDenyOrder(ActionEvent event) {
    	
    }
    
    @FXML
    void handleRefreshTable(ActionEvent event) {

    }
    
    
    @FXML
    void clickOrder(MouseEvent event) {
    	if(event.getClickCount()==1) {
    		int idx=0;
    		idx=tableOrders.getSelectionModel().getSelectedIndex();
    		selectedName.setText(col_name.getCellData(idx));
    		selectedCountry.setText(col_country.getCellData(idx));
    	}
    }
    

}
