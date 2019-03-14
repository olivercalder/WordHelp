import java.util.ArrayList;
import java.util.HashSet;

public class Wildcard {

    private String wildWord;
    private ArrayList<Integer> indices;
    private ArrayList<String> words;
    private Loader loader;
    private HashSet<String> D;
    private Generator G;

    public Wildcard() {
        wildWord = null;
        words = new ArrayList<>();
        loader = new Loader();
        D = new HashSet<>();
        G = new Generator();
    }

    public void loadDictionary(String fileName) {
        loader = new Loader();
        D = loader.loadDictionary(fileName);
    }

    public void genWords(String w) {
        words = new ArrayList<>();
        wildWord = w.toUpperCase();
        char[] origChars = wildWord.toCharArray();
        indices = new ArrayList<>();
        for (Integer i = 0; i < origChars.length; i++) {
            if (origChars[i] == ('#') || origChars[i] == ('%')) {
                indices.add(i);
            }
        }
        ArrayList<String> currentWords = new ArrayList<>();
        currentWords.add(wildWord);
        ArrayList<String> newWords = new ArrayList<>();
        for (Integer index : indices) {
            newWords = new ArrayList<>();
            for (String word : currentWords) {
                newWords.addAll(G.cycleSlotInWord(index, word));
            }
            currentWords = newWords;
        }
        for (String possibility : newWords) {
            if (D.contains(possibility)) {
                words.add(possibility);
            }
        }
    }

    public void printWords() {
        System.out.format("\nPossible words which fulfill wildcards of %s:\n\n", wildWord);
        for (String word : words) {
            System.out.println(word);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        String dictFile = null;
        if (args.length < 1) {
            System.out.println("Usage: java Wildcard word [--dictFile=fileName]");
            System.exit(1);
        } else if (args.length == 2) {
            dictFile = null;
            if (args[1].length() > 11 && args[1].substring(0, 11).equals("--dictFile=")) {
                dictFile = args[1].substring(11);
            } else {
                System.out.format("Argument %s not recognized\n", args[1]);
                System.exit(1);
            }
        }
        if (dictFile == null) {
            dictFile = "sowpods.txt";
        }
        String w = args[0];
        Wildcard W = new Wildcard();
        W.loadDictionary(dictFile);
        W.genWords(w);
        W.printWords();
    }
}
