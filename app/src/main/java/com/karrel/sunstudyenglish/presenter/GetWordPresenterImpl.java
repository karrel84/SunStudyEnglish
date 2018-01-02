package com.karrel.sunstudyenglish.presenter;

import android.support.v4.util.Pair;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.karrel.mylibrary.RLog;
import com.karrel.sunstudyenglish.model.WordItem;
import com.karrel.sunstudyenglish.base.util.EnglishToKorean;

import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.regex.Pattern;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

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

        // 단어를 번역하는 자업을 한다.
        translateWords(wordsList);
    }

    @Override
    public void onLoadOnce() {

    }

    /**
     * 단어를 번역하는 자업을 한다.
     *
     * @param wordsList
     */
    private void translateWords(ArrayList<String> wordsList) {
        if(!wordsList.isEmpty()){
            mView.hideEmptyText();
        }

        mWordItems.clear();

        // 큐에 넣고 작업하는건 어떨까?
        Queue<List<String>> queue = new ArrayDeque<>();
        queue.add(wordsList);


        // 최대 사이즈의 값이 2의 4승보다 작으면은 스레드 16개를 돌릴필요가 없으므로 최대 2의 제곱만큼 돌린다.
        int size = (int) (Math.log(wordsList.size()) / Math.log(2));
        RLog.e("size : " + size);
        if (size > 4) size = 4;

        // 2의 제곱만큼 돌린다. 그러면 큐에 최종 4개가 담겨야한다.
        for (int j = 0; j < size; j++) {
            // 루프를 돌기전에 사이즈를 확인한다 for문에서 확인하면 사이즈가 변하기 때문
            final int queueSize = queue.size();
            for (int i = 0; i < queueSize; i++) {
                List<String> list = queue.poll();
                Pair<List<String>, List<String>> pair = halfList(list);
                // 담은 리스트를 루프를 돌려서 다시 반으로 담아서 어딘가에 담는다.
                queue.add(pair.first);
                queue.add(pair.second);
            }
        }

        // 병렬로 작업을한다.
        while (!queue.isEmpty()) {
            getWord(queue.poll())
                    .subscribeOn(Schedulers.io()) // 작업은 비동기 스레드에서
                    .observeOn(AndroidSchedulers.mainThread()) // UI작업은 메인스레드에서
                    .subscribe(new Subscriber<WordItem>() {
                        @Override
                        public void onCompleted() {
//                        mView.hideProgress();
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
    }

    /**
     * 받은 리스트의 반을 리턴한다.
     *
     * @param list
     * @return
     */
    public Pair<List<String>, List<String>> halfList(List<String> list) {
        System.out.println("called halfList");
        int size = list.size();
        int halfCnt = size / 2;

        return Pair.create(list.subList(0, halfCnt), list.subList(halfCnt, size));
    }

    private void addWordItem(WordItem item) {
        mReference.child("words").child(item.word).setValue(item);
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

    @Override
    public void saveList() {
        if (mWordItems.isEmpty()) {
            return;
        }

        saveListToServer(mWordItems);
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

    private Observable<WordItem> getWord(List<String> wordsList) {
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
