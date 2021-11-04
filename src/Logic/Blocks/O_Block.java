package Logic.Blocks;

import Logic.Brick;

import java.util.ArrayList;

// Extends the abstract class Block, and implements the abstract methods.
public class O_Block extends Block {
    @Override
    public void layBricks() {
        this.bricks.add(new Brick(4, 0, 1));
        this.bricks.add(new Brick(5, 0, 1));
        this.bricks.add(new Brick(4, 1, 1));
        this.bricks.add(new Brick(5, 1, 1));
    }


    // Doesn't rotate.
    @Override
    public void rotateClockwise() {

    }

    @Override
    public ArrayList<Brick> getClockwiseRotatedBricks() {
        return new ArrayList<>();
    }
}
