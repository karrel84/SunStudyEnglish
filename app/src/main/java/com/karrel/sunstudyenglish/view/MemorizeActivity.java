package com.karrel.sunstudyenglish.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.widget.Toast;

import com.karrel.mylibrary.RLog;
import com.karrel.sunstudyenglish.R;
import com.karrel.sunstudyenglish.base.BaseActivity;
import com.karrel.sunstudyenglish.databinding.ActivityMemorizeBinding;
import com.karrel.sunstudyenglish.model.GroupItem;
import com.karrel.sunstudyenglish.model.WordItem;
import com.karrel.sunstudyenglish.presenter.memorize.MemorizePresenter;
import com.karrel.sunstudyenglish.presenter.memorize.MemorizePresenterImpl;
import com.karrel.sunstudyenglish.view.adapter.MemorizeAdapter;

/**
 * 단어를 외우는 액티비티이다
 * 실제로 여기서 단어공부를하게 될 것이다
 */
public class MemorizeActivity extends BaseActivity implements MemorizePresenter.View {
    private final String TAG = "MemorizeActivity";

    public class EXTRA {
        public static final String GROUP_ITEM = "GROUP_ITEM";
    }

    private ActivityMemorizeBinding mBinding;
    private GroupItem mItem;
    private MemorizePresenter mPresenter;
    private MemorizeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_memorize);
        mPresenter = new MemorizePresenterImpl(this);
    }

    @Override
    protected void onParseExtra() {
        super.onParseExtra();

        mItem = getIntent().getParcelableExtra(EXTRA.GROUP_ITEM);
        mPresenter.onParseExtra(mItem);
    }

    @Override
    protected void onLoadOnce() {
        super.onLoadOnce();
        initAdapter();
        mPresenter.getWords(mItem);
    }

    @Override
    protected void onLoad() {
        super.onLoad();
    }

    @Override
    public void onErrorFinish(String s) {
        Toast.makeText(mContext, s, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void addWordItem(WordItem item) {
        mAdapter.addItem(item);
    }

    @Override
    public void setTitle(String title) {
        RLog.d(String.format("setTitle(%s)", title));
        super.setTitle(title);
    }

    /**
     * 아답터를 초기화한다
     */
    private void initAdapter() {
        mAdapter = new MemorizeAdapter();
        mBinding.memoFlipper.setAdapter(mAdapter);
    }
}
