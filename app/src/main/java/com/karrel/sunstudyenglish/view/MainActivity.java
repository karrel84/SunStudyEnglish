package com.karrel.sunstudyenglish.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.karrel.sunstudyenglish.R;
import com.karrel.sunstudyenglish.base.BaseActivity;
import com.karrel.sunstudyenglish.databinding.ActivityMainBinding;
import com.karrel.sunstudyenglish.presenter.main.MainPresenter;
import com.karrel.sunstudyenglish.presenter.main.MainPresenterImpl;
import com.karrel.sunstudyenglish.view.adapter.ViewPagerAdapter;

public class MainActivity extends BaseActivity implements MainPresenter.View {

    private MainPresenter presenter;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        presenter = new MainPresenterImpl(this);
    }

    @Override
    protected void onLoadOnce() {
        super.onLoadOnce();
        // 타이틀을 설정한다
        setSupportActionBar(binding.toolbar);

        // 뷰페이저를 설정한다
        binding.viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        binding.viewPager.addOnPageChangeListener(presenter.onPageChangeListener());

        // 하단메뉴 설정
        binding.navigation.setOnNavigationItemSelectedListener(presenter.onNavigationItemSelectedListener());
    }

    @Override
    public void setCheckedBottomItem(int position) {
        binding.navigation.getMenu().getItem(position).setChecked(true);
    }

    @Override
    public void setCurrentItem(int i) {
        binding.viewPager.setCurrentItem(i);
    }

    @Override
    public void setTitle(String s) {
        binding.toolbar.setTitle(s);
    }
}
