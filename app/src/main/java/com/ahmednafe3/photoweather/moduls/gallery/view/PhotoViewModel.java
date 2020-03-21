package com.ahmednafe3.photoweather.moduls.gallery.view;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ahmednafe3.photoweather.base.status.StatusResponse;
import com.ahmednafe3.photoweather.moduls.gallery.data.PhotoRepo;
import com.ahmednafe3.photoweather.moduls.gallery.data.model.PhotoModel;
import com.ahmednafe3.photoweather.moduls.gallery.di.PhotoInjector;

import java.util.List;

public class PhotoViewModel extends ViewModel {
    PhotoRepo repo;

    public PhotoViewModel() {
        this.repo = PhotoInjector.providePhotoRepo();
    }

    public LiveData<StatusResponse<List<PhotoModel>>> getAllPhotos(String path) {
        return repo.getAllphoto(path);
    }
}
