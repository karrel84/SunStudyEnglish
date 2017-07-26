package com.karrel.sunstudyenglish.view.adapter.holder;

import android.support.v7.widget.RecyclerView;

import com.karrel.sunstudyenglish.databinding.ItemGroupNameBinding;
import com.karrel.sunstudyenglish.databinding.ItemWordBinding;
import com.karrel.sunstudyenglish.model.GroupItem;
import com.karrel.sunstudyenglish.model.WordItem;

/**
 * Created by 이주영 on 2017-06-21.
 */

public class GroupNameHolder extends RecyclerView.ViewHolder {
    private final ItemGroupNameBinding mBinding;
    private GroupItem mGroupItem;

    public GroupNameHolder(ItemGroupNameBinding binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    public void setData(GroupItem groupItem) {
        mGroupItem = groupItem;
        mBinding.groupName.setText(mGroupItem.getKey());
    }
}
