package com.karrel.sunstudyenglish.view.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.karrel.sunstudyenglish.R;
import com.karrel.sunstudyenglish.base.BaseFragment;
import com.karrel.sunstudyenglish.model.RequestCodes;
import com.karrel.sunstudyenglish.databinding.FragmentGetWordBinding;
import com.karrel.sunstudyenglish.model.WordItem;
import com.karrel.sunstudyenglish.presenter.GetWordPresenter;
import com.karrel.sunstudyenglish.presenter.GetWordPresenterImpl;
import com.karrel.sunstudyenglish.ocr.OcrCaptureActivity;
import com.karrel.sunstudyenglish.view.adapter.WordAdapter;
import com.karrel.sunstudyenglish.view.test.Post;
import com.karrel.sunstudyenglish.view.test.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class GetWordFragment extends BaseFragment {
    private final String TAG = "GetWordFragment";

    private FragmentGetWordBinding mBinding;
    private GetWordPresenter mPresenter;
    private WordAdapter mAdapter;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // data binding
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_get_word, container, false);

        // 파이어베이스
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference();

        return mBinding.getRoot();
    }

    @Override
    protected void onLoadOnce() {
        super.onLoadOnce();
        // 프리젠터
        mPresenter = new GetWordPresenterImpl();
        mPresenter.setView(mView);
        // 버튼설정
        mBinding.addWord.setOnClickListener(v -> {
            // 단어를 가져와
            mPresenter.getTheWord();
        });

        // 리사이클러뷰 초기화
        setupRecyclerView();

        mReference.child("user-posts").child("karrelqwe").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG, "onDataChange > " + dataSnapshot);

                Iterable<DataSnapshot> dataSnapshots = dataSnapshot.getChildren();

                Iterator<DataSnapshot> iterator = dataSnapshots.iterator();

                while (iterator.hasNext()) {
                    Log.e(TAG, "iterator.next() > " + iterator.next());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RequestCodes.GETWORD && resultCode == RESULT_OK) {
            ArrayList<String> wordsList = data.getStringArrayListExtra("words");
            // 수집한 단어를 보여줘
            mPresenter.showWord(wordsList);
        }
    }

    /**
     * 리사이클러뷰 초기화
     */
    private void setupRecyclerView() {
        mAdapter = new WordAdapter();
        mBinding.recyclerView.setAdapter(mAdapter);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
    }

    private final GetWordPresenter.View mView = new GetWordPresenter.View() {
        @Override
        public void startOcrActivity() {
            Intent intent = new Intent(mContext, OcrCaptureActivity.class);
            startActivityForResult(intent, RequestCodes.GETWORD);
        }

        @Override
        public void addWordItem(WordItem item) {
            Log.e(TAG, item.toString());
            addWord(item);

            // 아답터에 넣어줘라
            mAdapter.addItem(item);
            // 제일 하단으로 이동
            mBinding.recyclerView.smoothScrollToPosition(mBinding.recyclerView.getAdapter().getItemCount());
        }

        @Override
        public void showProgress() {
            showProgressDialog();
        }

        @Override
        public void hideProgress() {
            hideProgressDialog();
        }

        @Override
        public void onCompleted(ArrayList<WordItem> wordItems) {
            // 검색완료가 되었습니다 리스트를 받아가세요.
            saveListToServer(wordItems);
        }
    };

    // 서버에 리스트를 저장해볼까요
    private void saveListToServer(ArrayList<WordItem> wordItems) {

        StringBuilder builder = new StringBuilder();
        for (WordItem item : wordItems) {
            if (!builder.toString().isEmpty()) builder.append("|");
            builder.append(item.word);
        }

        mReference.child("users").child("karrel").child("words").child(getTime()).setValue(builder.toString());
    }

    private void addWord(WordItem item) {
        mReference.child("words").child(item.word).setValue(item);
    }

    public String getTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date(System.currentTimeMillis()));
    }
}
