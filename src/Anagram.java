import java.util.ArrayList;
import java.util.HashSet;

public class Anagram {

    private String word;
    private HashSet<String> anagrams;
    private Loader loader;
    private HashSet<String> D;

    /**
     * Constructor for the Anagram class.
     * Initializes empty values for all instance variables.
     */
    public Anagram() {
        word = null;
        anagrams = new HashSet<>();
        loader = new Loader();
        D = new HashSet<>();
    }

    /**
     * Loads words from a file, then creates a HashSet containing a String object for each word
     * @param fileName the name of the file from which the words are read
     */
    public void loadDictionary(String fileName) {
        loader = new Loader();
        D = loader.loadHashSet(fileName);
    }

    /**
     * Generates anagrams for the given word.
     * Calls the subAnagram() method, which recursively chooses each letter of the original word
     * and generates all the possible anagrams of the remaining letters, then concatenating those
     * possibilities to the initially chosen letter.
     * @param w the word from which the anagrams will be generated
     */
    public void genAnagrams(String w) {
        anagrams = new HashSet<>();
        word = w.toUpperCase();
        char[] chars = word.toCharArray();
        ArrayList<String> possibilities = subAnagram(chars);
        for (String p : possibilities) {
            if (D.contains(p) && !anagrams.contains(p)) {
                anagrams.add(p);
            }
        }
    }

    private ArrayList<String> subAnagram(char[] subChars) {
        ArrayList<String> ans = new ArrayList<>();
        if (subChars.length == 1) {
            ans.add(Character.toString(subChars[0]));
        } else {
            for (int i = 0; i < subChars.length; i++) {
                char c = subChars[i];
                char[] remaining = new char[subChars.length-1];
                for (int n = 0; n < subChars.length; n++) {
                    if (n < i) {
                        remaining[n] = subChars[n];
                    } else if (n > i) {
                        remaining[n-1] = subChars[n];
                    }
                }
                ArrayList<String> subAns = subAnagram(remaining);
                for (String subAn : subAns) {
                    String a = Character.toString(c) + subAn;
                    ans.add(a);
                }
            }
        }
        return ans;
    }

    /**
     * Prints the anagrams generated from the last call of the genAnagrams() method.
     */
    public void printAnagrams() {
        System.out.format("\nAnagrams for %s:\n\n", word);
        for (String a : anagrams) {
            System.out.println(a);
        }
        System.out.println();
    }

    /**
     * Main method for the Anagram class. Generates anagrams of a given word.
     * Draws dictionary of valid words from file specified with --dictFile=fileName
     * @param args the arguments used to generate anagrams:
     *             args[0] = word;
     *             Optional: args[1] = --dictFile=fileName;
     */
    public static void main(String[] args) {
        String dictFile = null;
        if (args.length < 1) {
            System.out.println("Usage: java Anagram word [--dictFile=fileName]");
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
            dictFile = "Dictionaries/english_words.txt";
        }
        String w = args[0];
        Anagram A = new Anagram();
        A.loadDictionary(dictFile);
        A.genAnagrams(w);
        A.printAnagrams();
    }
}
