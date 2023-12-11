package com.example.imageeditor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;

public class MainPageController {

    @FXML
    private ImageView imageView;
    @FXML
    private ImageView imageView1;

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    @FXML
    private void openImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg"));

        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        if (selectedFile != null) {
            String imagePath = selectedFile.toURI().toString();
            Image image = new Image(imagePath);
            imageView.setImage(image);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Mainfilter.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);

                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("MainFilter");


                MainFilterController mainFilterController = loader.getController();
                mainFilterController.setImage(image);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    private void back() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) imageView.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Homepage");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
