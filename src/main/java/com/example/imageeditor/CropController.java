package com.example.imageeditor;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CropController {
    @FXML
    private ImageView cropImageView;

    private Image originalImage;  // Store the original image

    public void setImageToCrop(Image image) {
        // Store the original image
        originalImage = image;
        // Resize the image to the desired dimensions
        Image resizedImage = resizeImage(image, 743, 391);
        // Set the resized image to the ImageView
        cropImageView.setImage(resizedImage);
        cropImageView.setPreserveRatio(true);
        cropImageView.setFitWidth(743);
        cropImageView.setFitHeight(391);
    }


    // Method to resize the image to the specified dimensions
    private Image resizeImage(Image image, int width, int height) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(javafx.scene.paint.Color.TRANSPARENT);
        return imageView.snapshot(params, null);
    }
    @FXML
    private void saveImage() {
        Image filteredImage = cropImageView.snapshot(null, null);
        // Set the filtered image back in filterImageView
        cropImageView.setImage(filteredImage);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Mainfilter.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            MainFilterController mainFilterController = loader.getController();
            mainFilterController.setImage(cropImageView.getImage());

            Stage stage = (Stage) cropImageView.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("MainFilter");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void back() {
        // Set the original image back
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Mainfilter.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            MainFilterController mainFilterController = loader.getController();
            mainFilterController.setImage(cropImageView.getImage());

            Stage stage = (Stage) cropImageView.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("MainFilter");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void crop() {
        // Get the current image from cropImageView
        Image image = originalImage;
        if (image == null) {
            return;  // No image to crop
        }

        double width = image.getWidth();
        double height = image.getHeight();

        double targetWidth;
        double targetHeight;

        // Determine the target dimensions for 9:16 aspect ratio
        targetWidth = Math.min(width, height * 9 / 16);
        targetHeight = targetWidth * 16 / 9;

        // Calculate the cropping parameters to center crop the image
        double xOffset = (width - targetWidth) / 2;
        double yOffset = (height - targetHeight) / 2;

        // Display the cropped image without modifying the stored image
        cropImageView.setViewport(new javafx.geometry.Rectangle2D(xOffset, yOffset, targetWidth, targetHeight));
    }
    @FXML
    private void crop1() {
        // Get the current image from cropImageView
        Image image = originalImage;
        if (image == null) {
            return;  // No image to crop
        }

        double width = image.getWidth();
        double height = image.getHeight();

        // Determine the target dimensions for a 1:1 aspect ratio (square crop)
        double targetWidth = Math.min(width, height);
        double targetHeight = targetWidth;

        // Calculate the cropping parameters to center crop the image
        double xOffset = (width - targetWidth) / 2;
        double yOffset = (height - targetHeight) / 2;

        // Display the cropped image without modifying the stored image
        cropImageView.setViewport(new javafx.geometry.Rectangle2D(xOffset, yOffset, targetWidth, targetHeight));
    }

    @FXML
    private void crop2() {
        // Get the current image from cropImageView
        Image image = originalImage;
        if (image == null) {
            return;  // No image to crop
        }

        double width = image.getWidth();
        double height = image.getHeight();

        // Determine the target dimensions for a 16:9 aspect ratio
        double targetWidth = Math.min(width, height * 16 / 9);
        double targetHeight = targetWidth * 9 / 16;

        // Calculate the cropping parameters to center crop the image
        double xOffset = (width - targetWidth) / 2;
        double yOffset = (height - targetHeight) / 2;

        // Display the cropped image without modifying the stored image
        cropImageView.setViewport(new javafx.geometry.Rectangle2D(xOffset, yOffset, targetWidth, targetHeight));
    }

    @FXML
    private void crop3() {
        // Get the current image from cropImageView
        Image image = originalImage;
        if (image == null) {
            return;  // No image to crop
        }

        double width = image.getWidth();
        double height = image.getHeight();

        // Determine the target dimensions for a 4:3 aspect ratio
        double targetWidth = Math.min(width, height * 4 / 3);
        double targetHeight = targetWidth * 3 / 4;

        // Calculate the cropping parameters to center crop the image
        double xOffset = (width - targetWidth) / 2;
        double yOffset = (height - targetHeight) / 2;

        // Display the cropped image without modifying the stored image
        cropImageView.setViewport(new javafx.geometry.Rectangle2D(xOffset, yOffset, targetWidth, targetHeight));
    }
    // Add similar methods for other cropping ratios
}
