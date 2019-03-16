import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Loader {

    private String currentFileName;
    private ArrayList<String> realWords;

    /**
     * Constructor for the Loader class.
     * Sets currentFileName to null and initializes new ArrayList for realWords.
     */
    public Loader() {
        currentFileName = null;
        realWords = new ArrayList<>();
    }

    /**
     * Reads words from a file and stores them in the instance variable ArrayList realWords
     * @param fileName the name of the file from which words are read
     */
    private void load(String fileName) {
        realWords = new ArrayList<>();
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
            realWords.add(word.toUpperCase());
        }
    }

    /**
     * Reads words from a file using the load() method, then creates a HashMap containing a MapNode object for each word.
     * @param fileName the name of the file from which the words are read
     * @return a HashMap containing MapNode objects for each word in the file
     */
    public HashMap<String, MapNode> loadHashMap(String fileName) {
        if (!fileName.equals(currentFileName)) {
            load(fileName);
        }
        HashMap<String, MapNode> M = new HashMap<>();
        for (String word : realWords) {
            MapNode newNode = new MapNode(word);
            M.put(word, newNode);
        }
        return M;
    }

    /**
     * Reads words from a file using the load() method, then creates a HashSet containing all the read words.
     * @param fileName the name of the file from which the words are read
     * @return a HashSet containing strings for each word in the file
     */
    public HashSet<String> loadHashSet(String fileName) {
        if (!fileName.equals(currentFileName)) {
            load(fileName);
        }
        HashSet<String> D = new HashSet<>();
        for (String word : realWords) {
            D.add(word);
        }
        return D;
    }
}
