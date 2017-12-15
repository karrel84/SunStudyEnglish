package com.karrel.sunstudyenglish.presenter;

import com.karrel.sunstudyenglish.model.WordItem;

/**
 * Created by Rell on 2017. 12. 7..
 */

public interface CardMemorizePresenter {
    void onLoad();

    interface View {

        void addWordItem(WordItem wordItem);
    }
}
