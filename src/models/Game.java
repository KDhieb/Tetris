package models;

import models.shapes.*;

import java.util.*;

// Main class that controls game logic
public class Game extends Observable {
    public static final int BOARD_COLS = 10;
    public static final int BOARD_ROWS = 20;
    private static final int SPEED = 200;
    private final List<ShapesEnum> shapeTypes;
    private final List<Block> blocks;
    private final List<String> filledSquares;
    public boolean lostGame;

    Shape shapeInPlay;

    // EFFECTS: creates a game instance
    public Game() {
        blocks = new ArrayList<>();
        filledSquares = new ArrayList<>();
        shapeTypes = new ArrayList<>(Arrays.asList(ShapesEnum.T, ShapesEnum.S, ShapesEnum.L,
                ShapesEnum.LINE, ShapesEnum.BOX));
        selectNewShape();
        lostGame = false;
    }

    // EFFECTS: Changes the current shape in play
    public void selectNewShape() {
        shapeInPlay = randomShapeCreator();
    }

    // EFFECTS: randomly selects and initializes a shape
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

    // EFFECTS: checks and clears filled rows
    public void checkAndClearRows() {
        HashMap<Integer, List<Block>> rowMap = new HashMap<>();
        int lowestRowReached = 0;
        int rowScoreCount = 0;

        for (int i = 0; i < 20; i++ ) {
            List<Block> emptyList = new ArrayList<>();
            rowMap.put(i, emptyList);
        }

        //sorts blocks into lists of rows
        for (Block block : blocks) {
            Integer rowNum = block.getRow();
            if (rowMap.containsKey(rowNum)) {
                rowMap.get(rowNum).add(block);
            }
        }
        // if row is full, remove all blocks
        for (Integer row: rowMap.keySet()) {
            List<Block> currentRow = rowMap.get(row);
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
        if (rowScoreCount > 0) {
            setChanged();
            notifyObservers(rowScoreCount);
            dropAboveRowsAfterClear(rowMap, lowestRowReached, rowScoreCount);
        }
    }

    // EFFECTS: Moves rows above cleared rows down
    private void dropAboveRowsAfterClear(HashMap<Integer, List<Block>> rowMap, int lowestRowReached, int rowScoreCount) {
        if (lowestRowReached != 0) {
            for (int i = lowestRowReached - 1; i >= 0; i--) {
                for (Block block: rowMap.get(i)) {
                    if (block.canMove(filledSquares, rowScoreCount, 0)) {
                        System.out.println("Before: " + filledSquares);
                        filledSquares.remove(block.getPosition());
                        block.move(rowScoreCount, 0);
                        filledSquares.add(block.getPosition());
                        System.out.println("After: " + filledSquares);
                        System.out.println("----------");
                    }
                }
            }
        }
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

    // REQUIRES: to be called only after shape can no longer move
    // EFFECTS: returns true if a shape has a block in row 0 (top row)
    public Boolean checkIfLost() {
        for (Block block: shapeInPlay) {
            if (block.getRow() == 0) {
                System.out.println("Game Over!");
                return true;
            }
        }
        return false;
    }

    // EFFECTS: resets game to initial state
    public void resetGame() {
        setChanged();
        notifyObservers("Reset");
        lostGame = false;
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
