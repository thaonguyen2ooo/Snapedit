package com.example.imageeditor;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FilterController {
    @FXML
    private ImageView filterImageView;
    private Image originalImage;  // Store the original image

    public void setImageToFilter(javafx.scene.image.Image image) {
        // Store the original image
        originalImage = image;

        // Resize the image to the desired dimensions
        javafx.scene.image.Image resizedImage = resizeImage(image, 743, 391);

        // Set the resized image to the ImageView
        filterImageView.setImage(resizedImage);
        filterImageView.setPreserveRatio(true);
        filterImageView.setFitWidth(743);
        filterImageView.setFitHeight(391);
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
    public void applyVividEffect() {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setSaturation(0.5);
        filterImageView.setEffect(colorAdjust);
    }
    @FXML
    public void applyBlackAndWhiteEffect() {
        try {
            // Define the API URL
            String apiUrl = "https://studio.pixelixe.com/api/sepia/v1?imageUrl=https:https://firebasestorage.googleapis.com/v0/b/pixelixe-4824a.appspot.com/o/users%2FYynF4ExpSJY9HHry2nMI3jXo4R42%2FUntitled-10-30-2023_16%3A4%3A48%3A877.png?alt=media&token=a1c4f79e-3ab8-4a48-9f5b-e3b7cfb1e558"; // Replace with the actual image URL

            // Create a URL object
            URL url = new URL(apiUrl);

            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer YynF4ExpSJY9HHry2nMI3jXo4R42"); // Replace 'api_key' with your actual API key

            // Get the response code
            int responseCode = connection.getResponseCode();

            if (responseCode == 200) {
                // If the response code is OK, get the response data input stream
                InputStream inputStream = connection.getInputStream();

                // Convert the input stream to a BufferedImage (assuming you have a library like BufferedImage or Java's ImageIO)
                BufferedImage bufferedImage = ImageIO.read(inputStream);

                // Convert the BufferedImage to a JavaFX WritableImage
                WritableImage fxImage = SwingFXUtils.toFXImage(bufferedImage, null);

                // Set the filtered image to the ImageView
                filterImageView.setImage(fxImage);
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void applyWarmToneEffect() {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setHue(0.1); // Shift the hue towards red-yellow range
        filterImageView.setEffect(colorAdjust);
    }

    @FXML
    public void applyCoolToneEffect() {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setHue(-0.5); // Shift the hue towards blue range
        filterImageView.setEffect(colorAdjust);
    }
    @FXML
    private void back() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Mainfilter.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            MainFilterController mainFilterController = loader.getController();
            mainFilterController.setImage(filterImageView.getImage());

            Stage stage = (Stage) filterImageView.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("MainFilter");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void saveImage() {
        Image filteredImage = filterImageView.snapshot(null, null);
        // Set the filtered image back in filterImageView
        filterImageView.setImage(filteredImage);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Mainfilter.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            MainFilterController mainFilterController = loader.getController();
            mainFilterController.setImage(filterImageView.getImage());

            Stage stage = (Stage) filterImageView.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("MainFilter");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
