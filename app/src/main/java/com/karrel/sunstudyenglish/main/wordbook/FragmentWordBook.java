package com.karrel.sunstudyenglish.main.wordbook;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karrel.sunstudyenglish.R;
import com.karrel.sunstudyenglish.databinding.FragmentFragmentWordBookBinding;

public class FragmentWordBook extends Fragment {

    private final String TAG = "FragmentWordBook";
    private FragmentFragmentWordBookBinding mBinding;

    public FragmentWordBook() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_fragment_word_book, container, false);
        // 파이어베이스 액티비티로 이동
        mBinding.firebase.setOnClickListener(v -> testFireBase());
        return mBinding.getRoot();
    }

    /**
     * //     * 파이어베이스를 테스트 하자
     * //
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
