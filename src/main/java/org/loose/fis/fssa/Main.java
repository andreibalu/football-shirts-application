package org.loose.fis.fssa;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.loose.fis.fssa.services.FileSystemService;
import org.loose.fis.fssa.services.OrderService;
import org.loose.fis.fssa.services.ShirtCartService;
import org.loose.fis.fssa.services.ShirtService;
import org.loose.fis.fssa.services.UserService;

import java.nio.file.Files;
import java.nio.file.Path;


public class Main extends Application {
   public static final String moneda="$";
    @Override
    public void start(Stage primaryStage) throws Exception {
        UserService.initDatabase();
        ShirtService.initDatabase();
        OrderService.initDatabase();
        ShirtCartService.initDatabase();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("register.fxml"));
        primaryStage.setTitle("Register");
        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.show();
    }
    


    public static void main(String[] args) {
        launch(args);
    }
}