package com.karrel.sunstudyenglish.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.karrel.sunstudyenglish.R;
import com.karrel.sunstudyenglish.databinding.ItemWordBinding;
import com.karrel.sunstudyenglish.model.WordItem;
import com.karrel.sunstudyenglish.view.adapter.holder.GroupNameHolder;
import com.karrel.sunstudyenglish.view.adapter.holder.WordNameHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 이주영 on 2017-06-21.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<WordNameHolder> {
    private List<WordItem> items;

    public RecyclerAdapter() {
        items = new ArrayList<>();
    }

    @Override
    public WordNameHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemWordBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_word, parent, false);
        WordNameHolder holder = new WordNameHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(WordNameHolder holder, int position) {
        holder.setData(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(WordItem item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void removeItems() {
        items.clear();
        notifyDataSetChanged();
    }

    public void setItems(List<WordItem> item) {
        items = item;
        notifyDataSetChanged();
    }
}
