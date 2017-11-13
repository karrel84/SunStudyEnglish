package com.karrel.sunstudyenglish.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.karrel.sunstudyenglish.R;
import com.karrel.sunstudyenglish.base.BaseFragment;
import com.karrel.sunstudyenglish.databinding.FragmentFragmentWordBookBinding;
import com.karrel.sunstudyenglish.model.GroupItem;
import com.karrel.sunstudyenglish.model.WordItem;
import com.karrel.sunstudyenglish.presenter.WordBookPresenter;
import com.karrel.sunstudyenglish.presenter.WordBookPresenterImpl;
import com.karrel.sunstudyenglish.view.adapter.GroupNameAdapter;

import java.util.List;

/**
 * 단어암기 화면이될것이다
 */
public class WordBookFragment extends BaseFragment implements WordBookPresenter.View {

    private final String TAG = "WordBookFragment";
    private FragmentFragmentWordBookBinding mBinding;
    private WordBookPresenter mPresenter;
    private GroupNameAdapter mAdapter;

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
        mAdapter = new GroupNameAdapter();
        mBinding.recycler.setAdapter(mAdapter);
        mBinding.recycler.setLayoutManager(new LinearLayoutManager(mContext));

        // setup swipe to remove item
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mBinding.recycler);
    }

    @Override
    protected void onLoad() {
        super.onLoad();

        mPresenter.getGroupName();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public void setonGroupListItems(List<GroupItem> list) {
        mAdapter.setGroupItems(list);
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
            // 데이터의 해당 포지션을 삭제한다
            mAdapter.removeItem(position);
        }
    };
}
