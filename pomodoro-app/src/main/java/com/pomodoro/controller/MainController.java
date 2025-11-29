// package com.pomodoro.controller;

// import com.pomodoro.model.Pet;
// import com.pomodoro.model.Task;
// import com.pomodoro.model.TimerModel;
// import javafx.animation.KeyFrame;
// import javafx.animation.PauseTransition;
// import javafx.animation.Timeline;
// import javafx.collections.FXCollections;
// import javafx.collections.ObservableList;
// import javafx.fxml.FXML;
// import javafx.scene.control.*;
// import javafx.scene.image.Image;
// import javafx.scene.image.ImageView;
// import javafx.scene.layout.VBox;
// import javafx.util.Duration;

// import java.util.Objects;

// public class MainController {

//     @FXML private TextField inputTask;
//     @FXML private ListView<Task> taskListView;
//     @FXML private Label timerLabel;
//     @FXML private Label lblCurrentTask;
//     @FXML private ImageView backgroundImageView;
//     @FXML private VBox timerContentBox;
//     @FXML private ComboBox<String> durationCombo; 
//     @FXML private Label coinLabel;                
//     @FXML private ProgressBar happinessBar;       

//     private ObservableList<Task> tasks; 
//     private TimerModel timerModel;      
//     private Pet pet; 
//     private Timeline timeline;          

//     private final Image IMAGE_NOT_STARTED = new Image(getClass().getResourceAsStream("/com/pomodoro/images/CatNotStart.png"));
//     private final Image IMAGE_STARTED = new Image(getClass().getResourceAsStream("/com/pomodoro/images/catStart.png"));
//     private final Image IMAGE_PAUSED = new Image(getClass().getResourceAsStream("/com/pomodoro/images/DogPause.png"));
//     private final Image IMAGE_FINISHED = new Image(getClass().getResourceAsStream("/com/pomodoro/images/DogFinish.png"));

//     @FXML
//     public void initialize() {
//         timerModel = new TimerModel();
//         pet = new Pet();
//         tasks = FXCollections.observableArrayList();
        
//         if (coinLabel != null) coinLabel.textProperty().bind(pet.coinsProperty().asString());
        
//         if (happinessBar != null) {
//             // FIX: Set tampilan awal bar sesuai data Pet (yaitu 0)
//             happinessBar.setProgress(pet.happinessProperty().get() / 100.0);
            
//             // Listener perubahan
//             pet.happinessProperty().addListener((obs, oldVal, newVal) -> {
//                 happinessBar.setProgress(newVal.doubleValue() / 100.0);
//             });
//         }

//         setupDurationSelector();
//         setupTodoList();
        
//         try {
//             backgroundImageView.setImage(IMAGE_NOT_STARTED);
//         } catch (Exception e) {
//             System.out.println("Gagal load gambar: " + e.getMessage());
//         }
//         updateTimerDisplay();
//     }

//     private void setupDurationSelector() {
//         if (durationCombo != null) {
//             durationCombo.getItems().addAll("10 Detik (Test)", "25 Menit (Fokus)", "50 Menit (Panjang)");
//             durationCombo.getSelectionModel().select(1);

//             durationCombo.setOnAction(e -> {
//                 if (!timerModel.isRunning()) {
//                     setTimerFromSelection();
//                 }
//             });
//         }
//     }
    
//     private void setTimerFromSelection() {
//         String selected = durationCombo.getValue();
//         int seconds = 25 * 60;
//         if (selected != null) {
//             if (selected.contains("10 Detik")) seconds = 10;
//             else if (selected.contains("50 Menit")) seconds = 50 * 60;
//         }
//         timerModel.setStartTime(seconds);
//         updateTimerDisplay();
//         backgroundImageView.setImage(IMAGE_NOT_STARTED);
//     }

//     private void setupTodoList() {
//         taskListView.setItems(tasks);

//         taskListView.getSelectionModel().selectedItemProperty().addListener(
//             (obs, oldSelection, newSelection) -> {
//                 if (newSelection != null) {
//                     lblCurrentTask.setText("Fokus: " + newSelection.getTitle());
//                 } else {
//                     lblCurrentTask.setText("Pilih tugas...");
//                 }
//             }
//         );

//         taskListView.setCellFactory(param -> new ListCell<Task>() {
//             private final CheckBox checkBox = new CheckBox();
            
//             @Override
//             protected void updateItem(Task item, boolean empty) {
//                 super.updateItem(item, empty);
                
//                 if (empty || item == null) {
//                     setGraphic(null);
//                     setText(null);
//                 } else {
//                     checkBox.setText(item.getTitle());
//                     checkBox.setSelected(item.isCompleted());
//                     checkBox.setStyle("-fx-text-fill: #2c3e50; -fx-font-size: 14px;"); 
                    
//                     checkBox.setOnAction(e -> {
//                         boolean isChecked = checkBox.isSelected();
//                         item.setCompleted(isChecked);
                        
//                         if (isChecked) {
//                             pet.earnReward(50);
//                             boolean isReset = pet.increaseHappiness(10);
                            
