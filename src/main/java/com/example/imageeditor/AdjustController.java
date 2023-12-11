package com.example.imageeditor;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;


import java.io.IOException;

public class AdjustController {

    @FXML
    private ImageView imageView;


    private Image originalImage; // Store the original image
    private Image adjustedImage; // Store the adjusted image

    public void setImageToAdjust(javafx.scene.image.Image image) {
        // Store the original image
        originalImage = image;
        adjustedImage = image;

        // Resize the image to the desired dimensions
        javafx.scene.image.Image resizedImage = resizeImage(image, 743, 391);

        // Set the resized image to the ImageView
        imageView.setImage(resizedImage);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(743);
        imageView.setFitHeight(391);
    }

    private Image resizeImage(Image image, int width, int height) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(javafx.scene.paint.Color.TRANSPARENT);
        return imageView.snapshot(params, null);
    }

    @FXML
    private void onBrightnessButtonClicked() {
        showSlider("Brightness", -1.0, 1.0);
    }

    @FXML
    private void onSharpnessButtonClicked() {
        showSlider("Sharpness", 0.0, 2.0);
    }

    @FXML
    private void onContrastButtonClicked() {
        showSlider("Contrast", 0.0, 2.0);
    }

    @FXML
    private void onSaturationButtonClicked() {
        showSlider("Saturation", 0.0, 2.0);
    }

    private void showSlider(String title, double minValue, double maxValue) {
        Dialog<Double> dialog = new Dialog<>();
        dialog.setTitle(title);

        // Create a slider
        Slider slider = new Slider(minValue, maxValue, 0.0);
        slider.setBlockIncrement(0.1);

        // Add the slider to the dialog
        dialog.getDialogPane().setContent(slider);

        // Create OK and Cancel buttons
        ButtonType okButton = new ButtonType("OK", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButton) {
                double value = slider.getValue();

                // Apply the filter based on the button pressed
                if (title.equals("Brightness")) {
                    adjustedImage = adjustBrightness(adjustedImage, value);
                    imageView.setImage(adjustedImage);
                } else if (title.equals("Sharpness")) {
                    // Adjust sharpness of adjustedImage
                    adjustedImage = adjustSharpness(adjustedImage, value);
                    imageView.setImage(adjustedImage);
                } else if (title.equals("Contrast")) {
                    adjustedImage = adjustContrast(adjustedImage, value);
                    imageView.setImage(adjustedImage);
                    // Adjust contrast of adjustedImage
                } else if (title.equals("Saturation")) {
                    adjustedImage = adjustSaturation(adjustedImage, value);
                    imageView.setImage(adjustedImage);
                    // Adjust saturation of adjustedImage
                }


            }
            return null;
        });
        imageView.setImage(adjustedImage);
        dialog.showAndWait();
    }


    private Image adjustBrightness(Image image, double brightness) {
        if (image == null) {
            return null;
        }

        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        // Create a WritableImage for the adjusted result
        WritableImage adjustedImage = new WritableImage(width, height);
        PixelReader pixelReader = image.getPixelReader();
        PixelWriter pixelWriter = adjustedImage.getPixelWriter();

        // Iterate through all pixels and adjust brightness
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color color = pixelReader.getColor(x, y);

                double red = color.getRed() + brightness;
                double green = color.getGreen() + brightness;
                double blue = color.getBlue() + brightness;

                // Ensure color components are within the valid range [0, 1]
                red = Math.min(1.0, Math.max(0.0, red));
                green = Math.min(1.0, Math.max(0.0, green));
                blue = Math.min(1.0, Math.max(0.0, blue));

                Color adjustedColor = new Color(red, green, blue, color.getOpacity());
                pixelWriter.setColor(x, y, adjustedColor);
            }
        }
        imageView.setImage(adjustedImage);
        return adjustedImage;
    }

    private Image adjustSharpness(Image image, double sharpness) {
        if (image == null) {
            return null;
        }

        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        // Create a WritableImage for the adjusted result
        WritableImage adjustedImage = new WritableImage(width, height);
        PixelReader pixelReader = image.getPixelReader();
        PixelWriter pixelWriter = adjustedImage.getPixelWriter();

        // Iterate through all pixels and adjust sharpness
        for (int x = 1; x < width - 1; x++) {
            for (int y = 1; y < height - 1; y++) {
                Color center = pixelReader.getColor(x, y);
                Color top = pixelReader.getColor(x, y - 1);
                Color bottom = pixelReader.getColor(x, y + 1);
                Color left = pixelReader.getColor(x - 1, y);
                Color right = pixelReader.getColor(x + 1, y);

                double red = center.getRed() - sharpness * (center.getRed() - (top.getRed() + bottom.getRed() + left.getRed() + right.getRed()) / 4);
                double green = center.getGreen() - sharpness * (center.getGreen() - (top.getGreen() + bottom.getGreen() + left.getGreen() + right.getGreen()) / 4);
                double blue = center.getBlue() - sharpness * (center.getBlue() - (top.getBlue() + bottom.getBlue() + left.getBlue() + right.getBlue()) / 4);

                // Ensure color components are within the valid range [0, 1]
                red = Math.min(1.0, Math.max(0.0, red));
                green = Math.min(1.0, Math.max(0.0, green));
                blue = Math.min(1.0, Math.max(0.0, blue));

                Color adjustedColor = new Color(red, green, blue, center.getOpacity());
                pixelWriter.setColor(x, y, adjustedColor);
            }
        }
        imageView.setImage(adjustedImage);
        return adjustedImage;
    }
    private Image adjustContrast(Image image, double contrast) {
        if (image == null) {
            return null;
        }

        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        // Create a WritableImage for the adjusted result
        WritableImage adjustedImage = new WritableImage(width, height);
        PixelReader pixelReader = image.getPixelReader();
        PixelWriter pixelWriter = adjustedImage.getPixelWriter();

        // Iterate through all pixels and adjust contrast
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color color = pixelReader.getColor(x, y);

                double red = (color.getRed() - 0.5) * contrast + 0.5;
                double green = (color.getGreen() - 0.5) * contrast + 0.5;
                double blue = (color.getBlue() - 0.5) * contrast + 0.5;

                // Ensure color components are within the valid range [0, 1]
                red = Math.min(1.0, Math.max(0.0, red));
                green = Math.min(1.0, Math.max(0.0, green));
                blue = Math.min(1.0, Math.max(0.0, blue));

                Color adjustedColor = new Color(red, green, blue, color.getOpacity());
                pixelWriter.setColor(x, y, adjustedColor);
            }
        }
        imageView.setImage(adjustedImage);
        return adjustedImage;
    }
    private Image adjustSaturation(Image image, double saturation) {
        if (image == null) {
            return null;
        }

        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        // Create a WritableImage for the adjusted result
        WritableImage adjustedImage = new WritableImage(width, height);
        PixelReader pixelReader = image.getPixelReader();
        PixelWriter pixelWriter = adjustedImage.getPixelWriter();

        // Iterate through all pixels and adjust saturation
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color color = pixelReader.getColor(x, y);

                double maxColor = Math.max(color.getRed(), Math.max(color.getGreen(), color.getBlue()));
                double red = (color.getRed() - maxColor) * saturation + color.getRed();
                double green = (color.getGreen() - maxColor) * saturation + color.getGreen();
                double blue = (color.getBlue() - maxColor) * saturation + color.getBlue();

                // Ensure color components are within the valid range [0, 1]
                red = Math.min(1.0, Math.max(0.0, red));
                green = Math.min(1.0, Math.max(0.0, green));
                blue = Math.min(1.0, Math.max(0.0, blue));

                Color adjustedColor = new Color(red, green, blue, color.getOpacity());
                pixelWriter.setColor(x, y, adjustedColor);
            }
        }
        imageView.setImage(adjustedImage);
        return adjustedImage;
    }

    @FXML
    private void saveImage() {
        Image filteredImage = imageView.snapshot(null, null); // Use imageView, not ImageView
        // Set the filtered image back in imageView
        imageView.setImage(filteredImage);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Mainfilter.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            MainFilterController mainFilterController = loader.getController();
            mainFilterController.setImage(imageView.getImage());

            Stage stage = (Stage) imageView.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("MainFilter");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    @FXML
    private void back() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Mainfilter.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            MainFilterController mainFilterController = loader.getController();
            mainFilterController.setImage(originalImage);

            Stage stage = (Stage) imageView.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("MainFilter");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
