package com.ahmednafe3.photoweather.base.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ahmednafe3.photoweather.utils.CommonUtil;

public abstract class BaseFragment extends Fragment implements BaseView {
    private BaseActivity mActivity;
    private ProgressDialog mProgressDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) context;
            this.mActivity = activity;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp(view);
    }

    @Override
    public void showLoading() {
        hideLoading();
        mProgressDialog = CommonUtil.showProgressDialog(getActivity());
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    @Override
    public void onError(int resId) {
        if (mActivity != null) {
            mActivity.onError(resId);
        }
    }

    @Override
    public void onError(String message) {
        if (mActivity != null) {
            mActivity.onError(message);
        }
    }

    @Override
    public void showMessage(String message) {
        if (mActivity != null) {
            mActivity.showMessage(message);
        }
    }

    @Override
    public void showMessage(int resId) {
        if (mActivity != null) {
            mActivity.showMessage(resId);
        }
    }

    @Override
    public void hideKeyboard() {
        if (mActivity != null) {
            mActivity.hideKeyboard();
        }
    }

    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }

    protected abstract void setUp(View view);


}
