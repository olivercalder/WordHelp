import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Loader {

    private String currentFileName;
    private ArrayList<String> realWords;

    public Loader() {
        currentFileName = null;
        realWords = new ArrayList<>();
    }

    public void load(String fileName) {
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

    public HashSet<String> loadDictionary(String fileName) {
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
