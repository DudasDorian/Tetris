package Logic.Blocks;

import Logic.Brick;

import java.util.ArrayList;

// Extends the abstract class Block, and implements the abstract methods.
public class S_Block extends Block {
    @Override
    public void layBricks() {
        this.bricks.add(new Brick(4, 0, 3));
        this.bricks.add(new Brick(5, 0, 3));
        this.bricks.add(new Brick(3, 1, 3));
        this.bricks.add(new Brick(4, 1, 3));
    }

    @Override
    public void rotateClockwise() {
        switch (phase){
            case 0 -> {
                bricks.get(0).setLocation(bricks.get(0).getX() + 1, bricks.get(0).getY() + 1);
                bricks.get(1).setLocation(bricks.get(1).getX(), bricks.get(1).getY() + 2);
                bricks.get(2).setLocation(bricks.get(2).getX() + 1, bricks.get(2).getY() - 1);
            }
            case 1 -> {
                bricks.get(0).setLocation(bricks.get(0).getX() - 1, bricks.get(0).getY() + 1);
                bricks.get(1).setLocation(bricks.get(1).getX() - 2, bricks.get(1).getY());
                bricks.get(2).setLocation(bricks.get(2).getX() + 1, bricks.get(2).getY() + 1);
            }
            case 2 -> {
                bricks.get(0).setLocation(bricks.get(0).getX() - 1, bricks.get(0).getY() - 1);
                bricks.get(1).setLocation(bricks.get(1).getX(), bricks.get(1).getY() - 2);
                bricks.get(2).setLocation(bricks.get(2).getX() - 1, bricks.get(2).getY() + 1);
            }
            case 3 -> {
                bricks.get(0).setLocation(bricks.get(0).getX() + 1, bricks.get(0).getY() - 1);
                bricks.get(1).setLocation(bricks.get(1).getX() + 2, bricks.get(1).getY());
                bricks.get(2).setLocation(bricks.get(2).getX() - 1, bricks.get(2).getY() - 1);
            }
        }
        phase = (phase + 1) % 4;
    }

    @Override
    public ArrayList<Brick> getClockwiseRotatedBricks() {
        ArrayList<Brick> toReturn = new ArrayList<>();

        switch (phase){
            case 0 -> {
                toReturn.add(new Brick(bricks.get(0).getX() + 1, bricks.get(0).getY() + 1));
                toReturn.add(new Brick(bricks.get(1).getX(), bricks.get(1).getY() + 2));
                toReturn.add(new Brick(bricks.get(2).getX() + 1, bricks.get(2).getY() - 1));
                toReturn.add(new Brick(bricks.get(3).getX(), bricks.get(3).getY()));
            }
            case 1 -> {
                toReturn.add(new Brick(bricks.get(0).getX() - 1, bricks.get(0).getY() + 1));
                toReturn.add(new Brick(bricks.get(1).getX() - 2, bricks.get(1).getY()));
                toReturn.add(new Brick(bricks.get(2).getX() + 1, bricks.get(2).getY() + 1));
                toReturn.add(new Brick(bricks.get(3).getX(), bricks.get(3).getY()));
            }
            case 2 -> {
                toReturn.add(new Brick(bricks.get(0).getX() - 1, bricks.get(0).getY() - 1));
                toReturn.add(new Brick(bricks.get(1).getX(), bricks.get(1).getY() - 2));
                toReturn.add(new Brick(bricks.get(2).getX() - 1, bricks.get(2).getY() + 1));
                toReturn.add(new Brick(bricks.get(3).getX(), bricks.get(3).getY()));
            }
            case 3 -> {
                toReturn.add(new Brick(bricks.get(0).getX() + 1, bricks.get(0).getY() - 1));
                toReturn.add(new Brick(bricks.get(1).getX() + 2, bricks.get(1).getY()));
                toReturn.add(new Brick(bricks.get(2).getX() - 1, bricks.get(2).getY() - 1));
                toReturn.add(new Brick(bricks.get(3).getX(), bricks.get(3).getY()));
            }
        }
        return toReturn;
    }
}
