package com.karrel.sunstudyenglish.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.karrel.sunstudyenglish.R;
import com.karrel.sunstudyenglish.databinding.ItemGroupNameBinding;
import com.karrel.sunstudyenglish.model.GroupItem;
import com.karrel.sunstudyenglish.view.adapter.holder.GroupNameHolder;

import java.util.List;

/**
 * Created by bodyfriend_dev on 2017. 7. 26..
 */

public class GroupNameAdapter extends RecyclerView.Adapter<GroupNameHolder> {


    private List<GroupItem> groupItems;

    @Override
    public GroupNameHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemGroupNameBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_group_name, parent, false);
        GroupNameHolder holder = new GroupNameHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(GroupNameHolder holder, int position) {
        holder.setData(groupItems.get(position));
    }

    @Override
    public int getItemCount() {
        if (groupItems == null) {
            return 0;
        }
        return groupItems.size();
    }

    public void setGroupItems(List<GroupItem> groupItems) {
        this.groupItems = groupItems;
        notifyDataSetChanged();
    }
}
