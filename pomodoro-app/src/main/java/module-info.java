module com.pomodoro {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens com.pomodoro.controller to javafx.fxml;
    exports com.pomodoro;
}
