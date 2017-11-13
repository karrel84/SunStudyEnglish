package com.karrel.sunstudyenglish.presenter;

import com.karrel.sunstudyenglish.model.GroupItem;
import com.karrel.sunstudyenglish.model.WordItem;

import java.util.List;

/**
 * Created by rell on 2017-07-21.
 */

public interface WordBookPresenter {
    void setView(WordBookPresenter.View view);

    void getGroupName();

    void onDestroy();

    interface View {

        void setonGroupListItems(List<GroupItem> list);
    }
}
