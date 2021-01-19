package models;

import models.shapes.*;

import java.util.*;

public class Game extends Observable {
    public static final int BOARD_COLS = 10;
    public static final int BOARD_ROWS = 20;
    private static final int SPEED = 200;
    private final List<ShapesEnum> shapeTypes;
    private final List<Block> blocks;
    private final List<String> filledSquares;
    public boolean lostGame;

    Shape shapeInPlay;



    public Game() {
        blocks = new ArrayList<>();
        filledSquares = new ArrayList<>();
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
        return shapeInPlay.canDescend(blocks);
    }


    // EFFECTS: checks and clears filled rows
    public void checkAndClearRows() {
        HashMap<Integer, List<Block>> rowMap = new HashMap<>();
        //sorts blocks into lists of rows
        for (Block block : blocks) {
            Integer rowNum = block.getRow();
            if (rowMap.containsKey(rowNum)) {
                List<Block> list = rowMap.get(rowNum);
                list.add(block);
                rowMap.put(rowNum, list);
            } else {
                List<Block> list = new ArrayList<>();
                list.add(block);
                rowMap.put(rowNum, list);
            }
        }
        Integer lowestRow = null;
        int rowScoreCount = 0;
        for (Integer row: rowMap.keySet()) {
            if (lowestRow == null) {
                lowestRow = row;
            } else if (lowestRow < row) {
                lowestRow = row;
            }
            List<Block> list = rowMap.get(row);
            if (list.size() == BOARD_COLS) {
                rowScoreCount++;
                for (Block block: list) {
                    blocks.remove(block);
                    filledSquares.remove(block.getPosition());
                }
            }
        }
        setChanged();
        notifyObservers(rowScoreCount);
        if (lowestRow != null) {
            // move down
        }

    }

    // EFFECTS: If shape can descend, returns true and advances piece,
    // if can't descend, extracts blocks from shape and selects new shape
    public void descend() {
        if (shapeInPlay.move(blocks, Moves.DOWN)) {
        } else {
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
        shapeInPlay.move(blocks, Moves.LEFT);
    }

    public void moveRight() {
        shapeInPlay.move(blocks, Moves.RIGHT);
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
