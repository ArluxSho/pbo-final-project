package com.pomodoro;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load FXML
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/pomodoro/view/timer_view.fxml")));
        
        primaryStage.setTitle("Pomodoro Timer");
        primaryStage.setScene(new Scene(root, 1000, 500));
        primaryStage.setResizable(false); // Agar ukuran window fix
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}