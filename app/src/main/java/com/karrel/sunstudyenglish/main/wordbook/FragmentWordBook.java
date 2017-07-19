package com.karrel.sunstudyenglish.main.wordbook;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
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
    private FirebaseDatabase mFireDatabase;
    private DatabaseReference mDatabaseReference;

    public FragmentWordBook() {

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
        mFireDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFireDatabase.getReference("message2");
        mDatabaseReference.setValue("Hello, World!");

        Log.d("TAG", "mFireDatabase.toString() > " + mFireDatabase.toString());

        // Read from the database
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Log.d(TAG, "Value is: " + dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        writeNewUser("hello", "rell", "rell@naver.com");
    }

    private void writeNewUser(String userId, String name, String email) {
        User user = new User(name, email);

        mDatabaseReference.child("users").child(userId).setValue(user).addOnCompleteListener(getActivity(), task -> Log.e(TAG, "onComplete"));
    }
}
