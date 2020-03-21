package com.ahmednafe3.photoweather.moduls.capture.view;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageCapture;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ahmednafe3.photoweather.R;
import com.ahmednafe3.photoweather.base.view.BaseFragment;
import com.ahmednafe3.photoweather.databinding.FragmentCamerxBinding;
import com.ahmednafe3.photoweather.utils.CameraHelper;
import com.ahmednafe3.photoweather.utils.ImageOverlayHelper;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;


/**
 * A simple {@link Fragment} subclass.
 */
public class CamerxFragment extends BaseFragment {
    private ImageCapture imgCap;
    private FragmentCamerxBinding binding;
    private ImageOverlayHelper imageOverlayHelper;
    private CameraHelper cameraHelper;

    public CamerxFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_camerx, container, false);
        binding = DataBindingUtil.bind(v);
        return v;
    }

    @Override
    protected void setUp(View view) {
        setUpCamera();
        binding.cameraxButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageOverlayHelper = ImageOverlayHelper.getInstance();
                try {
                    File file = imageOverlayHelper.setUpPhotoFile();
                    imgCap.takePicture(file, Executors.newSingleThreadExecutor(), new ImageCapture.OnImageSavedListener() {
                        @Override
                        public void onImageSaved(@NonNull File file) {
                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    Bundle bundle = new Bundle();
                                    bundle.putBoolean("isCapture", true);
                                    Navigation.findNavController(v).popBackStack(R.id.camerxFragment, true);
                                    Navigation.findNavController(v).navigate(R.id.captchPhotoFragment, bundle);
                                }
                            });
                        }

                        @Override
                        public void onError(@NonNull ImageCapture.ImageCaptureError imageCaptureError, @NonNull String message, @Nullable Throwable cause) {
                            String msg = "Pic capture failed : " + message;
                            Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
                            if (cause != null) {
                                cause.printStackTrace();
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setUpCamera() {
        cameraHelper = new CameraHelper(getActivity(), binding.viewFinder);
        imgCap = cameraHelper.startCamera();
        CameraX.bindToLifecycle((LifecycleOwner) this, cameraHelper.getPreview(), imgCap);
    }
}
