package models.shapes;

import models.Block;

import java.util.Arrays;

public class TShape extends Shape {

    public TShape() {
        super();
        color = "purple";
        Block b1 = new Block(4,0, color);
        Block b2 = new Block(5,0, color);
        Block b3 = new Block(5,1, color);
        Block b4 = new Block(6,0, color);

        blocks.addAll(Arrays.asList(b1, b2, b3, b4));
    }

    @Override
    public void rotateLeft() {

    }

    @Override
    public void rotateRight() {

    }
}
