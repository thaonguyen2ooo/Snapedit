package com.example.imageeditor;

import com.example.imageeditor.DoodleController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HomeController {
    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }


    @FXML
    private void openEdit(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Mainpage.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);


            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Mainpage");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openDoodle(ActionEvent event) {
        // Create a new DoodleController instance
        DoodleController doodleController = new DoodleController();

        // Create a new Stage to show the Doodle feature
        Stage doodleStage = new Stage();

        // Call the start method of DoodleController
        doodleController.start(doodleStage);
    }
}
