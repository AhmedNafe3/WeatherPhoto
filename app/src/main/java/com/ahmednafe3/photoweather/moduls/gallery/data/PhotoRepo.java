package com.ahmednafe3.photoweather.moduls.gallery.data;

import androidx.lifecycle.LiveData;

import com.ahmednafe3.photoweather.base.status.StatusResponse;
import com.ahmednafe3.photoweather.moduls.gallery.data.local.PhotoLocalDataSource;
import com.ahmednafe3.photoweather.moduls.gallery.data.model.PhotoModel;

import java.util.List;

public class PhotoRepo implements PhotoLocalDataSource {
    PhotoLocalDataSource photoLocalDataSource;

    public PhotoRepo(PhotoLocalDataSource photoLocalDataSource) {
        this.photoLocalDataSource = photoLocalDataSource;
    }

    @Override
    public LiveData<StatusResponse<List<PhotoModel>>> getAllphoto(String path) {
        return photoLocalDataSource.getAllphoto(path);
    }
}
