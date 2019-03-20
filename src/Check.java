import java.util.HashSet;

public class Check {

    private String word;
    private boolean found;
    private Loader loader;
    private HashSet<String> dictionary;

    /**
     * Constructor for the Check class.
     * Initializes empty values for all instance variables.
     */
    public Check() {
        word = null;
        found = false;
        loader = new Loader();
        dictionary = new HashSet<>();
    }

    /**
     * Loads words from a file, then creates a HashSet containing a String object for each word
     * @param fileName the name of the file from which the words are read
     */
    public void loadDictionary(String fileName) {
        loader = new Loader();
        dictionary = loader.loadHashSet(fileName);
    }

    /**
     * Checks whether the dictionary contains a given word
     * @param w the word which is to be checked
     */
    public void checkWord(String w) {
        word = w.toUpperCase();
        found = false;
        if (dictionary.contains(word)) {
            found = true;
        }
    }

    /**
     * Prints a message stating whether or not the most recently checked word is in the dictionary
     */
    public void printCheck() {
        if (found) {
            System.out.format("\n%s is a valid word in the dictionary.\n\n", word);
        } else {
            System.out.format("\n%s is NOT a valid word in the dictionary.\n\n", word);
        }
    }

    /**
     * Main method for the Check class. Checks whether or not a given word is in the dictionary.
     * Draws dictionary of valid words from file specified with --dictFile=fileName
     * @param args the arguments used to check the validity of a word:
     *             args[0] = word;
     *             Optional: args[1] = --dictFile=fileName;
     */
    public static void main(String[] args) {
        String dictFile = null;
        if (args.length < 1) {
            System.out.println("Usage: java Check word [--dictFile=fileName]");
            System.exit(1);
        } else if (args.length >= 2) {
            dictFile = null;
            for (int i = 1; i < args.length; i++)
                if (args[i].length() > 11 && args[i].substring(0, 11).equals("--dictFile=")) {
                    dictFile = args[1].substring(11);
                } else {
                    System.out.format("Argument %s not recognized\n", args[i]);
                    System.exit(1);
                }
        }
        if (dictFile == null) {
            dictFile = "Dictionaries/english_words.txt";
        }
        String w = args[0];
        Check check = new Check();
        check.loadDictionary(dictFile);
        check.checkWord(w);
        check.printCheck();
    }
}
