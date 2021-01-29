package ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import models.Block;
import models.Game;
import javafx.scene.layout.BorderPane;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

// Represents graphical user interface of game
public class Gui extends Application {
    private Game game;
    private ScoreTracker scoreTracker;
    private HashMap<String, Label> cells;
    private GridPane gridPane;
    private Scene mainScene;
    private BorderPane mainBorder;
    private Label scoreLabel;
    private Label messageLabel;
    private Thread gameThread;
    private final String BLANK_CELL_COLOR = "black";
    private final Color CELL_BORDER_COLOR = Color.DARKGRAY;

    public static void main(String[] args) {
        launch(args);
    }

    // EFFECTS: initializes all JavaFx UI elements and game thread
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Tetris");
        mainBorder = new BorderPane();
        mainScene = new Scene(mainBorder, 500, 502);

        initializeGameBoard();
        initializeRightPanel();

        primaryStage.setScene(mainScene);
        primaryStage.show();

        setQuitOnWindowClose(primaryStage);

        initializeGame();
    }

    // EFFECTS: initializes game instance and begins game thread
    private void initializeGame() {
        game = new Game();
        scoreTracker = new ScoreTracker();
        game.addObserver(scoreTracker);
        startGameThread();
    }

    // EFFECTS: initializes game board
    private void initializeGameBoard() {
        gridPane = new GridPane();
        gridPane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,BorderWidths.DEFAULT)));
        mainBorder.setLeft(gridPane);
        createCells(Game.getBoardCols(), Game.getBoardRows());

    }

    // EFFECTS: begins game thread
    private void startGameThread() {
        Task < Void > task = new Task<Void>() {
            @Override
            protected Void call() {
                runGame();
                return null;
            }
        };
        gameThread = new Thread(task);
        gameThread.start();
    }

    // EFFECTS: stops applications when window is closed
    private void setQuitOnWindowClose(Stage primaryStage) {
        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
    }

    // EFFECTS: initializes right side button and label panel
    private void initializeRightPanel() {
        BorderPane rightPane = new BorderPane();
        mainBorder.setRight(rightPane);
        initializeButtons(rightPane);
        initializeLabels(rightPane);
    }

    // EFFECTS: creates and add buttons to layout
    private void initializeButtons(BorderPane rightPane) {
        Button resetBtn = new Button();
        resetBtn.setText("Reset Game");
        resetBtn.setOnAction(event -> restartGame());
        resetBtn.setFocusTraversable(false);
        rightPane.setBottom(resetBtn);
        rightPane.setPadding(new Insets(10,30,10,0));

    }

    // EFFECTS: creates and add initial labels to layout
    private void initializeLabels(BorderPane rightPane) {
        scoreLabel = new Label("SCORE: 0");
        scoreLabel.setFont(new Font(25));
        scoreLabel.setMinSize(150, 20);

        messageLabel = new Label("");
        messageLabel.setFont(new Font(30));
        messageLabel.setMinSize(150, 20);

        rightPane.setTop(scoreLabel);
        rightPane.setCenter(messageLabel);
    }

    // EFFECTS: Creates all game cells and adds to layout
    private void createCells(int columns, int rows) {
        cells = new HashMap<>();
        for (int col=0; col < columns; col++ ) {
            for (int row=0; row < rows; row++ ) {
                Label label = new Label(" ");
                label.setMinSize(25, 25);
                colorCell(label, BLANK_CELL_COLOR);
                cells.put(getCellKey(col, row), label);
                gridPane.add(label, col, row);
            }
        }
    }

    // EFFECTS: color cell based on given coordinates and color
    private void colorCell(int col, int row, String color) {
        Label label = cells.get(getCellKey(col, row));
        label.setBackground(new Background(new BackgroundFill(Paint.valueOf(color),
                CornerRadii.EMPTY, Insets.EMPTY)));
    }

    // EFFECTS: color cells based on given label and color
    private void colorCell(Label label, String color) {
        label.setBackground(new Background(new BackgroundFill(Paint.valueOf(color),
                CornerRadii.EMPTY, Insets.EMPTY)));
    }

    // EFFECTS: gets unique cell key given coordinates
    // Helper method to find appropriate cell labels
    private String getCellKey(int col, int row) {
        return String.format("%s%s", col, row);
    }


    // EFFECTS: handles game states and updates UI
    public void runGame() {
        initializeEventHandlers();
        while (!game.lostGame) {
            try {
                game.descend();
                update(game);
                updateScore();
                TimeUnit.MILLISECONDS.sleep(Game.getSpeed());
                game.checkAndClearRows();
                update(game);
                TimeUnit.MILLISECONDS.sleep(Game.getSpeed());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        updateMessageLabel("GAME OVER!");
    }

    // EFFECTS: updates all cells based on current game state
    private void update(Game game) {
        for (Label label: cells.values()) {
            colorCell(label, BLANK_CELL_COLOR);
            makeCellBorderVisible(false, label);
        }
        List<Block> blocks =  game.getBlocks();
        for (Block block: blocks) {
            colorCell(block.getColumn(), block.getRow(), block.getColor());
            Label cellLabel = getCellLabel(block);
            makeCellBorderVisible(true, cellLabel);
        }

        for (Block block: game.getShapeInPlay()) {
            getCellKey(block.getColumn(), block.getRow());
            colorCell(block.getColumn(), block.getRow(), block.getColor());
            Label cellLabel = getCellLabel(block);
            makeCellBorderVisible(true, cellLabel);

        }

    }

    public Label getCellLabel(Block block) {
        return cells.get(getCellKey(block.getColumn(), block.getRow()));
    }

    public void makeCellBorderVisible(Boolean visible, Label label) {
        if (visible) {
            label.setBorder(new Border(new BorderStroke(CELL_BORDER_COLOR, BorderStrokeStyle.SOLID,
                    CornerRadii.EMPTY,BorderWidths.DEFAULT)));
        } else {
            label.setBorder(new Border(new BorderStroke(CELL_BORDER_COLOR, BorderStrokeStyle.NONE,
                    CornerRadii.EMPTY,BorderWidths.DEFAULT)));
        }
    }

    // EFFECTS: updates score with current score
    private void updateScore() {
        Platform.runLater(() -> scoreLabel.setText("SCORE: " + scoreTracker.getScore()));
    }

    // EFFECTS: updates message status
    private void updateMessageLabel(String msg){
        Platform.runLater(() -> messageLabel.setText(msg));
    }

    // EFFECTS: resets game to initial state
    public void restartGame() {
        game.resetGame();
        updateScore();
        updateMessageLabel("");
        gameThread.stop();
        startGameThread();
        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            System.out.println("Interrupted Exception thrown!");
        }
    }

    // EFFECTS: handles key events
    public void initializeEventHandlers() {
        mainScene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT: game.moveLeft(); break;
                case RIGHT: game.moveRight(); break;
                case SPACE: game.rotateShape(); break;
            }
        });
    }
}
