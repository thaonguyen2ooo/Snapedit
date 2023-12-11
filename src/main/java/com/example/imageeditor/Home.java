package com.example.imageeditor;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class Home extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the initial scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
        Parent root = loader.load();
        HomeController controller = loader.getController();
        controller.setPrimaryStage(primaryStage);
        // Set the scene with the desired dimensions
        Scene scene = new Scene(root, 1300, 650);

        // Set the stage with the initial scene
        primaryStage.setScene(scene);
        primaryStage.setTitle("Home Scene");
        primaryStage.show();
    }
}