package com.karrel.sunstudyenglish.presenter;

import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;

/**
 * Created by Rell on 2017. 12. 6..
 */

public interface MainPresenter {
    ViewPager.OnPageChangeListener onPageChangeListener();

    BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener();

    interface View {

        void setCheckedBottomItem(int position);

        void setCurrentItem(int i);
    }
}
