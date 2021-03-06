import java.util.ArrayList;
import java.util.HashSet;

public class Wildcard {

    private String wildWord;
    private ArrayList<Integer> indices;
    private ArrayList<String> words;
    private Loader loader;
    private HashSet<String> dictionary;
    private Generator generator;

    /**
     * Constructor for the Wildcard class.
     * Initializes empty values for all instance variables.
     */
    public Wildcard() {
        wildWord = null;
        indices = new ArrayList<>();
        words = new ArrayList<>();
        loader = new Loader();
        dictionary = new HashSet<>();
        generator = new Generator();
    }

    /**
     * Changes the alphabet used for word generation to a new alphabet. Each character in the inputted
     * String will be treated as a letter of the new alphabet.
     * @param a a String containing every character which will be considered part of the alphabet
     */
    public void setAlphabet(String a) {
        generator.setAlphabet(a);
    }

    /**
     * Loads words from a file, then creates a HashMap containing a MapNode object for each word
     * @param fileName the name of the file from which the words are read
     */
    public void loadDictionary(String fileName) {
        loader = new Loader();
        dictionary = loader.loadHashSet(fileName);
    }

    /**
     * Generates words which fulfill the letters and wildcard characters of a given string.
     * Records the indices in which wildcard characters exist, and then iterates through
     * those indices, replacing them with every letter of the alphabet. At each iteration,
     * the new list of words is again passed through, and the next index of wildcard
     * characters is replaced.
     * In the end, the ArrayList words contains Strings of possible words generated
     * by combining every possible combination of letters into the wildcard indices.
     * These possible words are then tested against the dictionary of valid words.
     * @param w the String containing letters and wildcard characters, from which
     *          possible words are generated
     */
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
                newWords.addAll(generator.cycleSlotInWord(index, word));
            }
            currentWords = newWords;
        }
        for (String possibility : newWords) {
            if (dictionary.contains(possibility)) {
                words.add(possibility);
            }
        }
    }

    /**
     * Prints the words generated by the last call of the genWords() method.
     */
    public void printWords() {
        System.out.format("\nPossible words which fulfill wildcards of %s:\n\n", wildWord);
        for (String word : words) {
            System.out.println(word);
        }
        System.out.println();
    }

    /**
     * Main method for the Wildcard class. Generates words which fulfill the wildcard indices of a given word.
     * Draws dictionary of valid words from a file specified with --dictFile=fileName
     * Generates words using the alphabet specified with --alphabet=ABC....
     * @param args the arguments used to generate the words:
     *             args[0] = word with wildcard characters
     *             Optional: args[i] for i > 1 can include: --dictfile=fileName --alphabet=ABC....
     */
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
        Wildcard wildcard = new Wildcard();
        if (alphabet != null) {
            wildcard.setAlphabet(alphabet.toUpperCase());
        }
        if (dictFile == null) {
            dictFile = "Dictionaries/english_words.txt";
        }
        wildcard.loadDictionary(dictFile);
        wildcard.genWords(w);
        wildcard.printWords();
    }
}
