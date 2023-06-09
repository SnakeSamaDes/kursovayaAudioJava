package com.example.audioplayerkursach;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/MainWindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 700, 650);
            stage.setTitle("Audioplayer");
            stage.setScene(scene);
            stage.show();
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch();
    }
}
