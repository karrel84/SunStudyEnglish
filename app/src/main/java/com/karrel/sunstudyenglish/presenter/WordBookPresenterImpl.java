package com.karrel.sunstudyenglish.presenter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.karrel.mylibrary.RLog;
import com.karrel.sunstudyenglish.base.net.NetManager;
import com.karrel.sunstudyenglish.model.GroupItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rell on 2017-07-21.
 */

public class WordBookPresenterImpl implements WordBookPresenter, ValueEventListener {

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
        // 방어코드 : 이전에 등록한 리스너가 있다면 해제한다.
        removeEventListeners();
        // getWords에 대한 쿼리를 리턴받는다
        mQuery = NetManager.getInstance().getWords();
        // 리스너를 등록해준다.
        mQuery.addListenerForSingleValueEvent(this);
    }

    @Override
    public void onDestroy() {
        RLog.d("onDestroy");
        removeEventListeners();
    }

    /**
     * 이벤트를 삭제하자
     */
    private void removeEventListeners() {
        RLog.d("removeEventListeners");
        if (mQuery != null) {
            mQuery.removeEventListener(this);
        }
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        RLog.d("onDataChange");
        Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();
        List<GroupItem> list = new ArrayList<>();
        for (DataSnapshot snapshot : iterable) {
            GroupItem item = new GroupItem(snapshot.getKey(), snapshot.getValue().toString());
            list.add(item);
            RLog.d("item : " + item.toString());
        }

        mView.setonGroupListItems(list);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        RLog.d("onCancelled");
    }
}