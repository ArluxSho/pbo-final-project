// package com.pomodoro;

// import javafx.application.Application;
// import javafx.fxml.FXMLLoader;
// import javafx.scene.Scene;
// import javafx.stage.Stage;
// import javafx.scene.Parent;

// import java.util.Objects;

// public class Main extends Application {

//     @Override
//     public void start(Stage primaryStage) throws Exception {
//         // Load FXML
//         Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/pomodoro/view/timer_view.fxml")));
        
//         primaryStage.setTitle("Pomodoro Timer");
//         primaryStage.setScene(new Scene(root, 1000, 500));
//         primaryStage.setResizable(false); // Agar ukuran window fix
//         primaryStage.show();
//     }

//     public static void main(String[] args) {
//         launch(args);
//     }
// }

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
        showWelcomeScreen(); // Mulai dari Welcome
        primaryStage.setTitle("PomoPet - Focus App");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    // --- NAVIGATION METHODS ---

    public static void showWelcomeScreen() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/com/pomodoro/view/welcome.fxml"));
        primaryStage.setScene(new Scene(loader.load(), 900, 650));
        primaryStage.centerOnScreen();
    }

    public static void showTaskScreen() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/com/pomodoro/view/task_view.fxml"));
        primaryStage.setScene(new Scene(loader.load(), 900, 650));
        primaryStage.centerOnScreen();
    }

    public static void showTimerScreen() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/com/pomodoro/view/timer_view.fxml"));
        primaryStage.setScene(new Scene(loader.load(), 900, 650));
        primaryStage.centerOnScreen();
    }

    public static void main(String[] args) {
        launch(args);
    }
}