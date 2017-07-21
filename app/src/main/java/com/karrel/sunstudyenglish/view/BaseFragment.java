package com.karrel.sunstudyenglish.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by rell on 2017-07-14.
 */

public class BaseFragment extends Fragment {
    protected Context mContext;
    protected ProgressDialog mProgressDlg;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity();
        debugSet();
        parseExtra();
        loadOnce();
        reload();
        updateUI();
    }


    /**
     * 디버깅할게있으면 여기서한다
     */
    private void debugSet() {
    }

    /**
     * 데이터를 전달받은 부분에 대한 파싱을 하자
     */
    final public void parseExtra() {
        try {
            onParseExtra();
        } catch (Exception e) {
        }
    }

    /**
     * 필요한 인스턴스를 만드는 작업을하자
     */
    final public void loadOnce() {
        // 프로그래스 다이얼로그를 만든다.
        createProgressDialog();
        onLoadOnce();
    }

    /**
     * 데이터를 다시 로드한다.
     */
    final public void reload() {
        onReload();
    }

    /**
     * 데이터를 삭제
     */
    final public void clear() {
        try {
            onClear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 데이터 로드
     */
    final public void load() {
        try {
            onLoad();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * UI작업을 하자
     */
    final public void updateUI() {
        try {
            onUpdateUI();
        } catch (Exception e) {
        }
    }

    protected void onParseExtra() {
    }

    protected void onLoadOnce() {
    }

    protected void onReload() {
        clear();
        load();
    }

    protected void onClear() {
    }

    protected void onLoad() {
    }

    protected void onUpdateUI() {
    }


    /**
     * 프로그래스 다이얼로그를 만든다.
     */
    protected void createProgressDialog() {
        mProgressDlg = new ProgressDialog(mContext);
        mProgressDlg.setProgress(ProgressDialog.STYLE_SPINNER);
        mProgressDlg.setCancelable(false);
    }

    /**
     * 프로그레스 다이얼로그를 보여준다.
     */
    protected final void showProgressDialog() {
        showProgressDialog("로딩중입니다..");
    }

    /**
     * 프로그래스 다이얼로그를 가린다.
     */
    protected final void hideProgressDialog() {
        mProgressDlg.hide();
    }

    protected final void showProgressDialog(String message) {
        mProgressDlg.setMessage(message);
        mProgressDlg.show();
    }
}
