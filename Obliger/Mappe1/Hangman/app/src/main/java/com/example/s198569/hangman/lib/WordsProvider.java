package com.example.s198569.hangman.lib;

import android.content.Context;
import android.content.res.Resources;

import com.example.s198569.hangman.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Provides an object containing all words currently avavilable in the app.
 * Words list is loaded and shuffled on initialization.
 */
public class WordsProvider {

    private Context context;
    private String[] raw_words;
    private Random r;
    private Resources res;
    private List<char[]> scrambled_words;

    public WordsProvider(Context context) {
        this.context = context;
        r = new Random();
        raw_words = context.getResources().getStringArray(R.array.words);
        scrambled_words = new ArrayList<>();

        for (String s : raw_words) {
            scrambled_words.add(s.toCharArray());
        }
        //Scrambling the words
        Collections.shuffle(scrambled_words);
    }

    /**
     * Returns a char array for randomized word for direct use in the game.
     * If all words have been used return an an cha array containing 0.
     * @return
     */
    public char[] getNextWord() {
        if (scrambled_words.size() > 0)
            return scrambled_words.remove(scrambled_words.size() - 1);

        return new char[]{'0'};
    }

}
