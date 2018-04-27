package com.karrel.sunstudyenglish.presenter.memorize;

import com.karrel.sunstudyenglish.base.net.NetManager;
import com.karrel.sunstudyenglish.model.GroupItem;
import com.karrel.sunstudyenglish.model.WordItem;

import rx.Observer;

/**
 * Created by bodyfriend_dev on 2017. 7. 27..
 */

public class MemorizePresenterImpl implements MemorizePresenter {
    private MemorizePresenter.View mView;

    private final String TAG = "MemorizePresenterImpl";
    private GroupItem mItem;


    public MemorizePresenterImpl(MemorizePresenter.View view) {
        mView = view;
    }

    @Override
    public void onParseExtra(GroupItem item) {
        // 값체크
        if (item == null) {
            mView.onErrorFinish("아이템의 값이 없습니다.");
            return;
        }
        mItem = item;
        mView.setTitle(mItem.getKey());
    }

    @Override
    public void getWords(GroupItem item) {
        // NetManager에서 각 단어에 대한 객체를 받아온다.
        for (String word : item.getWords()) {
            NetManager.getInstance().getWord(word).subscribe(new Observer<WordItem>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(WordItem wordItem) {
                    mView.addWordItem(wordItem);
                }
            });

        }
    }

}
