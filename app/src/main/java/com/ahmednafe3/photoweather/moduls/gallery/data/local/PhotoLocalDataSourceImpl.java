package com.ahmednafe3.photoweather.moduls.gallery.data.local;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ahmednafe3.photoweather.PhotoWeatherApp;
import com.ahmednafe3.photoweather.base.status.StatusResponse;
import com.ahmednafe3.photoweather.moduls.gallery.data.model.PhotoModel;

import java.util.ArrayList;
import java.util.List;

public class PhotoLocalDataSourceImpl implements PhotoLocalDataSource {
    private static volatile PhotoLocalDataSource INSTANCE;

    public static PhotoLocalDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PhotoLocalDataSourceImpl();
        }
        return INSTANCE;
    }

    @Override
    public LiveData<StatusResponse<List<PhotoModel>>> getAllphoto(String path) {
        MutableLiveData<StatusResponse<List<PhotoModel>>> responseMutableLiveData = new MutableLiveData<>();
        responseMutableLiveData.setValue(StatusResponse.loading());

        List<PhotoModel> photoModelArrayList = new ArrayList();
        Uri allVideosuri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.SIZE};
        Cursor cursor = PhotoWeatherApp.getAppContext().getContentResolver().query(allVideosuri, projection, MediaStore.Images.Media.DATA + " like ? ", new String[]{"%" + path + "%"}, null);
        try {
            cursor.moveToFirst();
            do {
                PhotoModel photo = new PhotoModel();
                photo.setPicturName(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)));
                photo.setPicturePath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)));
                photoModelArrayList.add(photo);
            } while (cursor.moveToNext());
            cursor.close();
            responseMutableLiveData.setValue(StatusResponse.success(photoModelArrayList));
        } catch (Exception e) {
            e.printStackTrace();
            responseMutableLiveData.setValue(StatusResponse.error(e.getMessage()));
        }
        return responseMutableLiveData;
    }
}
