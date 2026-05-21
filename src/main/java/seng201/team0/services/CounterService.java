package seng201.team0.services;

public class CounterService {

    private int currentCount = 0;

    public int getCurrentCount() {
        return currentCount;
    }

    public void incrementCounter() {
        currentCount++;
    }
}
