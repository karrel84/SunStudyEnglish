package com.karrel.sunstudyenglish;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karrel.sunstudyenglish.base.BaseActivity;

public class FireBaseActivity extends BaseActivity {

    private static final String TAG = "FireBaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);

        // 버튼들을 셋업한다.
        setupButtons();
    }

    /**
     * 버튼들을 셋업한다.
     */
    private void setupButtons() {
        findViewById(R.id.firebase_test).setOnClickListener(v -> testFireBase());
    }

    /**
     * 파이어베이스를 테스트 하자
     */
    private void testFireBase() {
        Log.d(TAG, ":: testFireBase");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        myRef.setValue("my message!! ");


        myRef.child("users").child("1234").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, ":: onDataChange");
                Object obj = dataSnapshot.getValue();
                if (obj != null) {
                    Log.d(TAG, "obj -> " + obj.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, ":: onDataChange");
                Object obj = dataSnapshot.getValue();
                Log.d(TAG, "obj -> " + obj.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                dataSnapshot.getValue(String.class)
//                Log.d(TAG, "value -> " + dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });
    }
}
