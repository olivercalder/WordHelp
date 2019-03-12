package edu.carleton;

import java.util.ArrayList;

public class Generator {

    private static char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    private static ArrayList<String> cycleSlotInWord(int i, String word) {
        ArrayList<String> words = new ArrayList<>();
        for (char L : alphabet) {
            String newWord = "";
            char[] letters = word.toUpperCase().toCharArray();
            letters[i] = L;
            for (int k = 0; k < letters.length; k++) {
                newWord = newWord + Character.toString(letters[i]);
                words.add(newWord);
            }
        }
        return words;
    }

    public static ArrayList<String> neighbors(String word) {
        ArrayList<String> nearWords = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            ArrayList<String> words = cycleSlotInWord(i, word);
            nearWords.addAll(words);
        }
        return nearWords;
    }

}
