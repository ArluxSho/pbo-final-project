package com.pomodoro.model;

import javafx.beans.property.*;

public class Task {
    private final StringProperty title;
    private final BooleanProperty isCompleted;

    public Task(String title) {
        this.title = new SimpleStringProperty(title);
        this.isCompleted = new SimpleBooleanProperty(false);
    }

    // Getters for properties (Wajib untuk JavaFX Binding)
    public StringProperty titleProperty() { return title; }
    public BooleanProperty isCompletedProperty() { return isCompleted; }

    // Standard getters
    public String getTitle() { return title.get(); }
    public boolean isCompleted() { return isCompleted.get(); }
    
    // Agar saat masuk ListView yang muncul namanya, bukan alamat memori
    @Override
    public String toString() {
        return getTitle(); 
    }
}