package ui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.Block;
import models.Game;
import javafx.scene.layout.BorderPane;

import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Gui extends Application {
    Game game;
    ScoreTracker scoreTracker;
    HashMap<String, Label> cells;
    StackPane root;
    GridPane gridPane;
    Scene gameScene;
    BorderPane mainBorder;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Tetris");
        this.root = new StackPane();
        game = new Game();
        game.addObserver(new ScoreTracker());
        gridPane = new GridPane();
        mainBorder = new BorderPane();
        mainBorder.setLeft(gridPane);
        createCells(Game.getBoardCols(), Game.getBoardRows());
        initializeRightPanel();
//        gameScene = new Scene(gridPane, 500, 500);
        gameScene = new Scene(mainBorder, 500, 500);

        primaryStage.setScene(gameScene);
        primaryStage.show();
//        mainBorder.setLeft(gridPane);

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                runGame();
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }

    private void createCells(int columns, int rows) {
        cells = new HashMap<>();
        for (int col=0; col < columns; col++ ) {
            for (int row=0; row < rows; row++ ) {
                Label label = new Label(" ");
                label.setMinSize(25, 25);
                label.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID,
                        CornerRadii.EMPTY,BorderWidths.DEFAULT)));
                cells.put(getCellKey(col, row), label);
                gridPane.add(label, col, row);
            }
        }
    }

    public void initializeDelay() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("running");
            }
        };
    }

    public void runGame() {
        initializeEventHandlers();
        while (!game.lostGame) {
            try {
                game.descend();
                update(game);
//                Thread.sleep(Game.getSpeed());
                TimeUnit.MILLISECONDS.sleep(100);
                game.checkAndClearRows();
                update(game);
//                Thread.sleep(Game.getSpeed());
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void update(Game game) {
        for (Label label: cells.values()) {
            label.setBackground(new Background(new BackgroundFill(Paint.valueOf("whitesmoke"),
                    CornerRadii.EMPTY, Insets.EMPTY)));
        }

        List<Block> blocks =  game.getBlocks();
        for (Block block: blocks) {
            colorCell(block.getColumn(), block.getRow(), block.getColor());
        }

        for (Block block: game.getShapeInPlay()) {
            colorCell(block.getColumn(), block.getRow(), block.getColor());
        }
    }

    private void initializeRightPanel() {
        initializeButtons();
        initializeLabels();
    }

    public void restartGame() {
        game.resetGame();
        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            System.out.println("Interrupted Exception thrown!");
        }
    }



    private void initializeButtons() {
        Button resetBtn = new Button();
        resetBtn.setText("Reset Game");
        resetBtn.setOnAction(event -> restartGame());
        resetBtn.setFocusTraversable(false);
        mainBorder.setRight(resetBtn);
    }

    private void initializeLabels() {
    }


    private void colorCell(int col, int row, String color) {
        Label label = cells.get(getCellKey(col, row));
        label.setBackground(new Background(new BackgroundFill(Paint.valueOf(color),
                CornerRadii.EMPTY, Insets.EMPTY)));
    }

    private String getCellKey(int col, int row) {
        return String.format("%s%s", col, row);
    }

    public void initializeEventHandlers() {
        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case LEFT: game.moveLeft(); break;
                    case RIGHT: game.moveRight(); break;
                    case SPACE: game.rotateShape(); break;
                }
            }
        });
    }


}
