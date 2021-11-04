package Logic.Blocks;

import java.util.ArrayList;

import Logic.BoundaryType;
import Logic.Brick;

// Part of the Factory Design Pattern. As an abstract class,
// it plays the role of an interface, with the added benefit of containing non-abstract methods,
// which are inherited by all the concrete classes that extend it.
public abstract class Block {
    // Represents the rotation phase. {0, 1, 2, 3} = {↑, →, ↓, ←}
    protected int phase = 0;

    // Array list of the Bricks which make up the Block
    protected ArrayList<Brick> bricks = new ArrayList<>();

    // Used to populate the bricks ArrayList. Is implemented in the concrete subclasses.
    public abstract void layBricks();

    // Movement control:
    public void moveDown() {
        for (Brick brick : bricks) {
            brick.setLocation(brick.getX(), brick.getY() + 1);
        }
    }

    public void moveLeft() {
        for (Brick brick : bricks) {
            brick.setLocation(brick.getX() - 1, brick.getY());
        }
    }

    public void moveRight() {
        for (Brick brick : bricks) {
            brick.setLocation(brick.getX() + 1, brick.getY());
        }
    }

    // Rotates the piece clockwise. Is implemented in the concrete subclasses.
    public abstract void rotateClockwise();

    // Returns an ArrayList of Bricks with their position after a hypothetical rotation.
    // Is implemented in the concrete subclasses.
    public abstract ArrayList<Brick> getClockwiseRotatedBricks();

    // Checks if there is any collision.
    public boolean collides(ArrayList<Brick> boardBricks, int difX, int difY, BoundaryType boundaryTypeAxis, int boundary) {
        for (Brick blockBrick : bricks) {
            if (boardBricks.contains(new Brick(blockBrick.getX() + difX, blockBrick.getY() + difY))) {
                return true;
            }
            if (boundaryTypeAxis.equals(BoundaryType.VERTICAL) ?
                    blockBrick.getY() == boundary :
                    blockBrick.getX() == boundary) {
                return true;
            }
        }
        return false;
    }

    // getters and setters:
    public ArrayList<Brick> getBricks() {
        return bricks;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public void setBricks(ArrayList<Brick> bricks) {
        this.bricks = bricks;
    }

    // for testing purposes:
    public String getName(){
        return getClass().getSimpleName();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Block{ phase: ").append(phase).append(", bricks: ");
        for(Brick brick : bricks){
            sb.append(brick.toString());
            sb.append(", ");
        }
        sb.append(" colour: ").append(bricks.get(0).getColour());
        return sb.toString();
    }
}
