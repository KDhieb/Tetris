package models;

import models.shapes.*;

import java.util.*;
import java.util.stream.Stream;

public class Game {
    private static final int BOARD_COLS = 10;
    private static final int BOARD_ROWS = 20;
    private static final int SPEED = 150;
    public boolean lostGame;

    List<Block> blocks;
    List<Shape> shapes;
    List<ShapesEnum> shapeTypes;
    Shape shapeInPlay;



    public Game() {
        blocks = new ArrayList<>();
        shapes = new ArrayList<>();
        shapeTypes = new ArrayList<>(Arrays.asList(ShapesEnum.T, ShapesEnum.S, ShapesEnum.L,
                ShapesEnum.BOX, ShapesEnum.LINE));
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
        System.out.println("Step 1");
        for (Block block : blocks) {
            System.out.println("Step 2");
            Integer rowNum = block.getRow();
            if (rowMap.containsKey(rowNum)) {
                System.out.println("Step 3");
                List<Block> list = rowMap.get(rowNum);
                list.add(block);
                rowMap.put(rowNum, list);
            } else {
                System.out.println("Step 4");
                List<Block> list = new ArrayList<>();
                list.add(block);
                rowMap.put(rowNum, list);
            }
        }
        System.out.println("Step 5");
        for (Integer row: rowMap.keySet()) {
            System.out.println("Step 6");
            List<Block> list = rowMap.get(row);
            if (list.size() == BOARD_COLS) {
                for (Block block: list) {
                    blocks.remove(block);
                }
            }
        }
    }


    // EFFECTS: If shape can descend, returns true and advances piece,
    // if can't descend, extracts blocks from shape and selects new shape
    public Boolean descend() {
        if (shapeInPlay.move(blocks, Moves.DOWN)) {
            return true;
        } else {
            if (checkIfLost()) {
                this.lostGame = true;
                return false;
            }
            shapes.add(shapeInPlay);
            for (Block block: shapeInPlay) {
                blocks.add(block);
            }
            selectNewShape();
            return false;
        }
    }

    public void moveLeft() {
        shapeInPlay.move(blocks, Moves.LEFT);
    }

    public void moveRight() {
        shapeInPlay.move(blocks, Moves.RIGHT);
    }

    public void rotateShapeLeft() {

    }

    public void rotateShapeRight() {

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



    public void update() {

    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public List<Shape> getShapes() {
        return shapes;
    }

    public List<ShapesEnum> getShapeTypes() {
        return shapeTypes;
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
