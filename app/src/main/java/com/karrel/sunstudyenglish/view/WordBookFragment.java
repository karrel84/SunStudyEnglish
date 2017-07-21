package com.karrel.sunstudyenglish.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.karrel.sunstudyenglish.R;
import com.karrel.sunstudyenglish.databinding.FragmentFragmentWordBookBinding;
import com.karrel.sunstudyenglish.model.WordItem;
import com.karrel.sunstudyenglish.presenter.WordBookPresenter;
import com.karrel.sunstudyenglish.presenter.WordBookPresenterImpl;

import java.util.List;

/**
 * 단어암기 화면이될것이다
 */
public class WordBookFragment extends BaseFragment implements WordBookPresenter.View {

    private final String TAG = "WordBookFragment";
    private FragmentFragmentWordBookBinding mBinding;
    private WordBookPresenter mPresenter;
    private RecyclerAdapter mAdapter;

    public WordBookFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_fragment_word_book, container, false);

        return mBinding.getRoot();
    }

    @Override
    protected void onLoadOnce() {
        super.onLoadOnce();

        // 프리젠터 초기화
        mPresenter = new WordBookPresenterImpl();
        mPresenter.setView(this);

        // 리사이클러 초기화
        mAdapter = new RecyclerAdapter();
        mBinding.recycler.setAdapter(mAdapter);
        mBinding.recycler.setLayoutManager(new LinearLayoutManager(mContext));

    }

    @Override
    protected void onLoad() {
        super.onLoad();

        mPresenter.getWord();
    }

    @Override
    public void showList(List<WordItem> list) {
        mAdapter.setItems(list);
    }
}
