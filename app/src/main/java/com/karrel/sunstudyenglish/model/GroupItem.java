package com.karrel.sunstudyenglish.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by bodyfriend_dev on 2017. 7. 26..
 */

public class GroupItem {
    private String key;
    private String value;
    private List<String> words;


    public GroupItem(String key, String value) {
        this.key = key;
        this.value = value;
        makeValueList(value);
    }

    private void makeValueList(String value) {
        String[] values = value.split("\\|");

        words = new ArrayList<>(Arrays.asList(values));

    }

    @Override
    public String toString() {
        return "GroupItem{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public List<String> getWords() {
        return words;
    }
}
