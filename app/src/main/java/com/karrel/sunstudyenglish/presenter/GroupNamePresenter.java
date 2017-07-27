package com.karrel.sunstudyenglish.presenter;

import com.karrel.sunstudyenglish.model.GroupItem;

/**
 * Created by bodyfriend_dev on 2017. 7. 27..
 */

public interface GroupNamePresenter {


    void onItemClick(GroupItem mGroupItem);

    interface View {

        void onError(String s);

        void startMemorizeActivity(GroupItem mGroupItem);
    }
}
