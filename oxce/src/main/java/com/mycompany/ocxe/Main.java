package com.mycompany.ocxe;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
public void start(Stage stage) throws IOException {
    try {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
        Scene scene = new Scene(root);

        stage.setTitle("My Application"); // Menambahkan judul untuk jendela
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
        System.out.println("Error loading Login.fxml: " + e.getMessage());
    }
}


    public static void main(String[] args) {
        launch(args);
    }
}