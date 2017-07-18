package com.karrel.sunstudyenglish.main.gettheword.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karrel.sunstudyenglish.R;
import com.karrel.sunstudyenglish.base.view.BaseFragment;
import com.karrel.sunstudyenglish.common.RequestCodes;
import com.karrel.sunstudyenglish.databinding.FragmentGetWordBinding;
import com.karrel.sunstudyenglish.main.gettheword.model.WordItem;
import com.karrel.sunstudyenglish.main.gettheword.presenter.GetWordPresenter;
import com.karrel.sunstudyenglish.main.gettheword.presenter.GetWordPresenterImpl;
import com.karrel.sunstudyenglish.main.ocr.OcrCaptureActivity;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class FragmentGetWord extends BaseFragment {
    private final String TAG = "FragmentGetWord";

    private FragmentGetWordBinding mBinding;
    private GetWordPresenter mPresenter;
    private RecyclerAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // data binding
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_get_word, container, false);
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
}