//                             if (isReset) {
//                                 lblCurrentTask.setText("LOVE FULL! Bonus +100 Koin!");
//                                 showAlert("HAPPINESS MAX!", "Selamat! Happiness Penuh & Reset. Bonus 100 Koin!");
//                             } else {
//                                 lblCurrentTask.setText("Selesai! +50 Koin & Happy Naik!");
//                             }
//                             checkStatusAnimation();
//                         }
//                     });
//                     setGraphic(checkBox);
//                 }
//             }
//         });
//     }
    
//     private void checkStatusAnimation() {
//         petImageViewAnim(IMAGE_STARTED); 
//         PauseTransition pause = new PauseTransition(Duration.seconds(1));
//         pause.setOnFinished(e -> {
//             if (!timerModel.isRunning()) petImageViewAnim(IMAGE_NOT_STARTED); 
//         });
//         pause.play();
//     }
    
//     private void petImageViewAnim(Image img) {
//         if (backgroundImageView != null) backgroundImageView.setImage(img);
//     }

//     @FXML
//     private void handleAddTask() {
//         String title = inputTask.getText();
//         if (title != null && !title.trim().isEmpty()) {
//             tasks.add(new Task(title));
//             inputTask.clear();
//         }
//     }

//     @FXML
//     private void handleDeleteTask() {
//         Task selected = taskListView.getSelectionModel().getSelectedItem();
//         if (selected != null) {
//             tasks.remove(selected);
//             lblCurrentTask.setText("Pilih tugas...");
//         }
//     }

//     @FXML
//     private void handleStart() {
//         if (timerModel.getTimeSeconds() <= 0) {
//             showAlert("Timer Selesai", "Waktu sudah habis! Silakan klik Reset dulu.");
//             return; 
//         }

//         if (!timerModel.isRunning()) {
//             if (timeline == null) setupTimeline();
//             timerModel.setRunning(true);
//             timeline.play();
//             backgroundImageView.setImage(IMAGE_STARTED);
//             if (durationCombo != null) durationCombo.setDisable(true);
//         }
//     }

//     @FXML
//     private void handlePause() {
//         if (timerModel.isRunning()) {
//             timerModel.setRunning(false);
//             timeline.pause();
//             backgroundImageView.setImage(IMAGE_PAUSED);
//         }
//     }

//     @FXML
//     private void handleReset() {
//         timerModel.setRunning(false);
//         if (timeline != null) timeline.stop();
//         setTimerFromSelection(); 
//         if (durationCombo != null) durationCombo.setDisable(false);
//     }

//     private void setupTimeline() {
//         timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
//             timerModel.decrementTime();
//             updateTimerDisplay();
//         }));
//         timeline.setCycleCount(Timeline.INDEFINITE);
//     }

//     private void updateTimerDisplay() {
//         int time = timerModel.getTimeSeconds();
//         int minutes = time / 60;
//         int seconds = time % 60;
//         timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
        
//         if (time <= 0 && timerModel.isRunning()) {
//             finishSession();
//         }
//     }

//     private void finishSession() {
//         timerModel.setRunning(false);
//         timeline.stop();
//         timerLabel.setText("DONE!"); 
//         backgroundImageView.setImage(IMAGE_FINISHED);
        
//         if (durationCombo != null) durationCombo.setDisable(false);

//         int reward = 50; 
//         if (durationCombo != null) {
//             String selected = durationCombo.getValue();
//             if (selected != null && selected.contains("50 Menit")) reward = 100;
//             if (selected != null && selected.contains("10 Detik")) reward = 5;
//         }

//         pet.earnReward(reward);
//         boolean isReset = pet.increaseHappiness(20);
        
//         String msg = "Sesi Selesai! +" + reward + " Koin";
//         if (isReset) {
//              msg += " & BONUS PRESTIGE (+100)!";
//              showAlert("LUAR BIASA!", "Timer Selesai + Happiness Penuh! Kamu dapat bonus besar!");
//         } else {
//              showAlert("Hebat!", "Kamu berhasil fokus! Hadiah: " + reward + " Koin");
//         }
//         lblCurrentTask.setText(msg);
//     }

//     @FXML private void handleFeed() { 
//         boolean isReset = pet.play(20, 10); 
//         if (isReset) lblCurrentTask.setText("Nyam.. LOVE FULL! +100 Koin!");
//         else lblCurrentTask.setText("Nyam nyam... Enak!");
//     }

//     @FXML private void handlePlay() { 
//         boolean isReset = pet.play(50, 25); 
//         if (isReset) lblCurrentTask.setText("Seru! LOVE FULL! +100 Koin!");
//         else lblCurrentTask.setText("Main bola seru banget!");
//     }

//     private void showAlert(String title, String content) {
//         Alert alert = new Alert(Alert.AlertType.INFORMATION);
//         alert.setTitle(title);
//         alert.setHeaderText(null);
//         alert.setContentText(content);
//         alert.showAndWait();
//     }
// }

package com.pomodoro.controller;

import com.pomodoro.Main;
import com.pomodoro.model.Pet;
import com.pomodoro.model.Task;
import com.pomodoro.model.TimerModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import java.io.IOException;

public class MainController {

