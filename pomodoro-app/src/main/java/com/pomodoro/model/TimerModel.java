package com.pomodoro.model;

import javafx.beans.property.*;

public class TimerModel {
    // Default awal (bisa diubah nanti)
    private int currentStartTime = 25 * 60; 
    
    private IntegerProperty timeSeconds = new SimpleIntegerProperty(currentStartTime);
    private BooleanProperty isRunning = new SimpleBooleanProperty(false);

    public IntegerProperty timeSecondsProperty() { return timeSeconds; }
    public BooleanProperty isRunningProperty() { return isRunning; }

    public int getTimeSeconds() { return timeSeconds.get(); }
    public boolean isRunning() { return isRunning.get(); }

    public void setRunning(boolean running) { this.isRunning.set(running); }

    // --- TAMBAHAN BARU: FITUR CHOOSE TIME ---
    public void setStartTime(int seconds) {
        this.currentStartTime = seconds;
        this.timeSeconds.set(seconds); // Update waktu saat ini juga
    }

    public void decrementTime() {
        if (timeSeconds.get() > 0) {
            timeSeconds.set(timeSeconds.get() - 1);
        }
    }

    public void reset() {
        timeSeconds.set(currentStartTime); // Reset kembali ke waktu yang dipilih user
        setRunning(false);
    }

    public enum SessionMode {
        WORK, BREAK
    }
}