package com.karrel.sunstudyenglish.main.wordbook;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karrel.sunstudyenglish.FireBaseActivity;
import com.karrel.sunstudyenglish.R;
import com.karrel.sunstudyenglish.databinding.FragmentFragmentWordBookBinding;

public class FragmentWordBook extends Fragment {

    private FragmentFragmentWordBookBinding mBinding;

    public FragmentWordBook() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_fragment_word_book, container, false);
        // 파이어베이스 액티비티로 이동
        mBinding.firebase.setOnClickListener(v -> startActivity(new Intent(getContext(), FireBaseActivity.class)));
        return mBinding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
