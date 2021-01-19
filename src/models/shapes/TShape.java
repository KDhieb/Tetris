package models.shapes;


import java.util.List;

public class TShape extends Shape {

    public TShape() {
        super();
        color = "purple";
        initializeBlocks(0,4,0,5,1,5,0,6);
    }

    @Override
    public void rotate(List<String> filledSquares) {
        if (direction == Direction.DEFAULT) {
            rotateInnerBlocks(filledSquares, 0,0,0,0,0,0,-1,-1);
            direction = Direction.EAST;

        } else if (direction == Direction.EAST) {
            rotateInnerBlocks(filledSquares, 0,0,0,0,-1,1,0,0);
            direction = Direction.NORTH;

        } else if (direction == Direction.NORTH) {
            rotateInnerBlocks(filledSquares, 1,1,0,0,0,0,0,0);
            direction = Direction.WEST;

        } else {
            rotateInnerBlocks(filledSquares, -1,-1,0,0,1,-1,1,1);
            direction = Direction.DEFAULT;
        }
    }


}
