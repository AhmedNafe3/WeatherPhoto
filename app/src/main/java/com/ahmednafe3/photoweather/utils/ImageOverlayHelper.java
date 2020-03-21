package com.ahmednafe3.photoweather.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.ahmednafe3.photoweather.PhotoWeatherApp;
import com.ahmednafe3.photoweather.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageOverlayHelper {
    private Context context;
    private static final String CAMERA_DIR = "/dcim/";
    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";
    public String mCurrentPhotoPath;

    private static volatile ImageOverlayHelper INSTANCE;


    public static ImageOverlayHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ImageOverlayHelper();
        }
        return INSTANCE;
    }


    private ImageOverlayHelper() {
        this.context = PhotoWeatherApp.getAppContext();
    }

    public String getmCurrentPhotoPath() {
        return mCurrentPhotoPath;
    }

    public void setmCurrentPhotoPath(String mCurrentPhotoPath) {
        this.mCurrentPhotoPath = mCurrentPhotoPath;
    }

    private File getAlbumDir() {
        File storageDir = null;

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            storageDir = getAlbumStorageDir(getAlbumName());

            if (storageDir != null) {
                if (!storageDir.mkdirs()) {
                    if (!storageDir.exists()) {
                        Log.d("CameraSample", "failed to create directory");
                        return null;
                    }
                }
            }
        }

        return storageDir;
    }

    private File getAlbumStorageDir(String albumName) {
        return new File(
                Environment.getExternalStorageDirectory()
                        + CAMERA_DIR
                        + albumName
        );
    }

    public String getFolderPath() {
        return Environment.getExternalStorageDirectory()
                + CAMERA_DIR
                + getAlbumName();
    }

    private String getAlbumName() {
        return context.getString(R.string.app_name);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
        File albumF = getAlbumDir();
        File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
        return imageF;
    }

    public File setUpPhotoFile() throws IOException {
        File f = createImageFile();
        setmCurrentPhotoPath(f.getAbsolutePath());
        return f;
    }

    public void galleryAddPic() {
        Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }


    public Bitmap addOverlayToImage(String filePath, String overlayText) {

        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        bitmap = bitmap.copy(Bitmap.Config.RGB_565, true);


        Canvas canvas = new Canvas(bitmap);
        // Draw overlay:
        Paint paint = new Paint();

        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, bitmap.getWidth(), bitmap.getHeight() / 9, paint);
        paint.setAntiAlias(true);
        paint.setARGB(0xff, 0x00, 0x00, 0x00);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setColor(Color.BLACK);
        paint.setTextSize(90);
        float textWidth = paint.measureText(overlayText);

        canvas.drawText(overlayText, (bitmap.getWidth() - textWidth) / 2, 90, paint);

        galleryAddPic();
        return bitmap;
    }

    public void store_image(Bitmap bmp) {
        File f = new File(getmCurrentPhotoPath());
        try {
            FileOutputStream out = new FileOutputStream(f);
            bmp.compress(Bitmap.CompressFormat.JPEG, 80, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
