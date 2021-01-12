package ui;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import models.Block;
import models.Game;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;

public class Gui extends Application {
    Game game;
    HashMap<String, Label> cells;
    StackPane root;
    GridPane gridPane;
    Scene scene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Tetris");
        this.root = new StackPane();
        game = new Game();
        gridPane = new GridPane();

        createCells(Game.getBoardCols(), Game.getBoardRows());
        initializeButtons();
        scene = new Scene(gridPane, 500, 500);
//        primaryStage.setScene(new Scene(gridPane, 500, 500));
        primaryStage.setScene(scene);
        primaryStage.show();
        Task task = new Task<Void>() {
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

    public void runGame() {
        initializeEventHandlers();
        while (!game.lostGame) {
            try {
                game.descend();
                update(game);
                Thread.sleep(Game.getSpeed());
//                game.clearFilledRows();
                update(game);
//                if (!canDescend) {
//                    System.out.println("Cant descend");
//                }
                Thread.sleep(Game.getSpeed());
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

    private void initializeButtons() {
        Button btn = new Button();
        btn.setText("Start Game");
        btn.setOnAction(event -> System.out.println("Game Starting"));
        root.getChildren().add(btn);
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
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case LEFT:
                        game.moveLeft();
                         break;
                    case RIGHT: game.moveRight(); break;
                }
            }
        });
    }


}
