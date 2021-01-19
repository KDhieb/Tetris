package models.shapes;

import models.Block;

import java.util.Arrays;
import java.util.List;

public class LShape extends Shape {

    public LShape() {
        super();
        color = "darkblue";
        initializeBlocks(0,4,1,4,2,4,2,5);
    }

    @Override
    public void rotate(List<String> filledSquares) {
        if (direction == Direction.DEFAULT) {
            rotateInnerBlocks(filledSquares, 1,1,0,0,-1,-1,0,-2);
            direction = Direction.EAST;

        } else if (direction == Direction.EAST) {
            rotateInnerBlocks(filledSquares, 1,-1,0,0,-1,1,-2,0);
            direction = Direction.NORTH;

        } else if (direction == Direction.NORTH) {
            rotateInnerBlocks(filledSquares, -1,-1,0,0,1,1,0,2);
            direction = Direction.WEST;

        } else {
            rotateInnerBlocks(filledSquares, -1,1,0,0,1,-1,2,0);
            direction = Direction.DEFAULT;
        }
    }

}
