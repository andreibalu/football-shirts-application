package org.loose.fis.fssa.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import org.loose.fis.fssa.model.Shirt;
import org.loose.fis.fssa.services.ShirtService;
import org.loose.fis.fssa.services.UserService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ShopOwnerHomeController implements Initializable {
	
	
	@FXML
    private Text afterMessage;

	@FXML
    private Button Logout;
	
	@FXML
	private Button addButton;

	@FXML
	private Button editButton;

	@FXML
	private Button removeButton;
	
	@FXML
	private Button refreshButton;

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
    private TableView<Shirt> tableShirts;

    @FXML
    private TableColumn<Shirt, String> col_team;

    @FXML
    private TableColumn<Shirt, String> col_league;

    @FXML
    private TableColumn<Shirt, Integer> col_price;

    @FXML
    private TableColumn<Shirt, Integer> col_quantity;

    @FXML
    private TableColumn<Shirt, String> col_image;
    
    private int contor;
    
    ObservableList<Shirt> list=  FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle rb) {   
    	
    	col_team.setCellValueFactory(new PropertyValueFactory<Shirt,String>("team"));
    	col_league.setCellValueFactory(new PropertyValueFactory<Shirt,String>("league"));
    	col_price.setCellValueFactory(new PropertyValueFactory<Shirt,Integer>("price"));
    	col_quantity.setCellValueFactory(new PropertyValueFactory<Shirt,Integer>("quantity"));
    	col_image.setCellValueFactory(new PropertyValueFactory<Shirt,String>("image"));
    	
    	contor=ShirtService.getShirtNumber();
    	for(int i=1;i<=contor;i++) {
    		Shirt shirt=new Shirt();
    		shirt= ShirtService.returnShirt(i);
    		list.add(shirt);
    	}
    	
    	tableShirts.setItems(list);
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
	void handleRefreshTable(ActionEvent event) {
		
		tableShirts.getItems().clear();
		
		col_team.setCellValueFactory(new PropertyValueFactory<Shirt,String>("team"));
    	col_league.setCellValueFactory(new PropertyValueFactory<Shirt,String>("league"));
    	col_price.setCellValueFactory(new PropertyValueFactory<Shirt,Integer>("price"));
    	col_quantity.setCellValueFactory(new PropertyValueFactory<Shirt,Integer>("quantity"));
    	col_image.setCellValueFactory(new PropertyValueFactory<Shirt,String>("image"));
    	
    	contor=ShirtService.getShirtNumber();
    	for(int i=1;i<=contor;i++) {
    		Shirt shirt=new Shirt();
    		shirt= ShirtService.returnShirt(i);
    		list.add(shirt);
    	}
    	
    	tableShirts.setItems(list);
		
    	
	}
	
	
	@FXML
    void handleAddShirts(ActionEvent event) {
		ShirtService.addShirt(teamField.getText(), leagueField.getText(), priceField.getText(),quantityField.getText(), imageField.getText() );
		afterMessage.setText("Added shirt successfully");
    }

    @FXML
    void handleEditShirts(ActionEvent event) {
    	ShirtService.editShirt(teamField.getText(), leagueField.getText(), priceField.getText(),quantityField.getText(), imageField.getText() );
    	afterMessage.setText("Edited shirt successfully");
    }
	
	
    @FXML
    void handleRemoveShirts(ActionEvent event) {
    	afterMessage.setText("Removed shirt successfully");
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
}
