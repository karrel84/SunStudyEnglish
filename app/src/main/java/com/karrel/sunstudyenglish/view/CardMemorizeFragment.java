package com.karrel.sunstudyenglish.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karrel.mylibrary.RLog;
import com.karrel.sunstudyenglish.R;
import com.karrel.sunstudyenglish.base.BaseFragment;
import com.karrel.sunstudyenglish.databinding.FragmentCardBinding;
import com.karrel.sunstudyenglish.model.WordItem;
import com.karrel.sunstudyenglish.presenter.CardMemorizePresenter;
import com.karrel.sunstudyenglish.presenter.CardMemorizePresenterImpl;
import com.karrel.sunstudyenglish.view.adapter.MemorizeAdapter;

/**
 * Created by Rell on 2017. 7. 31..
 * <p>
 * 단어 외우기 플래그먼트
 */

public class CardMemorizeFragment extends BaseFragment implements CardMemorizePresenter.View {

    private FragmentCardBinding binding;
    private CardMemorizePresenter presenter;
    private MemorizeAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // data binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_card, container, false);
        presenter = new CardMemorizePresenterImpl(this);
        return binding.getRoot();
    }

    @Override
    protected void onLoadOnce() {
        super.onLoadOnce();
        adapter = new MemorizeAdapter();
        binding.memoFlipper.setAdapter(adapter);
    }

    @Override
    protected void onLoad() {
        super.onLoad();

        presenter.onLoad();
    }

    @Override
    public void addWordItem(WordItem item) {
        adapter.addItem(item);
        RLog.d("addWordItem");
        RLog.d("wordItem : " + item.toString());
    }
}