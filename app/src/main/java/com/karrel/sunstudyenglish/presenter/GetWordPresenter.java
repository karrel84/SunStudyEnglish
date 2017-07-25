package com.karrel.sunstudyenglish.presenter;

import com.karrel.sunstudyenglish.model.WordItem;

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

        void showProgress();

        void hideProgress();

        void onCompleted(ArrayList<WordItem> wordItems);
    }
}
