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
import javafx.scene.control.TextArea;
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
    
    @FXML
    private TextArea denialReason;
    
    private int contor;
    
    private static String s; 
    
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
    void handleRefreshTable(ActionEvent event) {
    	
    	tableOrders.getItems().clear();
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
    	Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("shopownerHome.fxml"));
        primaryStage.setTitle("Shop Owner Home");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
    }

    @FXML
    void handleLogout(ActionEvent event) throws Exception {
		Stage primaryStage=(Stage)Logout.getScene().getWindow();
		 Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
	     primaryStage.setTitle("Login");
	     primaryStage.setScene(new Scene(root, 400, 300));
	     primaryStage.show();
    }
	
    @FXML
    void clickOrder(MouseEvent event) {
    	if(event.getClickCount()==1) {
    		int idx=0;
    		idx=tableOrders.getSelectionModel().getSelectedIndex();
    		selectedName.setText(col_name.getCellData(idx));
    		selectedCountry.setText(col_country.getCellData(idx));
    		s=col_teamquantity.getCellData(idx);
    	}
    }
    
   public static void setS(String str) {
	   s=str;
   }
    
    @FXML
    void handleAcceptOrder(ActionEvent event) {   	
    	OrderService.acceptOrder(selectedName.getText(), selectedCountry.getText());
    	messageAfter.setText("Order was accepted successfully !");
    }

    @FXML
    void handleDenyOrder(ActionEvent event) {
    	String ss;
    	ss=s;
    	OrderService.denyOrder(selectedName.getText(), selectedCountry.getText());
    	messageAfter.setText("Order was denied successfully !");
    	denialReason.clear();
    	int i,j,cont=0;
    	String aux="";
    	String nume="";
    	String cantitate="";
    	int ok=0,cant=0;
    	for(i=0; i<ss.length();i++) {
    		if(ss.charAt(i)!=','){
    			aux=aux+ss.charAt(i);
    		}
    		else {
    			for(j=0;j<aux.length();j++) {
    				if(aux.charAt(j)!='-' && ok==0) {
    					nume=nume+aux.charAt(j);
    				}
    				else {
    					ok=1;
    				}
    				if(ok==1 && aux.charAt(j)!='-') {   					
    					cantitate=cantitate+aux.charAt(j);
    				}    				
    			}
    			ok=0;
    			cant=Integer.parseInt(cantitate);
    			System.out.println(cant + " " + nume);
    			ShirtService.restoreQuantity(nume, cant);
    			aux="";nume="";cantitate="";cant=0;
    		}    			   		
    	}  	   	    	
    }
    
    
    
   
    
    
    
    

}
