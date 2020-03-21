package com.ahmednafe3.photoweather.moduls.capture.view;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;

import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;


import android.provider.MediaStore;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import com.ahmednafe3.photoweather.BuildConfig;
import com.ahmednafe3.photoweather.R;
import com.ahmednafe3.photoweather.base.view.BaseFragment;
import com.ahmednafe3.photoweather.databinding.FragmentCaptchPhotoBinding;
import com.ahmednafe3.photoweather.moduls.home.HomeViewModel;
import com.ahmednafe3.photoweather.utils.CommonUtil;
import com.ahmednafe3.photoweather.utils.ImageOverlayHelper;

import java.io.File;

import java.io.IOException;


import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class CapturePhotoFragment extends BaseFragment {
    private FragmentCaptchPhotoBinding binding;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageOverlayHelper imageOverlayHelper;
    private HomeViewModel model;
    private double currentTimp;
    private String city;

    public CapturePhotoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_captch_photo, container, false);
        binding = DataBindingUtil.bind(v);
        binding.cameraxButton.setOnClickListener(v1 -> {
            Navigation.findNavController(v1).navigate(R.id.camerxFragment);
        });
        imageOverlayHelper = ImageOverlayHelper.getInstance();
        binding.intentCameraButton.setOnClickListener(v2 -> {
            try {
                File f = imageOverlayHelper.setUpPhotoFile();
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (Build.VERSION.SDK_INT >= 24) {
                    Uri uri = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".provider", f);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        binding.shareButton.setOnClickListener(v3 -> {
            CommonUtil.share(imageOverlayHelper.getmCurrentPhotoPath(), getActivity());
        });
        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (getArguments() != null)
            if (getArguments().getBoolean("isCapture"))
                showImage();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            showImage();
        }
    }


    private void showImage() {
        if (imageOverlayHelper.getmCurrentPhotoPath() != null) {
            Bitmap bitmap = imageOverlayHelper.addOverlayToImage(imageOverlayHelper.getmCurrentPhotoPath(), city + String.valueOf(currentTimp));
            binding.capturedImage.setImageBitmap(bitmap);
            imageOverlayHelper.store_image(bitmap);
        }
    }


    @Override
    protected void setUp(View view) {
        model = new ViewModelProvider(getActivity()).get(HomeViewModel.class);
        model.getCurrentTimp().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                currentTimp = aDouble;
            }
        });


        model.getCurrentCity().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                city = s;
            }
        });
    }
}
