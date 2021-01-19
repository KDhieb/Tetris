package models.shapes;


import models.Block;
import models.Game;
import models.Moves;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public abstract class Shape implements Iterable<Block> {
    protected List<Block> blocks;
    String color;
    Direction direction;
    Block b1;
    Block b2;
    Block b3;
    Block b4;

    public Shape() {
        blocks = new ArrayList<>();
        direction = Direction.DEFAULT;
    }

    public void initializeBlocks(int row1, int col1, int row2, int col2,
                                 int row3, int col3, int row4, int col4) {
        this.b1 = new Block(col1,row1, color);
        this.b2 = new Block(col2,row2, color);
        this.b3 = new Block(col3,row3, color);
        this.b4 = new Block(col4,row4, color);
        blocks.addAll(Arrays.asList(b1, b2, b3, b4));
    }

    public abstract void rotate(List<String> filledSquares);

    // MODIFIES: this
    // EFFECTS: checks if each block can rotate safely, if so rotates them appropriately
    // Numbers correspond to blocks (row1 and col1 represent row and column of b1)
    protected void rotateInnerBlocks(List<String> filledSquares,
                                     int row1, int col1, int row2, int col2,
                                     int row3, int col3, int row4, int col4) {
        Boolean canMoveB1 =  b1.canMove(filledSquares, row1, col1);
        Boolean canMoveB2 = b2.canMove(filledSquares, row2, col2);
        Boolean canMoveB3 = b3.canMove(filledSquares, row3, col3);
        Boolean canMoveB4 = b4.canMove(filledSquares, row4, col4);

        if (canMoveB1 && canMoveB2 && canMoveB3 && canMoveB4) {
            b1.move(row1, col1);
            b2.move(row2, col2);
            b3.move(row3, col3);
            b4.move(row4, col4);
        }
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public Boolean move(List<Block> blockList, Moves move) {
        switch (move) {
            case DOWN:
                if (canDescend(blockList)) {
                    for (Block block: blocks) {
                        block.moveDown();
                    }
                    return true;
                } else {
                    return false;
                }
            case LEFT:
                if (canMoveLeft(blockList)) {
                    for (Block block: blocks) {
                        block.moveLeft();
                    }
                    return true;
                } else {
                    return false;
                }
            case RIGHT:
                if (canMoveRight(blockList)) {
                    for (Block block: blocks) {
                        block.moveRight();
                    }
                    return true;
                } else {
                    return false;
                }
            default:
                throw new RuntimeException();
        }
    }

    public Boolean canDescend(List<Block> blockList) {
        if (reachesBoundary(1, 0)) {
            return false;
        }
        for (Block block: blockList) {
            if (intersects(block, 1, 0)) {
                return false;
            }
        } return true;
    }

    private Boolean canMoveLeft(List<Block> blockList) {
        if (reachesBoundary(0, -1)) {
            return false;
        }
        for (Block block: blockList) {
            if (intersects(block, 0, -1)) {
                return false;
            }
        } return true;
    }

    private Boolean canMoveRight(List<Block> blockList) {
        if (reachesBoundary(0, 1)) {
            return false;
        }
        for (Block block: blockList) {
            if (intersects(block, 0, 1)) {
                return false;
            }
        } return true;
    }



    private Boolean intersects(Block checkBlock, int rowMove, int columnMove) {
        int checkRow = checkBlock.getRow();
        int checkColumn = checkBlock.getColumn();

        for (Block block : blocks) {
            int nextRow = block.getRow() + rowMove;
            int nextCol = block.getColumn() + columnMove;

            if (nextRow == checkRow && nextCol == checkColumn) {
                return true;
            }
        }
        return false;
    }

    private Boolean reachesBoundary(int rowMove, int columnMove) {
        int bottomRow = Game.getBoardRows();
        int outerColumn = Game.getBoardCols();

        for (Block block: blocks) {
            int nextRow = block.getRow() + rowMove;
            int nextCol = block.getColumn() + columnMove;
            if (nextRow == bottomRow || nextCol == outerColumn || nextCol < 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<Block> iterator() {
        return blocks.iterator();
    }
}
