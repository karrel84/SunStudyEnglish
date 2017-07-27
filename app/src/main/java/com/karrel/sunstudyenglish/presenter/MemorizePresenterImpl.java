package com.karrel.sunstudyenglish.presenter;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.karrel.sunstudyenglish.model.GroupItem;
import com.karrel.sunstudyenglish.model.WordItem;

/**
 * Created by bodyfriend_dev on 2017. 7. 27..
 */

public class MemorizePresenterImpl implements MemorizePresenter {
    private MemorizePresenter.View mView;

    private final DatabaseReference mReference;
    private final FirebaseDatabase mDatabase;
    private final String TAG = "MemorizePresenterImpl";


    public MemorizePresenterImpl(MemorizePresenter.View view) {
        mView = view;
        // 파이어베이스 초기화
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference();
    }

    @Override
    public void validityCheck(GroupItem mItem) {
        if (mItem == null) {
            mView.onErrorFinish("아이템의 값이 없습니다.");
        }
    }

    @Override
    public void getWords(GroupItem item) {
        // init firebase
        for (String word : item.getWords()) {
            Query query = mReference.child("words").child(word);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String meaning = dataSnapshot.child("meaning").getValue(String.class);
                    String word = dataSnapshot.child("word").getValue(String.class);
                    mView.addWordItem(new WordItem(word, meaning));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

}
