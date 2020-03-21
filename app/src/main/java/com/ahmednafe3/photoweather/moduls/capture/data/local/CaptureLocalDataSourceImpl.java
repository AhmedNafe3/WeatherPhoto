package com.ahmednafe3.photoweather.moduls.capture.data.local;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ahmednafe3.photoweather.base.status.StatusResponse;


public class CaptureLocalDataSourceImpl implements CaptureLocalDataSource {

    private static volatile CaptureLocalDataSource INSTANCE;

    public static CaptureLocalDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CaptureLocalDataSourceImpl();
        }
        return INSTANCE;
    }


    @Override
    public LiveData<StatusResponse> saveImage(Bitmap bitmap) {
        MutableLiveData<StatusResponse> responseMutableLiveData = new MutableLiveData<>();
        responseMutableLiveData.setValue(StatusResponse.loading());

        return null;
    }
}
