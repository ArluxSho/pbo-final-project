package com.pomodoro;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        showWelcomeScreen();
        primaryStage.setTitle("PomoCat - Focus App");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    //tampilan awal aplikasi
    public static void showWelcomeScreen() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/com/pomodoro/view/welcome.fxml"));
        Scene scene = new Scene(loader.load(), 1280, 720);

        //css
        scene.getStylesheets().add(
                Main.class.getResource("/com/pomodoro/view/style.css").toExternalForm()
        );

        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    //tampilan task
    public static void showTaskScreen() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/com/pomodoro/view/task_view.fxml"));
        Scene scene = new Scene(loader.load(), 1280, 720);

        //css
        scene.getStylesheets().add(
                Main.class.getResource("/com/pomodoro/view/style.css").toExternalForm()
        );

        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    //tampilan timer
    public static void showTimerScreen() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/com/pomodoro/view/timer_view.fxml"));
        Scene scene = new Scene(loader.load(), 1280, 720);

        //css
        scene.getStylesheets().add(
                Main.class.getResource("/com/pomodoro/view/style.css").toExternalForm()
        );

        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
