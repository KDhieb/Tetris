package ui;

import java.util.Observable;
import java.util.Observer;

public class ScoreTracker implements Observer {
    private final Integer POINTS_PER_ROW = 200;
    Integer score = 0;

    @Override
    public void update(Observable o, Object arg) {
        int rows = (int) arg;
        score += rows * POINTS_PER_ROW;
        System.out.println("SCORE: " + score);
    }
}
