package com.karrel.sunstudyenglish.view;

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
import com.karrel.sunstudyenglish.model.RequestCodes;
import com.karrel.sunstudyenglish.databinding.FragmentGetWordBinding;
import com.karrel.sunstudyenglish.model.WordItem;
import com.karrel.sunstudyenglish.presenter.GetWordPresenter;
import com.karrel.sunstudyenglish.presenter.GetWordPresenterImpl;
import com.karrel.sunstudyenglish.ocr.OcrCaptureActivity;
import com.karrel.sunstudyenglish.view.test.Post;
import com.karrel.sunstudyenglish.view.test.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;

import static android.app.Activity.RESULT_OK;

public class GetWordFragment extends BaseFragment {
    private final String TAG = "GetWordFragment";

    private FragmentGetWordBinding mBinding;
    private GetWordPresenter mPresenter;
    private RecyclerAdapter mAdapter;

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

        Log.e(TAG, " mReference.getKey() > " + mReference.child("posts").getKey());

        // TODO test code
        mBinding.addUser.setOnClickListener(v -> writeNewUser("karrel", "이주영", "karrel84@naver.com"));
        // TODO test code
        // 이름을 변경한다
        mBinding.updateName.setOnClickListener(v -> mReference.child("users").child("karrel").child("username").setValue("박지훈"));
        // TODO test code
        // 데이터추가
        mBinding.addDataList.setOnClickListener(v -> writeNewPost("karrel", "이주영", "타이틀입니다.", "내용입니다."));
        // TODO test
        // 데이터 삭제
        mBinding.deleteData.setOnClickListener(v -> mReference.child("posts").removeValue());

        // TODO test
        mBinding.transactionSave.setOnClickListener(v -> onStarClicked(mReference));
    }

    // TODO test
    private void onStarClicked(DatabaseReference postRef) {
        postRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Post p = mutableData.getValue(Post.class);
                if (p == null) {
                    return Transaction.success(mutableData);
                }

                if (p.stars.containsKey(getUid())) {
                    // Unstar the post and remove self from stars
                    p.starCount = p.starCount - 1;
                    p.stars.remove(getUid());
                } else {
                    // Star the post and add self to stars
                    p.starCount = p.starCount + 1;
                    p.stars.put(getUid(), true);
                }

                // Set value and report transaction success
                mutableData.setValue(p);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                // Transaction completed
                Log.d(TAG, "postTransaction:onComplete:" + databaseError);
            }
        });
    }

    // TODO testCode

    /**
     * 이런거는 한번만 호출해서 JSON트리의 여러위치에 동시업데이트하기위한 목적이다.
     */
    private void writeNewPost(String userId, String username, String title, String body) {
        // posts라는 차일드를 만들고 키를 가져온다.
        String key = mReference.child("posts").push().getKey();
        // post객체를 만든다.
        Post post = new Post(userId, username, title, body);
        // 만들어진 객체를 맵으로 변환
        Map<String, Object> postValues = post.toMap();

        // 여러개의 객체를 한번에 넣으려고할때 사용할 맵을 만든다.
        Map<String, Object> childUpdates = new HashMap<>();
        // 맵에 케이스1의 데이터를 만든다
        childUpdates.put("/posts/" + key, postValues);
        // 맵에 케이스2의 데이터를 만든다
        childUpdates.put("/user-posts/" + userId + "/" + key, postValues);

        // 여러개의 객체를 업데이트 한다.
        mReference.updateChildren(childUpdates);
    }

    // TODO test code
    private void writeNewUser(String userId, String name, String email) {
        User user = new User(name, email);

        mReference.child("users").child(userId).setValue(user);
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
        mAdapter = new RecyclerAdapter();
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
    };

    private void addWord(WordItem item) {
        mReference.child("words").child(item.word).setValue(item);
        mReference.child("users").child("karrel84").setValue(item.word);
    }

    public String getUid() {
        return "karrel8410";
    }
}
