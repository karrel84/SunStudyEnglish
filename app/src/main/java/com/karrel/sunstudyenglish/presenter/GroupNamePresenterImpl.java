package com.karrel.sunstudyenglish.presenter;

import com.karrel.sunstudyenglish.model.GroupItem;

/**
 * Created by bodyfriend_dev on 2017. 7. 27..
 */

public class GroupNamePresenterImpl implements GroupNamePresenter {

    private GroupNamePresenter.View mView;

    public GroupNamePresenterImpl(GroupNamePresenter.View view) {
        mView = view;
    }

    @Override
    public void onItemClick(GroupItem mGroupItem) {
        if(mGroupItem == null) mView.onError("데이터가 존재하지 않아요");

        mView.startMemorizeActivity(mGroupItem);
    }
}
