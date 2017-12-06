package com.karrel.sunstudyenglish.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.karrel.sunstudyenglish.view.CardMemorizeFragment;
import com.karrel.sunstudyenglish.view.GetWordFragment;
import com.karrel.sunstudyenglish.view.WordBookFragment;

/**
 * Created by Rell on 2017. 12. 6..
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                // 카드 암기
                return new CardMemorizeFragment();
            case 1:
                // 단어장
                return new WordBookFragment();
            case 2:
                // 단어수집
                return new GetWordFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
