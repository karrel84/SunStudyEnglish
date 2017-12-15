package com.karrel.sunstudyenglish.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.karrel.sunstudyenglish.model.WordItem;
import com.karrel.sunstudyenglish.base.util.EnglishToKorean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    ArrayList<WordItem> mWordItems = new ArrayList<>();

    private DatabaseReference mReference;
    private FirebaseDatabase mDatabase;

    public GetWordPresenterImpl() {
        mEnglishToKorean = new EnglishToKorean();
        // 파이어베이스
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference();

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
    public void onActivityResult(ArrayList<String> wordsList) {
        Log.d(TAG, wordsList.toString());

        mView.showProgress();
        mWordItems.clear();

        getWord(wordsList)
                .subscribeOn(Schedulers.io()) // 작업은 비동기 스레드에서
                .observeOn(AndroidSchedulers.mainThread()) // UI작업은 메인스레드에서
                .subscribe(new Subscriber<WordItem>() {
                    @Override
                    public void onCompleted() {
                        mView.hideProgress();
//                        mView.onCompleted(mWordItems);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(WordItem wordItem) {
                        mView.addWordItem(wordItem);
                        addWordItem(wordItem);
                        mWordItems.add(wordItem);
                    }
                });
    }

    private void addWordItem(WordItem item) {
        mReference.child("words").child(item.word).setValue(item);
    }

    @Override
    public void onLoadOnce() {
        boolean isEmpty = mWordItems.isEmpty();
        if (isEmpty) {
            mView.showAddWordView();
        } else {
            mView.showFloatButton();
        }
    }

    @Override
    public void saveList(ArrayList<WordItem> items) {
        if (items == null) return;
        saveListToServer(items);
    }

    @Override
    public void removeItem(int position) {
        mWordItems.remove(position);
        mView.removeItem(position);
    }

    // 서버에 리스트를 저장해볼까요
    private void saveListToServer(ArrayList<WordItem> wordItems) {

        StringBuilder builder = new StringBuilder();
        for (WordItem item : wordItems) {
            if (!builder.toString().isEmpty()) builder.append("|");
            builder.append(item.word);
        }

        mReference.child("users").child("karrel").child("words").child(getTime()).setValue(builder.toString());
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

    public String getTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date(System.currentTimeMillis()));
    }
}
