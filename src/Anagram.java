import java.util.ArrayList;
import java.util.HashSet;

public class Anagram {

    private String word;
    private ArrayList<String> anagrams;
    private Loader loader;
    private HashSet<String> D;

    public Anagram() {
        word = null;
        anagrams = new ArrayList<>();
        loader = new Loader();
        D = new HashSet<>();
    }

    public void loadDictionary(String fileName) {
        loader = new Loader();
        D = loader.loadDictionary(fileName);
    }

    public void genAnagrams(String w) {
        anagrams = new ArrayList<>();
        word = w.toUpperCase();
        char[] chars = word.toCharArray();
        ArrayList<String> possibilities = subAnagram(chars);
        for (String p : possibilities) {
            if (D.contains(p)) {
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

    public void printAnagrams() {
        System.out.format("\nAnagrams for %s:\n\n", word);
        for (String a : anagrams) {
            System.out.println(a);
        }
        System.out.println();
    }

    public static void main(String args[]) {
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
            dictFile = "sowpods.txt";
        }
        String w = args[0];
        Anagram A = new Anagram();
        A.loadDictionary(dictFile);
        A.genAnagrams(w);
        A.printAnagrams();
    }
}
