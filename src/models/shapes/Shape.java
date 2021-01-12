package models.shapes;


import models.Block;
import models.Game;
import models.Moves;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.awt.Graphics;

public abstract class Shape implements Iterable<Block> {
    protected List<Block> blocks;
    String color;

    public Shape() {
        blocks = new ArrayList<>();
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public abstract void rotateLeft();

    public abstract void rotateRight();

//    public void descend() {
//        for (Block block: blocks) {
//            block.moveDown();
//        }
//    }


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

    // EFFECTS: returns true if shape can descend without intersecting with any other shape
//    public Boolean canDescend(List<Shape> shapesList, int speed) {
//        for (Shape shape: shapesList) {
//            if (this.intersects(shape, speed)) {
//                return false;
//            }
//        } return true;
//    }

//    public Boolean canMove(List<Block> blockList, int speed) {
//
//    }

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
