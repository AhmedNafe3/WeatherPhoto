package com.ahmednafe3.photoweather.moduls.gallery.data.local;

import androidx.lifecycle.LiveData;

import com.ahmednafe3.photoweather.base.status.StatusResponse;
import com.ahmednafe3.photoweather.moduls.gallery.data.model.PhotoModel;

import java.util.List;

public interface PhotoLocalDataSource {
    LiveData<StatusResponse<List<PhotoModel>>> getAllphoto(String path);

}
