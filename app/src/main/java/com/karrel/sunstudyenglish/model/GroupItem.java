package com.karrel.sunstudyenglish.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by bodyfriend_dev on 2017. 7. 26..
 */

public class GroupItem implements Parcelable{
    private String key;
    private String value;
    private List<String> words;


    public GroupItem(String key, String value) {
        this.key = key;
        this.value = value;
        makeValueList(value);
    }

    protected GroupItem(Parcel in) {
        key = in.readString();
        value = in.readString();
        words = in.createStringArrayList();
    }

    public static final Creator<GroupItem> CREATOR = new Creator<GroupItem>() {
        @Override
        public GroupItem createFromParcel(Parcel in) {
            return new GroupItem(in);
        }

        @Override
        public GroupItem[] newArray(int size) {
            return new GroupItem[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(key);
        parcel.writeString(value);
        parcel.writeStringList(words);
    }
}
