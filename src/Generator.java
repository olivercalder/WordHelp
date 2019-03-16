import java.util.ArrayList;

public class Generator {

    private char[] alphabet;

    /**
     * Constructor for the Generator class.
     * Initializes the English alphabet as the default value for alphabet.
     */
    public Generator() {
        alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    }

    /**
     * Generates a list of words by replacing one letter of a given word with
     * every other letter of the alphabet.
     * @param i the index of the letter which will be changed
     * @param word the word into which different letters will be substituted
     * @return an ArrayList containing Strings for every word which was generated
     */
    public ArrayList<String> cycleSlotInWord(int i, String word) {
        ArrayList<String> words = new ArrayList<>();
        for (char L : alphabet) {
            String newWord = "";
            char[] letters = word.toUpperCase().toCharArray();
            letters[i] = L;
            for (int k = 0; k < letters.length; k++) {
                newWord = newWord + Character.toString(letters[k]);
            }
            words.add(newWord);
        }
        return words;
    }

    /**
     * Generates a list of words which differ from a given word by a single letter.
     * Operates by calling cycleSlotIntoWord() on every index of the given word.
     * @param word the word for which possible neighbors are generated
     * @return an ArrayList containing Strings for every word which was generated
     */
    public ArrayList<String> genNeighbors(String word) {
        ArrayList<String> nearWords = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            ArrayList<String> words = cycleSlotInWord(i, word);
            nearWords.addAll(words);
        }
        return nearWords;
    }

    /**
     * Changes the alphabet used for word generation to a new alphabet. Each character in the inputted
     * String will be treated as a letter of the new alphabet.
     * @param a a String containing every character which will be considered part of the alphabet
     */
    public void setAlphabet(String a) {
        alphabet = a.toCharArray();
    }

}
