package edu.carleton;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;

public class Loader {

    public Loader(String n) {
    }

    public HashMap load(String fileName) {
        HashMap<String, String> M = new HashMap<>();
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
            M.put(word, word);
        }
        return M;
    }
}
