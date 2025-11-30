package com.pomodoro.model;

import javafx.beans.property.*;

public class Task {
    private final StringProperty title;
    private final BooleanProperty isCompleted;

    public Task(String title) {
        this.title = new SimpleStringProperty(title);
        this.isCompleted = new SimpleBooleanProperty(false);
    }

    public StringProperty titleProperty() { return title; }
    public BooleanProperty isCompletedProperty() { return isCompleted; }

    public String getTitle() { return title.get(); }
    public boolean isCompleted() { return isCompleted.get(); }
    
    @Override
    public String toString() {
        return getTitle(); 
    }

    //Supaya bisa ganti nama tugas kalau mau edit
    public void setTitle(String title) { this.title.set(title); }
    
    //Supaya checkbox bisa mengubah status true/false
    public void setCompleted(boolean completed) { this.isCompleted.set(completed); }
}