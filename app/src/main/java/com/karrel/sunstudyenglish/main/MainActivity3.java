package com.karrel.sunstudyenglish.main;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.karrel.sunstudyenglish.R;
import com.karrel.sunstudyenglish.base.BaseActivity;
import com.karrel.sunstudyenglish.databinding.ActivityMain3Binding;
import com.kerrel.getthewordlibrary.OcrCaptureActivity;

/**
 * Created by 이주영 on 2017-06-16.
 */

public class MainActivity3 extends BaseActivity {

    private ActivityMain3Binding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main3);
    }

    @Override
    protected void onLoadOnce() {
        super.onLoadOnce();

        // 버튼 세팅
        setupButtons();
    }

    private void setupButtons() {
        Log.d("TAG", "setupButtons");
        mBinding.getWord.setOnClickListener(v -> {
            Log.d("TAG", "getWord");
            grubUpWord();
        });
    }

    // 단어를 캐자
    private void grubUpWord() {
        Intent intent = new Intent(this, OcrCaptureActivity.class);
        startActivity(intent);
    }

    // 단어를 변환 하자
    private void englishToKorean() {

    }

    // 서버에 저장하자
    private void saveToServer() {

    }
}
