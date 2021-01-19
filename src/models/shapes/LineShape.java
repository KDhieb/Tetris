package models.shapes;

import java.util.List;

public class LineShape extends Shape {

    public LineShape() {
        super();
        color = "lightblue";
        initializeBlocks(0,4, 1, 4, 2, 4, 3, 4);
    }

    public void rotate(List<String> filledSquares) {
        if (direction == Direction.DEFAULT) {
            rotateInnerBlocks(filledSquares, 0,0, -1, 1,
                    -2, 2, -3,3);
            direction = Direction.EAST;
        } else {
            rotateInnerBlocks(filledSquares, 0, 0, 1, -1,
                    2,-2, 3, -3);
            direction = Direction.DEFAULT;
        }
    }

}
