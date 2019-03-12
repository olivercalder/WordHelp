package edu.carleton;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Loader {

    public Loader() {
    }

    public ArrayList<String> load(String fileName) {
        ArrayList<String> realWords = new ArrayList<>();
        File wordsFile = new File(fileName);
        Scanner scanner = null;
        try {
            scanner = new Scanner(wordsFile);
        }
        catch (FileNotFoundException e) {
            System.err.println(e);
            System.exit(1);
        }
        while (scanner.hasNextLine()) {
            String word = scanner.nextLine();
            realWords.add(word);
        }
        return realWords;
    }
}
