package com.karrel.sunstudyenglish.presenter;

import com.karrel.sunstudyenglish.model.WordItem;

import java.util.ArrayList;

/**
 * Created by rell on 2017-07-14.
 */

public interface GetWordPresenter {

    void setView(GetWordPresenter.View view);

    void getTheWord();

    void onActivityResult(ArrayList<String> wordsList);

    void onLoadOnce();

    void saveList(ArrayList<WordItem> items);

    void removeItem(int position);

    void saveList();

    interface View {

        void startOcrActivity();

        void showProgress();

        void hideProgress();

        void addWordItem(WordItem wordItem);

        void removeItem(int position);

        void hideEmptyText();
    }
}
