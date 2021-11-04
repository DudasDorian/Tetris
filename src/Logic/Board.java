package Logic;

import Logic.Blocks.*;

import java.util.ArrayList;
import java.util.Collections;

public class Board {
    // The boundaries are set as immutable values.
    public static final int BOUNDARY_TOP = 0;
    public static final int BOUNDARY_BOTTOM = 21;
    public static final int BOUNDARY_LEFT = 0;
    public static final int BOUNDARY_RIGHT = 9;

    private int score, level, totalLinesCleared;
    // The currentBlock represents the Block that is controlled by the
    private Block currentBlock;
    // The Bricks of the Board are saved in this ArrayList.
    private ArrayList<Brick> boardBricks;
    // Blocks are held in a queue so that they may be set in a predictable order.
    private ArrayList<Block> blockQueue;
    // The currentBlock may be held aside.
    private Block heldBlock;
    private final BlockFactory blockFactory = new BlockFactory();
    private boolean gameOver = false;

    // Initializes the Board, and sets up the blockQueue.
    public Board() {
        this.score = 0;
        this.level = 0;
        this.totalLinesCleared = 0;
        this.boardBricks = new ArrayList<>();
        this.blockQueue = new ArrayList<>();
        this.heldBlock = null;
        setBlockQueue();
        this.currentBlock = this.blockQueue.get(0);
        this.blockQueue.remove(0);
    }

    // It generates a list of 14 randomly arranged pieces, which are used in gameplay afterwards.
    private void setBlockQueue(){
        // Firstly, it creates a list of all the numbers from 0 to 6, each appearing exactly twice.
        ArrayList<Integer> queue = new ArrayList<>();
        for(int i = 0; i < 7; i++){
            queue.add(i);
            queue.add(i);
        }
        // Secondly, it shuffles the order of those numbers.
        Collections.shuffle(queue);
        // Finally, using the BlockFactory, it transforms those numbers into playable pieces.
        int j = 0;
        for(Integer i : queue){
            blockQueue.add(blockFactory.getBlock(i));
            // If the method is called again, before it's become empty, the Bricks contained in
            // the remaining Blocks are removed, as they will be set again by the layBricks() method.
            if(blockQueue.get(j).getBricks() != null){
                blockQueue.get(j).getBricks().removeAll(blockQueue.get(j).getBricks());
            }
            // The Blocks are set to be ready to be played and displayed.
            blockQueue.get(j).layBricks(); j++;
        }
    }

    // Basic collision checks:
    private boolean downwardsCollision(){
        return currentBlock.collides(boardBricks, 0, +1, BoundaryType.VERTICAL, BOUNDARY_BOTTOM);
    }

    private boolean leftwardsCollision(){
        return currentBlock.collides(boardBricks, -1, 0, BoundaryType.HORIZONTAL, BOUNDARY_LEFT);
    }

    private boolean rightwardsCollision(){
        return currentBlock.collides(boardBricks, +1, 0, BoundaryType.HORIZONTAL, BOUNDARY_RIGHT);
    }

    private boolean spawnCollision(){
        return currentBlock.collides(boardBricks, 0, 0, BoundaryType.VERTICAL, BOUNDARY_TOP);
    }

    // currentBlock is changed out for another.
    public void holdBlock(){
        // currentBlock is reset.
        currentBlock.getBricks().removeAll(currentBlock.getBricks());
        currentBlock.layBricks();
        currentBlock.setPhase(0);
        // currentBlock is saved separately.
        Block aux = currentBlock;

        // If there was a held Block already, currentBlock will become it.
        if(heldBlock != null){
            currentBlock = heldBlock;
        }
        // Otherwise, currentBlock will take the form of the next Block in the blockQueue.
        else{
            currentBlock = blockQueue.get(0);
            blockQueue.remove(0);
        }
        // Initial currentBlock is set as the heldBlock.
        heldBlock = aux;
    }

