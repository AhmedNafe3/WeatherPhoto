package com.ahmednafe3.photoweather.moduls.capture.data;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;

import com.ahmednafe3.photoweather.base.status.StatusResponse;
import com.ahmednafe3.photoweather.moduls.capture.data.local.CaptureLocalDataSource;

public class CaptureRepo implements CaptureLocalDataSource {
    @Override
    public LiveData<StatusResponse> saveImage(Bitmap bitmap) {
        return null;
    }
}
