package models;

import models.shapes.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Game extends Observable {
    public static final int BOARD_COLS = 10;
    public static final int BOARD_ROWS = 20;
    private static final int SPEED = 100;
    private final List<ShapesEnum> shapeTypes;
    private final List<Block> blocks;
    private final List<String> filledSquares;
    private final HashMap<Integer, List<Block>> rowMap;
    public boolean lostGame;

    Shape shapeInPlay;

    public Game() {
        blocks = new ArrayList<>();
        filledSquares = new ArrayList<>();
        rowMap = new HashMap<>();
        shapeTypes = new ArrayList<>(Arrays.asList(ShapesEnum.T, ShapesEnum.S, ShapesEnum.L,
                ShapesEnum.LINE, ShapesEnum.BOX));
        selectNewShape();
        lostGame = false;
    }

    public void selectNewShape() {
        shapeInPlay = randomShapeCreator();
    }

    public Shape randomShapeCreator() {
        Random random = new Random();
        int randomIndex = random.nextInt(shapeTypes.size());
        ShapesEnum chosenShape = shapeTypes.get(randomIndex);
        if (chosenShape == ShapesEnum.S) {
            return new SShape();
        } else if (chosenShape == ShapesEnum.L) {
            return new LShape();
        } else if (chosenShape == ShapesEnum.T) {
            return new TShape();
        } else if (chosenShape == ShapesEnum.BOX) {
            return new BoxShape();
        } else {
            return new LineShape();
        }
    }

    public int getColumnNumber(int index) {
        return (index % 10) + 1;
    }

    public int getRowNumber(int index) {
        return (index / 10) + 1;
    }

    public Boolean canDescend() {
        return shapeInPlay.canMoveDown(filledSquares);
    }

//    public void checkAndClearRows2() {
//        for ()
//    }

    
    // EFFECTS: checks and clears filled rows
    public void checkAndClearRows() {
        HashMap<Integer, List<Block>> rowMap = new HashMap<>();
        int lowestRowReached = 0;
        int rowScoreCount = 0;
        //sorts blocks into lists of rows
        for (Block block : blocks) {
            Integer rowNum = block.getRow();
            if (rowMap.containsKey(rowNum)) {
                rowMap.get(rowNum).add(block);
            } else {
                List<Block> list = new ArrayList<>();
                list.add(block);
                rowMap.put(rowNum, list);
            }
        }
        for (Integer row: rowMap.keySet()) {
            List<Block> currentRow = rowMap.get(row);
            // if row is full, remove all blocks
            if (currentRow.size() == BOARD_COLS) {
                if (row > lowestRowReached) {
                    lowestRowReached = row;
                }
                rowScoreCount++;
                for (Block block: currentRow) {
                    blocks.remove(block);
                    filledSquares.remove(block.getPosition());
                }
            }
        }
        setChanged();
        notifyObservers(rowScoreCount);
//        if (lowestRowReached != 0) {
//            for (int i = lowestRowReached - 1; i >= 0; i-- ) {
//                for (Block block: rowMap.get(i)) {
//                    if (block.canMove(filledSquares, rowScoreCount, 0)) {
//                        block.move(rowScoreCount, 0);
//                    }
//                }
//            }
//        }

    }

    // EFFECTS: If shape can descend, returns true and advances piece,
    // if can't descend, extracts blocks from shape and selects new shape
    public void descend() {
        if (!shapeInPlay.move(filledSquares, Moves.DOWN)) {
            if (checkIfLost()) {
                this.lostGame = true;
            }
            for (Block block: shapeInPlay) {
                filledSquares.add(block.getPosition());
                blocks.add(block);
            }
            selectNewShape();
        }
    }

    public void moveLeft() {
        shapeInPlay.move(filledSquares, Moves.LEFT);
    }

    public void moveRight() {
        shapeInPlay.move(filledSquares, Moves.RIGHT);
    }

    public void rotateShape() {
        shapeInPlay.rotate(filledSquares);
    }


    public Boolean checkIfLost() {
        for (Block block: shapeInPlay) {
            if (block.getRow() == 0) {
                System.out.println("Game Over!");
                return true;
            }
        }
        return false;
    }

    public void resetGame() {
        notifyObservers("Reset");
        filledSquares.clear();
        blocks.clear();
        selectNewShape();
    }


    public List<Block> getBlocks() {
        return blocks;
    }

    public Shape getShapeInPlay() {
        return shapeInPlay;
    }

    public static int getBoardCols() {
        return BOARD_COLS;
    }

    public static int getBoardRows() {
        return BOARD_ROWS;
    }

    public static int getSpeed() {
        return SPEED;
    }


}
