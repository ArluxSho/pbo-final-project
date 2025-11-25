package com.pomodoro.model;

import javafx.beans.property.*;

public class TimerModel {
    // Default 25 menit (dalam detik)
    private final int START_TIME = 1 * 60; 
    
    // Property agar bisa diobservasi oleh Controller/View
    private IntegerProperty timeSeconds = new SimpleIntegerProperty(START_TIME);
    private BooleanProperty isRunning = new SimpleBooleanProperty(false);

    public int getStartTime() { return START_TIME; }

    // Getter untuk Property (untuk binding)
    public IntegerProperty timeSecondsProperty() { return timeSeconds; }
    public BooleanProperty isRunningProperty() { return isRunning; }

    // Getter & Setter biasa
    public int getTimeSeconds() { return timeSeconds.get(); }
    public void setTimeSeconds(int seconds) { this.timeSeconds.set(seconds); }

    public boolean isRunning() { return isRunning.get(); }
    public void setRunning(boolean running) { this.isRunning.set(running); }
    
    public void reset() {
        setTimeSeconds(START_TIME);
        setRunning(false);
    }
    
    public void decrementTime() {
        if (getTimeSeconds() > 0) {
            setTimeSeconds(getTimeSeconds() - 1);
        } else {
            setRunning(false); // Waktu habis
        }
    }
}