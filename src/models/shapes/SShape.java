package models.shapes;


import java.util.List;

public class SShape extends Shape {

    public SShape() {
        super();
        color = "green";
        initializeBlocks(0,4,1,4,1,5,2,5);
    }

    @Override
    public void rotate(List<String> filledSquares) {
        if (direction == Direction.DEFAULT) {
            rotateInnerBlocks(filledSquares, 0, 2, 0, 0,
                    0, 0, -2, 0);
            direction = Direction.EAST;
        } else {
            rotateInnerBlocks(filledSquares, 0, -2, 0, 0,
                    0, 0, 2, 0);
            direction = Direction.DEFAULT;
        }
    }

}
