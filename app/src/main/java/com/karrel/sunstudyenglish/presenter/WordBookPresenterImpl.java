package com.karrel.sunstudyenglish.presenter;

import com.google.firebase.database.Query;
import com.karrel.sunstudyenglish.base.net.NetManager;

/**
 * Created by rell on 2017-07-21.
 */

public class WordBookPresenterImpl implements WordBookPresenter {

    private static final String TAG = "WordBookPresenterImpl";
    private WordBookPresenter.View mView;
    private Query mQuery;

    public WordBookPresenterImpl() {

    }

    @Override
    public void setView(View view) {
        mView = view;
    }

    @Override
    public void getGroupName() {
        // getWords에 대한 쿼리를 리턴받는다
        NetManager.getInstance().getWords().subscribe(mView::setonGroupListItems);
    }
}