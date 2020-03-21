package com.ahmednafe3.photoweather.base.view;

import androidx.annotation.StringRes;

public interface BaseView {
    void showLoading();

    void hideLoading();

    void onError(@StringRes int resId);

    void onError(String message);

    void showMessage(String message);

    void showMessage(@StringRes int resId);

    void hideKeyboard();

}
