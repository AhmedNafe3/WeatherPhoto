package com.ahmednafe3.photoweather.moduls.capture.data.local;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;

import com.ahmednafe3.photoweather.base.status.StatusResponse;

public interface CaptureLocalDataSource {
    LiveData<StatusResponse> saveImage(Bitmap bitmap);

}
