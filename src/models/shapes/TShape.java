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

    }


}
