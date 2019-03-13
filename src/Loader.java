import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Loader {

    private ArrayList<String> realWords;

    public Loader() {
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
            realWords.add(word);
        }
    }

    public HashMap<String, MapNode> loadHashMap(String fileName) {
        load(fileName);
        HashMap<String, MapNode> M = new HashMap<>();
        for (String word : realWords) {
            MapNode newNode = new MapNode(word);
            M.put(word, newNode);
        }
        return M;
    }
}
