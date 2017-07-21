package com.karrel.sunstudyenglish.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.karrel.sunstudyenglish.model.WordItem;
import com.karrel.sunstudyenglish.util.EnglishToKorean;

import java.util.ArrayList;
import java.util.regex.Pattern;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
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

        mView.showProgress();

        getWord(wordsList)
                .subscribeOn(Schedulers.io()) // 작업은 비동기 스레드에서
                .observeOn(AndroidSchedulers.mainThread()) // UI작업은 메인스레드에서
                .subscribe(new Subscriber<WordItem>() {
                    @Override
                    public void onCompleted() {
                        mView.hideProgress();
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

    private Observable<WordItem> getWord(ArrayList<String> wordsList) {
        return Observable.from(wordsList)
                .filter(s -> !TextUtils.isEmpty(s)) // 빈값인지 판단
                .filter(s -> regularExpression(s)) // 문자인지 판단
                .map(s ->
                {
                    String wordMeans = mEnglishToKorean.getWord(s);
                    if (TextUtils.isEmpty(wordMeans.trim())) return null;
                    else return new WordItem(s, wordMeans);
                }) // 문자의 뜻을 가져옴
                .filter(i -> i != null);
    }

    /**
     * 문자인지 판단한다
     */
    private boolean regularExpression(String text) {
        return Pattern.matches("^[a-zA-Z]*$", text);
    }
}