    // --- DATA YANG DISIMPAN (STATIC) ---
    private static ObservableList<Task> sharedTasks = FXCollections.observableArrayList();
    private static Pet sharedPet = new Pet();
    private static int selectedDuration = 25 * 60; // Default 25 menit
    private static Task currentFocusTask = null;   // Tugas yang dipilih user

    // --- KOMPONEN PAGE TASK ---
    @FXML private TextField inputTask;
    @FXML private ListView<Task> taskListView;
    @FXML private ComboBox<String> durationSelector;

    // --- KOMPONEN PAGE TIMER ---
    @FXML private Label timerLabel;
    @FXML private Label lblCurrentTask;
    @FXML private ImageView backgroundImageView;
    @FXML private Label coinLabel;
    @FXML private ProgressBar happinessBar;

    // --- LOGIC ---
    private TimerModel timerModel;
    private Timeline timeline;

    // --- IMAGES ---
    private final Image IMAGE_NOT_STARTED = new Image(getClass().getResourceAsStream("/com/pomodoro/images/CatNotStart.png"));
    private final Image IMAGE_STARTED = new Image(getClass().getResourceAsStream("/com/pomodoro/images/catStart.png"));
    private final Image IMAGE_PAUSED = new Image(getClass().getResourceAsStream("/com/pomodoro/images/DogPause.png"));
    private final Image IMAGE_FINISHED = new Image(getClass().getResourceAsStream("/com/pomodoro/images/DogFinish.png"));

    @FXML
    public void initialize() {
        // --- LOGIC HALAMAN TASK ---
        if (taskListView != null) {
            taskListView.setItems(sharedTasks);
            setupTodoList();
            
            if (durationSelector != null) {
                durationSelector.getItems().addAll("10 Detik (Test)", "25 Menit (Fokus)", "50 Menit (Panjang)");
                durationSelector.getSelectionModel().select(1);
                durationSelector.setOnAction(e -> {
                    String val = durationSelector.getValue();
                    if (val.contains("10 Detik")) selectedDuration = 10;
                    else if (val.contains("50 Menit")) selectedDuration = 50 * 60;
                    else selectedDuration = 25 * 60;
                });
            }
        }

        // --- LOGIC HALAMAN TIMER ---
        if (timerLabel != null) {
            timerModel = new TimerModel();
            timerModel.setStartTime(selectedDuration);
            updateTimerDisplay();
            
            // Set judul tugas
            if (currentFocusTask != null) lblCurrentTask.setText(currentFocusTask.getTitle());
            else lblCurrentTask.setText("Free Focus");

            // Setup Pet Data
            coinLabel.textProperty().bind(sharedPet.coinsProperty().asString());
            happinessBar.setProgress(sharedPet.happinessProperty().get() / 100.0);
            sharedPet.happinessProperty().addListener((o, old, newVal) -> 
                happinessBar.setProgress(newVal.doubleValue() / 100.0));

            backgroundImageView.setImage(IMAGE_NOT_STARTED);
        }
    }

    // --- NAVIGASI ---
    
    @FXML private void goToTaskPage() throws IOException {
        Main.showTaskScreen();
    }

    @FXML private void goToTimerPage() throws IOException {
        // Simpan tugas yang dipilih sebelum pindah
        currentFocusTask = taskListView.getSelectionModel().getSelectedItem();
        Main.showTimerScreen();
    }
    
    @FXML private void backToTasks() throws IOException {
        if (timeline != null) timeline.stop(); // Stop timer kalau user balik
        Main.showTaskScreen();
    }

    // --- TIMER ACTIONS ---

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

    // --- HELPER METHODS ---

    private void setupTimeline() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timerModel.decrementTime();
            updateTimerDisplay();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
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
        timerLabel.setText("DONE");
        backgroundImageView.setImage(IMAGE_FINISHED);
        
        // Reward
        int reward = (selectedDuration > 60) ? 50 : 5;
        sharedPet.earnReward(reward);
        boolean reset = sharedPet.increaseHappiness(20);
        
        String msg = "DONE! +" + reward + " Coins";
        if (reset) msg += " (BONUS!)";
        lblCurrentTask.setText(msg);
    }

    // --- TASK ACTIONS ---
    @FXML private void handleAddTask() {
        if (inputTask.getText() != null && !inputTask.getText().isEmpty()) {
            sharedTasks.add(new Task(inputTask.getText()));
            inputTask.clear();
        }
    }
    
    @FXML private void handleDeleteTask() {
        Task t = taskListView.getSelectionModel().getSelectedItem();
        if (t != null) sharedTasks.remove(t);
    }

    @FXML private void handleFeed() { if(sharedPet.play(20, 10)) lblCurrentTask.setText("Nyam Enak!"); }
    @FXML private void handlePlay() { if(sharedPet.play(50, 25)) lblCurrentTask.setText("Seru Banget!"); }

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
                    checkBox.setOnAction(e -> {
                        item.setCompleted(checkBox.isSelected());
                        if (checkBox.isSelected()) {
                            sharedPet.earnReward(50);
                            sharedPet.increaseHappiness(10);
                        }
                    });
                    setGraphic(checkBox);
                }
            }
        });
    }
}