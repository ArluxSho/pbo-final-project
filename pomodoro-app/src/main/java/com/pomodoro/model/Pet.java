package com.pomodoro.model;

import javafx.beans.property.*;

public class Pet {
    private IntegerProperty happiness = new SimpleIntegerProperty(0); 
    private IntegerProperty coins = new SimpleIntegerProperty(0);

    public IntegerProperty happinessProperty() { return happiness; }
    public IntegerProperty coinsProperty() { return coins; }

    public void earnReward(int amount) {
        coins.set(coins.get() + amount);
    }

    public boolean play(int cost, int happinessGain) {
        if (coins.get() >= cost) {
            coins.set(coins.get() - cost); 
            increaseHappiness(happinessGain);
            return true; 
        }
        return false; 
    }

    public boolean increaseHappiness(int amount) {
        int current = happiness.get() + amount;
        
        if (current >= 100) {
            //Kalau penuh reset ke 20
            happiness.set(20); 
            coins.set(coins.get() + 100); //Bonus Prestige
            return true; 
        } else {
            happiness.set(current);
            return false;
        }
    }

    public void decreaseHappiness() {
        if (happiness.get() > 0) happiness.set(happiness.get() - 10);
    }
}