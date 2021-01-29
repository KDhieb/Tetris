package ui;

import java.util.Observable;
import java.util.Observer;

public class ScoreTracker implements Observer {
    private final Integer POINTS_PER_ROW = 100;
    private final Integer MULTIPLIER = 50;
    private Integer score = 0;

    @Override
    public void update(Observable o, Object arg) {
        if (arg == "Reset") {
            score = 0;
        } else {
            int rows = (int) arg;
            score += rows * POINTS_PER_ROW + rowMultiplier(rows);
        }
    }

    public Integer rowMultiplier(int rows) {
        return (rows - 1) * MULTIPLIER;
    }

    public Integer getScore() {
        return score;
    }

}
