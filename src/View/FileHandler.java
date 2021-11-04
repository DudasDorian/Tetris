package View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class FileHandler {
    // Writes the new high score.
    public static void writeNewHighScore(int newHighScore){
        try{
            FileWriter writer = new FileWriter("high-score.txt");
            writer.write("" + newHighScore);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Fetches the stored high score.
    public static int readHighScore(){
        try {
            Scanner reader = new Scanner(new File("high-score.txt"));
            int toReturn = reader.nextInt();
            reader.close();
            return toReturn;
        // The high score is assumed to be 0 if no other informant says otherwise.
        } catch (FileNotFoundException | NoSuchElementException e) {
            return 0;
        }
    }
}
