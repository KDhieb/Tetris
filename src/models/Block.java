package models;

public class Block {
    private Integer column;
    private Integer row;
    private String color;

    public Block(int column, int row, String color) {
        this.column = column;
        this.row = row;
        this.color = color;
    }

    public void moveDown() {
        this.row += 1;
    }

    public void moveLeft() {
        this.column -= 1;
    }

    public void moveRight() {
        this.column += 1;
    }

    public Integer getColumn() {
        return column;
    }

    public Integer getRow() {
        return row;
    }

    public String getColor() {
        return color;
    }
}
