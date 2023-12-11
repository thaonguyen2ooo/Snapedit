package com.example.imageeditor;

import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

public class Image {
    @FXML
    private ImageView imageView1;

    public void setImage(javafx.scene.image.Image image) {
        // Resize the image to the desired dimensions
        javafx.scene.image.Image resizedImage = resizeImage(image, 600, 388);

        // Set the resized image to the ImageView
        imageView1.setImage(resizedImage);
        imageView1.setPreserveRatio(true);
        imageView1.setFitWidth(332);
        imageView1.setFitHeight(205);
    }

    // Method to resize the image to the specified dimensions
    private javafx.scene.image.Image resizeImage(javafx.scene.image.Image image, int width, int height) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(javafx.scene.paint.Color.TRANSPARENT);
        WritableImage resizedImage = new WritableImage(width, height);
        return imageView.snapshot(params, resizedImage);
    }

}
