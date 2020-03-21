package com.ahmednafe3.photoweather.utils;

import android.app.Activity;
import android.graphics.Matrix;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.view.ViewGroup;

import androidx.camera.core.CameraX;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureConfig;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;



public class CameraHelper {
    private Activity activity;
    private int screenWidth, screenHight;
    private TextureView textureView;
    private Preview preview;

    public CameraHelper(Activity activity, TextureView view) {
        this.activity = activity;
        this.textureView = view;
        this.screenWidth = view.getMeasuredWidth();
        this.screenHight = view.getMeasuredHeight();
    }

    public ImageCapture startCamera() {
        CameraX.unbindAll();
        Size screen = new Size(this.screenWidth, this.screenHight);
        PreviewConfig pConfig = new PreviewConfig.Builder().setTargetResolution(screen).build();
        preview = new Preview(pConfig);
        preview.setOnPreviewOutputUpdateListener(
                new Preview.OnPreviewOutputUpdateListener() {
                    //to update the surface texture we  have to destroy it first then re-add it
                    @Override
                    public void onUpdated(Preview.PreviewOutput output) {
                        ViewGroup parent = (ViewGroup) textureView.getParent();
                        parent.removeView(textureView);
                        parent.addView(textureView, 0);

                        textureView.setSurfaceTexture(output.getSurfaceTexture());
                        textureView.setTransform(updateTransform(
                                textureView.getMeasuredWidth(),
                                textureView.getMeasuredHeight(),
                                textureView.getRotation()));

                    }
                });
        ImageCaptureConfig imageCaptureConfig = new ImageCaptureConfig.Builder().setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
                .setTargetRotation(activity.getWindowManager().getDefaultDisplay().getRotation()).build();
        return new ImageCapture(imageCaptureConfig);

    }


    public Matrix updateTransform(float width, float hight, float rotat) {
        Matrix mx = new Matrix();

        float cX = width / 2f;
        float cY = hight / 2f;

        int rotationDgr = 0;
        int rotation = (int) rotat;
        switch (rotation) {
            case Surface.ROTATION_0:
                rotationDgr = 0;
                break;
            case Surface.ROTATION_90:
                rotationDgr = 90;
                break;
            case Surface.ROTATION_180:
                rotationDgr = 180;
                break;
            case Surface.ROTATION_270:
                rotationDgr = 270;
                break;
            default:
                break;
        }

        mx.postRotate((float) rotationDgr, cX, cY);
        return mx;
    }

    public Preview getPreview() {
        return preview;
    }


}
