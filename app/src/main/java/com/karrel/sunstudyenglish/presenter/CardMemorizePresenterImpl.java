package com.karrel.sunstudyenglish.presenter;

import com.karrel.sunstudyenglish.base.net.NetManager;
import com.karrel.sunstudyenglish.model.GroupItem;
import com.karrel.sunstudyenglish.model.WordItem;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by Rell on 2017. 12. 7..
 */

public class CardMemorizePresenterImpl implements CardMemorizePresenter {
    private CardMemorizePresenter.View view;

    public CardMemorizePresenterImpl(CardMemorizePresenter.View view) {
        this.view = view;
    }

    @Override
    public void onLoad() {
        // 외울 단어 데이터를 로드한다.
        NetManager.getInstance().getWords().subscribe(CardMemorizePresenterImpl.this::setGroupItems);
    }

    private void setGroupItems(List<GroupItem> groupItems) {
        for (String word : groupItems.get(0).getWords()) {
            NetManager.getInstance().getWord(word).subscribe(view::addWordItem);
        }
    }

}
