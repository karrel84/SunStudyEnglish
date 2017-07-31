package com.karrel.sunstudyenglish.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.karrel.sunstudyenglish.R;
import com.karrel.sunstudyenglish.view.fragment.WordBookFragment;
import com.karrel.sunstudyenglish.view.fragment.GetWordFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 타이틀을 세팅한다
        setupTitle();

        // 하단메뉴 세팅
        setupBottomMenu();
    }

    /**
     * 타이틀을 세팅한다
     */
    private Toolbar setupTitle() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    /**
     * 플래그먼트를 변경한다
     *
     * @param fragment
     */
    public void changeFragment(Fragment fragment) {
        // 최초 플래그먼트를 설정한다.
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.commit();
    }

    /**
     * 하단메뉴
     */
    private void setupBottomMenu() {
        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        Fragment fragment = null;
        switch (item.getItemId()) {

            case R.id.navigation_cardmemorize:

                break;
            case R.id.navigation_wordbook:
                fragment = new WordBookFragment();
                break;
            case R.id.navigation_getword:
                fragment = new GetWordFragment();
                break;

        }

        if (fragment != null) {
            changeFragment(fragment);
            return true;
        } else {
            return false;
        }
    };


    @Override
    public void onBackPressed() {
    }
}
