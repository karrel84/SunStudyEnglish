package com.karrel.sunstudyenglish.view.adapter;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.databinding.DataBindingUtil;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.karrel.sunstudyenglish.R;
import com.karrel.sunstudyenglish.databinding.ItemCardWordBinding;
import com.karrel.sunstudyenglish.databinding.ItemCardWordMeanBinding;
import com.karrel.sunstudyenglish.databinding.ItemMemorizeBinding;
import com.karrel.sunstudyenglish.model.WordItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rell on 2017. 12. 18..
 */

public class MemorizeAdapter extends BaseAdapter {
    DataSetObservable mDataSetObservable = new DataSetObservable(); // DataSetObservable(DataSetObserver)의 생성

    private List<WordItem> wordItems = new ArrayList<>();

    @Override
    public int getCount() {
        return wordItems.size();
    }

    @Override
    public Object getItem(int position) {
        return wordItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemMemorizeBinding binding;
        if (convertView == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_memorize, parent, false);
            convertView.setTag(binding);
        }

        binding = (ItemMemorizeBinding) convertView.getTag();

        WordItem item = wordItems.get(position);

        ItemCardWordBinding cardFront = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_card_word, parent, false);
        cardFront.text.setText(item.meaning);
        binding.flipCard.setCardA(cardFront.getRoot());

        ItemCardWordMeanBinding cardBack = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_card_word_mean, parent, false);
        cardBack.text.setText(item.meaning);
        binding.flipCard.setCardA(cardBack.getRoot());

        return binding.getRoot();
    }

    public void addItem(WordItem item) {
        wordItems.add(item);
        notifyDataSetChanged();
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) { // DataSetObserver의 등록(연결)
        mDataSetObservable.registerObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) { // DataSetObserver의 해제
        mDataSetObservable.unregisterObserver(observer);
    }

    @Override
    public void notifyDataSetChanged() { // 위에서 연결된 DataSetObserver를 통한 변경 확인
        mDataSetObservable.notifyChanged();
    }
}
