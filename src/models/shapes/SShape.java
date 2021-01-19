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

    }


}
