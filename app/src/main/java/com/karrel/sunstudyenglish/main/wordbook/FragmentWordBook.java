package com.karrel.sunstudyenglish.main.wordbook;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karrel.sunstudyenglish.R;
import com.karrel.sunstudyenglish.databinding.FragmentFragmentWordBookBinding;
import com.karrel.sunstudyenglish.main.wordbook.test.User;

public class FragmentWordBook extends Fragment {

    private final String TAG = "FragmentWordBook";
    private FragmentFragmentWordBookBinding mBinding;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;

    public FragmentWordBook() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_fragment_word_book, container, false);

        // 파이어베이스
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference();
        mReference.addChildEventListener(mChildEventListener);
        mReference.addListenerForSingleValueEvent(mSingeValueEventListener);
        mReference.addValueEventListener(mValueEventListener);

        // test firebase 1
        mBinding.firebase.setOnClickListener(v -> intputData());
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * //     * 파이어베이스를 테스트 하자
     * //
     */
    private void intputData() {
        mReference.setValue("hello", "Hello, firebase!");
        mReference.push();
    }

    private void writeNewUser(String userId, String name, String email) {
        User user = new User(name, email);

        mReference.child("users").child(userId).setValue(user).addOnCompleteListener(getActivity(), task -> Log.e(TAG, "onComplete"));
    }

    private ChildEventListener mChildEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Log.e(TAG, "ChildEventListener");
            Log.d(TAG, "onChildAdded > " + s);
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            Log.e(TAG, "ChildEventListener");
            Log.d(TAG, "onChildChanged > " + s);
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            Log.e(TAG, "ChildEventListener");
            Log.d(TAG, "onChildRemoved");
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            Log.e(TAG, "ChildEventListener");
            Log.d(TAG, "onChildMoved > " + s);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.e(TAG, "ChildEventListener");
            Log.d(TAG, "onCancelled > ");
        }
    };

    private ValueEventListener mSingeValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Log.e(TAG, "ValueEventListener");
            Log.d(TAG, "onDataChange > ");
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.e(TAG, "ValueEventListener");
            Log.d(TAG, "onCancelled > ");
        }
    };

    private ValueEventListener mValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Log.e(TAG, "ValueEventListener");
            Log.d(TAG, "onDataChange > " + dataSnapshot.toString());
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.e(TAG, "ValueEventListener");
            Log.d(TAG, "onCancelled > ");
        }
    };
}
