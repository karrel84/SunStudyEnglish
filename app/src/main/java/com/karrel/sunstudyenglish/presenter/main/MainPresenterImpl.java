package com.karrel.sunstudyenglish.presenter.main;

import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;

import com.karrel.mylibrary.RLog;
import com.karrel.sunstudyenglish.R;

/**
 * Created by Rell on 2017. 12. 6..
 */

public class MainPresenterImpl implements MainPresenter {
    private MainPresenter.View view;

    public MainPresenterImpl(View view) {
        this.view = view;
        view.setTitle("단어 외우기");
    }

    @Override
    public ViewPager.OnPageChangeListener onPageChangeListener() {
        return new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                RLog.d(String.format("onPageSelected(%s)", position));
                view.setCheckedBottomItem(position);

                switch (position) {
                    case 0:
                        view.setTitle("단어 외우기");
                        break;
                    case 1:
                        view.setTitle("단어장");
                        break;
                    case 2:
                        view.setTitle("단어 수집");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
    }

    @Override
    public BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener() {
        return item -> {
            RLog.d();
            switch (item.getItemId()) {
                case R.id.navigation_cardmemorize:
                    view.setCurrentItem(0);
                    return true;
                case R.id.navigation_wordbook:
                    view.setCurrentItem(1);
                    return true;
                case R.id.navigation_getword:
                    view.setCurrentItem(2);
                    return true;
            }
            return false;
        };
    }
}
