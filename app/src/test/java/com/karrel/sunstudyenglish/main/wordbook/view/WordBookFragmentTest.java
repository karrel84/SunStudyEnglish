package com.karrel.sunstudyenglish.main.wordbook.view;

import com.karrel.sunstudyenglish.model.WordItem;
import com.karrel.sunstudyenglish.presenter.WordBookPresenter;
import com.karrel.sunstudyenglish.presenter.WordBookPresenterImpl;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by rell on 2017-07-21.
 */
public class WordBookFragmentTest implements WordBookPresenter.View {
    private WordBookPresenter mPresenter;

    @Before
    public void onLoadOnce() throws Exception {
        // 프리젠터 초기화
        mPresenter = new WordBookPresenterImpl();
        mPresenter.setView(this);
    }

    @Test
    public void onLoad() {
        mPresenter.getWord();
    }

    @Override
    public void showList(List<WordItem> list) {
        System.out.println("showList");

        for (WordItem item : list) {
            System.out.println("item > " + item.toString());
        }
    }
}