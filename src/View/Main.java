package View;

import Logic.Blocks.Block;
import Logic.Board;
import Logic.Brick;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {
    private Board board, ghostBoard = new Board();
    private ArrayList<Brick> bricks = new ArrayList<>();
    private double time;
    boolean highScoreSurpassed = false;

    private Scene scene;
    private GraphicsContext g;

    static final int BRICK_SIZE = 32;

    Label score = new Label();
    Label highScore = new Label();
    Label level = new Label();
    Label lines = new Label();

    // Populates the graphical space.
    private Parent createContent(){
        Pane root = new Pane();
        root.setPrefSize(BRICK_SIZE*(Board.BOUNDARY_RIGHT+7), BRICK_SIZE*(Board.BOUNDARY_BOTTOM+1));

        Rectangle background = new Rectangle(BRICK_SIZE*(Board.BOUNDARY_RIGHT+7), BRICK_SIZE*(Board.BOUNDARY_BOTTOM+1));
        background.setFill(Color.rgb(152, 172, 172));
        root.getChildren().add(background);

        Rectangle bar = new Rectangle(BRICK_SIZE*(Board.BOUNDARY_RIGHT+1), BRICK_SIZE*(Board.BOUNDARY_BOTTOM+1)); // the Board area
        bar.setFill(Color.rgb(31, 31, 31));
        root.getChildren().add(bar);

        Rectangle holdArea = new Rectangle(BRICK_SIZE*(Board.BOUNDARY_RIGHT-1) + 96, 10, BRICK_SIZE*4+4, BRICK_SIZE*4+4);
        holdArea.setFill(Color.rgb(31, 31, 31));
        holdArea.setStroke(Color.WHITE);
        holdArea.setStrokeWidth(1.0);
        root.getChildren().add(holdArea);

        Text holdLabel = new Text();
        holdLabel.setText("Hold: (X)");
        holdLabel.setLayoutX(BRICK_SIZE*(Board.BOUNDARY_RIGHT) + 96);
        holdLabel.setLayoutY(32);
        holdLabel.setFill(Color.WHITESMOKE);
        holdLabel.setFont(Font.font("Impact", 20));
        root.getChildren().add(holdLabel);

        Rectangle next = new Rectangle(BRICK_SIZE*(Board.BOUNDARY_RIGHT-1) + 96, 150, BRICK_SIZE*4+4, BRICK_SIZE*4+4);
        next.setFill(Color.rgb(91, 91, 91));
        next.setStroke(Color.WHITE);
        next.setStrokeWidth(1.0);
        root.getChildren().add(next);

        Text nextLabel = new Text();
        nextLabel.setText("Next:");
        nextLabel.setLayoutX(BRICK_SIZE*(Board.BOUNDARY_RIGHT) + 96);
        nextLabel.setLayoutY(172);
        nextLabel.setFill(Color.WHITESMOKE);
        nextLabel.setFont(Font.font("Impact", 20));
        root.getChildren().add(nextLabel);

        Canvas canvas = new Canvas(BRICK_SIZE*(Board.BOUNDARY_RIGHT+7), BRICK_SIZE*(Board.BOUNDARY_BOTTOM+1));
        g = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);

        Label scoreLabel = new Label();
        scoreLabel.setText("Score");
        scoreLabel.setLayoutX(360);
        scoreLabel.setLayoutY(332);
        scoreLabel.setFont(Font.font("Impact", 20));
        root.getChildren().add(scoreLabel);

        Label highScoreLabel = new Label();
        highScoreLabel.setText("High Score");
        highScoreLabel.setLayoutX(360);
        highScoreLabel.setLayoutY(382);
        highScoreLabel.setFont(Font.font("Impact", 20));
        root.getChildren().add(highScoreLabel);

        Label levelLabel = new Label();
        levelLabel.setText("Level");
        levelLabel.setLayoutX(360);
        levelLabel.setLayoutY(432);
        levelLabel.setFont(Font.font("Impact", 20));
        root.getChildren().add(levelLabel);

        Label linesLabel = new Label();
        linesLabel.setText("Lines Cleared");
        linesLabel.setLayoutX(360);
        linesLabel.setLayoutY(482);
        linesLabel.setFont(Font.font("Impact", 20));
        root.getChildren().add(linesLabel);

        board = new Board();

        score.setText("" + board.getScore());
        score.setLayoutX(360);
        score.setLayoutY(352);
        score.setFont(Font.font("Impact", 20));
        root.getChildren().add(score);

        highScore.setText("" + Math.max(FileHandler.readHighScore(), board.getScore()));
        highScore.setLayoutX(360);
        highScore.setLayoutY(402);
        highScore.setFont(Font.font("Impact", 20));
        root.getChildren().add(highScore);

        level.setText("" + board.getLevel());
        level.setLayoutX(360);
        level.setLayoutY(452);
        level.setFont(Font.font("Impact", 20));
        root.getChildren().add(level);

        lines.setText("" + board.getTotalLinesCleared());
        lines.setLayoutX(360);
        lines.setLayoutY(502);
        lines.setFont(Font.font("Impact", 20));
        root.getChildren().add(lines);

        refreshDisplay();

        // The AnimationTimer is responsible for moving the currently controlled piece down.
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(time < 0.2){
                    time = 0.0;
                }
                time += timeFractionBasedOnLevel();
                if(time >= 60.0){
                    board.moveDown();
                    refreshDisplay();
                    time = 0.0;
                }
            }
        };
        animationTimer.start();

        return root;
    }

    // Calculates the fraction of time to be continuously added to
    // the variable time in AnimationTimer based on the current level.
    private double timeFractionBasedOnLevel(){
        // assume AnimationTimer speed to be relevant to 60 fps
        switch (board.getLevel()){
            case 0 -> {
                return 60.0 / 48.0;
            }
            case 1 -> {
                return 60.0 / 43.0;
            }
            case 2 -> {
                return 60.0 / 38.0;
            }
            case 3 -> {
                return 60.0 / 33.0;
            }
            case 4 -> {
                return 60.0 / 28.0;
            }
            case 5 -> {
                return 60.0 / 23.0;
            }
            case 6 -> {
                return 60.0 / 18.0;
            }
            case 7 -> {
                return 60.0 / 13.0;
            }
            case 8 -> {
                return 60.0 / 8.0;
            }
            case 9 -> {
                return 60.0 / 6.0;
            }
            case 10, 11, 12 -> {
                return 60.0 / 5.0;
            }
            case 13, 14, 15 -> {
                return 60.0 / 4.0;
            }
            case 16, 17, 18 -> {
                return 60.0 / 3.0;
            }
            case 19, 20, 21, 22, 23, 24, 25, 26, 27, 28 -> {
                return 60.0 / 2.0;
            }
            default -> {
                return 60.0 / 1.01;
            }
        }
    }

    // Creates a copy of the board whose currentBlock will display the place
    // the currently controlled piece would land should it be dropped.
    private void setupGhostBoard(){
        // The ghostBoard is being set to be an exact copy of the board.
        ghostBoard.setBoardBricks(new ArrayList<>());
        for(Brick brick : board.getBoardBricks()){
            ghostBoard.getBoardBricks().add(new Brick(brick.getX(), brick.getY()));
        }
        ArrayList<Brick> ghostBlock = new ArrayList<>();
        for(Brick currentBlockBrick : board.getCurrentBlock().getBricks()){
            ghostBlock.add(new Brick(currentBlockBrick.getX(), currentBlockBrick.getY()));
        }
        ghostBoard.getCurrentBlock().setBricks(ghostBlock);
        // Its currentBlock is moved right up against the lowest reachable position.
        while (!ghostBoard.getDownwardsCollision()){
            ghostBoard.moveDown();
        }
    }

    // All graphical elements that may change are updated here.
    private void refreshDisplay(){
        // The ArrayList bricks gets all the bricks currently on the board.
        bricks.addAll(board.getBoardBricks());
        bricks.addAll(board.getCurrentBlock().getBricks());
        g.clearRect(0, 0, BRICK_SIZE*(Board.BOUNDARY_RIGHT+7), BRICK_SIZE*(Board.BOUNDARY_BOTTOM+1));

        setupGhostBoard();
        for (Brick brick : ghostBoard.getCurrentBlock().getBricks()){
            g.setFill(Color.rgb(61, 61, 61));
            g.fillRect(brick.getX() * BRICK_SIZE, brick.getY() * BRICK_SIZE, BRICK_SIZE, BRICK_SIZE);
            g.setStroke(Color.WHITE);
            g.setLineWidth(2.0);
            g.strokeRect(brick.getX() * BRICK_SIZE, brick.getY() * BRICK_SIZE, BRICK_SIZE, BRICK_SIZE);
        }

        for (Brick brick : bricks) {
            g.setFill(getBrickColour(brick.getColour()));
            g.fillRect(brick.getX() * BRICK_SIZE + 2, brick.getY() * BRICK_SIZE + 2, BRICK_SIZE - 4, BRICK_SIZE - 4);
        }

        if(board.getHeldBlock() != null){
            for(Brick brick : board.getHeldBlock().getBricks()){
                g.setFill(getBrickColour(brick.getColour()));
                g.fillRect(brick.getX() * BRICK_SIZE + 259, brick.getY() * BRICK_SIZE + 44, BRICK_SIZE - 4, BRICK_SIZE - 4);
            }
        }

        for(Brick brick : board.getBlockQueue().get(0).getBricks()){
            g.setFill(getBrickColour(brick.getColour()));
            g.fillRect(brick.getX() * BRICK_SIZE + 259, brick.getY() * BRICK_SIZE + 184, BRICK_SIZE - 4, BRICK_SIZE - 4);
        }

        score.setText("" + board.getScore());
        highScore.setText("" + Math.max(FileHandler.readHighScore(), board.getScore()));
        level.setText("" + board.getLevel());
        lines.setText("" + board.getTotalLinesCleared());
        // All the elements of bricks must be removed, otherwise they stack up.
        bricks.removeAll(bricks);

        if(board.isGameOver()){
            g.setFill(Color.rgb(31, 31, 31, 0.5));
            g.fillRect(0, 0, BRICK_SIZE*(Board.BOUNDARY_RIGHT+1), BRICK_SIZE*(Board.BOUNDARY_BOTTOM+1));
            g.setTextAlign(TextAlignment.CENTER);
            g.setTextBaseline(VPos.CENTER);
            g.setFill(Color.WHITE);
            g.setFont(new Font("Impact", 50));
            g.fillText(
                    "GAME OVER",
                    160, 352
            );
            g.setFill(Color.rgb(190, 190, 190));
            g.setFont(new Font("Impact", 20));
            g.fillText(
                    "Press 'Esc' to exit.",
                    160, 392
            );

            int oldHighScore = FileHandler.readHighScore(), newHighScore = board.getScore();
            if(oldHighScore < newHighScore){
                highScoreSurpassed = true;
            }
            if(highScoreSurpassed){
                g.setFill(Color.WHITESMOKE);
                g.setFont(new Font("Impact", 24));
                g.fillText(
                        "New High Score: " + newHighScore + "!\nOld High Score: " + oldHighScore + ".",
                        160, 502
                );
            }
        }
    }

    // Returns the color for each Brick.
    private Color getBrickColour(int brickColor){
        switch (brickColor) {
            case 0 -> { // I piece
                return Color.rgb(89,185,215);
            }
            case 1 -> { // O piece
                return Color.rgb(232,224,105);
            }
            case 2 -> { // T piece
                return Color.rgb(130,120,210);
            }
            case 3 -> { // S piece
                return Color.rgb(59,206,156);
            }
            case 4 -> { // Z piece
                return Color.rgb(227,38,54);
            }
            case 5 -> { // J piece
                return Color.rgb(34,68,145);
            }
            case 6 -> { // L piece
                return Color.rgb(255,183,51);
            }
            default -> {
                return Color.BLACK;
            }
        }
    }

    // Starts the graphical interface and handles all user input.
    @Override
    public void start(Stage primaryStage) {
        // Calls for the createContent() method, and sets the scene.
        scene = new Scene(createContent());

        // The input is handled:
        scene.setOnKeyPressed(keyEvent -> {
            // The variable time is decreased with each input in order
            // to avoid misinterpretations of the user's intent.
            //
            // It is especially important to decrease it whenever the user wishes to move the piece when it
            // is right about to stick to the board.
            //
            // The refreshDisplay() method is called to update the graphical elements.
            if(keyEvent.getCode().equals(KeyCode.DOWN) || keyEvent.getCode().equals(KeyCode.S)){
                time -= timeFractionBasedOnLevel() * 2;
                if(!board.isGameOver()){
                    board.moveDown();
                    board.increaseScore(1);
                }
                refreshDisplay();
            }
            if(keyEvent.getCode().equals(KeyCode.LEFT) || keyEvent.getCode().equals(KeyCode.A)){
                time -= timeFractionBasedOnLevel() * 2;
                if(board.getDownwardsCollision()){
                    time -= timeFractionBasedOnLevel() * 5;
                }
                if(!board.isGameOver()){
                    board.moveLeft();
                }
                refreshDisplay();
            }
            if(keyEvent.getCode().equals(KeyCode.RIGHT) || keyEvent.getCode().equals(KeyCode.D)){
                time -= timeFractionBasedOnLevel() * 2;
                if(board.getDownwardsCollision()){
                    time -= timeFractionBasedOnLevel() * 5;
                }
                if(!board.isGameOver()){
                    board.moveRight();
                }
                refreshDisplay();
            }
            if(keyEvent.getCode().equals(KeyCode.UP) || keyEvent.getCode().equals(KeyCode.W)){
                time -= timeFractionBasedOnLevel() * 2;
                if(board.getDownwardsCollision()){
                    time -= timeFractionBasedOnLevel() * 5;
                }
                board.rotateClockwise();
                refreshDisplay();
            }
            if(keyEvent.getCode().equals(KeyCode.SPACE)){
                while (!board.getDownwardsCollision()){
                    board.moveDown();
                }
                board.moveDown();
                refreshDisplay();
            }
            if(keyEvent.getCode().equals(KeyCode.X) || keyEvent.getCode().equals(KeyCode.C)){
                if(!board.isGameOver()){
                    board.holdBlock();
                }
                refreshDisplay();
            }
            if(keyEvent.getCode().equals(KeyCode.ESCAPE)){
                if(FileHandler.readHighScore() < board.getScore()){
                    FileHandler.writeNewHighScore(board.getScore());
                }
                System.exit(0);
            }

            // for testing purposes only
            if(keyEvent.getCode().equals(KeyCode.K)){
                board.setLevel(board.getLevel() + 1);
            }
            if(keyEvent.getCode().equals(KeyCode.P)){
                if(board.getBlockQueue().size() > 0){
                    for(Block b : board.getBlockQueue()){
                        System.out.print(b.getName() + ", ");
                    }
                    System.out.println();
                }
            }
            if(keyEvent.getCode().equals(KeyCode.V)){
                board.increaseScore(5000);
                refreshDisplay();
            }
        });

        // Applies the built scene.
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Runs the application.
    public static void main(String[] args) {
        launch(args);
    }
}
