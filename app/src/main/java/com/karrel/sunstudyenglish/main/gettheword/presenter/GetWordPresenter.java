package com.karrel.sunstudyenglish.main.gettheword.presenter;

import com.karrel.sunstudyenglish.main.gettheword.model.WordItem;

import java.util.ArrayList;

/**
 * Created by rell on 2017-07-14.
 */

public interface GetWordPresenter {

    void setView(GetWordPresenter.View view);

    void getTheWord();

    void showWord(ArrayList<String> wordsList);

    interface View {

        void startOcrActivity();

        void addWordItem(WordItem item);
    }
}
