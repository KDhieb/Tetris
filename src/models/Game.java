package models;

import models.shapes.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
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

    public void clearFilledRows() {
        for (int i = 1; i <= BOARD_ROWS; i++) {
            int rowNum = i;
            Stream<Block> rowStream = blocks.stream().filter(b -> b.getRow() == rowNum);
            if (rowStream.count() == BOARD_COLS) {
                rowStream.forEach(b -> blocks.remove(b));
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
