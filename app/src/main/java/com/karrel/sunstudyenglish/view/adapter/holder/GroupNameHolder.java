package com.karrel.sunstudyenglish.view.adapter.holder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.karrel.sunstudyenglish.databinding.ItemGroupNameBinding;
import com.karrel.sunstudyenglish.model.GroupItem;
import com.karrel.sunstudyenglish.presenter.GroupNamePresenter;
import com.karrel.sunstudyenglish.presenter.GroupNamePresenterImpl;
import com.karrel.sunstudyenglish.view.MemorizeActivity;

/**
 * Created by 이주영 on 2017-06-21.
 */

public class GroupNameHolder extends RecyclerView.ViewHolder implements GroupNamePresenter.View {
    private final ItemGroupNameBinding mBinding;
    private GroupItem mGroupItem;
    private GroupNamePresenter mGroupNamePresenter;
    private Context mContext;

    public GroupNameHolder(ItemGroupNameBinding binding) {
        super(binding.getRoot());
        mBinding = binding;
        mContext = mBinding.getRoot().getContext();
        mGroupNamePresenter = new GroupNamePresenterImpl(this);

        mBinding.getRoot().setOnClickListener(view -> mGroupNamePresenter.onItemClick(mGroupItem));
    }

    public void setData(GroupItem groupItem) {
        mGroupItem = groupItem;
        mBinding.groupName.setText(mGroupItem.getKey());
    }

    @Override
    public void onError(String s) {
        Toast.makeText(mContext, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startMemorizeActivity(GroupItem item) {
        Intent intent = new Intent(mContext, MemorizeActivity.class);
        intent.putExtra(MemorizeActivity.EXTRA.GROUP_ITEM, item);
        mContext.startActivity(intent);
    }
}
