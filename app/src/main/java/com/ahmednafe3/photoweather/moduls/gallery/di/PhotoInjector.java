package com.ahmednafe3.photoweather.moduls.gallery.di;

import com.ahmednafe3.photoweather.moduls.gallery.data.PhotoRepo;
import com.ahmednafe3.photoweather.moduls.gallery.data.local.PhotoLocalDataSource;
import com.ahmednafe3.photoweather.moduls.gallery.data.local.PhotoLocalDataSourceImpl;

public class PhotoInjector {
    public static PhotoLocalDataSource providePhotoLocalDataSource() {
        return PhotoLocalDataSourceImpl.getInstance();
    }

    public static PhotoRepo providePhotoRepo() {
        return new PhotoRepo(providePhotoLocalDataSource());
    }
}
