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

    public void setAlphabet(String a) {
        G.setAlphabet(a);
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
            if (origChars[i] == '#' || origChars[i] == '%' || origChars[i] == '*') {
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
        String alphabet = null;
        if (args.length < 1) {
            System.out.println("Usage: java Wildcard word [--dictFile=fileName] [--alphabet=ABCDEFGHIJKLMNOPQRSTUVWXYZ]");
            System.exit(1);
        } else if (args.length > 1) {
            dictFile = null;
            for (int i = 1; i < args.length; i++) {
                if (args[i].length() > 11 && args[i].substring(0, 11).equals("--dictFile=")) {
                    dictFile = args[i].substring(11);
                } else if (args[i].length() > 11 && args[i].substring(0, 11).equals("--alphabet=")) {
                    alphabet = args[i].substring(11);
                } else {
                    System.out.format("Argument %s not recognized\n", args[1]);
                    System.exit(1);
                }
            }
        }
        String w = args[0];
        Wildcard W = new Wildcard();
        if (alphabet != null) {
            W.setAlphabet(alphabet.toUpperCase());
        }
        if (dictFile == null) {
            dictFile = "sowpods.txt";
        }
        W.loadDictionary(dictFile);
        W.genWords(w);
        W.printWords();
    }
}
