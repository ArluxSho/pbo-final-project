package com.pomodoro.controller;

import com.pomodoro.model.Task;
import com.pomodoro.model.TimerModel; // Pastikan file model timer sebelumnya ada
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;
import javafx.scene.image.Image; // Import Image
import javafx.scene.image.ImageView; // Import ImageView
import javafx.scene.layout.VBox; // Import VBox (jika ingin mengubah background VBox)

public class MainController {

    // --- Components untuk ToDo List ---
    @FXML private TextField inputTask;
    @FXML private ListView<Task> taskListView;
    
    // --- Components untuk Timer ---
    @FXML private Label timerLabel;
    @FXML private Label lblCurrentTask;
    @FXML private ImageView backgroundImageView;
    @FXML private VBox timerContentBox;
    
    // --- Data Models ---
    private ObservableList<Task> tasks; // List data tugas
    private TimerModel timerModel;      // Logika timer
    private Timeline timeline;          // Loop animasi timer

    private final Image IMAGE_NOT_STARTED = new Image(getClass().getResourceAsStream("/com/pomodoro/images/CatNotStart.png"));
    private final Image IMAGE_STARTED = new Image(getClass().getResourceAsStream("/com/pomodoro/images/catStart.png"));
    private final Image IMAGE_PAUSED = new Image(getClass().getResourceAsStream("/com/pomodoro/images/DogPause.png"));
    private final Image IMAGE_FINISHED = new Image(getClass().getResourceAsStream("/com/pomodoro/images/DogFinish.png"));

    @FXML
    public void initialize() {
        // 1. Setup Todo List
        tasks = FXCollections.observableArrayList();
        taskListView.setItems(tasks);
        
        // Listener: Kalau user klik salah satu task di list
        taskListView.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    lblCurrentTask.setText(newSelection.getTitle());
                } else {
                    lblCurrentTask.setText("Tidak ada tugas dipilih");
                }
            }
        );

        // 2. Setup Timer (Sama seperti sebelumnya)
        timerModel = new TimerModel();
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timerModel.decrementTime();
            updateTimerDisplay();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        
        updateTimerDisplay();
        backgroundImageView.setImage(IMAGE_NOT_STARTED);
    }

    // --- LOGIC TO-DO LIST ---

    @FXML
    private void handleAddTask() {
        String title = inputTask.getText();
        if (title != null && !title.trim().isEmpty()) {
            tasks.add(new Task(title));
            inputTask.clear();
        }
    }

    @FXML
    private void handleDeleteTask() {
        Task selected = taskListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            tasks.remove(selected);
            lblCurrentTask.setText("Pilih tugas...");
        }
    }

    // --- LOGIC TIMER ---

    @FXML
    private void handleStart() {
        if (!timerModel.isRunning()) {
            timerModel.setRunning(true);
            timeline.play();

            backgroundImageView.setImage(IMAGE_STARTED);
        }
    }

    @FXML
    private void handlePause() {
        if (timerModel.isRunning()) {
            timerModel.setRunning(false);
            timeline.pause();

            backgroundImageView.setImage(IMAGE_PAUSED);
        }
    }

    @FXML
    private void handleReset() {
        timerModel.setRunning(false);
        timeline.stop();
        timerModel.reset();
        updateTimerDisplay();

        backgroundImageView.setImage(IMAGE_NOT_STARTED);
    }

    private void updateTimerDisplay() {
        int time = timerModel.getTimeSeconds();
        int minutes = time / 60;
        int seconds = time % 60;
        timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
        
        if (time == 0) {
            timerLabel.setText("DONE!");
            // Disini bisa tambah logika: tandai task selesai otomatis
            backgroundImageView.setImage(IMAGE_FINISHED);
        }
    }
}