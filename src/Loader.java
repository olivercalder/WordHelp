import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Loader {

    private String currentFileName;
    private HashSet<String> dictionary;

    /**
     * Constructor for the Loader class.
     * Sets currentFileName to null and initializes new HashSet for realWords.
     */
    public Loader() {
        currentFileName = null;
        dictionary = new HashSet<>();
    }

    /**
     * Reads words from a file and stores them in the instance variable HashSet realWords
     * @param fileName the name of the file from which words are read
     */
    private void load(String fileName) {
        dictionary = new HashSet<>();
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
            String word = scanner.nextLine().toUpperCase();
            dictionary.add(word);
        }
    }

    /**
     * Reads words from a file using the load() method, then creates a HashMap containing a GraphNode object for each word.
     * @param fileName the name of the file from which the words are read
     * @return a HashMap containing MapNode objects for each word in the file
     */
    public HashMap<String, GraphNode> loadHashMap(String fileName) {
        if (!fileName.equals(currentFileName)) {
            load(fileName);
        }
        HashMap<String, GraphNode> graph = new HashMap<>();
        for (String word : dictionary) {
            GraphNode newNode = new GraphNode(word);
            graph.put(word, newNode);
        }
        return graph;
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
        return dictionary;
    }
}
