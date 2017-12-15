package com.karrel.sunstudyenglish.base.net;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.karrel.sunstudyenglish.model.WordItem;

import rx.subjects.PublishSubject;

/**
 * Created by Rell on 2017. 12. 15..
 * <p>
 * 원격 저장소에 등록된 데이터들을(파이어베이스 등) 한곳에서 관리하기 위해 만들었다
 */
public class NetManager {

    private static NetManager instance;
    private final FirebaseDatabase mDatabase;
    private DatabaseReference mReference;

    public static NetManager getInstance() {
        if (instance == null) {
            instance = new NetManager();
        }
        return instance;
    }

    public NetManager() {
        // 파이어베이스 초기화
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference();
    }

    public Query getWords() {
        PublishSubject<WordItem> subject = PublishSubject.create();

        return mReference.child("users").child("karrel").child("words");
    }

    public PublishSubject<WordItem> getWord(String word) {
        PublishSubject<WordItem> subject = PublishSubject.create();

        Query query = mReference.child("words").child(word);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String meaning = dataSnapshot.child("meaning").getValue(String.class);
                String word = dataSnapshot.child("word").getValue(String.class);
                subject.onNext(new WordItem(word, meaning));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return subject;
    }
}
