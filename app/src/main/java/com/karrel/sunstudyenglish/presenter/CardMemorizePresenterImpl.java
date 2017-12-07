package com.karrel.sunstudyenglish.presenter;

/**
 * Created by Rell on 2017. 12. 7..
 */

public class CardMemorizePresenterImpl implements CardMemorizePresenter {
    private CardMemorizePresenter.View view;

    public CardMemorizePresenterImpl(CardMemorizePresenter.View view) {
        this.view = view;
    }
}
