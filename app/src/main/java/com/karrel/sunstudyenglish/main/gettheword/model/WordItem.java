package com.karrel.sunstudyenglish.main.gettheword.model;

/**
 * Created by 이주영 on 2017-06-21.
 */

public class WordItem {
    public String word;
    public String meaning;

    public WordItem(String word, String meaning) {
        this.word = word;
        this.meaning = meaning;
    }

    @Override
    public String toString() {
        return "WordItem{" +
                "word='" + word + '\'' +
                ", meaning='" + meaning + '\'' +
                '}';
    }
}