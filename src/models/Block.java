package models;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Block {
    private final String color;
    private Integer column;
    private Integer row;
    private String position;


    public Block(int column, int row, String color) {
        this.column = column;
        this.row = row;
        this.color = color;
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



    public Integer getColumn() {
        return column;
    }

    public Integer getRow() {
        return row;
    }


    public Boolean canMove(List<String> filledSquares, int rowUnits, int columnUnits) {
        String regexPattern = (Game.BOARD_ROWS) + ",\\d+|\\d+," + (Game.BOARD_COLS) + "|-1,\\d+";
        String newPosition = String.format("%d,%d", row + rowUnits, column + columnUnits);
        if (Pattern.matches(regexPattern,newPosition)){
            return false;
        }
        return !filledSquares.contains(String.format("%d,%d", row + rowUnits, column + columnUnits));
    }

    public void move(int rowUnits, int columnUnits) {
        this.row += rowUnits;
        this.column += columnUnits;
        updatePosition();
    }

    public String getColor() {
        return color;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public void updatePosition() {
        this.position = String.format("%d,%d", row, column);
    }

    public String getPosition() {
        return position;
    }
}
