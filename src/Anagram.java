import java.util.Arrays;
import java.util.HashSet;
import java.lang.Math;

public class Anagram {

    private String word;
    private HashSet<String> anagrams;
    private Boolean recursive;
    private Loader loader;
    private HashSet<String> dictionary;

    /**
     * Constructor for the Anagram class.
     * Initializes empty values for all instance variables.
     */
    public Anagram() {
        word = null;
        anagrams = new HashSet<>();
        recursive = null;
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
     * Generates anagrams for the given word.
     * Checks the size of the word vs the size of the dictionary to decide whether to use iteration or recursion.
     * @param w the word from which the anagrams will be generated
     */
    public void generateAnagrams(String w) {
        if (dictionary.size() > Math.pow(w.length(), w.length())) {
            generateAnagramsRecursive(w);
        } else {
            generateAnagramsIterative(w);
        }
    }

    /**
     * Generates anagrams for the given word.
     * Checks every word in the dictionary to see if it contains the same combination of letters as the given word.
     * @param w the word from which the anagrams will be generated
     */
    public void generateAnagramsIterative(String w) {
        recursive = false;
        anagrams = new HashSet<>();
        word = w.toUpperCase();
        String letters = sortString(word);
        for (String realWord : dictionary) {
            if (sortString(realWord).equals(letters)) {
                anagrams.add(realWord);
            }
        }
    }

    private String sortString(String word) {
        char[] charArray = word.toCharArray();
        Arrays.sort(charArray);
        String sortedWord = new String(charArray);
        return sortedWord;
    }

    /**
     * Generates anagrams for the given word.
     * Calls the subAnagram() method, which recursively chooses each letter of the original word
     * and generates all the possible anagrams of the remaining letters, then concatenating those
     * possibilities to the initially chosen letter.
     * @param w the word from which the anagrams will be generated
     */
    public void generateAnagramsRecursive(String w) {
        recursive = true;
        anagrams = new HashSet<>();
        word = w.toUpperCase();
        char[] chars = word.toCharArray();
        HashSet<String> possibilities = subAnagram(chars);
        for (String p : possibilities) {
            if (dictionary.contains(p) && !anagrams.contains(p)) {
                anagrams.add(p);
            }
        }
    }

    private HashSet<String> subAnagram(char[] subChars) {
        HashSet<String> ans = new HashSet<>();
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
                HashSet<String> subAns = subAnagram(remaining);
                for (String subAn : subAns) {
                    String a = c + subAn;
                    ans.add(a);
                }
            }
        }
        return ans;
    }

    /**
     * Prints the anagrams generated from the last call of the generateAnagrams() method.
     */
    public void printAnagrams() {
        String type = null;
        if (recursive) {
            type = "Recursive";
        } else {
            type = "Iterative";
        }
        System.out.format("\n%s Anagrams for %s:\n\n", type, word);
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
        Boolean recursive = null;
        if (args.length < 1) {
            System.out.println("Usage: java Anagram word [--dictFile=fileName] [--recursive=true/false]");
            System.exit(1);
        } else if (args.length >= 2) {
            dictFile = null;
            for (int i = 1; i < args.length; i++)
            if (args[i].length() > 11 && args[i].substring(0, 11).equals("--dictFile=")) {
                dictFile = args[i].substring(11);
            } else if (args[i].length() > 12 && args[i].substring(0, 12).equals("--recursive=")) {
                if (args[i].substring(12).toLowerCase().equals("true")) {
                    recursive = true;
                } else if (args[i].substring(12).toLowerCase().equals("false")) {
                    recursive = false;
                } else {
                    System.out.format("Argument %s not recognized\nValid values: true false", args[i]);
                    System.exit(1);
                }
            } else {
                System.out.format("Argument %s not recognized\n", args[i]);
                System.exit(1);
            }
        }
        if (dictFile == null) {
            dictFile = "Dictionaries/english_words.txt";
        }
        String w = args[0];
        Anagram anagram = new Anagram();
        anagram.loadDictionary(dictFile);
        if (recursive == null) {
            anagram.generateAnagrams(w);
        } else if (recursive) {
            anagram.generateAnagramsRecursive(w);
        } else {
            anagram.generateAnagramsIterative(w);
        }
        anagram.printAnagrams();
    }
}
