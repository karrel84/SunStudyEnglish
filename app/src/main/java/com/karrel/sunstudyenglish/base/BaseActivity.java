package com.karrel.sunstudyenglish.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by 이주영 on 2017-06-16.
 */

public class BaseActivity extends AppCompatActivity {
    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("TAG", "onCreate");
        mContext = this;
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        Log.e("TAG", "onPostCreate");

        parseExtra();
        loadOnce();
        reload();
    }

    /**
     * onParseExtra()를 호출한다.
     */
    final public void parseExtra() {
        try {
            onParseExtra();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 해당 메소드에서 액티비티가 호출될때에 전달된 extra를 파싱한다.
     */
    protected void onParseExtra() {
    }

    /**
     * onLoad()를 호출한다.
     */
    final public void loadOnce() {
        onLoadOnce();
    }

    /**
     * 액티비티가 실행되고 ui이 가능한 상태에 호출된다.<br>
     * ui작업과 인스턴스 생성등을 해당 메소드에서 한다.
     */
    protected void onLoadOnce() {
    }

    /**
     * onReload()를 호출한다.
     */
    final public void reload() {
        onReload();
    }

    /**
     * 통신이 재요청될때 호출된다.
     */
    protected void onReload() {
        clear();
        load();
    }

    /**
     * onClear()를 호출한다.
     */
    final public void clear() {
        try {
            onClear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 요청된 통신으로 받은 데이터를 clear해줘야 한다.
     */
    protected void onClear() {
    }

    /**
     * 통신 요청이 가능한 상태일때 onLoad()를 호출한다.
     */
    final public void load() {

        try {
            onLoad();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 해당 메소드내에서 화면에 출력할 전문을 요청한다.
     */
    protected void onLoad() {
    }
}
