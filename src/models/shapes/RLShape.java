package models.shapes;

import java.util.List;

public class RLShape extends Shape{

    public RLShape() {
        super();
        color = "orange";
        initializeBlocks(0,5,1,5,2,5,2,4);
    }

    @Override
    public void rotate(List<String> filledSquares) {
        if (direction == Direction.DEFAULT) {
            rotateInnerBlocks(filledSquares, 1,1,0,0,-1,-1,-2,0);
            direction = Direction.EAST;

        } else if (direction == Direction.EAST) {
            rotateInnerBlocks(filledSquares, 1,-1,0,0,-1,1,0,2);
            direction = Direction.NORTH;

        } else if (direction == Direction.NORTH) {
            rotateInnerBlocks(filledSquares, -1,-1,0,0,1,1,2,0);
            direction = Direction.WEST;

        } else {
            rotateInnerBlocks(filledSquares, -1,1,0,0,1,-1,0,-2);
            direction = Direction.DEFAULT;
        }
    }

}

