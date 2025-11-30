package com.pomodoro.controller;

import com.pomodoro.Main;
import com.pomodoro.model.Pet;
import com.pomodoro.model.Task;
import com.pomodoro.model.TimerModel;
import com.pomodoro.model.TimerModel.SessionMode;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.BufferedWriter;
//import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.List;

public class MainController {

    //data yg disimpan scr statis
    private static ObservableList<Task> sharedTasks = FXCollections.observableArrayList();
    private static Pet sharedPet = new Pet();
    private static int selectedDuration = 25 * 60; //Default 25 menit
    private static Task currentFocusTask = null;   //Tugas yang dipilih user
    private static final Path TASK_FILE = Paths.get("tasks.txt");

    //komponen pada halaman task
    @FXML private TextField inputTask;
    @FXML private ListView<Task> taskListView;
    @FXML private ComboBox<String> durationSelector;

    //komponen pada halaman timer
    @FXML private Label timerLabel;
    @FXML private Label lblCurrentTask;
    @FXML private ImageView backgroundImageView;
    @FXML private Label coinLabel;
    @FXML private ProgressBar happinessBar;
    private String defaultTaskLabel = ""; //default teks label awalnya kosong

    //logic
    private TimerModel timerModel;
    private Timeline timeline;
    private SessionMode mode = SessionMode.WORK;

    //gambar
    private final Image IMAGE_NOT_STARTED = new Image(getClass().getResourceAsStream("/com/pomodoro/images/notStartCat.gif"));
    private final Image IMAGE_STARTED = new Image(getClass().getResourceAsStream("/com/pomodoro/images/studyCat.gif"));
    private final Image IMAGE_PAUSED = new Image(getClass().getResourceAsStream("/com/pomodoro/images/sleepCat.gif"));
    private final Image IMAGE_FINISHED = new Image(getClass().getResourceAsStream("/com/pomodoro/images/finishCat.gif"));
    private final Image IMAGE_FEED = new Image(getClass().getResourceAsStream("/com/pomodoro/images/eatCat.gif"));
    private final Image IMAGE_BALL = new Image(getClass().getResourceAsStream("/com/pomodoro/images/ballCat.gif"));

