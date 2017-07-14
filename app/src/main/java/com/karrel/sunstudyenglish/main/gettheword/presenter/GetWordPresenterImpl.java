package com.karrel.sunstudyenglish.main.gettheword.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.karrel.sunstudyenglish.main.gettheword.model.WordItem;
import com.karrel.sunstudyenglish.main.gettheword.utils.EnglishToKorean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by rell on 2017-07-14.
 */

public class GetWordPresenterImpl implements GetWordPresenter {
    private String TAG = "GetWordPresenterImpl";
    private GetWordPresenter.View mView;
    private EnglishToKorean mEnglishToKorean;

    public GetWordPresenterImpl() {
        mEnglishToKorean = new EnglishToKorean();
    }

    @Override
    public void setView(View view) {
        mView = view;
    }

    @Override
    public void getTheWord() {
        mView.startOcrActivity();
    }

    @Override
    public void showWord(ArrayList<String> wordsList) {
        Log.d(TAG, wordsList.toString());

        Observable.from(wordsList)
                .filter(s -> !TextUtils.isEmpty(s)) // 빈값인지 판단
                .filter(s -> regularExpression(s)) // 문자인지 판단
                .map(s -> new WordItem(s, mEnglishToKorean.getWord(s))) // 문자의 뜻을 가져옴
                .subscribeOn(Schedulers.io()) // 작업은 비동기 스레드에서
                .observeOn(AndroidSchedulers.mainThread()) // UI작업은 메인스레드에서
                .subscribe(item -> mView.addWordItem(item));
    }

    /**
     * 문자인지 판단한다
     */
    private boolean regularExpression(String text) {
        return Pattern.matches("^[a-zA-Z]*$", text);
    }
}
