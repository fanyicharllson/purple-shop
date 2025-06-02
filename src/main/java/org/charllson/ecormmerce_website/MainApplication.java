package org.charllson.ecormmerce_website;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class MainApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/charllson/ecormmerce_website/welcome-view.fxml"));
        Parent root = loader.load();

        // Load CSS
        String css = Objects.requireNonNull(getClass().getResource("/org/charllson/ecormmerce_website/styles/style.css")).toExternalForm();

        // Create the scene
        Scene scene = new Scene(root);
        scene.getStylesheets().add(css);

        // Configure the stage
        primaryStage.setTitle("PurpleShop");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setMinWidth(900);
        primaryStage.setMinHeight(600);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}