    // Basic movement methods:
    public void moveDown(){
        if (!downwardsCollision()){
            currentBlock.moveDown();
        }
        else{
            if (spawnCollision()){
                gameOver = true;
            }
            else{
                // The currentBlock's Bricks are added to the board,
                // and the board is checked for lines to clear.
                this.boardBricks.addAll(this.currentBlock.getBricks());
                clearLines();
                // A new currentBlock is extracted from the blockQueue.
                this.currentBlock = blockQueue.get(0);
                this.blockQueue.remove(0);
                // If both the next piece and the held one are shown,
                // the queue would need to have at least 2 more pieces in advance.
                if(blockQueue.size() <= 2){
                    setBlockQueue();
                }
            }
        }
    }

    public void moveLeft() {
        if (!leftwardsCollision()) {
            currentBlock.moveLeft();
        }
    }

    public void moveRight() {
        if (!rightwardsCollision()) {
            currentBlock.moveRight();
        }
    }

    // Collision is checked in the event of a rotation.
    private boolean clockwiseRotationCollision(){
        for(Brick blockBrick : currentBlock.getClockwiseRotatedBricks()){
            if(boardBricks.contains(new Brick(blockBrick.getX(), blockBrick.getY()))){
                return true;
            }
            if(blockBrick.getY() == BOUNDARY_BOTTOM + 1 || blockBrick.getY() == BOUNDARY_TOP - 1
                    || blockBrick.getX() == BOUNDARY_LEFT - 1 || blockBrick.getX() == BOUNDARY_RIGHT + 1){
                return true;
            }
        }
        return false;
    }

    // Method for clockwise rotation;
    // calls for the implementation in each concrete Block class.
    public void rotateClockwise(){
        if(!clockwiseRotationCollision()){
            currentBlock.rotateClockwise();
        }
    }

    // This method checks for full lines to clear.
    private void clearLines(){
        ArrayList<Integer> lines = new ArrayList<>();
        for(int i = 0; i < BOUNDARY_BOTTOM + 1; i++){
            lines.add(0);
        }
        // It counts each line's amount of Bricks.
        for(Brick boardBrick : boardBricks){
            lines.set(boardBrick.getY(), lines.get(boardBrick.getY()) + 1);
        }
        int line = 0, linesCleared = 0;
        for(Integer lineAmount : lines){
            // Checks if the line is full.
            if(lineAmount == BOUNDARY_RIGHT + 1){
                linesCleared++;

                // All the Bricks with y equal to the determined line are removed.
                int finalLine = line;
                this.getBoardBricks().removeIf(brick -> (brick.isAtY(finalLine)));

                // All the Bricks above the y of the line are brought down by one.
                for(Brick boardBrick : boardBricks){
                    if(boardBrick.getY() < line){
                        boardBrick.setLocation(boardBrick.getX(), boardBrick.getY() + 1);
                    }
                }
            }
            line++;
        }
        // Based on the amount of cleared lines and level, the score is increased.
        if(linesCleared > 0){
            totalLinesCleared += linesCleared;
            switch (linesCleared){
                case 1 -> score += 40 * (level + 1);
                case 2 -> score += 100 * (level + 1);
                case 3 -> score += 300 * (level + 1);
                case 4 -> score += 1200 * (level + 1);
            }
            // Also the level is updated.
            level = totalLinesCleared / 10;
        }
    }

    // getters and setters:
    public int getScore() {
        return score;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getTotalLinesCleared() {
        return totalLinesCleared;
    }

    public void setBoardBricks(ArrayList<Brick> bricks){
        this.boardBricks = bricks;
    }

    public ArrayList<Brick> getBoardBricks() {
        return boardBricks;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public Block getCurrentBlock() {
        return currentBlock;
    }

    public boolean getDownwardsCollision() {
        return downwardsCollision();
    }

    public ArrayList<Block> getBlockQueue() {
        return blockQueue;
    }

    public Block getHeldBlock() {
        return heldBlock;
    }

    // A shortcut for adding to the score from outside the board itself.
    public void increaseScore(int addition){
        this.score += addition;
    }
}
