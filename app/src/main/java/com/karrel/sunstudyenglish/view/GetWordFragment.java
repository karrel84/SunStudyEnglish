package com.karrel.sunstudyenglish.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karrel.sunstudyenglish.R;
import com.karrel.sunstudyenglish.base.BaseFragment;
import com.karrel.sunstudyenglish.databinding.FragmentGetWordBinding;
import com.karrel.sunstudyenglish.model.RequestCodes;
import com.karrel.sunstudyenglish.model.WordItem;
import com.karrel.sunstudyenglish.ocr.OcrCaptureActivity;
import com.karrel.sunstudyenglish.presenter.GetWordPresenter;
import com.karrel.sunstudyenglish.presenter.GetWordPresenterImpl;
import com.karrel.sunstudyenglish.view.adapter.WordAdapter;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * 단어수집
 */
public class GetWordFragment extends BaseFragment implements GetWordPresenter.View {
    private final String TAG = "GetWordFragment";

    private FragmentGetWordBinding mBinding;
    private GetWordPresenter mPresenter;
    private WordAdapter mAdapter;


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
        setupPresenter();

        // 버튼 초기화
        setupButtons();

        // 리사이클러뷰 초기화
        setupRecyclerView();
    }

    /**
     * 버튼 초기화
     */
    private void setupButtons() {
        mBinding.getWord.setOnClickListener(view -> {
            // 단어를 가져와
            mPresenter.getTheWord();
        });

        // 버튼설정
        mBinding.addWord.setOnClickListener(v -> {
            // 단어를 가져와
            mPresenter.getTheWord();
        });
    }

    private void setupPresenter() {
        mPresenter = new GetWordPresenterImpl();
        mPresenter.setView(this);

        // 프리젠터 onLoadOnce
        mPresenter.onLoadOnce();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RequestCodes.GETWORD && resultCode == RESULT_OK) {
            ArrayList<String> wordsList = data.getStringArrayListExtra("words");
            // 수집한 단어를 보여줘
            mPresenter.onActivityResult(wordsList);
        }
    }

    /**
     * 리사이클러뷰 초기화
     */
    private void setupRecyclerView() {
        mAdapter = new WordAdapter();
        mBinding.recyclerView.setAdapter(mAdapter);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        // setup swipe to remove item
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mBinding.recyclerView);
    }

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
    public void removeItem(int position) {
        // todo 아답타에게 알린다
        mAdapter.removeItem(position);
    }

    @Override
    public void showProgress() {
        showProgressDialog();
    }

    @Override
    public void hideProgress() {
        hideProgressDialog();
    }

    // todo 서버에 저장버튼을 만들어서 이거해줘야함
    private void saveList() {
        mPresenter.saveList(null);
    }

    @Override
    public void showAddWordView() {
        mBinding.getWord.setVisibility(View.VISIBLE);
        mBinding.addWord.setVisibility(View.GONE);
    }

    @Override
    public void showFloatButton() {
        mBinding.getWord.setVisibility(View.GONE);
        mBinding.addWord.setVisibility(View.VISIBLE);
    }

    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            // 삭제되는 아이템의 포지션을 가져온다
            final int position = viewHolder.getAdapterPosition();
            // 특정 포지션 삭제
            mPresenter.removeItem(position);

        }
    };
}
