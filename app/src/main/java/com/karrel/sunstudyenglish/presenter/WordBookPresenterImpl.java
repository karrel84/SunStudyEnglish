package com.karrel.sunstudyenglish.presenter;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.karrel.sunstudyenglish.model.WordItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rell on 2017-07-21.
 */

public class WordBookPresenterImpl implements WordBookPresenter {

    private static final String TAG = "WordBookPresenterImpl";
    private final FirebaseDatabase mDatabase;
    private WordBookPresenter.View mView;

    public WordBookPresenterImpl() {
        // 파이어베이스 초기화
        mDatabase = FirebaseDatabase.getInstance();
    }

    @Override
    public void setView(View view) {
        mView = view;
    }

    @Override
    public void getWord() {
        DatabaseReference reference = mDatabase.getReference();

        Query query = reference.child("words");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("onDataChange > " + dataSnapshot.getValue());
                Log.e(TAG, "onDataChange > " + dataSnapshot.getValue());

                Log.d(TAG, "dataSnapshot.getKey()> " + dataSnapshot.getKey());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled > " + databaseError.toString());
            }
        });
//
//        List<WordItem> list = getTheWord();
//        mView.showList(list);
    }

    private List<WordItem> getTheWord() {
        // TODO 파이어베이스에서 아이템을 가져와야지
        List<WordItem> list = new ArrayList<>();
        list.add(new WordItem("hello", "안녕"));
        list.add(new WordItem("korea", "한국"));
        list.add(new WordItem("china", "중국"));
        list.add(new WordItem("japen", "일본"));
        return list;
    }
}
