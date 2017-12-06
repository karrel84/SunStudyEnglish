package com.karrel.sunstudyenglish.presenter;

import com.karrel.sunstudyenglish.model.GroupItem;
import com.karrel.sunstudyenglish.model.WordItem;

/**
 * Created by bodyfriend_dev on 2017. 7. 27..
 */

public interface MemorizePresenter {
    void onParseExtra(GroupItem mItem);

    void getWords(GroupItem mItem);

    interface View {

        void onErrorFinish(String s);

        void addWordItem(WordItem item1);

        void setTitle(String title);
    }
}
