package com.karrel.sunstudyenglish.view.adapter.holder;

import android.support.v7.widget.RecyclerView;

import com.karrel.sunstudyenglish.databinding.ItemWordBinding;
import com.karrel.sunstudyenglish.model.WordItem;

/**
 * Created by bodyfriend_dev on 2017. 7. 26..
 */

public class WordNameHolder extends RecyclerView.ViewHolder {
    private final ItemWordBinding mBinding;
    private WordItem data;

    public WordNameHolder(ItemWordBinding binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    public void setData(WordItem data) {
        this.data = data;

        // 단어
        mBinding.word.setText(data.word);
        // 뜻
        mBinding.mean.setText(data.meaning);
    }
}
