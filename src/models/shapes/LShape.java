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

    }

}
