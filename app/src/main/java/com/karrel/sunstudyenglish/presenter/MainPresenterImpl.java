package com.karrel.sunstudyenglish.presenter;

/**
 * Created by Rell on 2017. 12. 6..
 */

public class MainPresenterImpl implements MainPresenter {
    private MainPresenter.View view;

    public MainPresenterImpl(View view) {
        this.view = view;
    }
}
