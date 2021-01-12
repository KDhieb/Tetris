package models.shapes;

import models.Block;

import java.util.Arrays;

public class LShape extends Shape {

    public LShape() {
        super();
        color = "darkblue";
        Block b1 = new Block(4,0, color);
        Block b2 = new Block(4,1, color);
        Block b3 = new Block(4,2, color);
        Block b4 = new Block(5,2, color);

        blocks.addAll(Arrays.asList(b1, b2, b3, b4));
    }

    @Override
    public void rotateLeft() {

    }

    @Override
    public void rotateRight() {

    }
}
