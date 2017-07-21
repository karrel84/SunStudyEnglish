package com.karrel.sunstudyenglish.view;

import com.karrel.sunstudyenglish.model.WordItem;
import com.karrel.sunstudyenglish.presenter.WordBookPresenter;
import com.karrel.sunstudyenglish.presenter.WordBookPresenterImpl;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Created by rell on 2017-07-21.
 */
public class WordBookFragmentTest implements WordBookPresenter.View {
    private WordBookPresenterImpl mPresenter;
    private CountDownLatch mLatch;

    @Before
    public void setUp() throws Exception {
        mPresenter = new WordBookPresenterImpl();
        mPresenter.setView(this);
        mLatch = new CountDownLatch(1);
    }

    @Test
    public void onLoad() throws Exception {
        mPresenter.getWord();

        mLatch.await(10 * 1000, TimeUnit.MILLISECONDS);
    }

    @Override
    public void showList(List<WordItem> list) {

    }
}