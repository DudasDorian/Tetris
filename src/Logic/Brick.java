package Logic;

import java.util.Objects;

// Represents the smallest element of the game.
public class Brick {
    int x, y;   // coordinates
    int colour; // correlating Brick's colour to their Block
                // {0, 1, 2, 3, 4, 5, 6} = {CYAN, YELLOW, PURPLE, GREEN, RED, BLUE, ORANGE}

    public Brick(int x, int y, int colour) {
        this.x = x;
        this.y = y;
        this.colour = colour;
    }

    public Brick(int x, int y){
        this.x = x;
        this.y = y;
    }

    // getters and setters:
    public void setLocation(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getColour() {
        return colour;
    }

    public boolean isAtY(int y){
        return getY() == y;
    }

    // The equals() method has been overridden to only care about the x, y coordinates.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Brick brick = (Brick) o;
        return x == brick.x && y == brick.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    // for testing purposes:
    @Override
    public String toString() {
        return "(" + x +
                ", " + y +
                ")";
    }
}