    //simpan tasks
    private void saveTasks() {
        try (BufferedWriter writer = Files.newBufferedWriter(TASK_FILE)) {
            for (Task t: sharedTasks) {
                writer.write(t.getTitle() + "|" + t.isCompleted());
                writer.newLine();
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadTasks() {
        if (!sharedTasks.isEmpty()) return; //jika sdh ada task, skip aja

        if (Files.exists(TASK_FILE)) {
            try {
                List<String> lines = Files.readAllLines(TASK_FILE);
                for (String line: lines) {
                    if (line.isBlank()) continue;
                    String[] parts = line.split("\\|");
                    String title = parts[0];
                    boolean completed = Boolean.parseBoolean(parts[1]);
                    Task t = new Task(title);
                    t.setCompleted(completed);
                    sharedTasks.add(t);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showTemporaryMessage(String message, int duration) {
        String oldLabel = defaultTaskLabel;
        lblCurrentTask.setText(message);

        PauseTransition pause = new PauseTransition(Duration.seconds(duration));
        pause.setOnFinished(e -> lblCurrentTask.setText(oldLabel));
        pause.play();
    }

    @FXML
    public void initialize() {
        //HALAMAN TASK
        if (taskListView != null) {
            taskListView.setItems(sharedTasks);
            sharedTasks.clear();
            loadTasks();
            setupTodoList();
            
            if (durationSelector != null) {
                durationSelector.getItems().addAll("10 Second (Test)", "25 Minutes (Focus)", "50 Minutes (Long)");
                durationSelector.getSelectionModel().select(1);
                durationSelector.setOnAction(e -> {
                    String val = durationSelector.getValue();
                    if (val.contains("10 Second")) selectedDuration = 10;
                    else if (val.contains("50 Minutes")) selectedDuration = 50 * 60;
                    else selectedDuration = 25 * 60;
                });
            }
        }

        //HALAMAN TIMER
        if (timerLabel != null) {
            timerModel = new TimerModel();
            timerModel.setStartTime(selectedDuration);
            updateTimerDisplay();
            
            //Set judul tugas
            if (currentFocusTask != null) lblCurrentTask.setText(currentFocusTask.getTitle());
            else lblCurrentTask.setText("Free Focus");
            defaultTaskLabel = lblCurrentTask.getText();

            //Setup Pet Data
            coinLabel.textProperty().bind(sharedPet.coinsProperty().asString());
            happinessBar.setProgress(sharedPet.happinessProperty().get() / 100.0);
            sharedPet.happinessProperty().addListener((o, old, newVal) -> 
                happinessBar.setProgress(newVal.doubleValue() / 100.0));

            backgroundImageView.setImage(IMAGE_NOT_STARTED);
        }
    }

    //NAVIGASI
    @FXML private void goToTaskPage() throws IOException {
        Main.showTaskScreen();
    }

    @FXML private void goToTimerPage() throws IOException {
        Task selectedTask = taskListView.getSelectionModel().getSelectedItem();
        //Simpan tugas yang dipilih sebelum pindah
        currentFocusTask = selectedTask;
        Main.showTimerScreen();
    }
    
    @FXML private void backToTasks() throws IOException {
        if (timeline != null) timeline.stop(); //Stop timer kalau user balik
        Main.showTaskScreen();
    }

    //TIMER
    private int getBreakDuration() {
        if (selectedDuration == 25 * 60) {return 5 * 60;}
        if (selectedDuration == 50 * 60) {return 10 * 60;}
        if (selectedDuration == 10) {return 3;}
        return 5 * 60;
    }

    @FXML private void handleStart() {
        if (timerModel.getTimeSeconds() <= 0) return;
        if (!timerModel.isRunning()) {
            if (timeline == null) setupTimeline();
            timerModel.setRunning(true);
            timeline.play();
            backgroundImageView.setImage(IMAGE_STARTED);
        }
    }

    @FXML private void handlePause() {
        if (timerModel.isRunning()) {
            timerModel.setRunning(false);
            timeline.pause();
            backgroundImageView.setImage(IMAGE_PAUSED);
        }
    }

    @FXML private void handleReset() {
        timerModel.setRunning(false);
        if (timeline != null) timeline.stop();
        timerModel.setStartTime(selectedDuration);
        updateTimerDisplay();
        backgroundImageView.setImage(IMAGE_NOT_STARTED);
    }

    //method lain
    private void setupTimeline() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timerModel.decrementTime();
            updateTimerDisplay();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    //untuk break
    private void startBreakTime() {
        mode = SessionMode.BREAK;

        int breakTime = getBreakDuration();
        timerModel.setStartTime(breakTime);
        updateTimerDisplay();

        backgroundImageView.setImage(IMAGE_PAUSED);

        if (timeline == null) {setupTimeline();}
        timerModel.setRunning(true);
        timeline.play();
    }

    private void updateTimerDisplay() {
        int time = timerModel.getTimeSeconds();
        int minutes = time / 60;
        int seconds = time % 60;
        timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
        
        if (time <= 0 && timerModel.isRunning()) finishSession();
    }
    
    private void finishSession() {
        timerModel.setRunning(false);
        timeline.stop();

        if (mode == SessionMode.WORK) {
            timerLabel.setText("DONE");
            backgroundImageView.setImage(IMAGE_FINISHED);

            // Reward
            int reward = (selectedDuration > 60) ? 50 : 5;
            sharedPet.earnReward(reward);
            boolean reset = sharedPet.increaseHappiness(20);
            
            String msg = "DONE! +" + reward + " Coins";
            if (reset) msg += " (BONUS!)";
            lblCurrentTask.setText(msg);

            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(e -> startBreakTime());
            pause.play();
        }
        else {
            lblCurrentTask.setText("Break is over!");
            mode = SessionMode.WORK;
            timerModel.setStartTime(selectedDuration);
            updateTimerDisplay();
            backgroundImageView.setImage(IMAGE_NOT_STARTED);
        }
    }

    //handle halaman task
    @FXML private void handleAddTask() {
        if (inputTask.getText() != null && !inputTask.getText().isEmpty()) {
            sharedTasks.add(new Task(inputTask.getText()));
            inputTask.clear();
        }
        saveTasks();
    }
    
    @FXML private void handleDeleteTask() {
        Task t = taskListView.getSelectionModel().getSelectedItem();
        if (t != null) sharedTasks.remove(t);
        saveTasks();
    }

    private void showPetGif(Image gif) {
        if (backgroundImageView != null) {
            Image backupImage = backgroundImageView.getImage();
            
            backgroundImageView.setImage(gif);

            PauseTransition pause = new PauseTransition(Duration.seconds(15));
            pause.setOnFinished(e -> {
                backgroundImageView.setImage(backupImage);
                if (currentFocusTask != null) lblCurrentTask.setText(currentFocusTask.getTitle());
                else lblCurrentTask.setText("Free Focus");
            });
            pause.play();
        }
    }

    @FXML private void handleFeed() { 
        if (timerModel.isRunning()) {
            showTemporaryMessage("Focus First", 2);
            return;
        }
        if(sharedPet.play(20, 10)) {
            lblCurrentTask.setText("Nom Nom! Yummy!"); 
            showPetGif(IMAGE_FEED);
        }
        else {
            showTemporaryMessage("Oops! Not enough coins", 2);
        }
    }

    @FXML private void handlePlay() { 
        if (timerModel.isRunning()) {
            showTemporaryMessage("Focus First", 2);
            return;
        }
        if(sharedPet.play(50, 25)) {
            lblCurrentTask.setText("Yay! That was fun");
            showPetGif(IMAGE_BALL);
        } 
        else {
            showTemporaryMessage("Oops! Not enough coins", 2);
        }
    }

    private void setupTodoList() {
        taskListView.setCellFactory(param -> new ListCell<Task>() {
            private final CheckBox checkBox = new CheckBox();
            @Override protected void updateItem(Task item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) { setGraphic(null); setText(null); }
                else {
                    checkBox.setText(item.getTitle());
                    checkBox.setSelected(item.isCompleted());
                    checkBox.setStyle("-fx-text-fill: #2c3e50; -fx-font-size: 14px;");
                    checkBox.setOnMouseClicked(e -> {taskListView.getSelectionModel().select(item);}); //tambahan biar tasknya bisa dihapus
                    checkBox.setOnAction(e -> {
                        item.setCompleted(checkBox.isSelected());
                        if (checkBox.isSelected()) {
                            sharedPet.earnReward(50);
                            sharedPet.increaseHappiness(10);
                        }
                        saveTasks();
                    });
                    setGraphic(checkBox);
                }
            }
        });
    }
}