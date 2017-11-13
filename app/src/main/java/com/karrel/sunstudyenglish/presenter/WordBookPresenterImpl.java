package com.karrel.sunstudyenglish.presenter;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.karrel.sunstudyenglish.model.GroupItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rell on 2017-07-21.
 */

public class WordBookPresenterImpl implements WordBookPresenter {

    private static final String TAG = "WordBookPresenterImpl";
    private final FirebaseDatabase mDatabase;
    private WordBookPresenter.View mView;
    private DatabaseReference mReference;
    private Query mQuery;

    public WordBookPresenterImpl() {
        // 파이어베이스 초기화
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference();
    }

    @Override
    public void setView(View view) {
        mView = view;
    }

    @Override
    public void getGroupName() {
        removeEventListeners();
        mQuery = mReference.child("users").child("karrel").child("words");
        mQuery.addValueEventListener(onGroupValueEventListener);
    }

    @Override
    public void onDestroy() {
        removeEventListeners();
    }

    /**
     * 이벤트를 삭제하자
     */
    private void removeEventListeners() {
        if (mQuery != null) {
            mQuery.removeEventListener(onGroupValueEventListener);
        }
    }

    private final ValueEventListener onGroupValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();
            List<GroupItem> list = new ArrayList<>();
            for (DataSnapshot snapshot : iterable) {
                Log.e(TAG, "getKey > " + snapshot.getKey());
                Log.e(TAG, "getValue > " + snapshot.getValue());
                GroupItem item = new GroupItem(snapshot.getKey(), snapshot.getValue().toString());
                list.add(item);
            }

            mView.setonGroupListItems(list);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}