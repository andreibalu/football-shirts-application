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
        initDirectory();
        UserService.initDatabase();
        ShirtService.initDatabase();
        OrderService.initDatabase();
        ShirtCartService.initDatabase();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("resources/login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.show();
    }
    
    private void initDirectory() {
        Path applicationHomePath = FileSystemService.APPLICATION_HOME_PATH;
        if (!Files.exists(applicationHomePath))
            applicationHomePath.toFile().mkdirs();
    }


    public static void main(String[] args) {
        launch(args);
    }
}