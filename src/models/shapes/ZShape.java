package models.shapes;

import java.util.List;

public class ZShape extends Shape {

    public ZShape() {
        super();
        color = "red";
        initializeBlocks(0,5,1,5,1,4,2,4);
    }

    @Override
    public void rotate(List<String> filledSquares) {
        if (direction == Direction.DEFAULT) {
            rotateInnerBlocks(filledSquares, 1, 1, 0, 0,
                    -1, 1, -2, 0);
            direction = Direction.EAST;
        } else {
            rotateInnerBlocks(filledSquares, -1, -1, 0, 0,
                    1, -1, 2, 0);
            direction = Direction.DEFAULT;
        }
    }
}
