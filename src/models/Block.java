package models;

import java.util.List;
import java.util.regex.Pattern;

// Represents a single tetris block
public class Block {
    private final String color;
    private Integer column;
    private Integer row;
    private String position; // Format: row#,col#


    public Block(int column, int row, String color) {
        this.column = column;
        this.row = row;
        this.color = color;
        updatePosition();
    }


    // EFFECTS: moves block and updates position
    public void move(int rowUnits, int columnUnits) {
        this.row += rowUnits;
        this.column += columnUnits;
        updatePosition();
    }

    public void moveDown() {
        move(1, 0);
    }

    public void moveLeft() {
        move(0, -1);
    }

    public void moveRight() {
        move(0, 1);
    }

    // EFFECTS: Checks if block will move out of bounds
    // Then checks if block will intersect with another block
    public Boolean canMove(List<String> filledSquares, int rowUnits, int columnUnits) {
        String regexPattern = (Game.BOARD_ROWS) + ",\\d+|\\d+," + (Game.BOARD_COLS) + "|\\d+,-1";
        String newPosition = String.format("%d,%d", row + rowUnits, column + columnUnits);
        if (Pattern.matches(regexPattern,newPosition)){
            return false;
        }
        return !filledSquares.contains(String.format("%d,%d", row + rowUnits, column + columnUnits));
    }

    // Updates string position
    public void updatePosition() {
        this.position = String.format("%d,%d", row, column);
    }

    public String getColor() {
        return color;
    }


    public String getPosition() {
        return position;
    }

    public Integer getColumn() {
        return column;
    }

    public Integer getRow() {
        return row;
    }
}
